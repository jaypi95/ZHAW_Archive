import matplotlib.pyplot as plt
import numpy as np

'''INPUT'''
# Zu lösende Differentialgleichung
a = 0
b = 0.01

# h = ((b - a) * 1.0) / n
h = 0.01
n = int((b - a) / h)
y0 = np.array([0., 0.])


def f(t, y):
    # hier muss die matrix aufgebaut werden wie sie auf eine dgl 1 umgestellt wurde.
    # y[1] = z2
    # [ h', dgl ]
    return np.array([y[1], (100 - 80 * y[0] - (1 / (4 * 10 ** -4)) * y[1])])


np.set_printoptions(suppress=True)
'''INPUT'''


def mittelpunkt(f, x, h, y0):
    y = np.zeros((y0.size, n + 1))
    y[:, 0] = y0

    for k in range(x.shape[0] - 1):
        k1 = f(x[k], y[:, k])
        k2 = f(x[k] + h * 0.5, y[:, k] + 0.5 * h * k1)
        y[:, k + 1] = y[:, k] + h * k2
        print(f"k1: {k1}")
        print(f"k2: {k2}")
        print(f"x[{k + 1}]: {x[k + 1]}")
        print(f"z[:, {k + 1}]: {y[:, k + 1]}")

    return y


x = np.arange(a, b + h, step=h, dtype=np.float64)

y_mittelpunkt = mittelpunkt(f, x, h, y0)
print(y_mittelpunkt[:, 1])
# lösung ist [0.005, 0.6] weiss nicht wieso 0.6..
