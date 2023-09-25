import numpy as np
import sympy as sy

from tools import encodeURL

"""
=======================================================================================================================
INPUT
=======================================================================================================================
"""

x = np.array([-1,0,2,3])  # xi
y = np.array([-2,0,4,0])  # yi
x_int = 1  # Zu interpolierende Stelle
showSteps = 1 # 0: hide steps, 1: show steps

"""
=======================================================================================================================
"""

n = x.shape[0] - 1

def lagrange_int(x, y, x_int):
    return np.sum([y[i] * np.prod([(x_int - x[j] * 1.0) / (x[i] - x[j]) for j in range(x.shape[0]) if j != i]) for i in range(x.shape[0])])

y_int = lagrange_int(x, y, x_int)

if showSteps == 1:
    print(f'Interpolationspolynom vom Grad {n}:')
    sum_out = ''
    for i in range(n + 1):
        sum_out += f'{y[i]} * ℓ{i}(x)'
        if i < n:
            sum_out += ' + '

    print(f'Pn(x) = {sum_out} mit x = {x_int}\n')

    print('Lagrange-Polynome ℓi:')
    print('ℓi(x) = ∏[j = 0 .. n, j ≠ i](x - xj)/(xi - xj)')

    for i in range(n + 1):
        prod_out_sym = ''
        prod_out_con = ''
        prod_res = 1

        for j in range(n + 1):
            last = j == n or (i == n and j == n - 1)
            if j == i:
                continue

            prod_out_sym += f'(x - x{j})/(x{i} - x{j})'
            prod_out_con += f'({x_int} - {x[j]})/({x[i]} - {x[j]})'
            prod_res *= (x_int - x[j]) / (x[i] - x[j])

            if not last:
                prod_out_sym += ' * '
                prod_out_con += ' * '

        print(f'ℓ{i}(x) = {prod_out_sym} = {prod_out_con} = {prod_res}')
    print(f'\nEinsetzen:')
    print(f'Pn(x) = ∑[i = 0 .. {n}] yi * ℓi(x)')
    print(f'Pn(x) = {sum_out} = {y_int}')

x_val = sy.Matrix(x)  # xi
y_val = sy.Matrix(y)  # yi
xSymb = sy.symbols('x')

p = 0
for i in range(n + 1):

    l = 1
    for j in range(n + 1):
        if j == i:
            continue

        l *= (xSymb - x_val[j]) / (x_val[i] - x_val[j])

    p += y_val[i] * l

print(f'Pn(x) = {str(sy.simplify(p))}')
url = "https://latex.codecogs.com/svg.image?" + encodeURL(sy.latex(sy.simplify(p)))
print("LATEX: " + url)
print(f'P_n(x)={str(sy.latex(sy.simplify(p)))}')
print()

p = p.subs(xSymb, x_int)
y_int = p.evalf()

print(f"f({str(x_int)}) = {str(y_int)} = {y_int:.5f}")
