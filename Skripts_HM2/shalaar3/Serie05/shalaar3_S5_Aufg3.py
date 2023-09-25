import matplotlib.pyplot as plt
import numpy as np
import scipy.interpolate as ip

from shalaar3_S5_Aufg2 import shalaar3_S5_Aufg2 as a2


def plotFnc(x_data1, y_data1, xx_data, yy_data, numb, title):
    plt.figure(numb)
    plt.grid(visible=True)
    plt.plot(x_data1, y_data1, 'o', color='blue')
    plt.plot(xx_data, yy_data, '-', color='red')
    plt.legend(['Mine', 'Spline'])
    plt.title(title)
    plt.show()


x = np.arange(1900, 2020, 10)
y = np.array([75.995, 91.972, 105.711, 123.203, 131.669, 150.697, 179.323, 203.212, 226.505, 249.633, 281.422, 308.745])
xx = np.arange(1900, 2011, 1)

y_spline = a2(x, y, xx, 'A3')

plotFnc(x, y, xx, y_spline, 1, "A3 with own method")

spline = ip.CubicSpline(x, y, bc_type='natural')
xx2 = spline(xx)
plotFnc(x, y, xx, xx2, 2, 'A3 with scipy')
