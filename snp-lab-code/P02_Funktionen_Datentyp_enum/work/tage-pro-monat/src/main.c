#include <stdio.h>
#include <stdlib.h>
#include <string.h>

enum month_t{JAN=1, FEB, MAR, APR, MAI, JUN, JUL, AUG, SEP, OKT, NOV, DEZ};

int gibIntWert(char type[], int start, int end);
int istSchaltjahr(int jahr);
int tageProMonat(int jahr, int monat);

int main (int argc, char *argv[]) {

    int monat, jahr;

    //  Monat einlesen und Bereich ueberpruefen
    monat = gibIntWert("Monat", 1, 12);
    jahr  = gibIntWert("Jahr", 1600, 9999);

    //  Ausgabe zum Test
    printf("Monat: %d, Jahr: %d \n", monat, jahr);

    //  Ausgabe zum Test (hier mit dem ternaeren Operator "?:")
    printf("%d ist %s Schaltjahr\n", jahr, istSchaltjahr(jahr) ? "ein" : "kein");

    // Ausgabe
    printf("Der Monat %02d-%d hat %d Tage.\n", monat, jahr, tageProMonat(jahr, monat));

    return 0;
}

int gibIntWert(char type[], int start, int end) {
    int input;

    printf("Enter %s \n", type);
    scanf("%d", &input);

    if(input >= start && input <= end){
        return input;
    } else {
        printf("Your input is invalid\n");
        exit(1);
    }
}

int istSchaltjahr(int jahr){
    int returnVal;
    returnVal = 0;

    if(jahr % 4 == 0){
        if(jahr % 100 == 0){
            if(jahr % 400 == 0){
                returnVal = 1;
            } else{
                returnVal = 0;
            }
        } else{
            returnVal = 1;
        }
    }
    return returnVal;
}

int tageProMonat(int jahr, int monat){
    int returnVal;

    if(monat == FEB){
        if(istSchaltjahr(jahr)){
            returnVal = 29;
        } else {
            returnVal = 28;
        }
    } else if(monat % 2 == 0 || monat == JAN || monat == JUL){
        returnVal = 30;
    } else{
        returnVal = 31;
    }
    return returnVal;
}