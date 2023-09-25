#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
#include "list.h"

static node_t anchor;

static int is_anchor(const node_t *node)
{
	return node == &anchor;
}

static void remove_next(node_t *at)
{
	assert(at);
	assert(at->next);
	if (!is_anchor(at->next)) {
		node_t *next = at->next->next;
		free(at->next);
		at->next = next;
	}
}

static node_t *find_insert(const person_t *p)
{
	assert(p);
	node_t *last = &anchor;
	for(node_t *n = anchor.next; !is_anchor(n); last = n, n = n->next) {
		int res = person_compare(&(n->content), p);
		if (res == 0) {
			return NULL; // *** EARLY RETURN ***// // already part of the list
		} else if (res > 0) {
			break; // the predecessor is the insert point
		}
	}
	return last;
}

static node_t *find_remove(const person_t *p)
{
	assert(p);
	node_t *last = &anchor;
	for(node_t *n = anchor.next; !is_anchor(n); last = n, n = n->next) {
		int res = person_compare(&(n->content), p);
		if (res == 0) {
			break; // the predecessor is the remove point
		}
	}
	return is_anchor(last->next) ? NULL : last;
}

const node_t *list_anchor(void)
{
	return &anchor;
}

const node_t *list_init()
{
	anchor.next = &anchor;
	return &anchor;
}

int list_insert(const person_t *p)
{
	node_t *at = find_insert(p);
	node_t *insert = NULL;
	if (at) {
		insert = malloc(sizeof(node_t));
		if (insert) {
			insert->content = *p;
			insert->next = at->next;
			at->next = insert;
		}
	}
	return at && insert;
}

int list_remove(const person_t *p)
{
	node_t *at = find_remove(p);
	if (at) {
		remove_next(at);
	}
	return at != NULL;
}

void list_clear(void)	
{
	node_t *n = &anchor;
	do {
		remove_next(n);
	} while (!is_anchor(n->next));
}

void list_show(void)
{
	node_t *n = &anchor;
	do {
		if (!is_anchor(n)) printf("%20s %20s %u\n", n->content.name, n->content.first_name, n->content.age);
		n = n->next;
	} while(!is_anchor(n));
}

