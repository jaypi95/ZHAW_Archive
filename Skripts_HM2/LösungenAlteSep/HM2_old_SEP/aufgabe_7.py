import numpy as np
import matplotlib.pyplot as plt

'''INPUT'''
# Aufgaben wie: 2 DGL lösungen fehler plotten

a = 0
b = 6

# h = ((b - a) * 1.0) / n
h = 0.2
n = int((b - a) / h)
y0 = 0.0


def f(t, y):
    return 0.1 * y + np.sin(2 * t)


'''INPUT'''


def interpolate_euler(f, x, h, y0):
    y = np.zeros(n + 1)
    y[0] = y0

    for i in range(x.shape[0] - 1):
        y[i + 1] = y[i] + h * f(x[i], y[i])

    return y


def interpolate_runge_kutta(f, x, h, y0):
    y = np.zeros(n + 1)
    y[0] = y0

    for i in range(x.shape[0] - 1):
        k1 = f(x[i], y[i])
        k2 = f(x[i] + h / 2, y[i] + h / 2 * k1)
        k3 = f(x[i] + h / 2, y[i] + h / 2 * k2)
        k4 = f(x[i] + h, y[i] + h * k3)
        y[i + 1] = y[i] + h / 6 * (k1 + 2 * k2 + 2 * k3 + k4)

    return y


x = np.arange(a, b + h, step=h, dtype=np.float64)

y_euler = interpolate_euler(f, x, h, y0)
y_runge_kutta = interpolate_runge_kutta(f, x, h, y0)

print('x = ' + str(x))
print('y_euler = ' + str(y_euler))
print('y_runge_kutta = ' + str(y_runge_kutta))

xmin = -0.1
xmax = 1.5
ymin = 1.9
ymax = 2.6

x_exact = np.arange(xmin, xmax, step=0.001, dtype=np.float64)

plt.figure(0)
plt.title('Numerical integration methods')
plt.plot(x, y_euler, label='Euler')
plt.plot(x, y_runge_kutta, label='Runge-Kutta')
plt.legend()
plt.grid()
plt.show()

plt.figure(1)
plt.title('Global error')
plt.semilogy()
plt.plot(x, np.abs(y_euler - y_runge_kutta), label='Abs Fehler')
plt.legend()
plt.grid()
plt.show()

# plt.figure(1)
# plt.title('Absoluter error')
# plt.semilogy()
# plt.plot(x, np.abs((y_euler[0] - y_runge_kutta[0])), label='Fehler')
# # plt.plot(x, np.abs((y_euler[1] - y_runge_kutta[1])), label='h\'')
# plt.legend()
# plt.grid()
# plt.show()
