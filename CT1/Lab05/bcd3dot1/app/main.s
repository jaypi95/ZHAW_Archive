; ------------------------------------------------------------------
; --  _____       ______  _____                                    -
; -- |_   _|     |  ____|/ ____|                                   -
; --   | |  _  | |  | (___    Institute of Embedded Systems    -
; --   | | | '_ \|  __|  \___ \   Zurich University of             -
; --  _| |_| | | | |____ ____) |  Applied Sciences                 -
; -- |_____|_| |_|______|_____/   8401 Winterthur, Switzerland     -
; ------------------------------------------------------------------
; --
; -- main.s
; --
; -- CT1 P06 "ALU und Sprungbefehle" mit MUL
; --
; -- $Id: main.s 4857 2019-09-10 17:30:17Z akdi $
; ------------------------------------------------------------------
;Directives
        PRESERVE8
        THUMB

; ------------------------------------------------------------------
; -- Address Defines
; ------------------------------------------------------------------

ADDR_LED_15_0           EQU     0x60000100
ADDR_LED_31_16          EQU     0x60000102
ADDR_DIP_SWITCH_7_0     EQU     0x60000200
ADDR_DIP_SWITCH_15_8    EQU     0x60000201
ADDR_7_SEG_BIN_DS3_0    EQU     0x60000114
ADDR_BUTTONS            EQU     0x60000210

ADDR_LCD_RED            EQU     0x60000340
ADDR_LCD_GREEN          EQU     0x60000342
ADDR_LCD_BLUE           EQU     0x60000344
LCD_BACKLIGHT_FULL      EQU     0xffff
LCD_BACKLIGHT_OFF       EQU     0x0000
MY_CONST_A              EQU     0x0A
MY_CONST_10             EQU     0x10
MY_ZERO                 EQU     0x00

; ------------------------------------------------------------------
; -- myCode
; ------------------------------------------------------------------
        AREA myCode, CODE, READONLY

        ENTRY

main    PROC
        export main
            
	; STUDENTS: To be programmed
	;Task 3.1
	;Load value of DIP_Switches 
	LDR R1, =ADDR_DIP_SWITCH_7_0
	LDRB R1, [R1]

	LDR R2, =ADDR_DIP_SWITCH_15_8 
	LDRB R2, [R2]

	;Clear the last 4 digits of the BCD tens so that it cant go over 9
	MOVS R3, #0xF0
	BICS R1, R1, R3
	BICS R2, R2, R3

    ;Check if Button T0 is pressed -> First load the value of T0   
    LDR R6, =ADDR_BUTTONS 
    LDRB R6, [R6, #0]
	BICS R6, R6, R3
	
	
    ;If T0 is equals 0 then jump to the shifting process, else use the mul process
    LDR R7, =MY_ZERO
    CMP R6, R7
    BEQ jump_to_shifting_process

    ;Here starts the Multiplication process, mul the BCD ten value so it shifts 4 bits to the left
    LDR R5, =MY_CONST_10
    MULS R2, R5, R2
    ;Set Background to blue
    LDR R6, =ADDR_LCD_BLUE
    LDR R7, =0xffff
    STRH R7, [R6]
	
	;Clear red so it wont merge
	LDR R6, =ADDR_LCD_RED
    LDR R7, =0x0000
    STRH R7, [R6]
    ;Jump to the point where it does not matter which process was used
    B after_process
    ;Here starts the shifting method.
    ;Shift the BCD tens 4 bits to the left
jump_to_shifting_process
	LSLS R2, R2, #4
    ;Set background to red
    LDR R6, =ADDR_LCD_RED 
    LDR R7, =0xffff
    STRH R7, [R6]
	;Clear blue so it wont merge
	LDR R6, =ADDR_LCD_BLUE
    LDR R7, =0x0000
    STRH R7, [R6]
    ;Combine the BCD tens with the BCD ones
after_process 
	ADDS R3, R1, R2
    
	;Load the adress of the Leds 
	LDR R4, =ADDR_LED_15_0
	;Store the BCD value into the Led
	STRB R3, [R4]

    ;To get the Binary value, Mul the BCD tens with 10 and add the BCD ones
    LDR R5, =MY_CONST_A
	LDR R2, =ADDR_DIP_SWITCH_15_8 
	LDRB R2, [R2]

    MULS R2, R5, R2
    ADDS R2, R2, R1

    STRB R2, [R4, #1]

; END: To be programmed

        B       main
        ENDP
            
;----------------------------------------------------
; Subroutines
;----------------------------------------------------

;----------------------------------------------------
; pause for disco_lights
pause           PROC
        PUSH    {R0, R1}
        LDR     R1, =1
        LDR     R0, =0x000FFFFF
        
loop        
        SUBS    R0, R0, R1
        BCS     loop
    
        POP     {R0, R1}
        BX      LR
        ALIGN
        ENDP

; ------------------------------------------------------------------
; End of code
; ------------------------------------------------------------------
        END