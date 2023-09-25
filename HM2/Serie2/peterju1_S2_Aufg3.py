import sympy as sp

x1, x2, x3 = sp.symbols("x1 x2 x3")
x0 = sp.Matrix([[1.5], [3], [2.5]])


f1 = x1 + x2 ** 2 - x3 ** 2 - 13
f2 = sp.log(x2 / 4) + sp.exp(0.5 * x3 - 1) - 1
f3 = (x2 - 3) ** 2 - x3 ** 3 + 7

f = sp.Matrix([f1, f2, f3])

X = sp.Matrix([x1, x2, x3])
Df = f.jacobian(X)

f0 = f.subs([(x1, x0[0]), (x2, x0[1]), (x3, x0[2])])
Df0 = Df.subs([(x1, x0[0]), (x2, x0[1]), (x3, x0[2])])
delta = sp.Matrix([x1, x2, x3]) - sp.Matrix([x0[0], x0[1], x0[2]])


g = (f0 + Df0 * delta).expand()

print(g)
