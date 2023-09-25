from math import ceil

import numpy as np
import scipy.integrate

"""
=======================================================================================================================
INPUT
=======================================================================================================================
"""
# constants for probe SEP fs20
u = 2000
m0 = 10000
q = 100
g = 9.8


# Zu integrierende Funktion Marokkaner ACHTUNG: Für sinus/cosinus/Exponentialfunktion immer np.sin/np.cos/np.exp/np.log/np.abs verwenden!
def f(x):
    return 6 * x ** 2 - 2 * x
    #return 10 / (-x * np.sqrt(x))



# Abbruchkriterium
#break_criteria = "tol" # loop until desired tolerance
#break_criteria = "n" # loop n times (h gets calculated automatically)
break_criteria = "h"  # loop with step size h (n gets calculated automatically

tol = 10e-3  # Toleranz
h = 4
a = 4  # Obere Grenze der Integration
b = 0  # Untere Grenze der Integration

nValues = [
    4,  # Rechteck-Regel
    4,  # Trapez-Regel
    4  # Simpson-Regel
]

"""
=======================================================================================================================
"""


def Rf(f, a, b, n, h):
    xi = np.array([a + i * ((b - a) / n) for i in range(0, n)], dtype=np.float64)

    return h * np.sum(f(xi + (h / 2)))


def Tf(f, a, b, n, h):
    xi = np.array([a + i * ((b - a) / n) for i in range(1, n)], dtype=np.float64)

    return h * ((f(a) + f(b)) / 2 + np.sum(f(xi)))


def Sf(f, a, b, n, h):
    xi_1_n_m1 = np.array([a + i * ((b - a) / n) for i in range(1, n)], dtype=np.float64)  # xi from 1 to n-1
    xi_1_n = np.array([a + i * ((b - a) / n) for i in range(1, n + 1)], dtype=np.float64)  # xi from 1 to n
    xi_m1_1_n = np.array([a + (i - 1) * ((b - a) / n) for i in range(1, n + 1)], dtype=np.float64)  # x(i-1) from 1 to n

    return (h / 3) * ((1 / 2) * f(a) + np.sum(f(xi_1_n_m1)) + 2 * np.sum(f((xi_m1_1_n + xi_1_n) / 2)) + (1 / 2) * f(b))


# Only supported for n = 1, 2, 3
def Gauss_Formula(f, a, b, n):
    if n == 1:
        return (b - a) * f((b + a) / 2.0)
    if n == 2:
        return ((b - a) / 2.0) * (f((-1 / np.sqrt(3.0)) * ((b - a) / 2.0) + ((b + a) / 2.0)) + f(
            (1 / np.sqrt(3.0)) * ((b - a) / 2.0) + ((b + a) / 2.0)))
    if n == 3:
        return ((b - a) / 2.0) * ((5.0 / 9.0) * f(-np.sqrt(0.6) * ((b - a) / 2.0) + ((b + a) / 2.0)) + (8.0 / 9.0) * f(
            (b + a) / 2.0) + (5.0 / 9.0) * f(np.sqrt(0.6) * ((b - a) / 2.0) + ((b + a) / 2.0)))
    return 0


integral, _ = scipy.integrate.quad(f, a, b, epsabs=tol)

# --------------------------------------------------------------------
# Rechteck-Regel

error = tol + 1
if break_criteria == "tol":
    n = 1
    while error > tol:
        n += 1
        h = (b - a) / n
        error = np.abs(integral - Rf(f, a, b, n, h))
elif break_criteria == "n":
    n = nValues[0]
    h = (a - b) / n
elif break_criteria == "h":
    n = ceil((a - b) / h)

print('RECHTECK-REGEL')
print(f'Wert des Integrals (scipy) = {str(integral)}')
print(f'Wert mit Rechteckregel     = {str(Rf(f, a, b, n, h))}')
print(f'Absoluter Fehler = {str(error)}')
print('n = ' + str(n))
print(f"h = (b - a) / n = ({a} - {b}) / {n} => {h}")

print('-----------------------------------------------')

# --------------------------------------------------------------------
# Trapez-Regel
error = tol + 1
if break_criteria == "tol":
    n = 1
    while error > tol:
        n += 1
        h = (b - a) / n
        error = np.abs(integral - Tf(f, a, b, n, h))
elif break_criteria == "n":
    n = nValues[0]
    h = (a - b) / n
elif break_criteria == "h":
    n = ceil((a - b) / h)

print('TRAPEZ-REGEL')
print(f'Wert des Integrals (scipy) = {str(integral)}')
print(f'Wert mit Trapezregel       = {str(Tf(f, a, b, n, h))}')
print(f'Absoluter Fehler = {str(error)}')
print(f'n = {str(n)}')
print(f"h = (b - a) / n = ({a} - {b}) / {n} => {h}")

print('-----------------------------------------------')

# --------------------------------------------------------------------
# Simpson-Regel

error = tol + 1
if break_criteria == "tol":
    n = 1
    while error > tol:
        n += 1
        h = (b - a) / n
        error = np.abs(integral - Sf(f, a, b, n, h))
elif break_criteria == "n":
    n = nValues[2]
    h = (a - b) / n
elif break_criteria == "h":
    n = ceil((a - b) / h)

print('SIMPSON-REGEL')
print(f'Wert des Integrals (scipy) = {str(integral)}')
print(f'Wert mit Simpson-Regel     = {str(Sf(f, a, b, n, h))}')
print(f'Absoluter Fehler = {str(error)}')
print(f'n = {str(n)}')
print(f"h = (b - a) / n = ({a} - {b}) / {n} => {h}")
print('-----------------------------------------------')

# --------------------------------------------------------------------
# Gauss-Formeln
for n in range(1, 4):
    error = np.abs(integral - Gauss_Formula(f, a, b, n))
    print(f'GAUSS-FORMEL mit n = {n}')
    print(f'Wert des Integrals (scipy) = {str(integral)}')
    print(f'Wert mit Gauss-Formel (n = {n}) = {str(Gauss_Formula(f, a, b, n))}')
    print(f'Absoluter Fehler = {str(error)}')
    print()
