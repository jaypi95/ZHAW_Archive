import numpy as np
import sympy as sy
import matplotlib.pyplot as plt

"""
=======================================================================================================================
INPUT
=======================================================================================================================
"""

t = np.array([0.0, 0.2, 0.4, 0.6, 0.8, 1.0, 1.2, 1.4, 1.6, 1.8, 2.0], dtype=np.float64)
U = np.array([39.55, 46.55, 50.13, 51.75, 55.25, 56.79, 56.78, 59.13, 57.76, 59.39, 60.08], dtype=np.float64)

tol = 1e-7
max_iter = 300
pmax = 5

# Abschätzung (nachdem ersten Plot)
A0 = 39.0
Q0 = 60
tau0 = 0.4
# 5 * tau = 2, da der Kondensator nach 2s einigermassen fertig geladen aussieht

a = 0
b = 3
stepAccuracy = 0.001


# np Funktion aus der Aufgabenstellung (AUCH FÜR SP BEARBEITEN)
def U_np(t, lam):
    return lam[0] + (lam[1] - lam[0]) * (1 - np.exp(-t / lam[2]))


# sp Funktionen aus der Aufgabenstellung (AUCH FÜR NP BEARBEITEN)
def U_sp(t, lam):
    return lam[0] + (lam[1] - lam[0]) * (1 - sy.exp(-t / lam[2]))


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
A, Q, tau = sy.symbols('A Q tau')
lam = sy.Matrix([A, Q, tau])

g = sy.Matrix([U[k] - U_sp(t[k], lam) for k in range(len(t))])
Dg = g.jacobian(lam)

g = sy.lambdify([([A], [Q], [tau])], g, 'numpy')
Dg = sy.lambdify([([A], [Q], [tau])], Dg, 'numpy')


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


lam0 = np.array([[A0, Q0, tau0]], dtype=np.float64).T

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
