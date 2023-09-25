import numpy as np
import matplotlib.pyplot as plt

'''INPUT'''
x_in = np.array([0, 0.2, 0.4, 0.6, 0.8, 1.0, 1.2, 1.4, 1.6, 1.8, 2.0], dtype=np.float64)
y_in = np.array([39.55, 46.55, 50.13, 51.75, 55.25, 56.79, 56.78, 59.13, 57.76, 59.39, 60.08], dtype=np.float64)

'''INPUT'''

plt.figure(1)
plt.grid()

plt.scatter(x_in, y_in, marker='x', color='r', zorder=1, label='measured')
plt.legend()
plt.show()

# A = 39  (<40)
# Q = 60 (>60) Quelle muss grösser sein, als Ladung im Kondensator
# rho = 0.4 => t = 5r  t = voll = 2s <> 2 = 5*r => r = 0.4
