# -----------------------------------------------------------------
# HM2 SW02 UND SW03 SKRIPT 2 / adel
# -----------------------------------------------------------------

# BEISPIEL VON SEITE 10 IN VORLESUNGSNOTIZEN
# NEWTON-VERFAHREN MIT SYMPY UND MIT DAEMPFUNG

import numpy as np
import sympy as sy

# Funktion und Jacobi-Matrix symbolisch erzeugen
from tools import encodeURL

"""
=======================================================================================================================
INPUT
=======================================================================================================================
"""

x1, x2, x3, x4, x5, x6, x7, x8, x9 = sy.symbols('x1, x2, x3, x4, x5, x6, x7, x8, x9')

# ACHTUNG: Für sinus/cosinus/Exponentialfunktion immer sy.sin/sy.cos/sy.exp/sy.ln/sy.abs verwenden!
f = sy.Matrix([
   x1 + x2**2 - x3**2 - 13,
   sy.ln(x2/4) + sy.exp(0.5*x3-1)-1,
   (x2-3)**2 - x3**3 + 7
])

x = sy.Matrix([x1, x2, x3])  # Wenn mehr oder weniger als 2 Variablen auftreten, diese Liste anpassen!
# Startwert als Array von Arrays
x0 = np.array([
    [1.5],
    [3],
    [2.5]
])

k_max = 0  # Maximale Alternativen für 𝛅 (vgl. Skript Seite 107)
imax = 5  # Maximale Anzahl Iterationen

# Funktion und Jacobi-Matrix numerisch umwandeln
# mit Input und Output als Spaltenvektoren in Form von 2x1-Arrays
Df = f.jacobian(x)
f = sy.lambdify([[[x1], [x2], [x3]]], f, 'numpy')  # Wenn mehr oder weniger als 2 Variablen auftreten, diese Liste anpassen!
Df = sy.lambdify([[[x1], [x2], [x3]]], Df, 'numpy') # Wenn mehr oder weniger als 2 Variablen auftreten, diese Liste anpassen!

"""
=======================================================================================================================
"""


print(Df)
url = "https://latex.codecogs.com/svg.image?" + encodeURL(str(sy.latex(Df)) + '\n')
print("LATEX:\n" + url)
# Matrix([[2*x1, 1], [1, 2*x2]])



print("=====================================================")
print('f(x0) =\n', f(x0))
# f(x0) =
#  [[-9]
#  [-5]]
print('Df(x0) =\n', Df(x0))
# Df(x0) =
#  [[2 1]
#  [1 2]]

# Newtonverfahren mit Daempfung numerisch durchfuehren

print('-------------------------------------')
print('x0 =\n', x0)
print(f'‖f(x0)‖₂ = √({str(f(x0)[0][0])}² + {str(f(x0)[1][0])}²) -> {str(np.linalg.norm(f(x0), 2))}')

for i in range(1, imax + 1):
    delta1 = np.linalg.solve(Df(x0), -f(x0))

    # Daempfung
    k = 0
    while (k <= k_max) and (np.linalg.norm(f(x0 + delta1 / 2 ** k), 2) >= np.linalg.norm(f(x0), 2)):
        k = k + 1
    if k == k_max + 1:
        k = 0
    x1 = x0 + delta1 / 2 ** k

    print('-------------------------------------')
    print('Iteration i =', i)
    print('Daempfungszahl k =', k)
    print(f'delta1 =\n {str(delta1)}')
    print('xi =\n', x1)
    print(f'‖f(x(n))‖₂ = √({str(f(x1)[0][0])}² + {str(f(x1)[1][0])}²) -> {str(np.linalg.norm(f(x1), 2))}')

    currX = np.linalg.norm(f(x0), 2)
    x0 = x1
    nextX = np.linalg.norm(f(x0), 2)

    isSmaller = np.all(np.less(nextX, currX))
    print(f'‖f(x(n+1))‖₂ < ‖f(x(n))‖₂ = {isSmaller} (True = gut)')
