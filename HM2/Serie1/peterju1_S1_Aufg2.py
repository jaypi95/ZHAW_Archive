import numpy as np
import matplotlib.pyplot as plt

c = 1


def w_func(x, t):
    return np.sin(x + c * t)


def v_func(x, t):
    return np.sin(x + c * t) + np.cos(2 * x + 2 * c * t)


[x, t] = np.meshgrid(np.linspace(0, 10), np.linspace(0, 10))

w = w_func(x, t)
v = v_func(x, t)

fig = plt.figure(1)
ax = fig.add_subplot(111, projection="3d")
ax.plot_wireframe(x, t, w, rstride=5, cstride=5)


plt.title("Gitter W")
ax.set_xlabel("x")
ax.set_ylabel("t")
ax.set_zlabel("w")

plt.show()


fig = plt.figure(2)
ay = fig.add_subplot(111, projection="3d")
ay.plot_wireframe(x, t, v, rstride=5, cstride=5)

plt.title("Gitter V")
ax.set_xlabel("x")
ax.set_ylabel("t")
ax.set_zlabel("v")

plt.show()
