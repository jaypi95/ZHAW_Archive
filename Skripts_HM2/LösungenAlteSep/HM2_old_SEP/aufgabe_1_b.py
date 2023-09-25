import sympy as sy
import math


x = sy.symbols('x')

"""==================== INPUT ===================="""
g = lambda x : -5.39332183 + 2.52363039*sy.exp(x*2.86818502)
h = lambda x :  1600


f = g(x) - h(x)
x0 = 2.1 # 1600mio ist ausserhalb des plots und somit mehr als 2.0
max_error = 10**-4
"""==============================================="""

df = sy.diff(f, x)
d2f = sy.diff(df, x)
fl = sy.lambdify(x, f)
dfl = sy.lambdify(x, df)
d2fl = sy.lambdify(x, d2f)

print("f'(x) = " + str(df))

d = abs((fl(x0) * d2fl(x0)) / ((dfl(x0))**2))
print("Konvergenzbedingung für x0 prüfen:", d)

if d < 1:
    print("Konvergenzbedingung erfüllt!")
else:
    print("Konvergenzbedingung NICHT erfüllt!")


xn = [x0]
print("n = 0: x0 = " + str(x0))

n = 0

while n < 1 or abs(xn[n] - xn[n - 1]) > max_error:
    xn.append(xn[n] - fl(xn[n]) / dfl(xn[n]))
    n += 1

    print("n = " + str(n) + ": x" + str(n) + " = " + str(xn[n]) + ", Δ = " + str(abs(xn[n] - xn[n - 1])))