import math

import numpy as np
import matplotlib.pyplot as plt

x = np.arange(0.1,100,1)

#f(x)
y = 5 * ((2*(x**2))**(-1/3))
plt.loglog(x,y)
plt.xlabel('x')
plt.ylabel('f(x)')
plt.grid()
plt.plot()
plt.show()


# g(x)
y = 10**5 * (2*math.e)**(-x/100)
plt.semilogy(x,y)
plt.xlabel('x')
plt.ylabel('g(x)')
plt.grid()
plt.plot()
plt.show()

# h(x)
y = ((10**(2*x))/(2**(5*x)))**2
plt.semilogy(x,y)
plt.xlabel('x')
plt.ylabel('g(x)')
plt.grid()
plt.plot()
plt.show()