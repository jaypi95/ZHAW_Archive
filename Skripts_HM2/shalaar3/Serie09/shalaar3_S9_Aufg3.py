import numpy as np


def sum_trapez(f, a, b, h):
    """
    Berechnet die Integral der Funktion f(x)
    mit dem Trapezregel
    """
    return (h / 2) * (f(a) + f(b) + 2 * sum(f(a + h * i) for i in range(1, int((b - a) / h))))


def shalaar3_S9_Aufg3(f, a, b, m):
    a = float(a)
    b = float(b)
    h = b - a

    T = np.zeros((m + 1, m + 1))

    for j in range(m + 1):
        T[j, 0] = sum_trapez(f, a, b, h / 2 ** j)

    for j in range(1, m + 1):
        for i in range(0, m - j + 1):
            T[i, j] = (4 ** j * T[i + 1, j - 1] - T[i, j - 1]) / (4 ** j - 1)

    # print(T)
    return T[0, m]


def f(x):
    return np.cos(x ** 2)


shalaar3_S9_Aufg3(f, 0, np.pi, 4)
