import numpy as np
import matplotlib.pyplot as plt

'''INPUT'''
x_in = np.array([0, 1, 2, 3, 4, 5], dtype=np.float64)
y_in = np.array([0.54, 0.44, 0.28, 0.18, 0.12, 0.08], dtype=np.float64)
x = np.arange(0, 5, 0.1)
grad = 4

A = np.array([np.ones(x.shape), x ** 2]).T


def y(x):
    return 1 / (1.82083754 + 0.42108857 * x ** 2)


'''INPUT'''


coeff = np.polyfit(x_in, y_in, grad)

plt.figure(1)
plt.grid()
plt.plot(x, np.polyval(coeff, x), zorder=0, label='p(x)')
plt.plot(x, y(x), zorder=0, label='y(x)')
plt.scatter(x_in, y_in, marker='x', color='r', zorder=1, label='measured')
plt.legend()
plt.show()


print("fehler E: p(x)", np.linalg.norm((y_in - np.polyval(coeff, x_in))) ** 2)
print("fehler E: y(x)", np.linalg.norm((y_in - y(x_in))) ** 2)
