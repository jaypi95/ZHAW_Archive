import numpy as np
import matplotlib.pyplot as plt
from matplotlib import cm
from mpl_toolkits.mplot3d import Axes3D

c = 10
x = np.linspace(0, 4 * np.pi)
t = np.linspace(0, 10)
X, T = np.meshgrid(x, t)


def w(x, t):
    return np.sin(x + t)


def v(x, t):
    return np.sin(x + t) + np.cos(2 * x + 2  * t)


W = w(X, T)
V = v(X, T)
fig = plt.figure(1)
ax = fig.add_subplot(121, projection='3d')
ax.set_title('Funktion w')
ax.set_xlabel("x")
ax.set_ylabel("y")
ax.set_zlabel("z")
surf = ax.plot_surface(X, T, W, cmap=cm.coolwarm, linewidth=0, antialiased=True)
fig.colorbar(surf)

ax = fig.add_subplot(122, projection='3d')
ax.set_title('Funktion v')
ax.set_xlabel("x")
ax.set_ylabel("y")
ax.set_zlabel("z")
surf = ax.plot_surface(X, T, V, cmap=cm.coolwarm, linewidth=0, antialiased=True)
fig.colorbar(surf)

plt.show()

