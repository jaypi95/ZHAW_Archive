import numpy as np


def peterju1_S10_Aufg1(f, a, b, m):
    res = np.ones((m + 1, m + 1))

    for i in range(0, m + 1):
        h = (b - a) / 2 ** i
        res[i][0] = h * ((f(a) + f(b)) / 2)

        for j in range(1, 2 ** i):
            res[i][0] += h * f(a + j * h)

    for i in range(1, m + 1):
        res[0:m + 1 - i, i] = (4 ** i * res[:, i - 1][1:m + 2 - i] - res[:, i - 1][0:m + 1 - i]) / (4. ** i - 1)
        print(res[0:m + 1 - i, i])


    return res[0][m]


# Aufgabe 1 a
def f1(x):
    return 9700 / (-5 * x**2 - 570000)

# Intervallstart
a = 100
# Intervallende
b = 0
# Anzahl Extrapolationsschritte
m = 4

T = peterju1_S10_Aufg1(f1, a, b, m)

print("\n")
print(f"Result: {T}")

# Aufgabe 1 b
def f2(x):
    return (97000 * x) / (-5 * x**2 - 570000)


T = peterju1_S10_Aufg1(f2, a, b, m)

print("\n")
print(f"Result: {T}")


