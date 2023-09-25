import numpy as np
import matplotlib.pyplot as plt
import sympy as sp
from scipy import optimize as opt


def plotter(id, data, xlabel, ylabel, title):
    plt.figure(id)
    marker = 'o'
    linestyle = ''

    for i in range(0, len(data)):
        plt.plot(data[i][0], data[i][1], marker=marker, linestyle=linestyle, label=data[i][2])
        marker = ''
        linestyle = '-'

    plt.title(title)
    plt.xlabel(xlabel)
    plt.ylabel(ylabel)

    plt.grid()
    plt.legend()
    plt.show()


def gauss_newton(g, Dg, lambda_, tol, max_iter, pamx, damping):
    k = 0
    lambada = np.copy(lambda_)
    increment = tol + 1
    error = np.linalg.norm(g(lambada)) ** 2

    while increment > tol and k < max_iter:
        Q, R = np.linalg.qr(Dg(lambada))
        delta = np.linalg.solve(R, -Q.T @ g(lambada)).flatten()
        p = 0
        if damping:
            while p <= pamx and np.linalg.norm(g(lambada + delta / 2.0 ** p), 2) ** 2 >= error:
                p = p + 1
            if p == pamx + 1:
                p = 0

        lambada = lambada + delta / 2.0 ** p
        error = np.linalg.norm(g(lambada)) ** 2
        increment = np.linalg.norm(delta / (2.0 ** p))
        k += 1

        print(f"Iteration: {k}\nlambda: {lambada}\nincrement: {increment}\nerror: {error}\n==================")
    return (lambada, k)


x = np.array([2., 2.5, 3., 3.5, 4., 4.5, 5., 5.5, 6., 6.5, 7., 7.5, 8., 8.5, 9., 9.5])
# x = np.arange(2, 10, 0.5)
y = np.array(
    [159.57209984,
     159.8851819,
     159.89378952,
     160.30305273,
     160.84630757,
     160.94703969,
     161.56961845,
     162.31468058,
     162.32140561,
     162.88880047,
     163.53234609,
     163.85817086,
     163.55339958,
     163.86393263,
     163.90535931,
     163.44385491])

p = sp.symbols('p0 p1 p2 p3')


def f(x, p):
    return (p[0] + p[1] * pow(10, p[2] + p[3] * x)) / (1 + 10 ** (p[2] + p[3] * x))


g = sp.Matrix([y[k] - f(x[k], p) for k in range(len(x))])
Dg = g.jacobian(p)

g = sp.lambdify([p], g, 'numpy')
Dg = sp.lambdify([p], Dg, 'numpy')

lambda0 = np.array([100.0, 120.0, 3.0, -1.0])
tol = 1e-5
max_iter = 30
pmax = 5
damping = 1
lambda_damped, n = gauss_newton(g, Dg, lambda0, tol, max_iter, pmax, damping)
lambda_undamped, n = gauss_newton(g, Dg, lambda0, tol, max_iter, pmax, damping)

# plotter(1, [[x, y, 'Daten']], 'x', 'y', 'Mit Dampfung')

x_plot = np.arange(2, 10, 0.1)
plt.figure(1)
plt.plot(x, y, '+', x_plot, f(x_plot, lambda_damped))
plt.xlabel('x')
plt.ylabel('y')
plt.title("Mit Dämpfung")
plt.legend(['Daten', 'Fit'])
plt.grid()

plt.figure(2)
plt.plot(x, y, '+', x_plot, f(x_plot, lambda_undamped))
plt.xlabel('x')
plt.ylabel('y')
plt.title("Ohne Dämpfung")
plt.legend(['Daten', 'Fit'])

plt.grid()
plt.show()


def error_function(lambda_):
    return np.linalg.norm(g(lambda_)) ** 2


print(f"lambda with scipy: {opt.fmin(error_function, lambda0)}")

exit()
