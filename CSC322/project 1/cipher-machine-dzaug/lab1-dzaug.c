/*
 * Duncan Zaug
 * Project 1
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdint.h>
#include <limits.h>
#include <stdarg.h>
#include <stdbool.h>
#include <unistd.h>
#define MAX_LINE 2048

//This is the only good way to do it
int get_index(char* string, char c) {
    char *ptr = strchr(string, c);
    if (ptr == NULL) {
        return -1;
    }
    return (int)(ptr - string);
}

int main() {
    char *user;
    user = getlogin();
    char cmd[MAX_LINE];
    char input[MAX_LINE];
    char temp[MAX_LINE];
    int index;
    while (true) {
        printf("%s $ ", user);
        scanf(" %[^(\n](%[^\n]", cmd, input);
        //this is all for command checking
        if(strchr(input, ')') == NULL && strcmp(cmd, "exit") != 0) {
            memset(cmd, 0, sizeof(cmd));
            memset(input, 0, sizeof(input));
        } else {
            index = get_index(input, ')');
            strcpy(temp, input);
            memset(input, 0, sizeof(input));
            for(int j = 0; j < index; ++j) {
                input[j] = temp[j];
            }
            input[strlen(input) + 1] = '\0';
            if(strcmp(cmd, "unary") == 0 && strpbrk(input, "1234567890") == NULL) {
                memset(cmd, 0, sizeof(cmd));
                memset(input, 0, sizeof(input));
            }
        }
        //start of program functions
        if(strcmp(cmd, "encrypt") == 0 && strcmp(input, "") != 0) {
            //encrypt
            char output[strlen(input) + 1];
            for(int i = 0; i < strlen(input); ++i) {
                output[i] = input[i] + 5;
            }
            output[sizeof(output) - 1] = '\0';
            printf("%s\n", output);
            memset(cmd, 0, sizeof(cmd));
            memset(input, 0, sizeof(input));
        } else if(strcmp(cmd, "decrypt" ) == 0 && strcmp(input, "") != 0) {
            //decrypt
            char output[strlen(input) + 1];
            for(int i = 0; i < strlen(input); ++i) {
                output[i] = input[i] - 5;
            }
            output[sizeof(output) - 1] = '\0';
            printf("%s\n", output);
            memset(cmd, 0, sizeof(cmd));
            memset(input, 0, sizeof(input));
        } else if(strcmp(cmd, "unary" ) == 0 && strcmp(input, "") != 0) {
            //unary
            int convertedInput;
            sscanf(input, "%d", &convertedInput);
            char *output = calloc((convertedInput + 2), sizeof(char));
            int position = 0;
            while(position < convertedInput) {
                output[position] = '1';
                ++position;
            }
            output[convertedInput] = '0';
            output[convertedInput + 1] = '\0';
            printf("%s\n", output);
            free(output);
            memset(cmd, 0, sizeof(cmd));
            memset(input, 0, sizeof(input));
        } else if(strcmp(cmd, "exit") == 0) {
            //exit
            break;
        } else {
            printf("[ERROR] please enter valid command! \n");
            memset(cmd, 0, sizeof(cmd));
            memset(input, 0, sizeof(input));
        }
    }
    return 0;
}
