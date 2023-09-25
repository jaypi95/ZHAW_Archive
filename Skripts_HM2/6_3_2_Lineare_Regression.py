import numpy as np
import matplotlib.pyplot as plt

"""
=======================================================================================================================
INPUT
=======================================================================================================================
"""

x = np.array([0, 2, 4], dtype=np.float64)  # Messwerte xi
y = np.array([0, 4, 2], dtype=np.float64)  # Messwerte yi

"""
=======================================================================================================================
"""

print('Ansatz für lineare Regression: f(x) = ax + b = a * f1(x) + b * f2(x) mit f1(x) = x, f2(x) = 1')
print('Minimiere das Fehlerfunktional E(f)(a, b) = ∑[i = 1 .. n](yi - (a*xi + b))^2 '
      '(Quadrierte Differenz zwischen den Messwerten yi und den Schätzwerten von f(x))')
print('Die partiellen Ableitungen des Fehlerfunktionals nach a und nach b liefern zwei Gleichungen, als LGS Ax = r')
print('⎡ ∑xi^2  \t   ∑xi ⎤   ⎡ a ⎤   ⎡ ∑xi*yi ⎤\n' +
      '⎢        \t       ⎥ * ⎢   ⎥ = ⎢        ⎥\n' +
      '⎣ ∑xi    \t   n   ⎦   ⎣ b ⎦   ⎣ ∑yi    ⎦\n')

A = np.array([
    [np.sum(x ** 2), np.sum(x)],
    [np.sum(x), x.shape[0]]
])

r = np.array([np.sum(x * y), np.sum(y)])

print(f'A = \n{A}')
print(f'c = \n{np.array([["a"], ["b"]], dtype=str)}')  # Hilfswert
print(f'r = {r}')

print('LGS wird gelöst...\n')

ab = np.linalg.solve(A, r)
a = round(ab[0], 2)
b = round(ab[1], 2)

print(f'a = {a}, b = {b}')
print(f'Die gesuchte Ausgleichsgerade ist also f(x) = {a}x + {b}')

xx = np.arange(x[0], x[-1], (x[-1] - x[0]) / 10000)  # Plot-X-Werte
yy = a * xx + b

plt.figure(1)
plt.grid()
plt.plot(xx, yy, zorder=0)
plt.scatter(x, y, marker='x', color='r', zorder=1)
plt.show()

exit()
