import numpy as np
import matplotlib.pyplot as plt


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


data = np.array([[33, 53, 3.32, 3.42, 29],
                 [31, 36, 3.1, 3.26, 24],
                 [33, 51, 3.18, 3.18, 26],
                 [37, 51, 3.39, 3.08, 22],
                 [36, 54, 3.2, 3.41, 27],
                 [35, 35, 3.03, 3.03, 21],
                 [59, 56, 4.78, 4.57, 33],
                 [60, 60, 4.72, 4.72, 34],
                 [59, 60, 4.6, 4.41, 32],
                 [60, 60, 4.53, 4.53, 34],
                 [34, 35, 2.9, 2.95, 20],
                 [60, 59, 4.4, 4.36, 36],
                 [60, 62, 4.31, 4.42, 34],
                 [60, 36, 4.27, 3.94, 23],
                 [62, 38, 4.41, 3.49, 24],
                 [62, 61, 4.39, 4.39, 32],
                 [90, 64, 7.32, 6.7, 40],
                 [90, 60, 7.32, 7.2, 46],
                 [92, 92, 7.45, 7.45, 55],
                 [91, 92, 7.27, 7.26, 52],
                 [61, 62, 3.91, 4.08, 29],
                 [59, 42, 3.75, 3.45, 22],
                 [88, 65, 6.48, 5.8, 31],
                 [91, 89, 6.7, 6.6, 45],
                 [63, 62, 4.3, 4.3, 37],
                 [60, 61, 4.02, 4.1, 37],
                 [60, 62, 4.02, 3.89, 33],
                 [59, 62, 3.98, 4.02, 27],
                 [59, 62, 4.39, 4.53, 34],
                 [37, 35, 2.75, 2.64, 19],
                 [35, 35, 2.59, 2.59, 16],
                 [37, 37, 2.73, 2.59, 22],
                 ])

n = np.shape(data)[0]
m = np.shape(data)[1]

x_Data = np.arange(1, n + 1)
y_Data = data[0:n, m - 1]
A = np.ones((n, m))
A[0:n, 0:m - 1] = data[0:n, 0:m - 1]

Q, R = np.linalg.qr(A)
Q_T = np.transpose(Q)
lambada_qr = np.linalg.solve(R, Q_T @ y_Data)

y_plot = A @ lambada_qr

plotter(1, [[x_Data, y_Data, 'Data'], [x_Data, y_plot, 'Polyfit']], 'MessID', 'Messwert', 'A2')