; ------------------------------------------------------------------
; --  _____       ______  _____                                    -
; -- |_   _|     |  ____|/ ____|                                   -
; --   | |  _ __ | |__  | (___    Institute of Embedded Systems    -
; --   | | | '_ \|  __|  \___ \   Zurich University of             -
; --  _| |_| | | | |____ ____) |  Applied Sciences                 -
; -- |_____|_| |_|______|_____/   8401 Winterthur, Switzerland     -
; ------------------------------------------------------------------
; --
; -- sumdiff.s
; --
; -- CT1 P05 Summe und Differenz
; --
; -- $Id: sumdiff.s 705 2014-09-16 11:44:22Z muln $
; ------------------------------------------------------------------
;Directives
        PRESERVE8
        THUMB

; ------------------------------------------------------------------
; -- Symbolic Literals
; ------------------------------------------------------------------
ADDR_DIP_SWITCH_7_0     EQU     0x60000200
ADDR_DIP_SWITCH_15_8    EQU     0x60000201
ADDR_LED_7_0            EQU     0x60000100
ADDR_LED_15_8           EQU     0x60000101
ADDR_LED_23_16          EQU     0x60000102
ADDR_LED_31_24          EQU     0x60000103

; ------------------------------------------------------------------
; -- myCode
; ------------------------------------------------------------------
        AREA MyCode, CODE, READONLY

main    PROC
        EXPORT main

user_prog
        ; STUDENTS: To be programmed

		;Task 1
		;Read from Switches
		LDR R0, =ADDR_DIP_SWITCH_15_8 
		LDRB R1, [R0] ;Operand A
		
		LDR R2, =ADDR_DIP_SWITCH_7_0
		LDRB R3, [R2] ; Operand B
		
		;Shift values to the left by 24bits
		LSLS R1, R1, #24
		LSLS R3, R3, #24
		
		;Copy R1 so it will be available for subtracting
		MOVS R4, R1 ; Operand A
		
		;Add R3 to R1 with carry
		ADDS R1, R3 ; A + B
		
		;Display flags from addition
		MRS R0, APSR ;Read flags into R0
		LDR R6, =ADDR_LED_15_8
		LSRS R0, R0, #24 ;Right shift because flags are in bits 31 - 28
		STRB R0, [R6]
		
		;Subtract R3 from R4 (copy of R1) with carry
		SUBS R5, R4, R3 ; A - B
		
		;Display flags from subtraction
		MRS R0, APSR
		LDR R7, =ADDR_LED_31_24
		LSRS R0, R0, #24
		STRB R0, [R7]
		
		;Shift values back for displayin'
		LSRS R1, R1, #24
		LSRS R5, R5, #24
		
		;Display the most significant byte of the sum
		LDR R0, =ADDR_LED_7_0
		STRB R1, [R0]
		
		;Display the most significant byte of the difference
		LDR R2, =ADDR_LED_23_16
		STRB R5, [R2]
		


		;Display flags from subtraction

        ; END: To be programmed
        B       user_prog
        ALIGN
; ------------------------------------------------------------------
; End of code
; ------------------------------------------------------------------
        ENDP
        END
