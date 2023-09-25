import numpy as np
import sympy as sy

from tools import compareArraySizes

"""
=======================================================================================================================
INPUT
=======================================================================================================================
"""

# ENTWEDER DATENSÄTZE ANGEBEN
# x = np.array([
#     0,
#     800,
#     1200,
#     1400,
#     2000,
#     3000,
#     3400,
#     3600,
#     4000,
#     5000,
#     5500,
#     6370
# ], dtype=np.float64)
#
# y = np.array([
#     13000,
#     12900,
#     12700,
#     12000,
#     11650,
#     10600,
#     9900,
#     5500,
#     5300,
#     4750,
#     4500,
#     3300
# ], dtype=np.float64)

# f = y * 4 * np.pi * (1000 * x) ** 2  # Zu berechnende Funktion

# ODER ALLGEMEIN DEFINIEREN
x = np.arange(0, np.pi)
f = lambda x: np.cos(x)
y = f(x)

"""
=======================================================================================================================
"""
# F = sy.log(x ** 2)
# df = sy.diff(F, x)

compareArraySizes(x, y)

n = np.size(x) - 1
T = 0
for i in range(n):

    if isinstance(f, np.ndarray):
        T = T + (x[i + 1] - x[i]) * (f[i + 1] + f[i]) / 2
    else:
        T = T + (x[i + 1] - x[i]) * (f(x[i + 1]) + f(x[i])) / 2

    print(f"Iteration {i}")
    print(f"x[{i + 1}] - x[{i}] = {x[i + 1] - x[i]}")

    if isinstance(f, np.ndarray):
        print(f"y[{i + 1}] + y[{i}] = {y[i + 1] + y[i]}")
    else:
        print(f"y[{i + 1}] + y[{i}] = {f(x[i + 1]) + f(x[i])}")

    print(f'T_{i} = {T}')
    print("==========================\n")

print('\n')
print(f"Berechnetes T: {T}")
print(f"Anzahl der Iterationen: {n}")
