import numpy as np
import matplotlib.pyplot as plt

"""
=======================================================================================================================
INPUT
=======================================================================================================================
"""

a = 0  # Startwert
b = 190  # Endwert
n = 4  # Anzahl Schritte

data = np.arange(a, b, 0.1)  # Nichts tun hiermit!

# Zu interpolierende Funktion
def f(x):
    return np.cos(x) * x ** 2

# Zu vergleichende Funktion (aus Aufgabenstellung)
# Falls nicht benötigt, dann hier LÖSCHEN und AUFRÄUMEN!
f_exact = np.cos(data) * 4 * data * 2


#
# v_rel = 2600.
# m_A = 300000.
# m_E = 80000.
# t_E = 190
# g = 9.81
# mue = (m_A - m_E) / t_E
#
# data = np.arange(0, t_E)
#
#
# def f(x):
#     return v_rel * mue / (m_A - mue * x) - g
#
#
# # v_exact = (v_rel * np.log(m_A / (m_A - mue * data)) - g) * data
#
#
# def v_exact(x):
#     return v_rel * np.log(m_A / (m_A - mue * x)) - g * x
#
# def h_exact(t):
#     return - (v_rel * (m_A - mue * t) / mue) * np.log(m_A / (m_A - mue * t)) + v_rel * t - 0.5 * g * t ** 2
#

# ======================================================================================================================


"""
=======================================================================================================================
"""

dataF = f(data)
print(f"Startwert a = {str(a)}")
print(f"Endwert b = {str(b)}")
print(f"Schritte n = {str(n)}")


def Tf(f, a, b, n, j):
    h = (b - a) / n
    print(f"  Schrittweite h = (b - a) / n = {b} - {a} / {n} => {h}")

    xi = np.array([a + i * h for i in range(1, n)], dtype=np.float64)

    print(f"xi = {str(xi)}")

    T = h * ((f(a) + f(b)) / 2 + np.sum(f(xi)))

    print(f"T{j}0 = h * ((f(a) + f(b)) / 2 + SUM(f(xi))) = {T}")
    print(f"\t= {h} * ((f({a}) + f({b})) / 2 + {np.sum(f(xi))}) = {T}")
    print(f"\t= {h} * ({f(a)} + {f(b)}) / 2 + {np.sum(f(xi))} = {T}")

    return T


def romberg_extrapolate(f, a, b, m):
    print("1. Berechne die Tj0 mit der Trapezregel:")
    print("----------------------------------------")
    T = np.zeros((m + 1, m + 1), dtype=np.float64)

    T[0:, 0] = [Tf(f, a, b, 2 ** j, j) for j in range(m + 1)]

    print("2. Berechne die Tjk aus den Tj0:")
    print("--------------------------------")
    for k in range(1, m + 1):
        for j in range(m + 1 - k):
            T[j, k] = (4 ** k * T[j + 1, k - 1] - T[j, k - 1]) / (4 ** k - 1)
            print(
                f"T{j}{k} = (4^{k} * T{j + 1}{k - 1} - T{j}{k - 1}) / (4^{k} - 1) = {T[j, k]}")

    print('\nTij = \n', T)
    return T[0, m]


T = romberg_extrapolate(f, a, b, n)

print(f'\nIntegral mit Romberg-Extrapolation: {T}')


def simpStuff(x, y):
    n = np.size(x) - 1

    T = 0
    for i in range(n):
        T = T + (x[i + 1] - x[i]) * (y[i + 1] + y[i]) / 2
    return T


def doStuff(x, y):
    inArray = np.zeros(x.size)
    for i in range(x.size):
        inArray[i] = simpStuff(x[0:i], y[0:i])
    return inArray


def plotStuff(id, data, ylabel, legend):
    plt.figure(id)
    for dat in data:
        plt.plot(dat[0], dat[1])
    plt.xlabel('Zeit t')
    plt.ylabel(ylabel)
    plt.grid()
    plt.legend(legend)
    plt.title(ylabel)
    plt.show()

v_num = doStuff(data, dataF)

plotStuff(1, [[data, v_num], [data, f_exact]], 'V', ['num', 'exact'])

exit()
