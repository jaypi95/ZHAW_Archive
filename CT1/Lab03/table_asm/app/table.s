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

BITMASK_KEY_T0              EQU     0x01
BITMASK_LOWER_NIBBLE        EQU     0x0F

; ------------------------------------------------------------------
; -- Variables
; ------------------------------------------------------------------
        AREA MyAsmVar, DATA, READWRITE
; STUDENTS: To be programmed
		
data1	SPACE	16



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

	;TASK 3.2
	LDR R1, =ADDR_DIP_SWITCH_7_0      ; load input from dip switches 7 - 0
    LDRB R2, [R1]  ; Load value at dip switch into R2
    
	
    LDR R3, =ADDR_DIP_SWITCH_15_8    ; Load addr of dip switches 15 - 8
    LDRB R4, [R3]            ; Load value at dip switch into R4
    
    LDR R7, =BITMASK_LOWER_NIBBLE    ; Put bitmask over nibble
    ANDS R4, R4, R7

    LDR R6, =ADDR_LED_7_0      ; load address of led 7-0 into r6
    STR R2, [R6]			   ; store value for leds into r6
    LDR R7, =ADDR_LED_15_8     ; load address of led 15-8 into r3
    STR R4, [R7]			   ; store value for leds into address at R7


	;TASK 3.3
	LDR R5, =data1    ;load starting address of table into R5
	STRB R2, [R5, R4]  ;store values at R2 (values of switch 7_0) to R5 with R4 offset

	;TASK 3.4
	LDR R1, =ADDR_DIP_SWITCH_31_24      ; load input from dip switches 31 - 24
    LDRB R2, [R1]  ; Load value at dip switch into R2
	
	LDR R7, =BITMASK_LOWER_NIBBLE    ; Put bitmask over nibble
    ANDS R2, R2, R7
	
	LDR R6, =ADDR_LED_31_24     ; load address of led 31-24 into r6
    STR R2, [R6]			   ; store value for leds into address at R6

	;TASK 3.5	
	LDR R7, =ADDR_LED_23_16 ;load addresses of led 23-16 into R7	
	LDRB R3, [R5, R2] ;Load value of table at index R2 into R3
	STR R3, [R7] ; store value for leds into address at R7
	

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
