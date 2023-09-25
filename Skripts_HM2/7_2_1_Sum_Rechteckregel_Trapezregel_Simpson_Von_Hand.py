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
b = sy.pi

n = 51
h_SumSimp = 1
h_GaussTrapez = 2

n_SumSimp = 2  # N

# for macaroon people use sympy.pi instead of math.pi
f_sympy = sy.sin(x)

"""
=======================================================================================================================
"""
# Float Casting just to prevent errors like np.pi and sy.pi where functions try to call stuff from those classes
a = float(a)
b = float(b)

h = (b - a) / n if verwendeSummierteRegeln else (b - a)
print(f"Schrittweite h = {h}")


def closed_range(start, stop, step=1):
    dir = 1 if (step > 0) else -1
    return range(start, stop + dir, step)


f = sy.lambdify(x, f_sympy, "numpy")
x_Data = np.arange(a, b + h, h)
x_SumSimp = np.arange(a, b + h, h_SumSimp)
x_GaussTrapez = np.arange(a, b + h, h_GaussTrapez)


def integrate_sympy(f, a=None, b=None):
    return sy.integrate(f, x) if a is None or b is None else sy.integrate(f, (x, a, b))


exactValue = integrate_sympy(f_sympy, a, b).evalf()

print(f"\n=========================================")
print("Exact:")
print(f"f(x) = {f_sympy}")
print(f"F(x) = {integrate_sympy(f_sympy)}")
print(f"Integral von {a} bis {b} = {exactValue}")

print(f"\n=========================================")
print(f"Sum Rechteckregel:" if verwendeSummierteRegeln else f"Rechteckregel:")

if verwendeSummierteRegeln:
    print(f"h*[i=0_Σ_n-1 f(x_i+h/2)]:")
else:
    print(f"f((a+b)/2) * (b-a):")
zwischenresultateRechteck = []
for i in range(0, len(x_Data) - 1 if verwendeSummierteRegeln else 1):
    zwischenresultateRechteck.append(f(x_Data[i] + float(h / 2)))
approxSumRechteck = h * sum(zwischenresultateRechteck)
print(f"{h}*({[res and res for res in zwischenresultateRechteck]})\n\t = {approxSumRechteck}")
print(f"Absoluter Fehler: {abs(approxSumRechteck - exactValue)}")

print(f"\n=========================================")
print(f"Sum Trapezregel:" if verwendeSummierteRegeln else f"Trapezregel:")

if verwendeSummierteRegeln:
    print(f"h*[(f(x0)+f(xn))/2 + i=1_Σ_n-1 f(x_i)]:")
else:
    print(f"(f(a)+f(b)) /2 * (b-a)")
zwischenresultateTrapez = [(f(x_Data[0]) + f(x_Data[-1])) / 2]
for i in range(1, len(x_Data) - 1 if verwendeSummierteRegeln else 2):
    if verwendeSummierteRegeln:
        zwischenresultateTrapez.append(f(x_Data[i]))
approxSumTrapezregel = h * sum(zwischenresultateTrapez)
print(f"{h}*({[res for res in zwischenresultateTrapez]})\n\t = {approxSumTrapezregel}")
print(f"Absoluter Fehler: {abs(approxSumTrapezregel - exactValue)}")

print(f"\n=========================================")
print(f"Simpsonregel:")
print(f"(b-a)/6 * [f(a) + 4 * f((a+b)/2) + f(b)]:")
approxSimpson = (b - a) / 6 * (f(a) + 4 * f((a + b) / 2) + f(b))
print(f"{(b - a)}/6 * [{f(a)} + 4 * {f((a + b) / 2)} + {f(b)})\n\t = {approxSimpson}")
print(f"Absoluter Fehler: {abs(approxSimpson - exactValue)}")

print(f"\n=========================================")
print(f"Sum Simpsonregel:")
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
print(f"Absoluter Fehler: {abs(approxSumSimpson - exactValue)}")

print(f"\n=========================================")
print(f"Gauss-Formel (Trapez):")
print(f"CHECK FORMEL IM SKRIPT S74")
alpha = h_GaussTrapez / (2 * math.sqrt(3))
print(f"alpha = h/(2 * sqrt(3)) = {alpha}")
alphaX0 = (a + b) / 2 - alpha
print(f"alphaX0 = (a + b) / 2 - alpha = {alphaX0}")
alphaX1 = (a + b) / 2 + alpha
print(f"alphaX1 = (a + b) / 2 + alpha = {alphaX1}")
approxGaussFormel = (f(alphaX0) + f(alphaX1)) / 2 * (b - a)
print(f"({f(alphaX0)} + {f(alphaX1)})/2  * {(b - a)}\n\t = {approxGaussFormel}")
print(f"Absoluter Fehler: {abs(approxGaussFormel - exactValue)}")


plt.figure(1)
plt.title(f"Absoluter Fehler für das Integral von {f_sympy}")
plt.hlines(y=approxSumRechteck, xmin=a, xmax=b, linewidth=2, color='r', label="Sum Rechteckregel" if verwendeSummierteRegeln else "Rechteckregel")
plt.hlines(y=approxSumTrapezregel, xmin=a, xmax=b, linewidth=2, color='g', label="Sum Trapezregel" if verwendeSummierteRegeln else "Trapezregel")
plt.hlines(y=approxSimpson, xmin=a, xmax=b, linewidth=2, color='b', label="Simpsonregel")
plt.hlines(y=approxSumSimpson, xmin=a, xmax=b, linewidth=2, color='y', label="Sum Simpsonregel")
plt.hlines(y=approxGaussFormel, xmin=a, xmax=b, linewidth=2, color='orange', label="Gauss-Formel")
plt.legend()
plt.grid()
plt.show()

exit()
