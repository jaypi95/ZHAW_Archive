import sympy as sp

x1, x2, x3 = sp.symbols('x1, x2, x3')


def a1():
    f1 = 5 * x1 * x2
    f2 = x1 ** 2 * x2 ** 2 + x1 + 2 * x2
    jacobi([f1, f2], [x1, x2])


def a2():
    f1 = sp.log(x1 ** 2 + x2 ** 2) + x3 ** 2
    f2 = sp.exp(x2 ** 2 + x2 ** 2) + x1 ** 2
    f3 = 1 / (x3 ** 2 + x1 ** 2) + x2 ** 2
    jacobi([f1, f2, f3], [x1, x2, x3])


def jacobi(functions, x_vals):
    f = sp.Matrix(functions)
    x = sp.Matrix(x_vals)

    Df = f.jacobian(x)
    print("jacobian:")
    print(Df)

    subList = []
    for ind, x_val in enumerate(x_vals):
        subList.append((x_val, ind + 1))

    Df0 = Df.subs(subList)
    print(Df0)
    print(Df0.evalf())


a1()
print("===========================")
a2()
