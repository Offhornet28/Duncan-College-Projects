/*
 * Duncan Zaug
 * cache-lab
 * 806107062
 */

#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <getopt.h>
#include <string.h>
#include <math.h>
#include <limits.h>
#include <stdbool.h>
#include "cachelab.h"
#define debug 0
//I will have a ton of comments on this in order to understand what this program does if I ever come back to it.
// print result of cache simulation showing hit number, miss number, miss rate, and total running time
void printResult(int hits, int misses, int missRate, int runTime) {
    printf("[result] hits:%d misses:%d miss rate: %d%% total running time: %d cycle(s)\n",hits, misses, missRate, runTime);
}
//cache line struct
struct cache_line {
    int bit;
    unsigned long int tag;
    int lru;
};

// main function
int main(int argc, char** argv) {
    //command line reading
    int opt,m,s,e,b;
    char *i;
    char *r;
    while (-1 != (opt = getopt(argc, argv, "m:s:e:b:i:r:"))) {
        switch(opt) {
            case 'm':
                m = atoi(optarg);
                break;
            case 's' :
                s = atoi(optarg);
                break;
            case 'e':
                e = atoi(optarg);
                break;
            case 'b' :
                b = atoi(optarg);
                break;
            case 'i':
                i = optarg;
                break;
            case 'r' :
                r = optarg;
                break;
            default:
                printf("wrong argument\n") ;
                break;
        }
    }
    /* What the parameters mean:
     * m: The size of the address used in the catch in bits
     * s: The number of index bit
     * e: The number of line bits
     * b: The size of block bits
     * i: A set of address names in hexadecimal contained within a file
     * r: Page Replacement Algorithm - FIFO(optional)/Optimal(optional)/LRU(required)
     */
    //debug code
    #if debug
    char cwd[PATH_MAX];
    if (getcwd(cwd, sizeof(cwd)) != NULL) {
        printf("Current working dir: %s\n", cwd);
    } else {
        perror("getcwd() error");
        return 1;
    }
    printf("m = %d\n", m);
    printf("s = %d\n", s);
    printf("e = %d\n", e);
    printf("b = %d\n", b);
    printf("i = %s\n", i);
    printf("r = %s\n", r);
    #endif
    //program code
    unsigned long int S = pow(2, s); //number of sets
    unsigned long int E = pow(2, e); //number of lines
    //setting up cache
    struct cache_line **cache;
    cache = (struct cache_line **)malloc(sizeof(struct cache_line *) * S);
    for (int idx = 0; idx < S; idx++) {
        cache[idx] = (struct cache_line *)malloc(sizeof(struct cache_line) * E);
    }
    //initializing all values to zero
    for(int n = 0; n < S; n++) {
        for(int j = 0; j < E; j++) {
            cache[n][j].bit = 0;
            cache[n][j].tag = 0;
            cache[n][j].lru = 0;
        }
    }
    //opening a file
    FILE* fp;
    fp = fopen(i, "r");
    if(fp == NULL) {
        perror("Error opening file");
        return 1;
    }
    //variable declaration
    unsigned long long address;
    double maxAddressValue = pow(2, m); //due to type conversion nonsense this has to be double even if I subtract 1 from it
    int hits = 0, misses = 0, clock = 0;
    //actual start of cache reading and writing
    while(fscanf(fp, "%llx", &address)>0) {
        printf("%llX", address);
        //variable declaration
        bool found = false, val = false;
        unsigned long long tag, index;
        //bit shifting madness
        index = ((address >> b) & (S-1));
        tag = (address >> (s+b));
        //lru
        if(strcasecmp(r, "lru") == 0) {
            //checking address size
            if((double)address < maxAddressValue) {
                //actually going through the cache
                for (int idx = 0; idx < E; idx++) {
                    //checking for validity
                    if(cache[index][idx].bit == 1) {
                        //tag comparison
                        if(cache[index][idx].tag == tag) {
                            //say that we have found it
                            cache[index][idx].lru = clock++;
                            found = true;
                            break;
                        }
                    } else {
                        //found a non-valid so insert it here
                        val = true;
                        cache[index][idx].bit = 1;
                        cache[index][idx].tag = tag;
                        cache[index][idx].lru = clock++;
                        break;
                    }
                }
                //no data was found and no insertion happened
                //find the lowest lru and replace it
                if(found == false && val == false) {
                    int j = 0;
                    for (int idx = 1; idx < E; idx++) {
                        if (cache[index][j].lru > cache[index][idx].lru) {
                            j = idx;
                        }
                    }
                    cache[index][j].bit = 1;
                    cache[index][j].tag = tag;
                    cache[index][j].lru = clock++;
                }
            }
        } else {
            perror("unknown page replacement algorithm");
            return 1;
            break;
        }
        //calculating hits and misses
        if (found == true) {
            hits++;
            printf(" H\n");
        } else {
            misses++;
            printf(" M\n");
        }
    }
    //calculating and printing results
    double missRate = (double)misses / (misses + hits);
    double averageAccessTime = HIT_TIME + (missRate * MISS_PENALTY);
    double totalRunningTime = (misses + hits) * averageAccessTime;
    printResult(hits, misses, (int)(missRate * 100), (int)totalRunningTime);
    //freeing and closing pointers/files
    fclose(fp);
    for(int idx = 0; idx < S; idx++) {
        free(cache[idx]);
    }
    free(cache);
    return 0;
}
