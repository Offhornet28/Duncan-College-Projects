/*
Name: Duncan Zaug
Microproject 1
*/

#include <stdio.h>
#include <string.h>
#include <stdarg.h>
#include <stdlib.h>
#include <stdbool.h>

struct item
{
    char name[100];
    double price;
    int shelf;
    int slot;
};

int main( ) {
    int rows, columns;
    printf("\n Enter the Number of Shelves: ");
    scanf("%d", &rows);
    printf("\n Enter the Number of Slots: ");
    scanf("%d", &columns);
    struct item unit[rows][columns];
    char cmd[200];
    char uName[100];
    double uPrice = 0;
    int uShelf = 0;
    int uSlot = 0;
    while (strcmp(cmd, "exit") != 0) {
        RESET: printf("\ncmd? [write] [read] [exit]: ");
        scanf("%s", cmd);
        if(strcmp(cmd, "write") == 0) {
            printf("\nwrite: \n");
            printf("enter <name> <price> <shelf> <slot> = ");
            scanf("%s %lf %d %d", uName, &uPrice, &uShelf, &uSlot);
            //basic error handling
            if(uShelf > rows) {
                printf("out of bounds\n");
                goto RESET;
            }
            if(uSlot > columns) {
                printf("out of bounds\n");
                goto RESET;
            }
            strcpy(unit[uShelf][uSlot].name, uName);
            unit[uShelf][uSlot].price = uPrice;
            unit[uShelf][uSlot].shelf = uShelf;
            unit[uShelf][uSlot].slot = uSlot;
        }
        if(strcmp(cmd, "read") == 0) {
            printf("\nread: \n");
            printf("enter <shelf> <slot>: ");
            scanf("%d, %d", &uShelf, &uSlot);
            //basic error handling
            if(uShelf > rows) {
                printf("out of bounds\n");
                goto RESET;
            }
            if(uSlot > columns) {
                printf("out of bounds\n");
                goto RESET;
            }
            printf("\n%s; $%lf; Shelf %d; Slot %d; ", unit[uShelf][uSlot].name, unit[uShelf][uSlot].price, unit[uShelf][uSlot].shelf, unit[uShelf][uSlot].slot);
        }
    }
    printf("\nexit: \n");
    return 0; 
}