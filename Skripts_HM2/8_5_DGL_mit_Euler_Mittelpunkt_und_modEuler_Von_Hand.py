import numpy as np
import matplotlib.pyplot as plt
from tools import *

"""
=======================================================================================================================
INPUT
=======================================================================================================================
"""


def f(x, y):
    return ((x ** 2) * 1.0) / y  # Rechte Seite der DGL y' = f(x, y)


def y_exact(x, y):
    return np.sqrt((2 * x ** 3) / 3.0 + 4)  # Exakte Lösung der DGL


# fixme: Make it possible to display mashgrid in all cases (possibly add an area for mashgrid data OR derive it from the data somehow OR differenciate between a mashgrid for the function and a mashgrid by itself dick)

a = 0
b = 20

n = 4
y0 = 0
x0 = 0
schrittweite = 0.1
hx = 1  # Schrittweite in X
hy = 1  # Schrittweite in Y

# Area to Plot Arrows
xmin = 0
xmax = 17
ymin = 0
ymax = 50
h_meshgrid = 2.5

show_euler = 1  # 1 = show, 0 = don't show
show_mittelpunkt = 1  # 1 = show, 0 = don't show
show_modEuler = 1  # 1 = show, 0 = don't show

"""
=======================================================================================================================
"""

if show_euler == 0 and show_mittelpunkt == 0 and show_modEuler == 0:
    raise Exception('Nothing selected!')


h = ((b - a) * schrittweite) / n

def interpolate_euler(f, x, h, y0):
    xData = np.full(x.shape[0], 0, dtype=np.float64)
    y = np.full(x.shape[0], 0, dtype=np.float64)
    y[0] = y0

    print(f'Allgemeine Euler Formel:')
    print(f'x[i+1] = x[i] + h')
    print(f'y[i+1] = y[i] + h * f(x[i], y[i])')

    for i in range(x.shape[0] - 1):
        y[i + 1] = y[i] + h * f(x[i], y[i])
        xData[i + 1] = x[i] + h
        print(f'y[{i + 1}] = y[{i}] + {h} * f(x[{i}], y[{i}])\n\t{y[i + 1]} = {y[i]} + {h} * {f(x[i], y[i])}')
        print(f'x[{i + 1}] = x[{i}] + {h}\n\t{xData[i + 1]} = {xData[i]} + {h}')
        print('8=====>')

    return y


def interpolate_midpoint(f, x, h, y0):
    xData = np.full(x.shape[0], 0, dtype=np.float64)
    y = np.full(x.shape[0], 0, dtype=np.float64)
    y[0] = y0

    print(f"y' = f(x, y) mit y(a) = y0")
    print(f"x_h/2 = x[i] + h/2")
    print(f"y_h/2 = y[i] + (h/2) * f(x_i, y_i)")
    print(f"x[i+1] = x[i] + h")
    print(f"y[i+1] = y[i] + h * f(x_h/2, y_h/2)")

    for i in range(x.shape[0] - 1):
        x_h_2 = x[i] + (h / 2.0)
        y_h_2 = y[i] + h / 2.0 * f(x[i], y[i])
        xData[i + 1] = x[i] + h / 2.0
        y[i + 1] = y[i] + h * f(x[i] + (h / 2.0), y[i] + (h / 2.0) * f(x[i], y[i]))
        print(f'x_(h/2) = x[{i}] + {h} / 2.0\n\t{x_h_2} = {x[i]} + {h / 2.0}')
        print(f'y_(h/2) = y[{i}] + {h} * {f(x[i], y[i])} / 2.0\n\t{y_h_2} = {y[i]} + {h} * {f(x[i], y[i])} / 2.0')
        print(f'x[{i + 1}] = x[{i}] + {h}\n\t{xData[i + 1]} = {xData[i]} + {h}')
        print(f'y[{i + 1}] = y[{i}] + {h} * {f(x_h_2, y_h_2)}')
        print('8======>')

    return y


def interpolate_mod_euler(f, x, h, y0):
    y = np.full(x.shape[0], 0, dtype=np.float64)
    y[0] = y0

    print(f"y' = f(x, y) mit y(a) = y0")
    print(f"x[i+1] = x[i] + h")
    print(f"y[i+1] = y[i] + h * (f(x[i], y[i])")
    print(f"k[1] = f(x[i], y[i])")
    print(f"k[2] = f(x[i], y[i+1])")

    for i in range(x.shape[0] - 1):
        print(f'{x[i + 1]}{convertToSub(i + 1)} = {x[i]}{convertToSub(i)} + {h}')
        y[i + 1] = y[i] + h * (f(x[i], y[i]) + f(x[i + 1], y[i] + h * f(x[i], y[i]))) / 2
        print('_____________')

    return y


x = np.arange(a, b + h, step=h, dtype=np.float64)
print('x = ' + str(x))

y_euler = interpolate_euler(f, x, h, y0)
y_midpoint = interpolate_midpoint(f, x, h, y0)
y_mod_euler = interpolate_mod_euler(f, x, h, y0)

if show_euler == 1:
    print("Euler:")
    print('y_euler = ' + str(y_euler))
    print("\n")

if show_mittelpunkt == 1:
    print("Mittelpunkt:")
    print('y_midpoint = ' + str(y_midpoint))
    print("\n")

if show_modEuler == 1:
    print("Mod. Euler:")
    print('y_mod_euler = ' + str(y_mod_euler))


x_plot = np.arange(xmin, xmax, step=h_meshgrid, dtype=np.float64)
y_plot = np.arange(ymin, ymax, step=h_meshgrid, dtype=np.float64)
[x_grid, y_grid] = np.meshgrid(x_plot, y_plot)

x_exact = np.arange(xmin, xmax, step=0.001, dtype=np.float64)

dy = f(x_grid, y_grid)
dx = np.full((dy.shape[0], dy.shape[1]), 1, dtype=np.float64)

plt.figure(1)
plt.title("DGL plot")
plt.quiver(x_grid, y_grid, dx, dy)
plt.plot(x_exact, y_exact(x_exact, 0), label='Exact')
plt.plot(x, y_euler, label='Euler')
plt.plot(x, y_midpoint, label='Midpoint')
plt.plot(x, y_mod_euler, label='Mod. Euler')
plt.grid()
plt.legend()
plt.show()

plt.figure(2)
plt.title("Absolute Fehler: Vergleich")
plt.semilogy(x, abs(y_euler - y_exact(x, 0)), label='Euler')
plt.semilogy(x, abs(y_midpoint - y_exact(x, 0)), label='Midpoint')
plt.semilogy(x, abs(y_mod_euler - y_exact(x, 0)), label='Mod. Euler')
plt.grid()
plt.legend()
plt.show()


def plotField(f, xmin, xmax, ymin, ymax, hx, hy, figureId, title):
    x = np.arange(xmin, xmax + hx, hx)
    y = np.arange(ymin, ymax + hy, hy)
    X, Y = np.meshgrid(x, y)
    m, n = np.shape(X)

    DX = np.ones((m, n))
    DY = f(X, Y)
    figureId += + 1

    plt.figure(figureId)
    plt.title(f'dynamische Pfeile für {title}')
    plt.quiver(X, Y, DX, DY)
    plt.xlim([xmin - hx, xmax + hx])
    plt.ylim([ymin - hy, ymax + hy])
    plt.grid()
    plt.show()

    DZ = (DX ** 2 + DY ** 2) ** 0.5
    DXnorm = DX / DZ
    DYnorm = DY / DZ

    figureId += + 1
    plt.figure(figureId)
    plt.quiver(X, Y, DXnorm, DYnorm)
    plt.xlim([xmin - hx, xmax + hx])
    plt.ylim([ymin - hy, ymax + hy])
    plt.title(f'statische Pfeile für {title}')
    plt.grid()
    plt.show()
    return figureId


figureId = 3
figureId = plotField(f, xmin, xmax, ymin, ymax, hx, hy, figureId, 'f(x,y)')
figureId = plotField(y_exact, xmin, xmax, ymin, ymax, hx, hy, figureId, 'y_exact(x,y)')

exit()
