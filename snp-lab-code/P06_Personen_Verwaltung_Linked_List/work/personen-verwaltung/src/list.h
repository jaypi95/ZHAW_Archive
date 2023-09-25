#ifndef _LIST_H_
#define _LIST_H_

#include "person.h"

typedef struct node {
	person_t content; // in diesem Knoten gespeicherte Person
	struct node *next;  // Pointer auf den nächsten Knoten in der Liste
} node_t;

const node_t *list_init();
int list_insert(const person_t *p);
int list_remove(const person_t *p);
void list_clear(void);
void list_show(void);

#endif // _LIST_H_
