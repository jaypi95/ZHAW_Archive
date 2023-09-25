import numpy as np
import matplotlib.pyplot as plt

"""
=======================================================================================================================
INPUT
=======================================================================================================================
"""

#Nur Funktion a stimmt. Funktion b wird mit z gelöst und keiner weiss was das ist
x = np.array([0, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110], dtype=np.float64)  # Messwerte xi
y = np.array([76, 92, 106, 123, 137, 151, 179, 203, 227, 250, 281, 309], dtype=np.float64)  # Messwerte yi

z = 1 / y  # Alternative Methode (Nur Gott und der Kompiler wissen genau, was das ist)

"""
=======================================================================================================================
"""

# a)
n = len(x)
A = np.array([x ** 2, x, np.ones(n)]).T
p = np.linalg.solve(A.T @ A, A.T @ y)

print(f"Polynom Koeffizienten (höchster grad rechts): {p=}")
f = lambda t: np.polyval(p, t)

# b)
A = np.array([x ** 2, x, np.ones(n)]).T
q = np.linalg.solve(A.T @ A, A.T @ z)


print(f"Koeffizienten a und b: {q=}")
g = lambda t: 1 / (q[0] * t ** 2 + q[1])

# c)
t = np.arange(x[0] - 5, x[-1] + 5, 0.01)
plt.plot(x, y, 'o', label="data")
plt.plot(t, f(t), label="a)")
plt.plot(t, g(t), label="b)")
plt.legend()
plt.grid()
plt.show()

# d)

print("Fehlerquadratsummen: ")
Ea = np.linalg.norm(y - f(x)) ** 2
print(f"a) {Ea}")
Eb = np.linalg.norm(y - g(x)) ** 2
print(f"b) {Eb}")
