import numpy as np
import matplotlib.pyplot as plt

# Teilaufgabe a
x = np.array([1997, 1999, 2006, 2010]) - 1997  # subtract smallest value to shift zero
y = np.array([150, 104, 172, 152])

A = np.array([
    [pow(x[0], 3), pow(x[0], 2), pow(x[0], 1), pow(x[0], 0)],
    [pow(x[1], 3), pow(x[1], 2), pow(x[1], 1), pow(x[1], 0)],
    [pow(x[2], 3), pow(x[2], 2), pow(x[2], 1), pow(x[2], 0)],
    [pow(x[3], 3), pow(x[3], 2), pow(x[3], 1), pow(x[3], 0)]
])

b = np.copy(y).T

coefficient = np.linalg.solve(A, b)
plt.figure(1)
x_graph = np.arange(0, 15, 0.01)
y_graph = np.polyval(coefficient, x_graph)
plt.plot(x, y, color='r', marker='o', linestyle='', markersize='10')
plt.plot(x_graph, y_graph, color='b')
plt.ylim([0, 200])
plt.grid()

# Teilaufgabe b
x_estimate = np.array([2003, 2004]) - 1997
y_estimate = np.polyval(coefficient, x_estimate)
plt.plot(x_estimate, y_estimate, color='g', marker='*', linestyle='', markersize='10')

# Teilaufgabe c
x_est2 = x_graph
y_est2 = np.polyval(np.polyfit(x, y, 3), x_est2)

plt.figure(2)
plt.grid()
plt.plot(x_est2, y_est2)

plt.show()