import sympy as sp

x1, x2, x3 = sp.symbols('x1, x2, x3')

f1 = x1 + x2 ** 2 - x3 ** 2 - 13
f2 = sp.log(x2 / 4) + sp.exp(0.5 * x3 - 1) - 1
f3 = (x2 - 3) ** 2 - x3 ** 3 + 7

f = sp.Matrix([f1, f2, f3])
x = sp.Matrix([x1, x2, x3])

x0 = sp.Matrix([1.5, 3, 2.5])

Df = f.jacobian(x)
print("Df:", Df)

Df0 = Df.subs([(x1, 1.5), (x2, 3), (x3, 2.5)])
print("Df0:", Df0)

f0 = f.subs([(x1, 1.5), (x2, 3), (x3, 2.5)])
print("f0:", f0)

g = f0 + Df0 * (x - x0)
print("g:", g)
