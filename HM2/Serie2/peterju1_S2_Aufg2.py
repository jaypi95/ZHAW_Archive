import sympy as sp

x1, x2, x3 = sp.symbols("x1 x2 x3")
# Calculate derivatives
## a)
f1 = 5 * x1 * x2
f2 = x1 ** 2 * x2 ** 2 + x1 + 2 * x2

f = sp.Matrix([f1, f2])

X = sp.Matrix([x1, x2])
Df = f.jacobian(X)
print(Df)

## b)
f3 = sp.log(x1 ** 2 + x2 ** 2) + x3 ** 2
f4 = sp.exp(x2 ** 2 + x3 ** 2) + x1 ** 2
f5 = 1 / (x3 ** 2 + x1 ** 2) + x2 ** 2

f = sp.Matrix([f3, f4, f5])
X = sp.Matrix([x1, x2, x3])
Df = f.jacobian(X)
print(Df)
