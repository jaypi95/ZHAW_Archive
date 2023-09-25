import matplotlib.pyplot as plt
import numpy as np

'''Input'''

#x = np.array([0, 14, 28, 42, 56])
#y = np.array([29, 2072, 15798, 25854, 28997])
def f1(x, y):
    return 1 - x **2 - y **2



def f2(x, y):
    return ((x -2)**2) / 2 + ((y - 1) ** 2) / 4 - 1

x = np.linspace(-10, 10, 1000)
y = np.linspace(-10, 10, 1000)
X, Y = np.meshgrid(x, y)
z1 = f1(X, Y)
z2 = f2(X, Y)




'''end of input'''

plt.figure(1)
plt.contour(x, y, z1, levels = [-2, -1, 0, 0.5], label="data")
plt.contour(x, y, z2, levels = [0.], label="data2")
plt.legend()
plt.grid()
plt.show()