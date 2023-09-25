import numpy as np
import scipy.integrate

'''INPUT'''
f = lambda x: np.sin(x)  # funktion
a, b = 0, np.pi

n = 51  # interation schritte für höhe
# oder Schrittweite: setze eins von beiden und andere auf 0
h = 0  # 0.0618 nicht die breite sondern die max Schritte verwenden
err = 1e-3
'''INPUT'''


def sum_rectangle(f, a, b, n, h):
    x = np.linspace(a, b, n + 1)
    # h = (b - a) / n
    rf = h * np.sum(f(x[:-1] + h / 2))
    return rf


def sum_trapez(f, a, b, n, h):
    x = np.linspace(a, b, n + 1)
    # h = (b - a) / n
    tf = h * (
            (f(a) + f(b)) * 0.5
            + np.sum(f(x[1:-1]))
    )
    tmp = ''
    for x_in in x[1:-1]:
        tmp = '%s+ f(%s)' % (tmp, x_in)

    print(f' tf = {h} * (f({a}) + f({b})) * 0.5', tmp)
    return tf


def sum_simpson(f, a, b, n, h):
    x = np.linspace(a, b, n + 1)
    # h = (b - a) / n
    sf = h / 3 \
         * (0.5 * (f(a) + f(b))
            + np.sum(f(x[1:-1]))
            + 2 * np.sum(f((x[1:] + x[:-1]) * 0.5))
            )
    return sf


if n == 0:
    n = np.ceil((b - a) / h).astype('int')

if h == 0:
    h = (b - a) / n
print(f'schritte: {n}, weite:{h}')

tf = sum_trapez(f, a, b, n, h)

exact = scipy.integrate.quad(f, a, b)[0]

print("exakte lösung: ", exact)
print("sum Trapez:", tf, "  error: ", abs(tf - exact))
print('Schranke erfüllt: ', abs(tf - exact) < err)
