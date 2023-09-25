# ---------------------------------------------------------------------------
# HM 2 SW 13 LOESESN DES DGL-SYSTEM ZUM SIR-MODELL
#            MIT MITTELPUNKTVERFAHREN / adel
# ---------------------------------------------------------------------------

import numpy as np
import matplotlib.pyplot as plt

# Vorbemerkung

# VEKTOREN werden ZEILENWEISE als EINDIMENSIONALE Arrays implementiert.

# Groessen

# x : Zeit t in Tagen
# y1: Susceptible S in Anzahl Personen
# y2: Infected I in Anzahl Personen
# y : Vektor [y1, y2]
# y3: Removed R in Anzahl Personen

# Parameter
m = 97000

# Funktion
def f(x, y):
    return np.array([y[1], (-5* y[1] **2 - 0.1 * y[0] - 570000)/m])


# Anfangswerte
x0 = 0
y0 = np.array([0, 100])
print('y0 =', y0)

# Endwert und Schrittweite
xn = 20.
h = 0.1



# Erster Schritt Mittelpunktverfahren

k1 = f(x0, y0)
print(k1)
# [-19.8   9.8]
print(y0 + h / 2 * k1)
# [9801.  149.]
k2 = f(x0 + h / 2, y0 + h / 2 * k1)
print(k2)
# [-29.20698  14.30698]
x1 = x0 + h
print('x1 =', x1)
# 10
y1 = y0 + h * k2
print(y1)


# Implementation Mittelpunktverfahren

def mittelpunkt(f, x0, y0, xn, h):
    # Loest DGL-System y' = f(x, y) zum AW y(x0) = y0 mit Mittelpunkt-Verfahren
    # auf Intervall x0 <= x <= xn mit Schrittwqeite h
    # Gibt x-Werte x0, .., xn als Vektor zurueck
    # Gibt y-Vektoren y0, ..., yn als Zeilen einer (n+1) x 2 - Matrix zurueck
    n = np.int64((xn - x0) / h)
    x = np.zeros(n + 1)
    y = np.zeros([n + 1, 2])
    y[0, :] = y0
    for i in range(n):
        k1 = f(x[i], y[i, :])
        k2 = f(x[i] + h / 2, y[i, :] + h / 2 * k1)
        k = k2
        x[i + 1] = x[i] + h
        y[i + 1, :] = y[i, :] + h * k
    return x, y


# Durchfuehrung Mittelpunktverfahren

x, y = mittelpunkt(f, x0, y0, xn, h)
print('x =\n', x)
print('y =\n', y)

# Plot

plt.plot(x, y[:, 0], label='Bremsweg x(t)')
plt.plot(x, y[:, 1], label='Geschwindigkeit v(t)')


plt.xlabel('Zeit t in Sekunden')
plt.ylabel('m/s oder m')
plt.grid()
plt.legend()
plt.show()


