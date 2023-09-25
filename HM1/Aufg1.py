import matplotlib.pyplot as plt
import numpy as np

x = np.arange(-3, 3, 0.1)

# f(x) aus Aufgabe 1
y = np.exp(x ** 2) + x ** (-3) - 10

plt.plot(x, y)
plt.grid()
plt.ylim(-10, 10)
plt.plot()
plt.show()

# Beim Newton Verfahren muss die Ableitung gemacht werden. Python bietet hier zwar eine Möglichkeit oder man benutzt die Funktion aus Serie 1
