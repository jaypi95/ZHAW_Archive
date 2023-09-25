import os
import numpy as np
import matplotlib.pyplot as plt
from fractions import Fraction

from rich import box
from rich.console import Console
from rich.table import Table

from tools import *

console = getCompatibleConsole()

"""
=======================================================================================================================
INPUT
=======================================================================================================================
"""

#x = np.array([4, 6, 8, 10], dtype=np.float64)  # Stützpunkte (Knoten) xi
#y = np.array([6, 3, 9, 0], dtype=np.float64)  # Stützpunkte (Knoten) yi
#x_int = 9  # Zu interpolierender Wert


x = np.array([0, 0.5, 2, 3], dtype=np.float64)  # Stützpunkte (Knoten) xi
y = np.array([1, 2, 2.5, 0], dtype=np.float64)  # Stützpunkte (Knoten) yi
x_int = 1  # Zu interpolierender Wert

"""
=======================================================================================================================
"""

# ak, bk und ck
# a0 = y0 = 1.0
# a1 = y1 = 2.0
# a2 = y2 = 2.5
#
# b0 = 429 / 200 = 2.1447368421052633
# b1 = 393 / 200 = 1.9649122807017545
# b2 = -741 / 500 = -1.4824561403508771
#
# c0 = 0
# c1 = -0.868421052631579
# c2 = -1.5263157894736843

# Position zu t(1)
#  2.747076023391813

n = x.shape[0] - 1  # Anzahl Spline-Polynome
print('n = ' + str(n))

print(f'Die Natürlichen kubischen Spline-Polynome S0(x) .. S{n - 1}(x) haben die Form:')
print('Si(x) = a_i + b_i(x - x_i) + c_i(x - x_i)^2 + d_i(x - x_i)^3')
print("=====================================")
for i in range(n):
    print(f'S{i}(x) = a_{i} + b_{i}(x - {x[i]}) + c_{i}(x - {x[i]})^2 + d_{i}(x - {x[i]})^3')

print("=====================================\n")
print('=> Bestimme die Koeffizienten a_0..a_2, b_0..b_2, c_0..c_2, d_0..d_2')

print('\n1. ai = yi')

a = [y[i] for i in range(n)]

for i in range(n):
    print(f'\ta{i} = y{i} = {y[i]}')

print('\n2. h_i = x(i+1) - x_i Hilfsgrössen (Distanz zwischen Stützstellen)')

h = [x[i + 1] - x[i] for i in range(n)]

for i in range(n):
    print(f'\th{i} = {x[i + 1]} - {x[i]} = {h[i]}')

print('\n3. c_0 = 0, c_n = 0 // per default so')
c = [0 for i in range(n + 1)]

tridiagonalMatrixLatex = '\\begin{pmatrix} 2(h_{0} + h_{1}) & h_{1} &  &  \\\\ h_{1} & 2(h_{1} + h_{2}) & h_{2}& \\\\ & h_{2} & 2(h_{2} + h_{3}) & h_{3}& \\\\ & \\ddots & \\ddots & \\ddots & \\\\  && h_{n-3} & 2(h_{n-3} + h_{n-2}) & h_{n-2}\\\\&&& h_{n-2} & 2(h_{n-2} + h_{n-1}) \\\\ \\end{pmatrix}'
url = "https://latex.codecogs.com/svg.image?" + encodeURL(tridiagonalMatrixLatex)

print('\n4. Berechne c_1 .. c_(n-1) aus dem LGS Ac = z (vgl. Matrix unter Kasten Skript S. 120)')
print(f'\tA hat auf der Diagonalen die Elemente 2*(h_(i-1) + h_i und h_i unter und über der Diagonalen (vgl. Skript S. 120 oder LATEX: {url}')
print('\tc = [c_1 c_2 .. c_(n-1)]')
print('\tz = [3 * (y_(i+1) - y_i) / (h_i) - 3 * (y_i - y_(i-1)) / (h_(i-1)), usw.]')


# Linear system for finding c1, ... cn-1
A = np.full((n - 1, n - 1), 0)
for i in range(n - 1):
    # Diagonal element
    A[i, i] = 2 * (h[i] + h[i + 1])

    if i < n - 2:
        # Element below diagonal
        A[i + 1, i] = h[i + 1]

        # Element right of diagonal
        A[i, i + 1] = h[i + 1]

print('\tA = \n' + str(A))

z = [3 * (y[i + 2] - y[i + 1]) / h[i + 1] - 3 * (y[i + 1] - y[i]) / h[i] for i in range(n - 1)]

print('\tz = \n' + str(z))
print('\tLöse damit Ac = z')

c[1:-1] = np.linalg.solve(A, z)

for i in range(n):
    print(f'\tc{i} = {c[i]}')

print('\n5. b_i = (y_(i+1) - y_i) / h_i - (h_i / 3) * (c_(i + 1) + 2 * c_i)')

b = [(y[i + 1] - y[i]) / (h[i]) - (h[i] / 3) * (c[i + 1] + 2 * c[i]) for i in range(n)]

for i in range(n):
    print(f'\tb{i} = {Fraction(round(b[i], 3)).limit_denominator()} = {b[i]}')

print('\n6. d_i = (1 / (3 * h_i)) * (c_(i+1) - c_i)')

d = [(1 / (3 * h[i])) * (c[i + 1] - c[i]) for i in range(n)]

for i in range(n):
    print(f'\td{i} = {Fraction(round(d[i], 3)).limit_denominator()} = {d[i]}')

print('\nDiese Werte jetzt einsetzen in')
print('S_i(x) = a_i + b_i(x - x_i) + c_i(x - x_i)^2 + d_i(x - x_i)^3:')

for i in range(n):
    print(f'\tS{i}(x) = {a[i]} + {b[i]} * (x - {x[i]}) + {c[i]} * (x - {x[i]})^2 + {d[i]} * (x - {x[i]})^3')

print('\nBestimmen, welches Spline-Polynom verwendet werden muss (Vergleich mit den Stützstellen)')

i = np.max(np.where(x <= x_int))  # Finde die Stützstelle, deren x-Wert am grössten, aber gerade noch kleiner ist als x_int
print(f'Für x_int = {x_int} muss S{i} verwendet werden.')

y_int = a[i] + b[i] * (x_int - x[i]) + c[i] * (x_int - x[i]) ** 2 + d[i] * (x_int - x[i]) ** 3

print(f'S{i}({x_int}) = {y_int}')

# Ableitung 1 von S1
def x_abl1(x):
    return -0.43859649 * x ** 2 - 1.29824551 * x + 2.7236841575

def x_abl2(x):
    return -0.87719298 * x - 1.29824551


# PLOTTING
xx = np.arange(x[0], x[-1], (x[-1] - x[0]) / 10000)  # Plot-X-Werte

# Bestimme für jeden x-Wert, welches Spline-Polynom gilt
xxi = [np.max(np.where(x <= xxk)) for xxk in xx]

# Bestimme die interpolierten Werte für jedes x
yy = [a[xxi[k]] + b[xxi[k]] * (xx[k] - x[xxi[k]]) + c[xxi[k]] * (xx[k] - x[xxi[k]]) ** 2 + d[xxi[k]] * (
        xx[k] - x[xxi[k]]) ** 3 for k in range(xx.shape[0])]
#
# plt.figure(1)
# plt.grid()
# plt.plot(xx, yy, zorder=0, label='spline interpolation')
# plt.scatter(x, y, marker='x', color='r', zorder=1, label='measured')
# plt.scatter(x_int, y_int, marker='X', color='fuchsia', label='interpolated')
# plt.legend()
# plt.title(f'Naturliche Kubische Spline (Algo), x_int = {x_int}')
# plt.show()

plt.plot(xx, x_abl1(xx), zorder=0, label='spline interpolation')
plt.show()

results = []
# pretty print results
for i in range(n):
    results.append(f'S{i}({x_int}) = {y_int}')


table = Table(title="Hilfstabelle Original", expand=True, width=100)

table.add_column("i", justify="right", style="cyan", no_wrap=True)
for i in range(x.size):
    table.add_column(str(i), justify="right", style="cyan", no_wrap=True)

table.add_row("x_i", *[str(round(item, 4)) for item in x])
table.add_row("y_i", *[str(round(item, 4)) for item in y])
table.add_row("h_i", *[str(round(item, 4)) for item in h])
table.add_row("a_i", *[str(round(item, 4)) for item in a])
table.add_row("b_i", *[str(round(item, 4)) for item in b])
table.add_row("c_i", *[str(round(item, 4)) for item in c])
table.add_row("d_i", *[str(round(item, 4)) for item in d])

console.print(table)

# make the same table but transposed
tableT = Table(title="Hilfstabelle Transponiert", expand=True)
for i in ["i", "x_i", "y_i", "h_i", "a_i", "b_i", "c_i", "d_i"]:
    tableT.add_column(i, justify="right", style="cyan", no_wrap=True, width=64)

for index, item in enumerate(zip(*[x, y, h, a, b, c, d])):
    tableT.add_row(str(index), *[str(i) for i in item])

console.print(tableT)



print(f'Geschwindigkeit an Stelle t = 1 = {x_abl1(x_int)}')
print(f'Beschleunigung an Stelle t = 1 = {x_abl2(x_int)}')


exit()


