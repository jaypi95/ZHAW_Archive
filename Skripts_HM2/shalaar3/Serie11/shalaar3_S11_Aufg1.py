import numpy as np
import matplotlib.pyplot as plt


def shalaar3_S11_Aufg1(f, xmin, xmax, ymin, ymax, hx, hy):
    x = np.arange(xmin, xmax + hx, hx)
    y = np.arange(ymin, ymax + hy, hy)
    X, Y = np.meshgrid(x, y)
    m, n = np.shape(X)

    DX = np.ones((m, n))
    DY = f(X, Y)

    plt.figure(1)
    plt.quiver(X, Y, DX, DY)
    plt.xlim([xmin - hx, xmax + hx])
    plt.ylim([ymin - hy, ymax + hy])
    plt.title('dynamische Pfeile')
    plt.grid()
    plt.show()

    DZ = (DX ** 2 + DY ** 2) ** 0.5
    DXnorm = DX / DZ
    DYnorm = DY / DZ

    plt.figure(2)
    plt.quiver(X, Y, DXnorm, DYnorm)
    plt.xlim([xmin - hx, xmax + hx])
    plt.ylim([ymin - hy, ymax + hy])
    plt.title('statische Pfeile')
    plt.grid()
    plt.show()


def f(x, y):
    return 0.5 * y + x ** 2


shalaar3_S11_Aufg1(f, -2, 2, -1, 1, 1, 1)