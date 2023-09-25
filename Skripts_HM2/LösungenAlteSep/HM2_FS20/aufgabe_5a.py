import numpy as np
import matplotlib.pyplot as plt
from matplotlib import cm

# Wertebereich definieren:
x = np.array([500, 1000, 1500, 2500, 3500, 4000, 4500, 5000, 5250, 5500], dtype=np.float64)
y = np.array([10.5, 49.2, 72.1, 85.4, 113, 121, 112, 80.2, 61.1, 13.8], dtype=np.float64)

plt.grid()

plt.scatter(x, y, marker='x', color='r', zorder=1, label='measured')
plt.legend()
plt.show()
# 3 oder höher Grad

coeff = np.polyfit(x, y, 4)


plt.figure(1)
plt.grid()
plt.plot(x, np.polyval(coeff, x), zorder=0, label='polyfit')
plt.scatter(x, y, marker='x', color='r', zorder=1, label='measured')
plt.legend()
plt.show()