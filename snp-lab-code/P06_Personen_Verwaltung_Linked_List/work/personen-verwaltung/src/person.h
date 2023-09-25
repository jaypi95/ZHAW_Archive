#ifndef _PERSON_H_
#define _PERSON_H_

#define NAME_LEN 20

typedef struct {
	char name[NAME_LEN];
	char first_name[NAME_LEN];
	unsigned int age;
} person_t;

/**
 * @brief Compares two persons in this sequence: 1st=name, 2nd=first_name, 3rd=age
 * @param a [IN] const reference to 1st person in the comparison
 * @param b [IN] const reference to 2nd person in the comparison
 * @return =0 if all record fields are the same
 *         >0 if all previous fields are the same, but for this field, a is greater
 *         <0 if all previous fields are the same, but for this field, b is greater
 * @remark strncmp() is used for producing the result of string field comparisons
 * @remark a->age – b->age is used for producing the result of age comparison
 */
int person_compare(const person_t *a, const person_t *b);

int person_read(person_t *p);

#endif // _PERSON_H_
