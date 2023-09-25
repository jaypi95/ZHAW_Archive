import numpy as np
import matplotlib.pyplot as plt

"""
=======================================================================================================================
INPUT
=======================================================================================================================
"""
data = np.array([
    [1971, 2250.],
    [1972, 2500.],
    [1974, 5000.],
    [1978, 29000.],
    [1982, 120000.],
    [1985, 275000.],
    [1989, 1180000.],
    [1989, 1180000.],
    [1993, 3100000.],
    [1997, 7500000.],
    [1999, 24000000.],
    [2000, 42000000.],
    [2002, 220000000.],
    [2003, 410000000.],
])

offset = 1970  # Meistens data[0][0]

wertZuExtrapolieren = 2015

f_basis = lambda x, power=0: pow(x, power)

f = lambda x, lam: lam[0] * f_basis(x) + lam[1] * f_basis(x, 1)

N = lambda t, lam: 10 ** f(t - offset, lam)


plt1_xlabel = 'Jahre ab 1970'
plt1_ylabel = 'Anzahl Chips pro Transistor (Zehnerlog)'
plt1_title = 'A3'

plt2_xlabel = 'Meswerte'
plt2_ylabel = 'Anzahl Chips pro Transistor'
plt2_title = 'A3'

"""
=======================================================================================================================
"""

n = data.shape[0]
m = data.shape[1]
n_data = data[0:n, 1]
t_data = data[0:n, 0]
x_data = t_data - offset
y_data = np.log10(n_data)

A = np.zeros((n, m))
for i in range(0, n):
    A[i, 0:m] = np.array([f_basis(x_data[i]), f_basis(x_data[i], 1)])

Q, R = np.linalg.qr(A)
Q_T = np.transpose(Q)
lambada = np.linalg.solve(R, Q_T @ y_data)
print(f"lambda = {lambada}")

x_plot = np.arange(0, 35, 0.1)
y_plot = f(x_plot, lambada)


def plotter(id, data, xlabel, ylabel, title):
    plt.figure(id)
    marker = 'o'
    linestyle = ''

    for i in range(0, len(data)):
        plt.plot(data[i][0], data[i][1], marker=marker, linestyle=linestyle, label=data[i][2])
        marker = ''
        linestyle = '-'

    plt.title(title)
    plt.xlabel(xlabel)
    plt.ylabel(ylabel)

    plt.grid()
    plt.legend()
    plt.show()

plotter(1, [[x_data, y_data, 'Data'], [x_plot, y_plot, 'Regressionsgerade']], plt1_xlabel, plt1_ylabel, plt1_title)

t_plot = np.arange(data[0][0] - abs(data[0][0]-data[1][0]), data[-1][0] + abs(data[-1][0]-data[-2][0]), 0.1)
N_plot = N(t_plot, lambada)
plotter(2, [[t_data, n_data, 'Data'], [t_plot, N_plot, 'Fit']], plt2_xlabel, plt2_ylabel, plt2_title)

a = 10 ** (lambada[0] - offset * lambada[1])
b = lambada[1]

print(f"N({wertZuExtrapolieren}) extrapoliert = {N(wertZuExtrapolieren, lambada)}")
print(f"{a=}")
print(f"{b=}")
print(f"T = {np.log10(2) / b}")

exit()
