import numpy as np
import matplotlib.pyplot as plt

"""
=======================================================================================================================
INPUT
=======================================================================================================================
"""

# Funktion
def f(y, x):
    return np.array([y[0], y[1], (3 * x ** 2 * y[2] - 6 * x * y[0] + 6 * x ** 4) / (x ** 3)])

def f_exact(x):
    return 2 * x + x ** 2 - x ** 3 + x ** 4

# Grenzen
a = 1
b = 4
h1 = 0.1
h2 = 0.01

# Anfangswerte
x0 = 0
y0 = np.array([3, 5, 8])

"""
=======================================================================================================================
"""

xn = b

# Erster Schritt Mittelpunktverfahren

k1 = f(y0, x0)
print(k1)
# [-19.8   9.8]
print(y0 + h1 / 2 * k1)
# [9801.  149.]
k2 = f(y0 + h1 / 2 * k1, x0 + h1)
print(k2)
# [-29.20698  14.30698]
x1 = x0 + h1
print('x1 =', x1)
# 10
y1 = y0 + h1 * k2
print(y1)


# [9607.9302  243.0698]

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
        k1 = f(y[i, :], x[i, :])
        k2 = f(y[i, :] + h / 2 * k1)
        k3 = f(y[i, :] + h / 2 * k2 + h / 2 * k1)
        k = k2
        x[i + 1] = x[i] + h
        y[i + 1, :] = y[i, :] + h * k
    return x, y


# Durchfuehrung Mittelpunktverfahren

x, y = mittelpunkt(f, x0, y0, xn, h1)
print('x =\n', x)
print('y =\n', y)

# x =
#  [  0.  10.  20.  30.  40.  50.  60.  70.  80.  90. 100. 110. 120.]
# y =
#  [[9900.          100.        ]
#   [9607.9302      243.0698    ]
#   [8942.20848735  553.71684544]
#   [7637.97587613 1085.94588645]
#   [5769.14822717 1582.35774384]
#   [4114.07128182 1533.37018   ]
#   [3140.49090073 1109.4260489 ]
#   [2636.17078894  710.61889508]
#   [2370.40277941  433.74617974]
#   [2225.41849552  259.04205875]
#   [2144.2707149   153.02111114]
#   [2098.10475492   89.86464681]
#   [2071.57898331   52.60355074]]

# Plot

plt.plot(x, y[:, 0], label='Bremsweg x(t)')
plt.plot(x, y[:, 1], label='Geschwindigkeit v(t)')

# Zusatzplot der Recovered ueber Bilanzgleichung S + I + R = N
# plt.plot(x, N - y[:, 0] - y[:, 1], '.', label='Recoverd R(t)')

plt.xlabel('Zeit t in Sekunden')
plt.ylabel('m/s oder m')
plt.grid()
plt.legend()
plt.show()
