import numpy as np
import matplotlib.pyplot as plt


def euler(f, a, b, n, y0):

    x = np.zeros(n+1)
    x[0] = a
    y = np.zeros(n+1)
    y[0] = y0
    h = (b-a)/n

    for i in range(n):
        x[i+1] = x[i] + h
        y[i+1] = y[i] + h*f(x[i], y[i])

    return x, y

def euler_mid(f, a, b, n, y0):

    x = np.zeros(n + 1)
    x[0] = a
    y = np.zeros(n + 1)
    y[0] = y0
    h = (b - a) / n

    for i in range(n):
        x_2 = x[i] + h/2.
        y_2 = y[i] + h/2. * f(x[i], y[i])
        x[i + 1] = x[i] + h
        y[i + 1] = y[i] + h * f(x_2, y_2)

    return x, y

def euler_mod(f, a, b, n, y0):
    x = np.zeros(n + 1)
    x[0] = a
    y = np.zeros(n + 1)
    y[0] = y0
    h = (b - a) / n

    for i in range(n):
        x[i+1] = x[i] + h
        k1 = f(x[i], y[i])
        y_euler = y[i] + h * k1
        k2 = f(x[i+1], y_euler)
        y[i+1] = y[i] + h*(k1 + k2)/2.

    return x, y

def richtungfeld(f, xmin, xmax, ymin, ymax, hx, hy):
    x = np.arange(xmin, xmax + hx, hx)
    y = np.arange(ymin, ymax + hy, hy)
    X, Y = np.meshgrid(x, y)
    m, n = np.shape(X)
    DX = np.ones((m, n))
    DY = f(X, Y)

    # alle pfeile gleich lang
    Pfeil_laenge = (DX ** 2 + DY ** 2) ** 0.5
    DXnormiert = DX / Pfeil_laenge
    DYnormiert = DY / Pfeil_laenge
    plt.quiver(X, Y, DXnormiert, DYnormiert, scale=8)
    plt.xlim([xmin - hx, xmax + hx])
    plt.ylim([ymin - hy, ymax + hy])
    plt.xlabel('x')
    plt.ylabel('y')
    plt.grid()
    plt.show()

def Serie11_Aufg3(f, a, b, n, y0):

    x, y_euler = euler(f, a, b, n, y0)
    x, y_euler_mid = euler_mid(f, a, b, n, y0)
    x, y_euler_mod = euler_mod(f, a, b, n, y0)

    plt.plot(x, y_euler)
    plt.plot(x, y_euler_mid)
    plt.plot(x, y_euler_mod)

    ymin = np.min([y_euler, y_euler_mid, y_euler_mod])
    ymax = np.max([y_euler, y_euler_mid, y_euler_mod])
    hx = (b-a)/10
    hy = (ymax-ymin)/10
    richtungfeld(f, a, b, ymin, ymax, hx, hy)

    plt.legend(['Euler', 'Mittelpunkt-Euler', 'Modifizierter Euler', 'Richtungsfeld'])

    return x, y_euler, y_euler_mid, y_euler_mod

def f(x,y):
    return x**2/y

a = 0
b = 1.4
n = 2
y0 = 2

x, y_euler, y_euler_mid, y_euler_mod = Serie11_Aufg3(f, a, b, n, y0)

print('x = ', x)
print('y_euler = ', y_euler)
print('y_euler_mid', y_euler_mid)
print('y_euler_mod', y_euler_mod)