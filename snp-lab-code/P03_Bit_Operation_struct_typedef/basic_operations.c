#include <stdlib.h>
#include <stdio.h>

int main() {
    unsigned int number = 0x75; //binary: 01110101
    unsigned int bit = 3; // bit at position 3

    // Setting a bit
    number |= 1 << bit;

    // Clearing a bit
    bit = 1;
    number &= ~(1 << bit);

    // Toggling a bit
    bit = 0;
    number ^= 1 << bit;

    printf("number = 0x%02X\n", number);

    return EXIT_SUCCESS;
}