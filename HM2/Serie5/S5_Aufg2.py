import numpy as np
import matplotlib.pyplot as plt

def spline(x, y, xx):

    n = np.size(x)-1

    a = np.copy(y[0:n])
    h = x[1:n+1] - x[0:n]
    c = np.zeros(n+1)

    A = np.zeros((n-1, n-1))

    for i in range(n-1):
        A[i,i] = 2*(h[i]+h[i+1])

    for i in range(0, n-2):
        A[i, i+1] = h[i]
        A[i+1, i] = h[i]

    z = 3*((y[2:n+1]-y[1:n])/h[1:n] - (y[1:n]-y[0:n-1])/h[0:n-1])
    c[1:n] = np.linalg.solve(A,z)

    b = (y[1:n+1] -y[0:n])/h - h/3*(c[1:n+1]+2*c[0:n])

    d = (c[1:n+1]-c[0:n])/(3*h)

    m = np.size(xx)
    yy = np.zeros(m)

    for i in range(0,m):
        j = 0
        while xx[i] > x[j+1]:
            j += 1
        t = xx[i] - x[j]
        yy[i] = a[j] + t*(b[j] + t*(c[j] + t*d[j]))

    plt.plot(x, y, 'o')
    plt.plot(xx, yy)
    plt.legend(['Stützpunkte', 'Interpolationspunkte'])
    plt.show()
    return yy

x = np.array([4,6,8,10])
y = np.array([6,3,9,0])

xx = np.arange(4,10,0.1)
yy = spline(x, y, xx)