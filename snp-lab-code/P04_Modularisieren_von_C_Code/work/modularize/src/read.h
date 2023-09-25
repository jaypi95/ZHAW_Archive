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
// begin students to add code for task 4.1
#ifndef HF_READ
#define HF_READ

#include <stdlib.h>
#include <stdio.h>

// end of input
// end of line
#define EOL 10

// abnormal return values
#define PARSE_ERROR -1
#define READ_ERROR -2

// ASCII Codes
// ' '
#define ASCII_SPACE 32
// '0'
#define ASCII_DIGIT_0 48
// '9'
#define ASCII_DIGIT_9 57

// conversion buffer
#define NO_POS -1
#define BUFFERSIZE 10

int getInt(int maxResult);

#endif
// end students to add code
