import math
import numpy as np
import scipy.integrate
import matplotlib.pyplot as plt
import sympy as sy

a, b, c, d, x, y, z, n = sy.symbols('a b c d x y z n')

"""
hand Schrittweite hand H h (h wird of als bezeichnung für schrittweite benutzt)
"""

"""
=======================================================================================================================
INPUT
=======================================================================================================================
"""

# f2 = a + 2 * x + c * x ** 2 + 0.5 * x ** 3
# f1 = 6 + b * (x - 2) + 2 * (x - 2) ** 2 + d * (x - 2) ** 3
f = 10 * x * sy.sqrt(x)

a = 5
b = 20
tol = 1e-5  # Toleranz

typeOfQuadraturformel = 3  # 1 = Rechteckregel, 2 = Trapezregel, 3 = Simpsonregel

"""
=======================================================================================================================
"""
# default values for Rechteckregel
korrespondierenderNenner = 24  # 24 = Rechteckregel, 12 = Trapezregel, 2880 = Simpsonregel
anzahlAbleitungen = 2  # 2 = Rechteckregel ODER Trapezregel, 4 = Simpsonregel

if typeOfQuadraturformel == 2:
    korrespondierenderNenner = 12
    anzahlAbleitungen = 2
elif typeOfQuadraturformel == 3:
    korrespondierenderNenner = 2880
    anzahlAbleitungen = 4

print(f"Die Funktion {anzahlAbleitungen} abgeleitet:")
dnf_SyDiff = [f]
dnf_SyLambdify = [f]
for i in range(0, anzahlAbleitungen + 1):
    last_dnf_SyDiff = dnf_SyDiff[i]
    dnf_SyDiff.append(sy.diff(last_dnf_SyDiff, x))
    dnf_SyLambdify.append(sy.lambdify([x], last_dnf_SyDiff, "numpy"))
    print(f"f^({i})(x) = {dnf_SyDiff[i]}")
print("")

dfl = lambda x: dnf_SyLambdify[2](x)
maxFuncInput = a if np.abs(dfl(a)) > np.abs(
    dfl(b)) else b  # Mega viel gschied für armando danke bro gump us balkon nur in montreux
maxFuncOutout = max(dfl(a), dfl(b))
print(f"max[{a}, {b}](|f''(x)|) = {maxFuncOutout} => x = {maxFuncInput}")

rightHandSide = np.abs(b - a) * maxFuncOutout / korrespondierenderNenner

literalH = "h⁴" if typeOfQuadraturformel == 3 else "h²"

print(f"tol < ({literalH}/{korrespondierenderNenner}) * (b - a)*max[{a}, {b}] = {tol} < ({maxFuncOutout} * {literalH} * {np.abs(b - a)}) / {korrespondierenderNenner}")

newLeftHandSide = 1 / rightHandSide

print(f"{newLeftHandSide} * {tol} < {literalH}")
print(f"√(b - a) < h")
print(f" // h = (b - a) / n =>  h = ({b} - {a}) / n")
print(f"√({newLeftHandSide} * {tol}) < {(np.abs(b - a))} / n")

calculatedSchrittweite = np.abs(b - a) / np.sqrt(newLeftHandSide * tol)
print(f"n = {b - a} / √({newLeftHandSide} * {tol}) = {calculatedSchrittweite}")
print(f"Anzahl benötigter Schritte: {math.ceil(calculatedSchrittweite)}")

# fixme:
# integral, _ = scipy.integrate.quad(f, a, b, epsabs=tol)
#
# error = tol + 1
# while error > tol:
#     n += 1
#     error = np.abs(integral - Rf(f, a, b, n))
#
# print('RECHTECK-REGEL')
# print(f'Wert des Integrals (scipy) = {str(integral)}')
# print(f'Wert mit Rechteckregel     = {str(Rf(f, a, b, n))}')
# print(f'Absoluter Fehler = {str(error)}')
# print('n = ' + str(n))
# h = (b - a) / n
# print(f"h = (b - a) / n = ({b} - {a}) / {n} => {h}")
#
# print('-----------------------------------------------')
