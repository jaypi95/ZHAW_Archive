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
 * @brief Lab implementation
 */
#include <stdio.h>
#include <stdlib.h>

#include "file_io.h"
#include "person.h"
#include "list.h"

void perror_and_exit(const char *context) { perror(context); exit(EXIT_FAILURE); } // das muss noch an einen anderen Ort, scia

// May divide your code in further functions
// BEGIN-STUDENTS-TO-ADD-CODE
#define PERR_EXIT(M) do {perror_and_exit(M)}while(0)
char fileName[] = "list.csv";
static const int max_len = 128; //!!!könnte man schöner lösen, scia
// END-STUDENTS-TO-ADD-CODE

void store_person_list(void)
{
	// BEGIN-STUDENTS-TO-ADD-CODE
	FILE *listFile = fopen(fileName, "w");
	
	node_t *node = list_anchor()->next;
	while(!is_anchor(node)){
		char *s = malloc(sizeof(char)*max_len);
		person_to_csv_string(&(node->content), s);
		fprintf(listFile, "%s\n", s);
		node = node->next;
	}
	fclose(listFile);
	// END-STUDENTS-TO-ADD-CODE
}

void load_person_list(void)
{
	// BEGIN-STUDENTS-TO-ADD-CODE
	FILE *listFile = fopen(fileName, "r");
	if (listFile == NULL){
		listFile = fopen(fileName, "w");
		fclose(listFile);
		listFile = fopen(fileName, "r");
	}
	
	char *s = malloc(sizeof(char*));
	while(fgets(s, max_len, listFile) != NULL){
		person_t *newP = malloc(sizeof(person_t));
		person_from_csv_string(newP, s);
		list_insert(newP);
	}
	
	
	
	fclose(listFile);
	// END-STUDENTS-TO-ADD-CODE
}
