# Duncan Zaug
# Microproject 5

import subprocess


def main():
    uInput = input("enter directory name with path: ")
    rawlsResult = subprocess.run('ls -d -p "$PWD/"* | grep -v "/$"', shell=True, cwd=uInput, stdout=subprocess.PIPE)
    lsResult = rawlsResult.stdout.decode('utf-8')
    fileList = lsResult.splitlines()
    for filePath in fileList:
        wcOutRaw = subprocess.run("wc -l < '" + filePath + "' | tr -d '\n'", shell=True, stdout=subprocess.PIPE)
        wcOut = wcOutRaw.stdout.decode('utf-8')
        rawFileName = filePath.split('/')
        fileName = rawFileName[-1]
        print(fileName + " " + wcOut)


if __name__ == "__main__":
    main()
