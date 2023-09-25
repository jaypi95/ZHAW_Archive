import numpy as np
import matplotlib.pyplot as plt

'''INPUT'''
# DGL mit mehr als 1 ableitung. Dann muss mit Vektoren gearbeitet werden.
# zurest DGL umfrormen, dass links die höchste steht.
# dann in Matrix umformen (z1, z2, z3, ..)

a = 0
b = 190

# h = ((b - a) * 1.0) / n
h = 0.1
n = int((b - a) / h)
y0 = np.array([0., 0.])

# konstante
ma = 300_000
me = 80_000
q = (ma - me) / 190


def f(t, y):
    # hier muss die matrix aufgebaut werden wie sie auf eine dgl 1 umgestellt wurde.
    # [ h', dgl ]
    return np.array(
        [y[1],
         2600 * q / (ma - q * t) - 9.81 - (np.exp((-y[0]) / 8000) / (ma - q * t)) * (y[1] ** 2)
         ])


'''INPUT'''


def rk4_vektor(f, x, h, y0):
    y = np.zeros((y0.size, n + 1))

    # AB
    y[:, 0] = y0

    for i in range(0, n):
        k1 = f(x[i], y[:, i])
        k2 = f(x[i] + h / 2, y[:, i] + h / 2 * k1)
        k3 = f(x[i] + h / 2, y[:, i] + h / 2 * k2)
        k4 = f(x[i] + h, y[:, i] + h * k3)
        y[:, i + 1] = y[:, i] + h / 6 * (k1 + 2 * k2 + 2 * k3 + k4)
        # print(x[i + 1])
        # print(y[:, i + 1])
        # print('')

    return y


x = np.arange(a, b + h, step=h, dtype=np.float64)

y = rk4_vektor(f, x, h, y0)

print('x = ' + str(x))
print('y = ' + str(y))

plt.figure(0)
plt.title('höhe')
plt.plot(x, y[0, :], label='h') #hoehe
plt.legend()
plt.grid()
plt.show()
print('Max Höhe = ' + str(max(y[0,:])))

plt.figure(1)
plt.title('geschwindigkeit')
plt.plot(x, y[1, :], label='h\'') # gesch
plt.legend()
plt.grid()
plt.show()
print('Max Geschw = ' + str(max(y[1,:])))

# dgl 2 plotten
plt.figure(2)
plt.title('beschleunigung')
# hier DGL funktion einfügen
y = 2600 * q / (ma - q * x) - 9.81 - (np.exp((-y[0]) / 8000) / (ma - q * x)) * (y[1] ** 2)
plt.plot(x, y, label='h\'\'') # besch
plt.legend()
plt.grid()
plt.show()
print('Max beschleunigung = ' + str(max(y)))
