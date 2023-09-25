#include <assert.h>
#include <string.h>
#include <stdio.h>
#include <stdlib.h>

#include "person.h"

int person_compare(const person_t *a, const person_t *b)
{
	assert(a);
	assert(b);
	int res = strncmp(a->name, b->name, NAME_LEN);
	if (res == 0) res = strncmp(a->first_name, b->first_name, NAME_LEN);
	if (res == 0) res = a->age - b->age;
	return res;
}

int person_read(person_t *p)
{
	assert(p);
	assert(NAME_LEN == 20);
	memset(p, 0, sizeof(person_t));  
 
	return scanf("%19s %19s %u", p->name, p->first_name, &(p->age)) == 3;
}

//operations for persistency lab


static const int max_len = 128; //!!!könnte man schöner lösen, scia

int person_to_csv_string(person_t* person, char* s)
{
	// BEGIN-STUDENTS-TO-ADD-CODE
	return snprintf(s, max_len, "%s,%s,%u", person->name, person->first_name, person->age);
	// END-STUDENTS-TO-ADD-CODE
}

/**
* own implementation of strsep as the compiler somehow doesn't wan to know it..
*/
char *strsep(char **stringp, const char *delim) {
    char *rv = *stringp;
    if (rv) {
        *stringp += strcspn(*stringp, delim);
        if (**stringp)
            *(*stringp)++ = '\0';
        else
            *stringp = 0; }
    return rv;
}

void person_from_csv_string(person_t* person, char* s)
{
	// BEGIN-STUDENTS-TO-ADD-CODE
	strcpy(person->name, strsep(&s, ","));
	strcpy(person->first_name, strsep(&s, ","));
	(*person).age = atoi(strsep(&s, ","));




	// END-STUDENTS-TO-ADD-CODE
}



