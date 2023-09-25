import numpy as np
import matplotlib.pyplot as plt

m = 97000.
c = 570000.
x0 = 0.
v0 = 100.

a = 0.
b = 20.
h = 0.1

n = int((b - a) / h)
rows = 2

t = np.zeros(n + 1)
z = np.zeros([rows, n + 1])

t[0] = a
z[:, 0] = np.array([x0, v0])

def f(z):
    return np.array([z[1], -5 * z[1] ** 2 / m - c / m])

for i in range(0, n):
    th2 = t[i] + h/2
    zh2 = z[:, i] + h/2 * f(z[:, i])
    t[i + 1] = t[i] + h
    z[:, i + 1] = z[:, i] + h * f(zh2)

plt.plot(t, z[0, :], t, z[1, :])
plt.grid(True)
plt.legend(['Bremsweg', 'Geschwindigkeit'])
plt.show()


exit();