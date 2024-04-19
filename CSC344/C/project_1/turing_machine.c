/*
 * Name: Duncan Zaug
 * Project 1: Turing Machine
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <limits.h>
#include <stdarg.h>
#include <stdbool.h>
#include <unistd.h>
#define FILENAME_SIZE 1024
#define MAX_LINE 2048
//linked list
struct cell {
    char data;
    struct cell *next;
    struct cell *prev;
};
struct cell* head;
struct cell* GetNewCell(char x) {
    struct cell* newCell = (struct cell*)malloc(sizeof(struct cell));
    newCell->data = x;
    newCell->prev = NULL;
    newCell->next = NULL;
    return newCell;
}
void InsertAtHead(char x) {
    struct cell* newCell = GetNewCell(x);
    if(head == NULL) {
        head = newCell;
        return;
    }
    head->prev = newCell;
    newCell->next = head;
    head = newCell;
}
void InsertAtEnd(char x) {
    struct cell* temp = head;
    struct cell* newCell = GetNewCell(x);
    if(head == NULL) {
        head = newCell;
        return;
    }
    while(temp->next != NULL) {
        temp = temp->next;
    }
    temp->next = newCell;
    newCell->prev = temp;
}
void Insert(char x, int pos){
     struct cell* temp = head;
     struct cell* next;
     struct cell* curr = GetNewCell(x);
     if(pos == 0){
        head = temp;
        return;
     }
     int i;
     for (i = 0; i < pos-1; i++){
         temp = temp->next;
     }
     if(temp->next == NULL) {
         temp->next = curr;
         curr->prev = temp;
         return;
     }
     next = temp->next;
     curr->next = temp->next;
     curr->prev = temp;
     temp->next = curr;
     next->prev = curr;
}
void DeleteAtHead() {
    struct cell* temp = head;
    head = temp->next;
    free(temp);
}
void DeleteAtEnd() {
    struct cell* temp = head;
    struct cell* curr;
    if(head == NULL) {
        free(head);
        return;
    }
    while(temp->next !=NULL) {
        temp = temp->next;
    }
    curr = temp->prev;
    curr->next = NULL;
    free(temp);
}
void Delete(int pos) {
    struct cell* temp = head;
    struct cell* curr;
    struct cell* next;
    if(pos == 0) {
        head = temp->next;
        free(temp);
        return;
    }
    int i;
    for (i = 0; i < pos-1; i++){
        temp = temp->next;
    }
    if((temp->next)->next == NULL) {
        temp = temp->next;
        curr = temp->prev;
        curr->next = NULL;
        free(temp);
        return;
    }
    curr = temp->next;
    next = curr->next;
    temp->next=curr->next;
    next->prev=curr->prev;
    free(curr);
}
void Print() {
    struct cell* temp = head;
    while(temp != NULL) {
        printf("%c", temp->data);
        temp = temp->next;
    }
}
char ReadAt(int pos) {
    struct cell* temp = head;
    char returnVal;
    int i = 1;
    while(i <= pos) {
        temp = temp->next;
        ++i;
    }
    if(temp == NULL) {
        while(temp->prev != NULL) {
            temp = temp->prev;
        }
        return '\0';
    }
    returnVal = temp->data;
    while(temp->prev != NULL) {
        temp = temp->prev;
    }
    return returnVal;
}
//instruction set
struct instruction {
    char writeVal;
    char moveDirection;
    int newState;
};
int main() {
    //code for file path checking for debug reasons
    char cwd[PATH_MAX];
    if (getcwd(cwd, sizeof(cwd)) != NULL) {
        printf("Current working dir: %s\n", cwd);
    } else {
        perror("getcwd() error");
        return 1;
    }
    //actual start of the program
    char fInput[FILENAME_SIZE];
    printf("Input File: ");
    scanf("%s", fInput);
    FILE* fp;
    fp = fopen(fInput, "r");
    if(fp == NULL) {
        perror("Error opening file");
        return 1;
    }
    printf("\nreading file");
    //variable declaration
    char tempTape[MAX_LINE];
    char initialTape[MAX_LINE] = "A";
    char buffer[MAX_LINE];
    int numStates, startState, endState;
    int counter = 1;
    //loop to get initial values
    while(fgets(buffer, MAX_LINE, fp)) {
        if(counter == 1) {
            strcpy(tempTape, buffer);
            strcat(initialTape, tempTape);
        } else if(counter == 2) {
            sscanf(buffer, "%d", &numStates);
        } else if(counter == 3) {
            sscanf(buffer, "%d", &startState);

        } else if(counter == 4) {
            sscanf(buffer, "%d", &endState);
            break;
        } else {
            break;
        }
        ++counter;
    }
    //set up instruction table
    //continuation of the loop
    //(CurrentState,ReadVal)->(WriteVal,MoveDirection,NewState)
    struct instruction instructionTable[numStates][128];
    int currentState, newState;
    char readVal, writeVal, moveDirection;
    while(fgets(buffer, MAX_LINE, fp)) {
        sscanf(buffer, "(%d,%c)->(%c,%c,%d)", &currentState, &readVal, &writeVal, &moveDirection, &newState);
        instructionTable[currentState][readVal].writeVal = writeVal;
        instructionTable[currentState][readVal].moveDirection = moveDirection;
        instructionTable[currentState][readVal].newState = newState;
    }
    printf("\nWriting Tape... \n");
    //set up of tape to be worked on;
    printf("\nInitial Tape: %s\n", initialTape);
    int tapeSize = strlen(initialTape) - 1;
    head = NULL;
    for(int i = 0; i <= tapeSize; ++i) {
        InsertAtEnd(initialTape[i]);
    }
    DeleteAtEnd();
    //doing the instructions
    int state = startState;
    int index = 0;
    char readChar;
    char writeChar;
    struct cell* pCell = GetNewCell('A');
    pCell->next = head->next;
    pCell->prev = NULL;
    while (state != endState) {
        readChar = pCell->data;
        writeChar = instructionTable[state][readChar].writeVal;
        if(state == 0 || index == 0) {
            DeleteAtHead();
            InsertAtHead(writeChar);
        } else {
            Delete(index);
            Insert(writeChar, index);
        }
        if (instructionTable[state][readChar].moveDirection == 'R') {
            if (pCell->next == NULL) {
                InsertAtEnd('B');
                ++tapeSize;
            }
            ++index;
            pCell = pCell->next;
        }
        if (instructionTable[state][readChar].moveDirection == 'L') {
            --index;
            pCell = pCell->prev;
        }
        state = instructionTable[state][readChar].newState;
    }
    printf("\nFinal Tape: "); Print(); printf("\n");
    //shutdown
    fclose(fp);
    return 0;
}