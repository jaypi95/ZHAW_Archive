import sympy as sp
import numpy as np
import matplotlib.pyplot as plt

'''INPUT'''
x = np.array([0, 14, 28, 42, 56])
y = np.array([29, 2072, 15798, 25854, 28997])
c_t = 0.2

# symbole für Ansatzfunktion
p = sp.symbols('p0 p1 p2')


def f(x, p):
    return p[0] / ((p[0] - p[1]) / p[1] * sp.exp(-1*p[2] * x) + 1)


lam0 = np.array([30_000, 29, 0.3049266], dtype=np.float64)  # Startvektor für Iteration

# Fehlertoleranz für abbruch
tol = 1e-5
max_iter = 30
pmax = 10  # Maximale Dämpfung
damping_toggle = 1

# parameter für plot:
[a, b] = {-5, 70}
steps = 1
'''INPUT'''

sp.init_printing()


def gauss_newton_d(g, Dg, lam0, tol, max_iter, pmax, damping):
    k = 0
    lam = np.copy(lam0)
    increment = tol + 1
    err_func = np.linalg.norm(g(lam)) ** 2

    while k < max_iter and increment > tol:
        # QR-Zerlegung von Dg(lam)
        [Q, R] = np.linalg.qr(Dg(lam))
        delta = np.linalg.solve(R, -Q.T @ g(lam)).flatten()
        p = 0
        while damping == 1 and p < pmax \
                and np.linalg.norm(g(lam + delta / (2 ** p))) ** 2 >= err_func:
            p += 1

        # Update des Vektors Lambda
        lam = lam + delta / 2 ** p
        err_func = np.linalg.norm(g(lam), 2) ** 2
        increment = np.linalg.norm(delta)
        k = k + 1
        print('Iteration: ', k)
        print('lambda = ', lam)
        print('Inkrement = ', increment)
        print('Fehlerfunktional =', err_func)
    print('letztes lamda = gefittete Parameter')
    return (lam, k)


def showPlot(lam, x, y, f):
    t = sp.symbols('t')
    F = f(t, lam)
    F = sp.lambdify([t], F, 'numpy')
    t = np.arange(a, b, steps)
    plt.scatter(x, y, color='orange', label='data')
    plt.plot(t, F(t), label='f(x)')
    plt.xlabel('x')
    plt.ylabel('y')
    plt.grid()
    plt.legend()
    plt.show()


g = sp.Matrix([y[k] - f(x[k], p) for k in range(len(x))])
Dg = g.jacobian(p)
g = sp.lambdify([p], g, 'numpy')
Dg = sp.lambdify([p], Dg, 'numpy')

[lam_damp, n] = gauss_newton_d(g, Dg, lam0, tol, max_iter, 5, damping_toggle)
showPlot(lam_damp, x, y, f)
