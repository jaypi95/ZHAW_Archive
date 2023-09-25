;* ------------------------------------------------------------------
;* --  _____       ______  _____                                    -
;* -- |_   _|     |  ____|/ ____|                                   -
;* --   | |  _ __ | |__  | (___    Institute of Embedded Systems    -
;* --   | | | '_ \|  __|  \___ \   Zurich University of             -
;* --  _| |_| | | | |____ ____) |  Applied Sciences                 -
;* -- |_____|_| |_|______|_____/   8401 Winterthur, Switzerland     -
;* ------------------------------------------------------------------
;* --
;* -- Project     : CT1 - Lab 7
;* -- Description : Control structures
;* -- 
;* -- $Id: main.s 3748 2016-10-31 13:26:44Z kesr $
;* ------------------------------------------------------------------


; -------------------------------------------------------------------
; -- Constants
; -------------------------------------------------------------------
    
                AREA myCode, CODE, READONLY
                    
                THUMB

ADDR_LED_15_0           EQU     0x60000100
ADDR_LED_31_16          EQU     0x60000102
ADDR_7_SEG_BIN_DS1_0    EQU     0x60000114
ADDR_DIP_SWITCH_15_0    EQU     0x60000200
ADDR_HEX_SWITCH         EQU     0x60000211

NR_CASES        		EQU     0xB
BITMASK_LOWER_NIBBLE    EQU     0x0F
MY_CONST_FFFF			EQU		0xFFFF

jump_table  DCD case_0, case_1, case_2, case_3, case_4, case_5, case_6, case_7, case_8, case_9, case_a, case_default    ; ordered table containing the labels of all cases
                ; STUDENTS: To be programmed 

                ; END: To be programmed
    

; -------------------------------------------------------------------
; -- Main
; -------------------------------------------------------------------   
                        
main            PROC
                EXPORT main
                
read_dipsw      ; Read operands into R0 and R1 and display on LEDs
                ; STUDENTS: To be programmed
				;
                LDR R1, =ADDR_DIP_SWITCH_15_0
                LDRB R1, [R1]
				
				LDR R0, =ADDR_DIP_SWITCH_15_0
				LDRH R0, [R0]
				MOVS R7, R0 ;copy R0 into R7 for displaying only
				LSRS R0, #8

                LDR R2, =ADDR_LED_15_0
				STRH R7, [R2]

                ; END: To be programmed
                    
read_hexsw      ; Read operation into R2 and display on 7seg.
                ; STUDENTS: To be programmed

                LDR R3, =ADDR_HEX_SWITCH
                LDRB R3, [R3]
                LDR R7, =BITMASK_LOWER_NIBBLE ;bitmask that shit
                ANDS R3, R3, R7

                LDR R4, =ADDR_7_SEG_BIN_DS1_0

                STRH R3, [R4]

                ; END: To be programmed
                
case_switch     ; Implement switch statement as shown on lecture slide
                ; STUDENTS: To be programmed

                CMP R3, #NR_CASES ;compare hex switch value with Nr of cases
				BHS case_default
				LSLS R3, #2 ;multiply by 4
				LDR R7, =jump_table
				LDR R7, [R7, R3]
				BX R7


                ; END: To be programmed


; Add the code for the individual cases below
; - operand 1 in R0
; - operand 2 in R1
; - result in R0


; STUDENTS: To be programmed

case_0 ;all dark
                LDR  R0, =0
                B    display_result  
case_1 ;add
                ADDS R0, R0, R1
                B    display_result
case_2 ;subtract op1 - op2
                SUBS R0, R0, R1
                B    display_result
case_3 ;multiply (unsigned)
				MULS R0, R1, R0
				B	 display_result
case_4 ;logical AND
				ANDS R0, R0, R1
				B    display_result 
case_5 ;OR
				ORRS R0, R0, R1
				B    display_result 
case_6 ;XOR
				EORS R0, R0, R1
				B    display_result 
case_7 ;NOT (op 1)
				MOVS R7, #0xFF
				EORS R0, R0, R7
				B    display_result 
case_8 ;NAND
				;!(op1 & op2) == !op1 OR !op2
				MOVS R7, #0xFF
				EORS R0, R0, R7
				EORS R1, R1, R7
				ORRS R0, R0, R1
				B    display_result 
case_9 ;NOR
				;!(op1 OR op2) == !op1 AND !op2
				MOVS R7, #0xFF
				EORS R0, R0, R7
				EORS R1, R1, R7
				ANDS R0, R0, R1
				B    display_result 
				
case_a ;XNOR
				;!(op1 XOR op2) == !op1 XOR !op2
				MOVS R7, #0xFF
				EORS R0, R0, R1
				EORS R0, R0, R7
				;EORS R1, R1, R7
				
				B    display_result 
				
case_default ;all bright (cases b, c, d, e and f)
                LDR  R0, =MY_CONST_FFFF
                B    display_result  

; END: To be programmed


display_result  ; Display result on LEDs
                ; STUDENTS: To be programmed

				LDR R5, =ADDR_LED_31_16
				STRH R0, [R5]

                ; END: To be programmed

                B    read_dipsw
                
                ALIGN
                ENDP

; -------------------------------------------------------------------
; -- End of file
; -------------------------------------------------------------------                      
                END

