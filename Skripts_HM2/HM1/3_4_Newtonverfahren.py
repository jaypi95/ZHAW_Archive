import sympy as sy
import math

x = sy.symbols('x')

"""==================== INPUT ===================="""
f = -2.58741552e-12 * x ** 4 + 2.75617649e-08 * x ** 3 - 1.06061102e-04 * x ** 2 + 1.94285784e-01 * x -6.32458454e+01

# Optional if you need maxima or minima, differentiate once first
# f = f.diff()

x0 = 3000
max_error = 1

force_convergence = True
"""==============================================="""

df = sy.diff(f, x)
d2f = sy.diff(df, x)
fl = sy.lambdify(x, f)
dfl = sy.lambdify(x, df)
d2fl = sy.lambdify(x, d2f)

print("f'(x) = " + str(df))

print("Konvergenzbedingung für x0 prüfen:")
if force_convergence:
    d = 0
else:
    d = abs((fl(x0) * d2fl(x0)) / ((dfl(x0)) ** 2))

if d < 1:
    print("Konvergenzbedingung erfüllt!")
else:
    print("Konvergenzbedingung NICHT erfüllt!")

if d < 1:
    xn = [x0]
    print("n = 0: x0 = " + str(x0))

    n = 0

    while n < 1 or abs(xn[n] - xn[n - 1]) > max_error:
        xn.append(xn[n] - fl(xn[n]) / dfl(xn[n]))

        n += 1

        print("n = " + str(n) + ": x" + str(n) + " = " + str(xn[n]) + ", Δ = " + str(abs(xn[n] - xn[n - 1])))
