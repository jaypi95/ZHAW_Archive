import numpy as np
import matplotlib.pyplot as plt

'''INPUT'''


def f(x, y):
    return 1 -y /x


def y_exact(x):
    return x/2+9/(2*x)


a = 1
b = 6
h = 0.01
y0 = 5
x = np.arange(a, b + h, step=h, dtype=np.float64)
'''INPUT'''


def interpolate_runge_kutta(f, x, h, y0):
    y = np.full(x.shape[0], 0, dtype=np.float64)
    y[0] = y0

    for i in range(x.shape[0] - 1):
        k1 = f(x[i], y[i])
        k2 = f(x[i] + (h / 2.0), y[i] + (h / 2.0) * k1)
        k3 = f(x[i] + (h / 2.0), y[i] + (h / 2.0) * k2)
        k4 = f(x[i] + h, y[i] + h * k3)

        y[i + 1] = y[i] + h * (1 / 6.0) * (k1 + 2 * k2 + 2 * k3 + k4)

    return y


def interpolate_runge_kutta_custom(f, x, h, y0):
    a = np.array([
        [0, 0, 0, 0],
        [0.5, 0, 0, 0],
        [0.5, 1, 0, 0]
        ,[0.75, 0.5, 0.75, 0]
    ], dtype=np.float64)
    b = np.array([1/10, 4/10, 4/10,1/10], dtype=np.float64)
    c = np.array([0.25,0.5,0.25,0.25], dtype=np.float64)
    s = a.shape[0]

    y = np.full(x.shape[0], 0, dtype=np.float64)
    y[0] = y0

    for i in range(x.shape[0] - 1):
        k = np.full(s, 0, dtype=np.float64)

        for n in range(s):
            k[n] = f(x[i] + (c[n] * h), y[i] + h * np.sum([a[n][m] * k[m] for m in range(n - 1)]))

        y[i + 1] = y[i] + h * np.sum([b[n] * k[n] for n in range(s)])

    return y


y = interpolate_runge_kutta(f, x, h, y0)
y_c = interpolate_runge_kutta_custom(f, x, h, y0)
print(f'Ergebnis RK: y({b}) = y{len(y)-1} = {y[len(y)-1]}')
print(f'Ergebnis RKC: y({b}) = y{len(y_c)-1} = {y_c[len(y_c)-1]}')


plt.figure(0)
plt.title('Runge-Kutta vs Runge-Kutta-custom vs Exact')
plt.plot(x, y_exact(x), label='Exact',linewidth=2)
plt.plot(x, y_c,'r', label='Runge-Kutta custom',linewidth=2, linestyle=(0, (5, 2, 1, 2)), dash_capstyle='round')
plt.plot(x, y,'g', label='Runge-Kutta',linewidth=2, linestyle=':')

plt.legend()
plt.grid()
plt.show()

plt.figure(1)
plt.semilogy()
plt.title('Absolute error')
plt.plot(x, np.abs(y - y_exact(x)), label='Runge-Kutta',linewidth=2)
plt.plot(x, np.abs(y_c - y_exact(x)), 'r--', label='Runge-Kutta custom' ,linewidth=2)
plt.legend()
plt.grid()
plt.show()
