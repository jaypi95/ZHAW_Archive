import numpy as np
import matplotlib.pyplot as plt

A = 25
G = 500
K = 0.0025

def H(t):
    return G / (1 + ((G/A) - 1) * np.exp(-K * G * t))

x = np.arange(0, 10)
plt.figure(1)
plt.grid()
plt.plot(x, H(x), zorder=0, label='Hefe')
plt.legend()
plt.show()