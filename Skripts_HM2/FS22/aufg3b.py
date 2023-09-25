import numpy as np
import sympy as sy
import math
import matplotlib.pyplot as plt
from sympy import symbols

x, y = symbols('x y')

"""
=======================================================================================================================
INPUT
=======================================================================================================================
"""

verwendeSummierteRegeln = 1  # 0 = nein, 1 = ja

a = 0
b = 4

n = 51
h_SumSimp = 4

n_SumSimp = 2  # N

#use sympy.pi instead of math.pi
f_sympy = 6 * x ** 2 - 2 * x

"""
=======================================================================================================================
"""
# Float Casting just to prevent errors like np.pi and sy.pi where functions try to call stuff from those classes
a = float(a)
b = float(b)

h = 4
print(f"Schrittweite h = {h}")


def closed_range(start, stop, step=1):
    dir = 1 if (step > 0) else -1
    return range(start, stop + dir, step)


f = sy.lambdify(x, f_sympy, "numpy")
x_Data = np.arange(a, b + h, h)
x_SumSimp = np.arange(a, b + h, h_SumSimp)


def integrate_sympy(f, a=None, b=None):
    return sy.integrate(f, x) if a is None or b is None else sy.integrate(f, (x, a, b))


exactValue = integrate_sympy(f_sympy, a, b).evalf()

print(f"\n=========================================")
print(f"Sum Simpsonregel mit h = 4:")
print(f"h/6 * [f(x0) + 2 * i=1_Σ_n-1 f(x_i) + 4 i=0_Σ_n-1 f(x_i+h/2) + f(xn)]:")
zwischenresultateSumSimpson1 = []
for i in closed_range(1, n_SumSimp - 1):
    zwischenresultateSumSimpson1.append(f(x_SumSimp[i]))

zwischenresultateSumSimpson2 = []
for i in closed_range(0, n_SumSimp - 1):
    zwischenresultateSumSimpson2.append(f(x_SumSimp[i] + h_SumSimp / 2))
approxSumSimpson = (h_SumSimp / 6) * (f(x_Data[0]) + 2 * sum(zwischenresultateSumSimpson1) + 4 * sum(zwischenresultateSumSimpson2) + f(x_Data[-1]))

print(
    f"{h_SumSimp}/6 * ({f(x_Data[0])} + 2*{[res for res in zwischenresultateSumSimpson1]} + 4*{[res for res in zwischenresultateSumSimpson2]} + {f(x_Data[-1])})\n\t= {approxSumSimpson}")


x, y = symbols('x y')

"""
=======================================================================================================================
INPUT
=======================================================================================================================
"""

verwendeSummierteRegeln = 1  # 0 = nein, 1 = ja

a = 0
b = 4

n = 51
h_SumSimp = 1

n_SumSimp = 2  # N

#use sympy.pi instead of math.pi
f_sympy = 6 * x ** 2 - 2 * x

"""
=======================================================================================================================
"""
# Float Casting just to prevent errors like np.pi and sy.pi where functions try to call stuff from those classes
a = float(a)
b = float(b)

h = 1
print(f"Schrittweite h = {h}")

def closed_range(start, stop, step=1):
    dir = 1 if (step > 0) else -1
    return range(start, stop + dir, step)


f = sy.lambdify(x, f_sympy, "numpy")
x_Data = np.arange(a, b + h, h)
x_SumSimp = np.arange(a, b + h, h_SumSimp)


def integrate_sympy(f, a=None, b=None):
    return sy.integrate(f, x) if a is None or b is None else sy.integrate(f, (x, a, b))


exactValue = integrate_sympy(f_sympy, a, b).evalf()


print(f"\n=========================================")
print(f"Sum Simpsonregel mit h = 1:")
print(f"h/6 * [f(x0) + 2 * i=1_Σ_n-1 f(x_i) + 4 i=0_Σ_n-1 f(x_i+h/2) + f(xn)]:")
zwischenresultateSumSimpson1 = []
for i in closed_range(1, n_SumSimp - 1):
    zwischenresultateSumSimpson1.append(f(x_SumSimp[i]))

zwischenresultateSumSimpson2 = []
for i in closed_range(0, n_SumSimp - 1):
    zwischenresultateSumSimpson2.append(f(x_SumSimp[i] + h_SumSimp / 2))
approxSumSimpson = (h_SumSimp / 6) * (f(x_Data[0]) + 2 * sum(zwischenresultateSumSimpson1) + 4 * sum(zwischenresultateSumSimpson2) + f(x_Data[-1]))

print(
    f"{h_SumSimp}/6 * ({f(x_Data[0])} + 2*{[res for res in zwischenresultateSumSimpson1]} + 4*{[res for res in zwischenresultateSumSimpson2]} + {f(x_Data[-1])})\n\t= {approxSumSimpson}")


## Kommentar zu 2b
# Es gibt eine grosse Differenz in der Lösung je nachdem wie man h ansetzt
# Ich kann die Beobachtung aus a) jedoch nicht begründen
exit()

