import numpy as np


def lagrange_int(x, y, x_int):
    n = np.size(x) - 1
    L = np.ones(n + 1)
    l_size = np.size(L)
    y_int = 0

    for i in np.arange(0, l_size):
        for j in np.arange(0, l_size):
            if j != i:
                L[i] = L[i] * (x_int - x[j]) / (x[i] - x[j])
        y_int = y_int + y[i] * L[i]

    return y_int


x = np.array(
    [0, 2500, 5000, 10000]
    , dtype=float)
y = np.array(
    [1013, 747, 540, 226]
    , dtype=float)
x_int = 3750

y_int = lagrange_int(x, y, x_int)
print('y_int', y_int)
