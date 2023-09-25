import matplotlib.pyplot as plt
import numpy as np

debug = False


def shalaar3_S5_Aufg2(x, y, xx, title ='A2'):
    n = np.size(x) - 1
    a = np.copy(y[0:n])
    h = x[1:n + 1] - x[0:n]
    c = np.zeros(n + 1)

    if (debug):
        print("n: ", n)
        print("a: ", a)
        print("h: ", h)
        print("c: ", c)

    nn = n - 1

    A = np.zeros((nn, nn))
    for i in range(nn):
        A[i, i] = 2 * (h[i] + h[i + 1])
    for i in range(nn - 1):
        A[i, i + 1] = A[i + 1, i] = h[i]

    z = 3 * ((y[2:n + 1] - y[1:n]) / h[1:n]) - (y[1:n] - y[:nn]) / h[:nn]
    c[1:n] = np.linalg.solve(A, z)

    b = (y[1:n + 1] - y[:n]) / h - h / 3 * (c[1:n + 1] + 2 * c[:n])

    d = (c[1:n + 1] - c[:n]) / (3 * h)

    m = np.size(xx)
    yy = np.zeros(m)

    if (debug):
        print("A: ", A)
        print("z: ", z)
        print("b: ", b)
        print("d: ", d)
        print("m: ", m)
        print("yy: ", yy)

    for j in range(m):
        i = 0
        while xx[j] > x[i + 1]:
            i = i + 1
        t = xx[j] - x[i]
        yy[j] = a[i] + t * (b[i] + t * (c[i] + t * d[i]))

    plt.plot(xx, yy, 'x', color='blue')
    plt.plot(x, y, 'o', color='red')
    plt.legend(['Knoten', 'Interpolationspunkte'])
    plt.title(title)

    plt.show()
    plt.grid()

    return yy


x = np.array([4, 6, 8, 10])
y = np.array([6, 3, 9, 0])

xx = np.arange(4, 10, 0.1)
yy = shalaar3_S5_Aufg2(x, y, xx)
