# Duncan Zaug
# 806107062
# Project 5: Website Creator

import subprocess
import re
import os
import tarfile

# path will always be ~/csc344 a.k.a running in the home directory


def main():
    os.chdir('csc344')
    # debug code
    print("current working directory:", os.getcwd(), "\n")
    # back to the actual code
    rawlsResult = subprocess.run('cd ~/csc344 && ls -d -p * */* | grep -v "/$\|.html$\|.css$\|.tar.gz$\|~$"', shell=True, stdout=subprocess.PIPE)
    lsResult = rawlsResult.stdout.decode('utf-8')
    fileList = lsResult.splitlines()
    for rawProgramFilePath in fileList:
        programFilePath = rawProgramFilePath.split('/')
        programFolderName = programFilePath[0]
        programFileName = programFilePath[-1]
        programFile = open(rawProgramFilePath, 'r')
        programFileData = programFile.read()
        rawLineCount = subprocess.run("wc -l < ~/csc344/ " + rawProgramFilePath + " | tr -d '\n'", shell=True, stdout=subprocess.PIPE)
        lineCount = ((rawLineCount.stdout.decode('utf-8')).split(" "))[0]
        htmlProgramFilePath = programFilePath[0] + "/summary_" + programFolderName + ".html"
        htmlProgramFile = open(htmlProgramFilePath, 'w')
        program_html_text = f"""
        <html>
            <head>
                <title>{programFileName}</title>
                <link href="../CSC344.css" rel="stylesheet" type="text/css" media="screen" />
            </head>
            
            <body>
                <div class="block">
                    <h1>{programFileName}</h1>
                </div>
                
                <div class="block">
                    <p> <p class="activity"> <b>Project {programFolderName} Summary</b> </p>
                    <p> <a href="{programFileName}">{programFileName}</a> </p>
                    <p> line count: {lineCount} </p>
                    
            """
        if programFolderName == 'a1':
            # C program
            summaryDetails = cProgramMatcher(programFileData)
            for detail in summaryDetails:
                if detail is not None: program_html_text += f'<p>{detail}</p>'
        elif programFolderName == "a2":
            # Clojure program
            summaryDetails = clojureProgramMatcher(programFileData)
            for detail in summaryDetails:
                if detail is not None: program_html_text += f'<p>{detail}</p>'
        elif programFolderName == "a3":
            # OCaml program
            summaryDetails = ocamlProgramMatcher(programFileData)
            for detail in summaryDetails:
                if detail is not None: program_html_text += f'<p>{detail}</p>'
        elif programFolderName == "a4":
            # ASP program
            summaryDetails = aspProgramMatcher(programFileData)
            for detail in summaryDetails:
                if detail is not None: program_html_text += f'<p>{detail}</p>'
        elif programFolderName == "a5":
            # Python program
            summaryDetails = pythonProgramMatcher(programFileData)
            for detail in summaryDetails:
                if detail is not None: program_html_text += f'<p>{detail}</p>'
        else:
            print('error\n')
        program_html_text += '</div>\n</body>\n</html>'
        htmlProgramFile.write(program_html_text)
        # closing files
        htmlProgramFile.close()
        programFile.close()
    # index.html file
    htmlMainFile = open('index.html', 'w')
    main_html_text = f"""
    <html>
        <head>
            <title>CSC344 Project Directory Site</title>
            <link href="CSC344.css" rel="stylesheet" type="text/css" media="screen" />
        </head>
        
        <body>
            <div class="block">
                <h1>Duncan's CSC344 Project Directory</h1>
                <p>This is the site where I will host all my CSC344 projects</p> 
            </div>
            
            <div class="block">
                <h2>Projects</h2>
                
                <p>
                    <p class="activity"> <b>Lab1: Turing Machine in C</b>
                    <a href="{((fileList[0]).split('/'))[0]+"/summary_"+((fileList[0]).split('/'))[0]+".html"}">Turing Machine Summary</a>
                </p>
                
                <p>
                    <p class="activity"> <b>Lab2: Natural Inference in Clojure</b>
                    <a href="{((fileList[1]).split('/'))[0]+"/summary_"+((fileList[1]).split('/'))[0]+".html"}">Natural Inference Summary</a>
                </p>
                
                <p>
                    <p class="activity"> <b>Lab3: String Matcher in Ocaml</b>
                    <a href="{((fileList[2]).split('/'))[0]+"/summary_"+((fileList[2]).split('/'))[0]+".html"}">String Matcher Summary</a>
                </p>
                
                <p>
                    <p class="activity"> <b>Lab4: Social Distancing Simulator in ASP</b>
                    <a href="{((fileList[3]).split('/'))[0]+"/summary_"+((fileList[3]).split('/'))[0]+".html"}">Social Distancing Simulator Summary</a>
                </p>
                
                <p>
                    <p class="activity"> <b>Lab5: Website Creator in Python</b>
                    <a href="{((fileList[-1]).split('/'))[0]+"/summary_"+((fileList[-1]).split('/'))[0]+".html"}">Website Creator Summary</a>
                </p>
                
            </div>
        </body>
    </html>
    """
    htmlMainFile.write(main_html_text)
    htmlMainFile.close()
    # tar file time
    rawlsResult = subprocess.run('cd ~/csc344 && ls -d -p * */* | grep -v "/$\|.tar.gz$\|~$"', shell=True, stdout=subprocess.PIPE)
    lsResult = rawlsResult.stdout.decode('utf-8')
    fileList = lsResult.splitlines()
    tarfileName = "csc344.tar.gz"
    tarFile = tarfile.open(tarfileName, 'w')
    for file in fileList:
        tarFile.add(file)
    tarFile.close()
    # mail sending time
    textFileName = 'emailBody.txt'
    textFile = open(textFileName, 'w')
    textToWrite = "These are my project files\n\n-Duncan Zaug"
    textFile.write(textToWrite)
    textFile.close()
    email = input("enter your email: ")
    muttCommand = f"mutt -s 'Duncan Zaug Project Files' {email} -a {tarfileName} < {textFileName}"
    subprocess.run(muttCommand, shell=True)
    removeCommand = f'rm {textFileName}'
    subprocess.run(removeCommand, shell=True)
    print("\ndone\n")


def cProgramMatcher(fileData):
    structMatcher = re.compile(r'^((struct) (\S+)) [{]$', re.MULTILINE)
    structVariableMatcher = re.compile(r'^(?: {0,5})*((struct \S+ \w+)(?: =)* *(.+)*);$', re.MULTILINE)
    variableMatcher = re.compile(r'^(?: {0,5})*(((int|char) (\w+))(?: =)* *(.+)*);$', re.MULTILINE)
    functionMatcher = re.compile(r'^((void|int|char|struct \S+) (\S+[(](...)*[)])) *[{]$', re.MULTILINE)
    returnSet = set()
    for line in fileData.split('\n'):
        matchedStruct = structMatcher.match(line)
        matchedStructVariable = structVariableMatcher.match(line)
        matchedVariable = variableMatcher.match(line)
        matchedFunction = functionMatcher.match(line)
        if matchedStruct: returnSet.add(matchedStruct.group(1))
        if matchedFunction: returnSet.add(matchedFunction.group(1))
        if matchedStructVariable: returnSet.add(matchedStructVariable.group(1))
        if matchedVariable: returnSet.add(matchedVariable.group(1))
    return returnSet


def clojureProgramMatcher(fileData):
    functionMatcher = re.compile(r'^[(]defn (\S+)$', re.MULTILINE)
    variableMatcher = re.compile(r'^ {0,5}\[(\S+) *((\S+){1,100})*\]$', re.MULTILINE)
    returnSet = set()
    for line in fileData.split('\n'):
        matchedFunction = functionMatcher.match(line)
        matchedVariable = variableMatcher.match(line)
        if matchedFunction:
            returnSet.add(matchedFunction.group(1))
        if matchedVariable:
            for group in matchedVariable.groups(): returnSet.add(group)
    return returnSet


def ocamlProgramMatcher(fileData):
    typeMatcher = re.compile(r'^(type (\w+)) = (\S+) of (char|int)\s*$', re.MULTILINE)
    patternMatcher = re.compile(r'^ {2,5}[| ]*(?!\[)(\w+)(?!\])$', re.MULTILINE)
    globalVariableMatcher = re.compile(r'^let ((\w+) = ref (\S+));;$', re.MULTILINE)
    variableMatcher = re.compile(r'^let (\w+) = (?!ref )(.+)$', re.MULTILINE)
    functionMatcher = re.compile(r'^(?:let|and)(?: rec)* ((\w+) (.+))= *$', re.MULTILINE)
    returnSet = set()
    for line in fileData.split('\n'):
        matchedType = typeMatcher.match(line)
        matchedPattern = patternMatcher.match(line)
        matchedVariable = variableMatcher.match(line)
        matchedGlobalVariable = globalVariableMatcher.match(line)
        matchedFunction = functionMatcher.match(line)
        if matchedType:
            returnSet.add(matchedType.group(1))
            if matchedType.group(3) != 'C': returnSet.add(matchedType.group(3))
        if matchedPattern:
            pattern = matchedPattern.group(1)
            if pattern != 'else' and pattern != 'if' and pattern != 'in' and pattern != 'a1' and pattern != 're':
                returnSet.add(matchedPattern.group(1))
        if matchedVariable: returnSet.add(matchedVariable.group(1) + ' = ' + matchedVariable.group(2))
        if matchedGlobalVariable: returnSet.add(matchedGlobalVariable.group(1))
        if matchedFunction: returnSet.add(matchedFunction.group(1))
    return returnSet


def aspProgramMatcher(fileData):
    constMatcher = re.compile(r'^#const ((\w+?)=(\w+))\. *$', re.MULTILINE)
    factMatcher = re.compile(r'^((\w+)\((\S+)\))\. *$', re.MULTILINE)
    ruleMatcher = re.compile(r'^\{*(\w+\(\S+\)) *:-* (.+)\. *$', re.MULTILINE)
    returnSet = set()
    for line in fileData.split('\n'):
        matchedConst = constMatcher.match(line)
        matchedFact = factMatcher.match(line)
        matchedRule = ruleMatcher.match(line)
        if matchedConst: returnSet.add(matchedConst.group(1))
        if matchedFact: returnSet.add(matchedFact.group(1))
        if matchedRule: returnSet.add(matchedRule.group(1))
    return returnSet


def pythonProgramMatcher(fileData):
    functionMatcher = re.compile(r'^def ((\w+)\((\w+)*\)): *$', re.MULTILINE)
    variableMatcher = re.compile(r'^(?: {0,20})* ((\w+) = (.+)) *$', re.MULTILINE)
    returnSet = set()
    for line in fileData.split('\n'):
        matchedFunction = functionMatcher.match(line)
        matchedVariable = variableMatcher.match(line)
        if matchedFunction: returnSet.add(matchedFunction.group(1))
        if matchedVariable:
            if matchedVariable.group(3) != 'f"""':
                if len(matchedVariable.group(3)) <= 38:
                    returnSet.add(matchedVariable.group(1))
    return returnSet


if __name__ == "__main__":
    main()
