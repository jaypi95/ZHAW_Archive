import numpy
import matplotlib.pyplot as plt
import numpy as np


def sum_trapez(x, y):
    Tf = 0
    for i in range(0, x.size - 1):
        Tf = Tf + (y[i] + y[i + 1]) / 2 * (x[i + 1] - x[i])
    return Tf

def calc_plot_values(x, y):
    arr = np.zeros(x.size)
    for i in range(x.size):
        arr[i] = sum_trapez(x[0:i], y[0:i])
    return arr

def beschl(x):
    return v_rel * (µ/(ma - µ * x)) - g

#plotting graph credit goes to @shalaar3
def plotStuff(id, data, ylabel, legend):
    plt.figure(id)
    for dat in data:
        plt.plot(dat[0], dat[1])
    plt.xlabel('Zeit t')
    plt.ylabel(ylabel)
    plt.grid()
    plt.legend(legend)
    plt.title(ylabel)
    plt.show()


# Parameter
a = 0  # untere Grenze
b = 190  # obere Grenze
ma = 300000
me = te = 80000
µ = (ma-me)/190
v_rel = 2600
g = 9.81



x = np.arange(a,b)

v = calc_plot_values(x, beschl(x))
h = calc_plot_values(x, v)

v_analytic = v_rel * np.log(ma / (ma - µ * x)) -g * x
h_analytic = -v_rel * (ma - µ * x) / µ * np.log(ma / (ma - µ * x)) + v_rel * x - 0.5 * g * x**2

plotStuff(1, [[x, beschl(x)]], 'Beschleunigung', [])
plotStuff(1, [[x, v], [x, v_analytic]], 'Geschwindigkeit', ['Numerisch', 'Analytisch'])
plotStuff(1, [[x, h], [x, h_analytic]], 'Höhe', ['Numerisch', 'Analytisch'])