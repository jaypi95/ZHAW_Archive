# -*- coding: utf-8 -*-

import numpy as np
import timeit

def fact_rec(n):
    # y = fact_rec(n) berechnet die Fakultät von n als fact_rec(n) = n * fact_rec(n -1) mit fact_rec(0) = 1
    # Fehler, falls n < 0 oder nicht ganzzahlig
    if n < 0 or np.trunc(n) != n:
        raise Exception('The factorial is defined only for positive integers')
        
    if n <= 1:
        return 1
    else:
        return n * fact_rec(n - 1)
    
def fact_for(n):
    # Fehler, falls n < 0 oder nicht ganzzahlig
    if n < 0 or np.trunc(n) != n:
        raise Exception('The factorial is defined only for positive integers')
        
    factorial = 1;
        
    for factor in range(1, n + 1):
        factorial = factor * factorial
        
    return factorial


t_rec = timeit.repeat("fact_rec(500)", "from __main__ import fact_rec", number=10)
t_for = timeit.repeat("fact_for(500)", "from __main__ import fact_for", number=10)

print(t_rec)
print(t_for)

print("Average factor of calculation time between recursive and iterative approach: ")
print(np.average(t_rec) / np.average(t_for))

print([str(n) + "! = " + str(fact_for(n)) for n in range(190, 201)])
print("float(170!) = " + str(float(fact_for(170))))
print("float(171!) = " + str(float(fact_for(171))))
