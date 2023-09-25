import numpy as np
import matplotlib.pyplot as plt

'''INPUT'''
# Aufgaben wie: 2 DGL lösungen fehler plotten
# geg: f''.
# ges: fehler plotten von f und f' via RK und andere

a = 0
b = 190

# h = ((b - a) * 1.0) / n
h = 0.1
n = int((b - a) / h)
y0 = np.array([0., 0.])

# konstante
ma = 300_000
me = 80_000
q = (ma - me) / 190


def f(t, y):
    # hier muss die matrix aufgebaut werden wie sie auf eine dgl 1 umgestellt wurde.
    # [ h', dgl ]
    return np.array([y[1], 2600 * q / (ma - q * t) - 9.81 - (np.exp((-y[0]) / 8000) / (ma - q * t)) * (y[1] ** 2)])


'''INPUT'''


def interpolate_mod_euler(f, x, h, y0):
    y = np.zeros((y0.size, n + 1))
    y[:, 0] = y0

    for i in range(x.shape[0] - 1):
        y[:, i + 1] = y[:, i] + h * (f(x[i], y[:, i]) + f(x[i + 1], y[:, i] + h * f(x[i], y[:, i]))) / 2

    return y


def interpolate_runge_kutta(f, x, h, y0):
    y = np.zeros((y0.size, n + 1))
    y[:, 0] = y0

    for i in range(x.shape[0] - 1):
        k1 = f(x[i], y[:, i])
        k2 = f(x[i] + h / 2, y[:, i] + h / 2 * k1)
        k3 = f(x[i] + h / 2, y[:, i] + h / 2 * k2)
        k4 = f(x[i] + h, y[:, i] + h * k3)
        y[:, i + 1] = y[:, i] + h / 6 * (k1 + 2 * k2 + 2 * k3 + k4)

    return y


x = np.arange(a, b + h, step=h, dtype=np.float64)

y_mod_euler = interpolate_mod_euler(f, x, h, y0)
y_runge_kutta = interpolate_runge_kutta(f, x, h, y0)

print('x = ' + str(x))
print('y_mod_euler = ' + str(y_mod_euler))
print('y_runge_kutta = ' + str(y_runge_kutta))

xmin = -0.1
xmax = 1.5
ymin = 1.9
ymax = 2.6

x_exact = np.arange(xmin, xmax, step=0.001, dtype=np.float64)

plt.figure(1)
plt.title('Relativer error')
plt.semilogy()
plt.plot(x, np.abs((y_mod_euler[0, :] - y_runge_kutta[0, :])) / y_mod_euler[0, :], label='h')
plt.plot(x, np.abs((y_mod_euler[1, :] - y_runge_kutta[1, :])) / y_mod_euler[1, :], label='h\'')
plt.legend()
plt.grid()
plt.show()
