import sympy as sp

x1, x2, x3 = sp.symbols('x1, x2, x3')

f1 = sp.ln(x1 ** 2 + x2 ** 2) + x3 ** 2
f2 = sp.exp(x2 ** 2 + x3 ** 2) + x1 ** 2
f3 = 1 / (x3 ** 2 + x1 ** 2) + x2 ** 2

f = sp.Matrix([f1, f2, f3])
x = sp.Matrix([x1, x2, x3])


Df = f.jacobian(x)
print("Df:", Df)

Df0 = Df.subs([(x1, 1), (x2, 2), (x3, 3)])
print("Df0:", Df0)

f0 = f.subs([(x1, 1.5), (x2, 3), (x3, 2.5)])
print("f0:", f0)

