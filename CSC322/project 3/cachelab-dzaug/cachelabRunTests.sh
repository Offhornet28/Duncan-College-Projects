#!/bin/sh
#start of program
gcc cachelab-dzaug.c -lm -o cachelabProgram
#test 1
printf "\n<test 1>\n./cachelabProgram -m 64 -s 4 -e 0 -b 4 -i address01 -r lru\n"
./cachelabProgram -m 64 -s 4 -e 0 -b 4 -i address01 -r lru
#test 2
printf "\n<test 2>\n./cachelabProgram -m 64 -s 2 -e 0 -b 2 -i address03 -r lru\n"
./cachelabProgram -m 64 -s 2 -e 0 -b 2 -i address03 -r lru
#test 3
printf "\n<test 3>\n./cachelabProgram -m 64 -s 2 -e 1 -b 2 -i address03 -r lru\n"
./cachelabProgram -m 64 -s 2 -e 1 -b 2 -i address03 -r lru
#test 4
printf "\n<test 4>\n./cachelabProgram -m 64 -s 2 -e 1 -b 3 -i address02 -r lru\n"
./cachelabProgram -m 64 -s 2 -e 1 -b 3 -i address02 -r lru
#end of program
rm cachelabProgram
