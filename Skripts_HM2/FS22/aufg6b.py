import numpy as np
import sympy as sy
import matplotlib.pyplot as plt

"""
=======================================================================================================================
INPUT
=======================================================================================================================
"""

t = np.array([0, 2, 4, 6, 8, 10], dtype=np.float64)
U = np.array([52.9, 184, 426, 529, 499, 510], dtype=np.float64)

tol = 1e-7
max_iter = 30
pmax = 5

A0 = 20
G0 = 450
K0 = 0.001


a = 0
b = 20
stepAccuracy = 0.001


# np Funktion aus der Aufgabenstellung (AUCH FÜR SP BEARBEITEN)
def U_np(t, lam):
    return lam[1] / (1 + ((lam[1] / lam[0]) - 1) * np.exp(-(lam[2] * lam[0] * t)))


# sp Funktionen aus der Aufgabenstellung (AUCH FÜR NP BEARBEITEN)
def U_sp(t, lam):
    return lam[1] / (1 + ((lam[1] / lam[0]) - 1) * sy.exp(-(lam[2] * lam[0] * t)))


"""
=======================================================================================================================
"""

# Aufgabe a)

# Plot der Daten
plt.figure(1)
plt.plot(t, U, '*')
plt.xlabel('t')
plt.ylabel('U(t)')
plt.title("Plot Data as scatter plot")
plt.grid()
plt.show()

# Aufgabe b)
A, G, K = sy.symbols('A G K')
lam = sy.Matrix([A, G, K])

g = sy.Matrix([U[k] - U_sp(t[k], lam) for k in range(len(t))])
Dg = g.jacobian(lam)

g = sy.lambdify([([A], [G], [K])], g, 'numpy')
Dg = sy.lambdify([([A], [G], [K])], Dg, 'numpy')


# Numerischer Teil
# Gedämpftes Gauss-Newton Verfahren
def gauss_newton(g, Dg, lam0, tol, max_iter, pmax):
    k = 0
    lam = np.copy(lam0)
    increment = tol + 1
    err_func = np.linalg.norm(g(lam)) ** 2

    while increment > tol and k < max_iter:
        # QR-Zerlegung von Dg(lam)
        [Q, R] = np.linalg.qr(Dg(lam))
        delta = np.linalg.solve(R, Q.T @ -g(lam))

        # Dämpfung
        p = 0
        while np.linalg.norm(g(lam + delta / (2 ** p))) >= err_func and p <= pmax:
            p = p + 1
        if p == pmax + 1:
            p = 0

        # Update des vektors Lambda
        lam = lam + 0.5 ** p * delta
        err_func = np.linalg.norm(g(lam)) ** 2
        increment = np.linalg.norm(delta / (2 ** p))
        k = k + 1

        print(f"Iteration: {k}")
        print(f"Dämpfung: {p}")
        print(f"Lambda: {lam}")
        print(f"Increment: {increment}")
        print(f"Fehlerfunktion: {err_func}")
        print("")
    return (lam, k)


lam0 = np.array([[A0, G0, K0]], dtype=np.float64).T

[lamn, n] = gauss_newton(g, Dg, lam0, tol, max_iter, pmax)

print(f"Lambda: {n} = {np.reshape(lamn, -1)}")

# Aufgabe c)
plt.figure(2)
tf = np.arange(a, b, stepAccuracy)
Uf = U_np(tf, lamn)
plt.plot(t, U, "*", label="data")
plt.plot(tf, Uf, label="fit")
plt.title("Fit")
plt.grid()
plt.show()

exit()
