#include <stdlib.h>
#include <stdio.h>
#include <string.h>

#define MAX_LENGTH 21

char *pointerArray[10];

int inputIsDone(int itr);

int checkSameInput(char input[MAX_LENGTH], int itr);

void returnSortedChars(int itr);

int main() {
    int itr = 0;
    while (inputIsDone(itr) != 1) {
        //char str1[MAX_LENGTH];
        char *str1 = malloc(sizeof(char) * MAX_LENGTH);
        printf("Enter a string with max 20 characters\n");
        scanf("%s", str1);
        if (strlen(str1) <= MAX_LENGTH && checkSameInput(str1, itr) == 0) {
            pointerArray[itr] = str1;
            *pointerArray[itr] = *pointerArray[itr] & '_';
            itr += 1;
        }
    }

    returnSortedChars(itr);

}

int inputIsDone(int itr) {
    int res = 0;
    if (itr > 0) {
        char exit[MAX_LENGTH] = "ZZZ";
        for (int i = 0; i < itr; i++) {
            int result = strcmp(pointerArray[i], exit);
            if (i == 9 || result == 0) {
                res = 1;
                break;
            }
        }
    }
    return res;
}

int checkSameInput(char input[MAX_LENGTH], int itr) {
    int res = 0;
    for (int i = 0; i < itr; i++) {
        if (strcmp(input, pointerArray[i]) == 0) {
            res = 1;
        }
    }
    return res;
}

void returnSortedChars(int itr) {
    for (int i = 0; i < itr - 1; i++) {
        for (int j = 0; j < itr - i - 1; j++) {
            if (strcmp(pointerArray[j], pointerArray[j + 1]) > 0) {
                char *temp = pointerArray[j];
                pointerArray[j] = pointerArray[j + 1];
                pointerArray[j + 1] = temp;
            }
        }
    }


    for (int i = 0; i < itr; i++) {
        printf("%s\n", pointerArray[i]);
    }

}