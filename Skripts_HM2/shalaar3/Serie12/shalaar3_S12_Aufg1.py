import numpy as np


def s12(f, a, b, n, y0):
    x = np.zeros(n+1)
    x[0] = a
    y = np.zeros(n+1)
    y[0] = y0
    h = (b-a)/n
    for i in range(0, n):
        k1 = f(x[i], y[i])
        k2 = f(x[i] + h/2, y[i] + h*k1/2)
        k3 = f(x[i] + h/2, y[i] + h*k2/2)
        k4 = f(x[i] + h, y[i] + h*k3)
        x[i+1] = x[i] + h
        y[i+1] = y[i] + h / 6.0 * (k1 + 2.0*k2 + 2.0*k3 + k4)
    return x, y

def f(t, y):
    return 1 - y/t

a = 1
b = 6
n = 5
y0 = 5

t, ynum = s12(f, a, b, n, y0)
print("t = ", t)
print("ynum = ", ynum)

def yexact(t):
    return t/2 + 9/(2*t)
print("yexact = ", yexact(t))
