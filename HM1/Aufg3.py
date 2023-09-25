import math

import numpy as np

x0 = -1.0
x1 = -1.2

tol = 0.0001

def length(n):
    if n > 0:
        digits = int(math.log10(n))+1
    else:
        digits = 0

    return digits

def fx(x):
    return np.exp(x ** 2) + x ** (-3) - 10


def sekanten(f, x0, x1, tol):
    xn = x0
    while abs(x0-x1) > tol:
        xn = x1 - ((x1 - x0) / (f(x1) - (f(x0)))) * f(x1)
        x0 = x1
        x1 = xn
        print(xn)


sekanten(fx, x0, x1, tol)
