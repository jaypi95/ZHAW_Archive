import sympy as sp
import numpy as np
import matplotlib.pyplot as plt

'''INPUT'''
x = np.array([500.0, 1000, 1500, 2500, 3500, 4000, 4500, 5000, 5250, 5500], dtype=np.float64)
y = np.array([10.5, 49.2, 72.1, 85.4, 113, 121, 112, 80.2, 61.1, 13.8], dtype=np.float64)


def f(x, p):
    return p[0] * x ** 4 + p[1] * x ** 3 + p[2] * x ** 2 + p[3] * x + p[4]


lam0 = np.array([1, 1, 1, 1, 1], dtype=np.float64)  # Startvektor für Iteration (einfach was eingegeben)
p = sp.symbols('p:{n:d}'.format(n=lam0.size))

tol = 1e-5  # Fehlertoleranz
max_iter = 30  # Maximale Iterationen

# Fehlertoleranz für abbruch
pmax = 5  # Maximale Dämpfung
damping_toggle = 0

# parameter für plot:
a, b = 500.0, 5500
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


def showPlot(x, y, f):
    t = np.arange(a, b, step=steps)
    plt.scatter(x, y, color='orange', label='data')
    plt.plot(t, f(t), label='f(x)')
    plt.xlabel('x')
    plt.ylabel('y')
    plt.grid()
    plt.legend()
    plt.show()


g = sp.Matrix([y[k] - f(x[k], p) for k in range(len(x))])
Dg = g.jacobian(p)
g = sp.lambdify([p], g, 'numpy')
Dg = sp.lambdify([p], Dg, 'numpy')

[lam, n] = gauss_newton_d(g, Dg, lam0, tol, max_iter, 5, damping_toggle)
t = sp.symbols('t')
F = f(t, lam)
F = sp.lambdify([t], F, 'numpy')

showPlot(x, y, F)

# c: via newton verfahren 0-Stelle finden => f Ableiten für 0-Stellen. danach 2. Ableitung für Newton
# angepasst mit polynome
dP = np.polyder(lam)
# Zweite Ableitung ddP(d)
ddP = np.polyder(dP)

xn = [4000]  # startwert aus grafik
n = 0

while n < 1 or abs(xn[n] - xn[n - 1]) > 1e-2:
    xn.append(xn[n] - np.polyval(dP, xn[n]) / np.polyval(ddP, xn[n]))
    n += 1

    print("n = " + str(n) + ": x" + str(n) + " = " + str(xn[n]) + ", Δ = " + str(abs(xn[n] - xn[n - 1])))
