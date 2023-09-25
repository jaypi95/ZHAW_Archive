import sympy as sy

from tools import encodeURL

x1, x2, x3, x4, x5, x6, x7, x8, x9 = sy.symbols('x1, x2, x3, x4, x5, x6, x7, x8, x9')

"""
=======================================================================================================================
INPUT
=======================================================================================================================
"""

# ACHTUNG: Für sinus/cosinus/Exponentialfunktion immer sy.sin/sy.cos/sy.exp/sy.ln/sy.abs verwenden!
# WORKAROUND: define x,y,z as x1,x2,x3...
f = sy.Matrix([
    16 * x1 ** 2 - (x1 ** 2 + 1) * (x1 + x2) ** 2,
    9 * x2 ** 2 - (x2 ** 2 + 1) * (x1 + x2) ** 2
])

x = sy.Matrix([x1, x2])  # Wenn mehr oder weniger als 3 Variablen auftreten, diese Liste anpassen!
x0 = sy.Matrix([1, 1])    # Stelle, an welcher die Jacobi-Matrix ausgewertet werden soll, z.B. x0 bei Newton

"""
=======================================================================================================================
"""

print(f'Bilde die Jacobi-Matrix Df(x) für f(x) = {str(f)} mit x = {str(x)}.\n')

# Für jede Teilfunktion von f gibt es eine Zeile in der Jacobi-Matrix
for row in range(f.shape[0]):

    # Für jede Variable im Vektor x gibt es eine Spalte in der Jacobi-Matrix
    for col in range(x.shape[0]):
        rd = row + 1
        cd = col + 1

        print(f'Berechne den Eintrag der Jacobi-Matrix in Zeile {str(rd)}, Spalte {str(cd)}.')
        print(f'Berechne dazu die partielle Ableitung von f{str(rd)} nach x{str(cd)}:')
        d = sy.diff(f[row], x[col])
        print('∂f' + str(rd) + '/∂x' + str(cd) + ' = ' + str(d))
        # print('LA TEX (Zum Anschauen eingeben unter https://www.codecogs.com/latex/eqneditor.php):')
        print('\\frac{\\partial f_' + str(rd) + '}{\\partial x_' + str(cd) + '}=' + str(sy.latex(d)))

        url = "https://latex.codecogs.com/svg.image?" + encodeURL('\\frac{\\partial f_' + str(rd) + '}{\\partial x_' + str(cd) + '}=' + str(sy.latex(d)) + '\n')
        print("LATEX: " + url)

print('------------------------------------------------------------------------------------------------------------')

Df = f.jacobian(x)

print(f'Ganze Jacobi-Matrix: Df mit x = {x0} = \n{Df}')
print('Mit Pretty Print: Df = ')
sy.init_printing(use_unicode=True)
sy.pretty_print(Df)
# print('LA TEX (Zum Anschauen eingeben unter https://www.codecogs.com/latex/eqneditor.php):')
print(sy.latex(Df))
url = "https://latex.codecogs.com/svg.image?" + encodeURL(sy.latex(Df))
print("LATEX: " + url)
print()

Dfx0 = Df
for i in range(x.shape[0]):
    Dfx0 = Dfx0.subs(x[i], x0[i])

Dfx0_eval = Dfx0.evalf()

print(f'Jacobi-Matrix ausgewertet an Stelle: Df({str(x0)}) = {str(Dfx0_eval)}')
# print('LA TEX (Zum Anschauen eingeben unter https://www.codecogs.com/latex/eqneditor.php):')
print(sy.latex(Dfx0_eval))
url = "https://latex.codecogs.com/svg.image?" + encodeURL(sy.latex(Dfx0_eval))
print("LATEX: " + url)
print()
print()
exit()