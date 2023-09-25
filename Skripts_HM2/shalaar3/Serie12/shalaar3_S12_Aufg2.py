import numpy as np
import matplotlib.pyplot as plt
from shalaar3_S12_Aufg1 import s12
from shalaar3_S12_Aufg1 import f

a = 1
b = 6
y0 = 5
h = 0.01
n = int((b - a) / h)

t, y_num = s12(f, a, b, n, y0)

def f(x, y):
    return 1 - y / x

def y_exact(x):
    return (x / 2.0) + 9 / (2.0 * x)

# plt.figure(1)
# plt.plot(t, y_exact(t), "r")
# plt.plot(t, y_num, "b--")
# plt.legend(["exact", "classical RK4"])
# plt.title("Fig1")
# plt.grid()
# plt.show()

def my_rk4(f, a, b, n, y0):
    h = (b - a) / n
    x = np.zeros(n + 1)
    x[0] = a
    y = np.zeros(n + 1)
    y[0] = y0

    c = np.array([0.25, 0.5, 0.5, 0.75])
    a = np.array([[0, 0, 0],
                  [0.25, 0, 0],
                  [0.5, 0.5, 0],
                  [0.75, 0.75, 0.75]])

    b = np.array([0.1, 0.4, 0.4, 0.1])
    for i in range(0, n):
        k1 = f(x[i] + c[0] * h, y[i])
        k2 = f(x[i] + c[1] * h, y[i] + h * (a[1, 0] * k1))
        k3 = f(x[i] + c[2] * h, y[i] + h * (a[2, 0] * k1 + a[2, 1] * k2))
        k4 = f(x[i] + c[3] * h, y[i] + h * (a[3, 0] * k1 + a[3, 1] * k2 + a[3, 2] * k3))
        x[i + 1] = x[i] + h
        y[i + 1] = y[i] + h * (b[0] * k1 + b[1] * k2 + b[2] * k3 + b[3] * k4)
    return x, y

t, my_y = my_rk4(f, a, b, n, y0)

plt.figure(2)
plt.plot(t, y_num, "g--", label="classical RK4")
plt.plot(t, y_exact(t), "b", label="exact")
plt.plot(t, my_y, "r--", label="my RK4")
plt.legend()
plt.title("Fig2")
plt.grid()
plt.show()

plt.figure(3)
plt.semilogy(t, np.abs(y_exact(t) - y_num), "g", label="classical RK4")
plt.semilogy(t, np.abs(y_exact(t) - my_y), "b--", label="my RK4")
plt.legend()
plt.title("Absolute Error")
plt.grid()
plt.show()