import sympy as sy
import numpy as np

x = sy.symbols('x')
''' INPUT '''
f = sy.sin(x)

# für integral
a, b = 0, np.pi
''' INPUT '''

# df = sy.diff(f, x)
# dfl = sy.lambdify(x, df)
F = sy.integrate(f, x)

# print('Function:' + str(f))
# print('Ableitung:' + str(df))
print('Integral:' + str(F))

# g = np.abs(df * x) / np.abs(f)  # Konditionszahl
# print(sy.simplify(g))  # Vereinfacht

integral = sy.lambdify(x, F)
print('Integral nummerisch:', integral(b) - integral(a))

# b )
# einfache Tr = (f(a) + f(b)) / 2 * (b-a)
print(f'({np.sin(a)} + {np.sin(b)}) / 2 * {b}-{a} = {((np.sin(a) + np.sin(b)) / 2) * (b - a)}')
# 1.9236706937217898e-16 => 0


# c)
# exakt <= h^2/12 * (b-a) * max(f''(x)) <= 10^-3
df = sy.diff(f, x)
df = sy.diff(df, x)
zweite_ableitung = sy.lambdify(x, df)
h = sy.symbols('h')
max = np.max(np.abs(zweite_ableitung(np.arange(a, b, 0.1))))
eqn = sy.Eq(h ** 2 / 12 * (b - a) * max - 10 ** -3, 0)
print('max breite: ', sy.solve(eqn))
h = sy.solve(eqn)[1]
print('max Schritte: ', np.ceil((b - a) / h))

# max h = 0.06181, max steps = 51
