import numpy as np
import matplotlib.pyplot as plt
from matplotlib import cm

# Teilaufgabe a


g = 9.81


def W(v0, alpha):
    return (v0 ** 2 * np.sin(2 * alpha)) / g


[v0, alpha] = np.meshgrid(np.linspace(0, 100), np.linspace(0, np.pi / 2))

W = W(v0, alpha)


## Wireframe

fig = plt.figure(1)
ax = fig.add_subplot(111, projection="3d")
ax.plot_wireframe(v0, alpha, W, rstride=5, cstride=5)

plt.title("Gitter")
ax.set_xlabel("v0")
ax.set_ylabel("alpha")
ax.set_zlabel("W")

plt.show()


## Surfaceplot

fig = plt.figure(2)
ax = fig.add_subplot(111, projection="3d")
surf = ax.plot_surface(v0, alpha, W, cmap=cm.coolwarm, linewidth=0, antialiased=False)

fig.colorbar(surf, shrink=0.5, aspect=5)

plt.title("Colored Surface")
ax.set_xlabel("v0")
ax.set_ylabel("alpha")
ax.set_zlabel("W")

plt.show()

## Höhenlinien
cont = plt.contour(v0, alpha, W)

plt.colorbar(cont)
plt.title("Höhenlinien")
plt.xlabel("v0")
plt.ylabel("alpha")

plt.show()

### get max value
xmax, ymax = np.unravel_index(np.argmax(W), W.shape)
max = (v0[xmax, ymax], alpha[xmax, ymax], W.max())

### print alpha value for maximum W
print(max[1])
### you get the highest W with an alpha of 0.7693696294505615 (angle is in rad)


# Teilaufgabe b

R = 8.31


def p_func(V, T):
    return (R * T) / V


def V_func(p, T):
    return (R * T) / p


def T_func(p, V):
    return (p * V) / R


## Wireframe

### p
[V, T] = np.meshgrid(np.linspace(0.01, 0.2), np.linspace(0, 1e4))
# Hier kann V nicht 0 sein, da durch V geteilt wird.
p = p_func(V, T)
fig = plt.figure(1)
ap = fig.add_subplot(111, projection="3d")
ap.plot_wireframe(V, T, p, rstride=5, cstride=5)

plt.title("Gitter p")
ax.set_xlabel("V")
ax.set_ylabel("T")
ax.set_zlabel("p")

plt.show()

### V
[p, T] = np.meshgrid(np.linspace(10 ** 4, 10 ** 5), np.linspace(0, 1e4))
V = V_func(p, T)
fig = plt.figure(20)
ap = fig.add_subplot(111, projection="3d")
ap.plot_wireframe(V, T, p, rstride=5, cstride=5)

plt.title("Gitter V")
ax.set_xlabel("V")
ax.set_ylabel("T")
ax.set_zlabel("p")

plt.show()

### T
[p, V] = np.meshgrid(np.linspace(10e4, 10e6), np.linspace(0, 10))
T = T_func(p, V)
fig = plt.figure(20)
ap = fig.add_subplot(111, projection="3d")
ap.plot_wireframe(V, T, p, rstride=5, cstride=5)

plt.title("Gitter T")
ax.set_xlabel("V")
ax.set_ylabel("T")
ax.set_zlabel("p")

plt.show()
