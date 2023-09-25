#include <assert.h>
#include <string.h>
#include <stdio.h>

#include "person.h"

int person_compare(const person_t *a, const person_t *b) {
    assert(a);
    assert(b);
     int result = strcmp(a->name, b->name);
     if (result == 0) {
        result = strcmp(a->first_name, b->first_name);
    }
    if (result == 0) {
        result = a->age - b->age;
    }
    return result;
}

int person_read(person_t *p) {
    assert(p);
    assert(NAME_LEN == 20);
    return scanf("%19s %19s %u", p->name, p->first_name, &(p->age)) == 3;
}
