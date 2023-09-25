; ------------------------------------------------------------------
; --  _____       ______  _____                                    -
; -- |_   _|     |  ____|/ ____|                                   -
; --   | |  _ __ | |__  | (___    Institute of Embedded Systems    -
; --   | | | '_ \|  __|  \___ \   Zurich University of             -
; --  _| |_| | | | |____ ____) |  Applied Sciences                 -
; -- |_____|_| |_|______|_____/   8401 Winterthur, Switzerland     -
; ------------------------------------------------------------------
; --
; -- main.s
; --
; -- CT1 P08 "Strukturierte Codierung" mit Assembler
; --
; -- $Id: struct_code.s 3787 2016-11-17 09:41:48Z kesr $
; ------------------------------------------------------------------
;Directives
        PRESERVE8
        THUMB

; ------------------------------------------------------------------
; -- Address-Defines
; ------------------------------------------------------------------
; input
ADDR_DIP_SWITCH_7_0       EQU        0x60000200
ADDR_BUTTONS              EQU        0x60000210

; output
ADDR_LED_31_0             EQU        0x60000100
ADDR_7_SEG_BIN_DS3_0      EQU        0x60000114
ADDR_LCD_COLOUR           EQU        0x60000340
ADDR_LCD_ASCII            EQU        0x60000300
ADDR_LCD_ASCII_BIT_POS    EQU        0x60000302
ADDR_LCD_ASCII_2ND_LINE   EQU        0x60000314


; ------------------------------------------------------------------
; -- Program-Defines
; ------------------------------------------------------------------
; value for clearing lcd
ASCII_DIGIT_CLEAR        EQU         0x00000000
LCD_LAST_OFFSET          EQU         0x00000028

; offset for showing the digit in the lcd
ASCII_DIGIT_OFFSET        EQU        0x00000030

; lcd background colors to be written
DISPLAY_COLOUR_RED        EQU        0
DISPLAY_COLOUR_GREEN      EQU        2
DISPLAY_COLOUR_BLUE       EQU        4

; ------------------------------------------------------------------
; -- myConstants
; ------------------------------------------------------------------
        AREA myConstants, DATA, READONLY
; display defines for hex / dec
DISPLAY_BIT               DCB        "Bit "
DISPLAY_2_BIT             DCB        "2"
DISPLAY_4_BIT             DCB        "4"
DISPLAY_8_BIT             DCB        "8"

        ALIGN

; ------------------------------------------------------------------
; -- myCode
; ------------------------------------------------------------------
        AREA myCode, CODE, READONLY
        ENTRY

        ; imports for calls
        import adc_init
        import adc_get_value

main    PROC
        export main
        ; 8 bit resolution, cont. sampling
        BL         adc_init 
        BL         clear_lcd

main_loop
; STUDENTS: To be programmed

		;Read ADC and store it in R0
		BL adc_get_value
		
		;Load 0xF0 so we can use it to clear bits
		MOVS R7, #0xF0
		
		;Check if T0 pressed
		LDR R1, =ADDR_BUTTONS
		LDRB R1, [R1, #0]
		BICS R1, R1, R7
		LDR R6, =0x00
		CMP R1, R6
		BEQ T0_not_pressed

		;clear lcd first
		BL remove_lcd_colour
		BL clear_lcd
		;set background to green
		LDR R6, =ADDR_LCD_COLOUR
		LDR R7, =DISPLAY_COLOUR_GREEN
		ADDS R6, R6, R7
		LDR R7, =0xFFFF
		STRH R7, [R6]
		
		;display adc value on 7-segment display
		LDR R2, =ADDR_7_SEG_BIN_DS3_0
		STRB R0, [R2, #0]
		
		
		;clear LEDs first
		LDR R4, =ADDR_LED_31_0
		LDR R7, =0x00000000
		STR R7, [R4]
		
		;if value is FF, add one otherwise the loop stops to early for some reason
		LDR R7, =0xFF
		CMP R0, R7
		BL value_is_FF
		

		;calc value to build LED bar
		LSRS R0, R0, #3
		LDR R2, =0x00 ;Register for the LED bar
		
		
		
		
magnitude_bar_loop
		LDR R7, =0x00
		CMP R0, R7 ;check if R0 is empty
		BEQ main_loop
		
		
		;if led bar is zero --> add one
		CMP R2, R7
		BEQ led_is_zero
		;else --> multiply the existing value by two
		LDR R3, =0x02
		MULS R2, R3, R2
		;and add one to light up more LEDs
		ADDS R2, R2, #1
		B converge
		
led_is_zero
		ADDS R2, R2, #1
		B converge
		
converge
		;display the led bar
		LDR R4, =ADDR_LED_31_0
		STR R2, [R4]
		;subtract one from R0 and go to the top of the loop
		SUBS R0, R0, #1
		B magnitude_bar_loop
		
value_is_FF
		ADDS R0, R0, #1
		BX LR

T0_not_pressed

		;clear LEDs first
		LDR R4, =ADDR_LED_31_0
		LDR R7, =0x00000000
		STR R7, [R4]
		
		;read dip switches
		LDR R1, =ADDR_DIP_SWITCH_7_0
		LDRB R1, [R1]
		
		;calc difference between dip switches and adc for displaying
		;diff = dip_switches - adc
		SUBS R2, R1, R0
		
		;check if resulting value is negative (R0 < 0 == true)
		CMP R1, R0
		BMI diff_negative
		
		;if diff is not negative, set lcd blue
		;clear lcd first
		BL remove_lcd_colour
		BL clear_lcd
		;set background to blue
		LDR R6, =ADDR_LCD_COLOUR
		LDR R7, =DISPLAY_COLOUR_BLUE
		ADDS R6, R6, R7
		LDR R7, =0xFFFF
		STRH R7, [R6]
		
		;prepare LCD display
		LDR R3, =ADDR_LCD_ASCII
		
		
		;compare if diff - 4 results in a negative value.
		LDR R7, =0x4
		CMP R2, R7
		BMI display_2bit
		;otherwise, compare if diff - 16 results in a negative value
		LDR R7, =0x10
		CMP R2, R7
		BMI display_4bit
		;otherwise jump to display_8bit
		B display_8bit
		
		
diff_negative
		;if diff is negative, set lcd to red
		;clear lcd first
		BL remove_lcd_colour
		BL clear_lcd
		;set background to blue
		LDR R6, =ADDR_LCD_COLOUR
		LDR R7, =DISPLAY_COLOUR_RED
		ADDS R6, R6, R7
		LDR R7, =0xFFFF
		STRH R7, [R6]
		BL count_number_of_binary_0_in_diff
		B display_diff
		
display_2bit
		LDR R5, =DISPLAY_2_BIT
		LDR R5, [R5]
		STRB R5, [R3]
		BL write_bit_ascii
		B display_diff

display_4bit
		LDR R5, =DISPLAY_4_BIT
		LDR R5, [R5]
		STRB R5, [R3]
		BL write_bit_ascii
		B display_diff
		
display_8bit
		LDR R5, =DISPLAY_8_BIT
		LDR R5, [R5]
		STRB R5, [R3]
		BL write_bit_ascii
	
display_diff
		;display diff value on 7-segment display
		LDR R3, =ADDR_7_SEG_BIN_DS3_0
		STRB R2, [R3, #0]
		
; END: To be programmed
        B          main_loop
		

count_number_of_binary_0_in_diff
		MOVS R7, #0x00 ;counting register
		MOVS R6, #0x00 ;iterator register (there can be max 32 zeroes in the diff)
		MOVS R0, R2 ;copy the diff to make changes to it
counting_loop_binary_0
		MOVS R5, #0x20
		CMP R6, R5
		BEQ binary_0_loop_ended
		
		ADDS R6, R6, #1 ;add one to the iterator
		
		LSRS R2, R2, #1 ;shift the register one to the right to get the next bit
		MOVS R3, R2 ;You know I should have used push at the beginning so I wouldn't have to worry about reusing registers but here we are
		LDR R4, =0xFFFFFFFE ;bitmask to only extract the lsb
		BICS R3, R4
		MOVS R5, #0x00
		CMP R3, R5
		BNE counting_loop_binary_0 ;jump to beginning of loop if a 1 is encountered
		
		;otherwise add one to the counting variable
		ADDS R7, R7, #1
		B counting_loop_binary_0
		
binary_0_loop_ended
		LDR R3, =ADDR_LCD_ASCII ;=ADDR_LCD_ASCII_2ND_LINE
		LDR R6, =ASCII_DIGIT_OFFSET
		ADDS R3, R3, R6
		STRB R7, [R3]
		MOVS R2, R0 ;restore the original diff value (yes I know about push and pop)
		
		BX LR
		

remove_lcd_colour
		;TODO: improve this, this is horrible
		LDR R6, =ADDR_LCD_COLOUR
		LDR R7, =DISPLAY_COLOUR_RED
		ADDS R6, R6, R7
		LDR R7, =0x0000
		STRH R7, [R6]
		LDR R6, =ADDR_LCD_COLOUR
		LDR R7, =DISPLAY_COLOUR_GREEN
		ADDS R6, R6, R7
		LDR R7, =0x0000
		STRH R7, [R6]
		LDR R6, =ADDR_LCD_COLOUR
		LDR R7, =DISPLAY_COLOUR_BLUE
		ADDS R6, R6, R7
		LDR R7, =0x0000
		STRH R7, [R6]
		BX LR
        
clear_lcd
        PUSH       {R0, R1, R2}
        LDR        R2, =0x0
clear_lcd_loop
        LDR        R0, =ADDR_LCD_ASCII
        ADDS       R0, R0, R2                       ; add index to lcd offset
        LDR        R1, =ASCII_DIGIT_CLEAR
        STR        R1, [R0]
        ADDS       R2, R2, #4                       ; increase index by 4 (word step)
        CMP        R2, #LCD_LAST_OFFSET             ; until index reached last lcd point
        BMI        clear_lcd_loop
        POP        {R0, R1, R2}
        BX         LR

write_bit_ascii
        PUSH       {R0, R1}
        LDR        R0, =ADDR_LCD_ASCII_BIT_POS 
        LDR        R1, =DISPLAY_BIT
        LDR        R1, [R1]
        STR        R1, [R0]
        POP        {R0, R1}
        BX         LR

        ENDP
        ALIGN


; ------------------------------------------------------------------
; End of code
; ------------------------------------------------------------------
        END
