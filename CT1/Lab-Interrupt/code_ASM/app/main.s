;* ----------------------------------------------------------------------------
;* --  _____       ______  _____                                              -
;* -- |_   _|     |  ____|/ ____|                                             -
;* --   | |  _ __ | |__  | (___    Institute of Embedded Systems              -
;* --   | | | '_ \|  __|  \___ \   Zurich University of                       -
;* --  _| |_| | | | |____ ____) |  Applied Sciences                           -
;* -- |_____|_| |_|______|_____/   8401 Winterthur, Switzerland               -
;* ----------------------------------------------------------------------------
;* --
;* -- Project     : CT1 - Lab 12
;* -- Description : Reading the User-Button as Interrupt source
;* -- 				 
;* -- $Id: main.s 5082 2020-05-14 13:56:07Z akdi $
;* -- 		
;* ----------------------------------------------------------------------------


                IMPORT init_measurement
                IMPORT clear_IRQ_EXTI0
                IMPORT clear_IRQ_TIM2

; -----------------------------------------------------------------------------
; -- Constants
; -----------------------------------------------------------------------------

                AREA myCode, CODE, READONLY

                THUMB

REG_GPIOA_IDR       EQU  0x40020010
LED_15_0		    EQU  0x60000100
LED_16_31			EQU  0x60000102
REG_CT_7SEG         EQU  0x60000114
REG_SETENA0         EQU  0xe000e100


; -----------------------------------------------------------------------------
; -- Main
; -----------------------------------------------------------------------------             
main            PROC
                EXPORT main


                BL   init_measurement    

                ; Configure NVIC (enable interrupt channel)
                ; STUDENTS: To be programmed
				LDR R0, =REG_SETENA0
				LDR R1, =0x10000000
				STR R1, [R0]
				
				LDR R0, =REG_SETENA0
				MOVS R1, #64
				STR R1, [R0]
				
				

                ; END: To be programmed 

                ; Initialize variables
                ; STUDENTS: To be programmed	
				MOVS R4, #0x00
				LDR R5, =interruptCounter
				STR R4, [R5]

                ; END: To be programmed
				
loop
                ; Output counter on 7-seg
				; STUDENTS: To be programmed
				LDR R6, =REG_CT_7SEG
				LDR R7, =bufferCounter
				LDR R7, [R7]
				STRB R7, [R6, #0]

                ; END: To be programmed

                B    loop

				
                ENDP

 
; -----------------------------------------------------------------------------
; Handler for EXTI0 interrupt
; -----------------------------------------------------------------------------
                 ; STUDENTS: To be programmed
EXTI0_IRQHandler\
				PROC
				EXPORT EXTI0_IRQHandler
				PUSH{R4, R5, LR}
				BL clear_IRQ_EXTI0
				;Aufsummieren
				LDR R4, =interruptCounter
				LDR R5, [R4]
				ADDS R5, R5, #1
				STR R5, [R4]
				POP{R4, R5, PC}
				ENDP
				
				
				 
                 ; END: To be programmed

 
; -----------------------------------------------------------------------------                   
; Handler for TIM2 interrupt
; -----------------------------------------------------------------------------
				; STUDENTS: To be programmed
TIM2_IRQHandler\
				PROC
				EXPORT TIM2_IRQHandler
				PUSH{R4, R5, LR}
				BL clear_IRQ_TIM2
				;LED
				LDR R4, =LED_16_31
				LDR R5, [R4]
				MVNS R5, R5
				STR R5, [R4]
				;Puffer
				LDR R4, =interruptCounter
				LDR R5, = bufferCounter
				LDR R6, [R4]
				STR R6, [R5]
				LDR R5, =0x00
				STR R5, [R4]
				POP{R4, R5, PC}
				ENDP

                ; END: To be programmed
                ALIGN

; -----------------------------------------------------------------------------
; -- Variables
; -----------------------------------------------------------------------------

                AREA myVars, DATA, READWRITE

                ; STUDENTS: To be programmed
interruptCounter SPACE 10
bufferCounter    SPACE 10

                ; END: To be programmed


; -----------------------------------------------------------------------------
; -- End of file
; -----------------------------------------------------------------------------
                END
