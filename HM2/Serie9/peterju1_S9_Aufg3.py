import numpy as np


def peterju1_S9_Aufg3(f, a, b, m):
    res = np.ones((m + 1, m + 1))

    for i in range(0, m + 1):
        h = (b - a) / 2 ** i
        res[i][0] = h * ((f(a) + f(b)) / 2)

        for j in range(1, 2 ** i):
            res[i][0] += h * f(a + j * h)

    for i in range(1, m + 1):
        res[0:m + 1 - i, i] = (4 ** i * res[:, i - 1][1:m + 2 - i] - res[:, i - 1][0:m + 1 - i]) / (4. ** i - 1)

    return res[0][m]


def f(x):
    return np.cos(x ** 2)


a = 0
b = np.pi
m = 4

T = peterju1_S9_Aufg3(f, a, b, m)

print(f"Result: {T}")
# Result: 0.5641876002784855

