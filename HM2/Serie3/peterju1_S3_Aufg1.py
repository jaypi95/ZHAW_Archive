import numpy as np
import sympy as sp

x, y = sp.symbols("x y")
F = sp.Matrix([20 - 18 * x - 2 * y ** 2, -4 * y * (x - y ** 2)])
X = sp.Matrix([x, y])
J = F.jacobian(X)

f = sp.lambdify([[[x], [y]]], F, "numpy")
Df = sp.lambdify([[[x], [y]]], J, "numpy")


def newton(x0):
    f0 = f(x0)
    Df0 = Df(x0)
    delta = np.linalg.solve(Df0, -f0)
    x1 = x0 + delta

    print("f =", f0)
    print("Df =", Df0)
    print("||f||2 =", np.linalg.norm(f0, 2))
    print("delta =", delta)
    print("||x_(n+1) - xn||2 =", np.linalg.norm(x1 - x0, 2))
    print("x1 =", x1)

    return x1


x0 = np.array([[1.1, 0.9]]).T

x1 = newton(x0)
print("x1 =", x1)

x2 = newton(x1)
print("x2 =", x2)
