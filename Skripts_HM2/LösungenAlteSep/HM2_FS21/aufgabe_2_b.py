import numpy as np
import matplotlib.pyplot as plt

'''INPUT'''
x = np.array([0, 1, 2, 3, 4, 5], dtype=np.float64)
y = np.array([0.54, 0.44, 0.28, 0.18, 0.12, 0.08], dtype=np.float64)
# Transformation
y = 1 / y

# ausgleichsfunktion = 1/a+b*x**2  => a + b*x**2
A = np.array([
    np.ones(x.shape),
    x ** 2
]).T

'''INPUT'''


# verwenden für die berechnung von a und b
def linalg_solve(A, y):
    return np.linalg.solve(A.T @ A, A.T @ y)


print('a, b = ', linalg_solve(A, y))
