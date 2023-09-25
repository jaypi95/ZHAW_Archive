import numpy as np
import matplotlib.pyplot as plt

"""
=======================================================================================================================
INPUT
=======================================================================================================================
"""

x = np.array([0, 1, 2, 3, 4, 5], dtype=np.float64)
y = np.array([1/0.54, 1/0.44, 1/0.28, 1/0.18, 1/0.12, 1/0.08], dtype=np.float64)

"""
=======================================================================================================================
"""

print('Ansatz: f(x) = a*x^2 + b*x + c')
print('\tf1(x) = x^2\n\tf2(x) = x\n\tf3(x) = 1')

f1 = x ** 2
f2 = [1 for xi in x]
f3 = [1 for xi in x]

print(f'\tf1(x) = {f1}\n\tf2(x) = {f2}\n\tf3(x) = {f2}')

print('\nKonstruiere die Matrix A, so dass in Spalte 1 die Werte der Funktion f1(x) ausgewertet für alle xi stehen, usw. für die weiteren Spalten mit f2(x) und f3(x)')

A = np.array([f1, f2]).T
#A = np.array([f1, f2, f3]).T
print(f'A = \n{A}')

print('\nFühre für A eine QR-Zerlegung durch, Q * R = A.')
Q, R = np.linalg.qr(A)
print(f'Q = \n{Q}\nR = {R}')

print('\nLöse das LGS R * λ = QT * y')

coeff_direct = np.linalg.solve(A.T @ A, A.T @ y)
coeff_qr = np.linalg.solve(R, Q.T @ y)
coeff_polyfit = np.polyfit(x, y, A.shape[1] - 1)

error_direct = np.linalg.norm(y - A @ coeff_direct, 2) ** 2
error_qr = np.linalg.norm(y - A @ coeff_qr, 2) ** 2
error_polyfit = np.linalg.norm(y - A @ coeff_polyfit, 2) ** 2

print(f'\n\nMit direktem Lösen von AT * A * λ = AT * y: λ = {str(coeff_direct)}')
print(f'Mit QR-Zerlegung: λ = {str(coeff_qr)}')
print(f'Koeffizienten wenn mit numpy polyfit Grad 2 gelöst: λ = {str(coeff_polyfit)}')

print(f'\nKonditionszahl von AT * A = {str(np.linalg.cond(A.T @ A, np.inf))}')
print(f'Konditionszahl von QR = {str(np.linalg.cond(R, np.inf))}')

print(f'\nFehler mit direktem Lösen = {str(error_direct)}')
print(f'Fehler mit QR = {str(error_qr)}')
print(f'Fehler mit numpy polyfit = {str(error_polyfit)}')

xx = np.arange(x[0], x[-1], (x[-1] - x[0]) / 1000)

plt.figure(1)
plt.grid()
plt.plot(xx, np.polyval(coeff_direct, xx), zorder=0, label='direct solve')
plt.plot(xx, np.polyval(coeff_qr, xx), label='qr + solve')
plt.plot(xx, np.polyval(coeff_polyfit, xx), label='np.polyfit')
plt.scatter(x, y, marker='x', color='r', zorder=1, label='measured')
plt.legend()
plt.show()

"""
b) Vergleichen Sie die Konditionszahl der auftretenden Matrizen ATA bzw. R. Was fällt Ihnen auf?

A: Beide Matrizen haben eine sehr schlechte Kondition; die Kondition von ATA ist aber noch einmal deutlich schlechter
   als die Kondition von R. Die resultierenden Koeffizienten unterscheiden sich mit den beiden Verfahren jedoch kaum
   und auch graphisch lassen sich kaum Diskrepanzen feststellen.


d) Berechnen Sie die Fehlerfunktionale für Ihre Lösungen aus a) und c). Gibt es Unterschiede?

A: Die Fehlerfunktionale sind bei allen Lösungsvarianten beinahe identisch. Die Lösungen unterscheiden sich graphisch
   fast gar nicht.
"""