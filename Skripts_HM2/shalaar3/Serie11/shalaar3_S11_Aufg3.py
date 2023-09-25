import numpy as np
import matplotlib.pyplot as plt
from Serie11.shalaar3_S11_Aufg1 import shalaar3_S11_Aufg1


# def euler_base(a, b, n, y0):
#     x = np.zeros(n + 1)
#     y = np.zeros(n + 1)
#     x[0] = a
#     y[0] = y0
#     h = (b - a) / n
#     return x, y, h


def euler(f, a, b, n, y0):
    x = np.zeros(n + 1)
    y = np.zeros(n + 1)
    x[0] = a
    y[0] = y0
    h = (b - a) / n
    # x, y, h = euler_base(f, a, b, n, y0)

    for i in range(n):
        x[i + 1] = x[i] + h
        y[i + 1] = y[i] + h * f(x[i], y[i])

    return x, y


def euler_mid(f, a, b, n, y0):
    x = np.zeros(n + 1)
    y = np.zeros(n + 1)
    x[0] = a
    y[0] = y0
    h = (b - a) / n
    # x, y, h = euler_base(f, a, b, n, y0)

    for i in range(n):
        x_2 = x[i] + h / 2.
        y_2 = y[i] + h / 2. * f(x[i], y[i])
        x[i + 1] = x[i] + h
        y[i + 1] = y[i] + h * f(x_2, y_2)

    return x, y


def euler_mod(f, a, b, n, y0):
    x = np.zeros(n + 1)
    x[0] = a
    y = np.zeros(n + 1)
    y[0] = y0
    h = (b - a) / n

    for i in range(n):
        x[i + 1] = x[i] + h
        k1 = f(x[i], y[i])
        y_euler = y[i] + h * k1
        k2 = f(x[i + 1], y_euler)
        y[i + 1] = y[i] + h * (k1 + k2) / 2.

    return x, y


def shalaar3_serie11_Aufg3(f, a, b, n, y0):
    x, y_euler = euler(f, a, b, n, y0)
    x, y_euler_mid = euler_mid(f, a, b, n, y0)
    x, y_euler_mod = euler_mod(f, a, b, n, y0)

    plt.plot(x, y_euler)
    plt.plot(x, y_euler_mid)
    plt.plot(x, y_euler_mod)

    ymin = np.min([y_euler, y_euler_mid, y_euler_mod])
    ymax = np.max([y_euler, y_euler_mid, y_euler_mod])
    hx = (b - a) / 10
    hy = (ymax - ymin) / 10
    shalaar3_S11_Aufg1(f, a, b, ymin, ymax, hx, hy)
    plt.legend(['Euler', 'Mittelpunkt-Euler', 'Modifizierter Euler', 'Richtungsfeld'])

    return x, y_euler, y_euler_mid, y_euler_mod


def f(x, y):
    return x ** 2 / y

a = 0
b = 1.4
n = 2
y0 = 2

x, y_euler, y_euler_mid, y_euler_mod = shalaar3_serie11_Aufg3(f, a, b, n, y0)


print(f"x\t\t {x}")
print(f"y_euler\t\t {y_euler}")
print(f"y_euler_mid\t\t {y_euler_mid}")
print(f"y_euler_mod\t\t {y_euler_mod}")
