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


def sum_simpson(f, a, b, n):
    h = int((b - a) / n)
    S = f(a) + 4*f(a + h/2) + f(b)
    for i in range(1, n):
        x = a + i * h
        S = S + 2*f(x) + 4*f(x + h/2)
    S = h/6*S
    return S


R = sum_simpson(f, a, b, n)
abs_fehler = np.abs(R - I)

print("Simpsonregel: ")
print(R)
print("Mit sympy: ")
print(I)
print("Absoluter Fehler: ")
print(abs_fehler)
