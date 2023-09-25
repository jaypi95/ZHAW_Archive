import numpy as np
import sympy as sy


# Die zu berechnende Funktion
def f(x):
    return 10 * pow(x, -(3 / 2))


# Exaktes Integral
v = sy.Symbol('v')
I = sy.integrate(f(v), (v, 5, 20))

# Parameter
a = 5  # untere Grenze
b = 20  # obere Grenze
n = 5  # Anzahl Schritte


def sum_rechteck(f, a, b, n):
    h = int((b - a) / n)
    R = 0
    for i in range(0, n):
        x = a + i * h
        R = R + f(x + h / 2)
    R = h * R
    return R


R = sum_rechteck(f, a, b, n)
abs_fehler = np.abs(R - I)

print("Rechteckregel: ")
print(R)
print("Mit sympy: ")
print(I)
print("Absoluter Fehler: ")
print(abs_fehler)