#!/bin/sh

gcc cachelab-dzaug.c -lm -o cachelabProgram
echo "./cachelabProgram -m 64 -s 4 -e 0 -b 4 -i address01 -r lru"
./cachelabProgram -m 64 -s 4 -e 0 -b 4 -i address01 -r lru
rm cachelabProgram
