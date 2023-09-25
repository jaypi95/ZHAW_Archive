import numpy as np
import sympy as sp


def damped_newton(f, Df, x0, tol, n_max, k_max):
    n = 0
    x = np.copy(x0)
    err = np.linalg.norm(f(x), 2)

    while err > tol and n < n_max:
        delta = np.linalg.solve(Df(x), -f(x))
        k = 0
        while np.linalg.norm(f(x + 0.5 ** k * delta), 2) >= err and k <= k_max:
            k = k + 1
        if k == k_max + 1:
            k = 0
        x = x + 0.5 ** k * delta
        err = np.linalg.norm(f(x), 2)
        n = n + 1
    return (x, n)


x1, x2, x3 = sp.symbols("x1, x2, x3")

f1 = x1 + x2 ** 2 - x3 ** 2 - 13
f2 = sp.log(x2 / 4) + sp.exp(0.5 * x3 - 1) - 1
f3 = (x2 - 3) ** 2 - x3 ** 3 + 7

f = sp.Matrix([f1, f2, f3])
x = sp.Matrix([x1, x2, x3])
Df = f.jacobian(x)

f = sp.lambdify([[[x1], [x2], [x3]]], f, "numpy")
Df = sp.lambdify([[[x1], [x2], [x3]]], Df, "numpy")

tol = 1e-5
x0 = np.array([[1.5, 3, 2.5]]).T
[xn, n] = damped_newton(f, Df, x0, tol, 100, 4)

print(f"Nach {n} Iterationen: \n{xn}")
