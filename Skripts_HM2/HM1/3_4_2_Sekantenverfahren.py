import sympy as sy
import math


x = sy.symbols('x')

"""==================== INPUT ===================="""
f = 4*x-3**x
x0 = 3
x1 = 5
max_error = 1e-8
"""==============================================="""

fl = sy.lambdify(x, f)

xn = [x0, x1]
print("n = 0: x0 = " + str(x0))
print("n = 1: x1 = " + str(x1))

n = 1

while abs(xn[n] - xn[n-1]) > max_error:
    xn.append(xn[n] - ((xn[n] - xn[n-1]) / (fl(xn[n]) - fl(xn[n-1]))) * fl(xn[n]))

    n += 1

    print("n = " + str(n) + ": x" + str(n) + " = " + str(xn[n]) + ", Δ = " + str(abs(xn[n] - xn[n-1])))
