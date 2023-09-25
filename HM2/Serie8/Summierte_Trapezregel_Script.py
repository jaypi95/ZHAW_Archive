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


def sum_trapez(f, a, b, n):
    h = int((b - a) / n)
    T = (f(a) + f(b)) / 2
    for i in range(0, n):
        x = a + i * h
        T = T + f(x)
    T = h * T
    return T


R = sum_trapez(f, a, b, n)
abs_fehler = np.abs(R - I)

print("Trapezregel: ")
print(R)
print("Mit sympy: ")
print(I)
print("Absoluter Fehler: ")
print(abs_fehler)
