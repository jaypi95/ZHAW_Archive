import numpy as np
import matplotlib.pyplot as plt

"""
=======================================================================================================================
INPUT
=======================================================================================================================
"""

xmin = -1
xmax = 4
ymin = 0.5
ymax = 8
hx = 0.5
hy = 0.5


def f(x, y):
    return ((x ** 2) * 1.0) / y  # Rechte Seite der DGL in expliziter Form y' = f(x, y)


"""
=======================================================================================================================
"""


def plot_slope_field(f, xmin, xmax, ymin, ymax, hx, hy):
    x = np.arange(xmin, xmax, step=hx, dtype=np.float64)
    y = np.arange(ymin, ymax, step=hy, dtype=np.float64)
    [x_grid, y_grid] = np.meshgrid(x, y)

    dy = f(x_grid, y_grid)
    dx = np.full((dy.shape[0], dy.shape[1]), 1, dtype=np.float64)

    plt.quiver(x_grid, y_grid, dx, dy)
    plt.show()


plot_slope_field(f, xmin, xmax, ymin, ymax, hx, hy)  # f, xmin, xmax, ymin, ymax, hx, hy (Schrittweiten)
