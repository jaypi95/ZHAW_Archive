import matplotlib.pyplot as plt
import numpy as np


def aufg1(f, xmin, xmax, ymin, ymax, hx, hy):

    x = np.arange(xmin, xmax+hx, hx)
    y = np.arange(ymin, ymax+hy, hy)
    X, Y = np.meshgrid(x, y)
    m, n = np.shape(X)

    # länge basierend auf stärke
    DX = np.ones((m, n))
    DY = f(X, Y)
    plt.quiver(X, Y, DX, DY)
    plt.xlim([xmin-hx, xmax+hx])
    plt.ylim([ymin-hy, ymax+hy])
    plt.xlabel('x')
    plt.ylabel('y')
    plt.grid()
    plt.show()


    # alle pfeile gleich lang
    Pfeil_laenge = (DX**2 + DY**2)**0.5
    DXnormiert = DX / Pfeil_laenge
    DYnormiert = DY / Pfeil_laenge
    plt.quiver(X, Y, DXnormiert, DYnormiert, scale=8)
    plt.xlim([xmin - hx, xmax + hx])
    plt.ylim([ymin - hy, ymax + hy])
    plt.xlabel('x')
    plt.ylabel('y')
    plt.grid()
    plt.show()

def f(x, y):
    return 1/2*y + x**2

aufg1(f, -2, 2, -1, 2, 1, 1)