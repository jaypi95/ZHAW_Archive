; ------------------------------------------------------------------
; --  _____       ______  _____                                    -
; -- |_   _|     |  ____|/ ____|                                   -
; --   | |  _ __ | |__  | (___    Institute of Embedded Systems    -
; --   | | | '_ \|  __|  \___ \   Zurich University of             -
; --  _| |_| | | | |____ ____) |  Applied Sciences                 -
; -- |_____|_| |_|______|_____/   8401 Winterthur, Switzerland     -
; ------------------------------------------------------------------
; --
; -- table.s
; --
; -- CT1 P04 Ein- und Ausgabe von Tabellenwerten
; --
; -- $Id: table.s 800 2014-10-06 13:19:25Z ruan $
; ------------------------------------------------------------------
;Directives
        PRESERVE8
        THUMB
; ------------------------------------------------------------------
; -- Symbolic Literals
; ------------------------------------------------------------------
ADDR_DIP_SWITCH_7_0         EQU     0x60000200
ADDR_DIP_SWITCH_15_8        EQU     0x60000201
ADDR_DIP_SWITCH_31_24       EQU     0x60000203
ADDR_LED_7_0                EQU     0x60000100
ADDR_LED_15_8               EQU     0x60000101
ADDR_LED_23_16              EQU     0x60000102
ADDR_LED_31_24              EQU     0x60000103
ADDR_BUTTONS                EQU     0x60000210
ADDR_SEG7_BIN               EQU     0x60000114

BITMASK_KEY_T0              EQU     0x01
BITMASK_LOWER_NIBBLE        EQU     0x0F

; ------------------------------------------------------------------
; -- Variables
; ------------------------------------------------------------------
        AREA MyAsmVar, DATA, READWRITE
; STUDENTS: To be programmed
		
data1	SPACE	32



; END: To be programmed
        ALIGN

; ------------------------------------------------------------------
; -- myCode
; ------------------------------------------------------------------
        AREA myCode, CODE, READONLY

main    PROC
        EXPORT main

readInput
        BL    waitForKey                    ; wait for key to be pressed and released
; STUDENTS: To be programmed

	;Read input
	LDR R1, =ADDR_DIP_SWITCH_7_0      ; load input from dip switches 7 - 0
    LDRB R2, [R1]  ; Load value at dip switch into R2
    
	
    LDR R3, =ADDR_DIP_SWITCH_15_8    ; Load addr of dip switches 15 - 8
    LDRB R4, [R3]            ; Load value at dip switch into R4

    LDR R7, =BITMASK_LOWER_NIBBLE    ; Put bitmask over nibble
    ANDS R4, R4, R7 ; Logical and to apply bitmask

    ;Do logical operations for half words
    LSLS R4, R4, #8 ;Shift existing bits one byte to the left to make space for switches 7-0
    ADDS R2, R4 ;Store Value of 15-8 into most significant byte
    LSRS R4, R4, #8 ;Shift bits back so we can use the same register for later

    ;Load Table
    LDR R5, =data1 ;load starting address of table into R5
    LSLS R4, R4, #1 ;Shift index one bit to the left to double it and account for half words
    STRH R2, [R5, R4] ;Store half word values from R2 to R5 with offset R4

    ;Write to lower LEDs
    LDR R6, =ADDR_LED_7_0      ; load address of led 7-0 into r6
    STRB R2, [R6]			   ; store value for leds into r6
    LDR R7, =ADDR_LED_15_8     ; load address of led 15-8
    LSRS R2, R2, #8 ;Shift R4 one byte to the right to let the correct led light up in the input index
    STRB R2, [R7]			   ; store value for leds into address at R7
    
    ;Load index switches for output
	LDR R1, =ADDR_DIP_SWITCH_31_24      ; load input from dip switches 31 - 24
    LDRB R2, [R1]  ; Load value at dip switch into R2
	
	LDR R7, =BITMASK_LOWER_NIBBLE    ; Put bitmask over nibble
    ANDS R2, R2, R7

    ;Light up output index LEDs
	LDR R6, =ADDR_LED_31_24     ; load address of led 31-24 into r6
    STRB R2, [R6]			   ; store value for leds into address at R6

    ;Read from table and light up output value LEDs
    LSLS R2, R2, #1 ;Shift index one bit to the left to double it and account for half words
	LDR R7, =ADDR_LED_23_16 ;load addresses of led 23-16 into R7	
	LDRH R3, [R5, R2] ;Load value of table at index R2 into R3
	STRH R3, [R7] ;Store value of R3 into LEDs at R7

    ;Output index and value to 7 segment display 
    LDR R0, =ADDR_SEG7_BIN
    LSLS R2, R2, #7 ;Shift index seven bits to the left to make space for corresponding values
    ORRS R2, R3 ;Add value halfword to register
    STR R2, [R0] ;Store value of R2 into 7 segment display
	
    ;Note: I could have read the output index from the table but in case where that index would have been empty (because no input has been stored yet) the output index would have been empty as well on the 7 segment display which makes no sense since the output index is determined by the switch position and not the table entry.

; END: To be programmed
        B       readInput
        ALIGN

; ------------------------------------------------------------------
; Subroutines
; ------------------------------------------------------------------

; wait for key to be pressed and released
waitForKey
        PUSH    {R0, R1, R2}
        LDR     R1, =ADDR_BUTTONS           ; laod base address of keys
        LDR     R2, =BITMASK_KEY_T0         ; load key mask T0

waitForPress
        LDRB    R0, [R1]                    ; load key values
        TST     R0, R2                      ; check, if key T0 is pressed
        BEQ     waitForPress

waitForRelease
        LDRB    R0, [R1]                    ; load key values
        TST     R0, R2                      ; check, if key T0 is released
        BNE     waitForRelease
                
        POP     {R0, R1, R2}
        BX      LR
        ALIGN

; ------------------------------------------------------------------
; End of code
; ------------------------------------------------------------------
        ENDP
        END

