import sympy as sy

from tools import encodeURL

"""
=======================================================================================================================
INPUT
=======================================================================================================================
"""
x_val = sy.Matrix([0, 1, 2])  # xi
y_val = sy.Matrix([1, 2, 4])  # yi
x_int = 0.5  # Zu interpolierende Stelle
showSteps = 1  # 0: hide steps, 1: show steps

n = x_val.shape[0] - 1

p = 0

"""
=======================================================================================================================
"""

x = sy.symbols('x')

for i in range(n + 1):

    l = 1
    for j in range(n + 1):
        if j == i:
            continue

        l *= (x - x_val[j]) / (x_val[i] - x_val[j])

    p += y_val[i] * l

print('Pn(x) = ' + str(sy.simplify(p)))
url = "https://latex.codecogs.com/svg.image?" + encodeURL(sy.latex(sy.simplify(p)))
print("LATEX:\n" + url)
print('P_n(x)=' + str(sy.latex(sy.simplify(p))))
print()

p = p.subs(x, x_int)
y_int = p.evalf()

print(f'Pn({x_int}) = {y_int}')
