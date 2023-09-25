EMPTY      :=
SPACE      := $(EMPTY) $(EMPTY)
NL         := $(EMPTY)\\n$(EMPTY)

LABS       := $(sort $(wildcard P[0-9][0-9]*))
EXAMPLE    := $(if $(firstword $(LABS)),$(firstword $(LABS)),"Pxx")

all: main.o

main.o: main.c Makefile
		gcc main.c -o ../tagepromonat