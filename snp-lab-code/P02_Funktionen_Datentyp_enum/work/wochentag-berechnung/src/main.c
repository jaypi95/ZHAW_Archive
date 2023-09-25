 /* ----------------------------------------------------------------------------
 * --  _____       ______  _____                                              -
 * -- |_   _|     |  ____|/ ____|                                             -
 * --   | |  _ __ | |__  | (___    Institute of Embedded Systems              -
 * --   | | | '_ \|  __|  \___ \   Zuercher Hochschule Winterthur             -
 * --  _| |_| | | | |____ ____) |  (University of Applied Sciences)           -
 * -- |_____|_| |_|______|_____/   8401 Winterthur, Switzerland               -
 * ----------------------------------------------------------------------------
 */
/**
 * @file
 * @brief Lab P02 weekday
 */
#include <stdio.h>
#include <stdlib.h>
#include <assert.h>


// *** TASK1: typedef enum types for month_t (Jan=1,...Dec} ***
// BEGIN-STUDENTS-TO-ADD-CODE

typedef enum {JAN=1, FEB, MAR, APR, MAI, JUN, JUL, AUG, SEP, OKT, NOV, DEZ} month_t;


// END-STUDENTS-TO-ADD-CODE



// *** TASK1: typedef struct for date_t ***
// BEGIN-STUDENTS-TO-ADD-CODE

typedef struct{int year; int month; int day;} date_t;

// END-STUDENTS-TO-ADD-CODE



// *** TASK2: typedef enum weekday_t (Sun=0, Mon, ...Sat) ***
// BEGIN-STUDENTS-TO-ADD-CODE

typedef enum {Sun=0, Mon, Tue, Wed, Thu, Fri, Sat}weekday_t;

// END-STUDENTS-TO-ADD-CODE

/**
 * @brief   TASK1: Checks if the given date is a leap year.
 * @returns 0 = is not leap year, 1 = is leap year
 */
// BEGIN-STUDENTS-TO-ADD-CODE

int istSchaltjahr(int jahr);

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


// END-STUDENTS-TO-ADD-CODE


/**
 * @brief   TASK1: Calculates the length of the month given by the data parameter
 * @returns 28, 29, 30, 31 if a valid month, else 0
 */
// BEGIN-STUDENTS-TO-ADD-CODE

int tageProMonat(int jahr, int monat);

int tageProMonat(int jahr, int monat){
	int returnVal;

	if(monat == FEB){
		if(istSchaltjahr(jahr)){
			returnVal = 29;
		} else {
			returnVal = 28;
		}
	} else if(monat % 2 == 1  && monat <= 7){
		returnVal = 31;
	} else if(monat % 2 == 0 && monat > 7){
		returnVal = 31;
	} else {
		returnVal = 30;
	}
	return returnVal;
}


// END-STUDENTS-TO-ADD-CODE

/**
 * @brief   TASK1: Checks if the given date is in the gregorian date range
 * @returns 0 = no, 1 = yes
 */
// BEGIN-STUDENTS-TO-ADD-CODE

int isInGregorianRange(int jahr, int monat, int tag);

int isInGregorianRange(int jahr, int monat, int tag){
	int returnVal;

	if(jahr >= 1583 && jahr < 10000){
		returnVal = 1;
	} else if(jahr == 1582){
		if(monat >= 11){
			returnVal = 1;
		} else if (monat == 10 && tag > 14){
			returnVal = 1;
		} else {
			returnVal = 0;
		}
	} else {
		returnVal = 0;
	}
    return returnVal;
}

// END-STUDENTS-TO-ADD-CODE


/**
 * @brief   TASK1: Checks if the given date is a valid date.
 * @returns 0 = is not valid date, 1 = is valid date
 */
// BEGIN-STUDENTS-TO-ADD-CODE

int dateIsValid(int jahr, int monat, int tag);

int dateIsValid(int jahr, int monat, int tag) {
    //check if is in range
    int returnVal;
    returnVal = 0;

    if (isInGregorianRange(jahr, monat, tag) == 1) {
        if (monat >= 1 && monat <= 12) {
            if (tag >= 1 && tag <= tageProMonat(jahr, monat)) {
                returnVal = 1;

            }
        }
    }
    return returnVal;
}
// END-STUDENTS-TO-ADD-CODE


/**
 * @brief   TASK2: calculated from a valid date the weekday
 * @returns returns a weekday in the range Sun...Sat
 */
// BEGIN-STUDENTS-TO-ADD-CODE


weekday_t calculatesWeekday(date_t date) {

   // assert(dateIsValid(date.year, date.month, date.day));
    if (dateIsValid(date.year, date.month, date.day) == 0){
	printf("Date is not valid\n");
        return EXIT_FAILURE;
    } else {
    return (date.year + date.year / 4 - date.year / 100 + date.year / 400 + (13 * date.month + 8) / 5 + date.day) % 7;
    }
}
// END-STUDENTS-TO-ADD-CODE



/**
 * @brief   TASK2: print weekday as 3-letter abreviated English day name
 */
// BEGIN-STUDENTS-TO-ADD-CODE

char* printWeekday(int weekday){
	char* weekdays[] = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
	return weekdays[weekday];
}

// END-STUDENTS-TO-ADD-CODE

/**
 * @brief   main function
 * @param   argc [in] number of entries in argv
 * @param   argv [in] program name plus command line arguments
 * @returns returns success if valid date is given, failure otherwise
 */
int main(int argc, const char *argv[])
{
    // TASK1: parse the mandatory argument into a date_t variable and check if the date is valid
    // BEGIN-STUDENTS-TO-ADD-CODE

	if(argc != 2){
		printf("Error! Excpected 1 argument but got %d \n", argc - 1);
		return EXIT_FAILURE;
	}

	date_t datum;
	sscanf(argv[1], "%d-%d-%d", &datum.year, &datum.month, &datum.day);

	
    // END-STUDENTS-TO-ADD-CODE


    // TASK2: calculate the weekday and print it in this format: "%04d-%02d-%02d is a %s\n"
    // BEGIN-STUDENTS-TO-ADD-CODE

    if (dateIsValid(datum.year, datum.month, datum.day) == 0){
	printf("Date is not valid\n");

        return EXIT_FAILURE;
    } else {
	char weekday = calculatesWeekday(datum);
    printf("%04d-%02d-%02d is a %s\n", datum.year, datum.month, datum.day, printWeekday(weekday));
    }
    // END-STUDENTS-TO-ADD-CODE
    return EXIT_SUCCESS;

}

