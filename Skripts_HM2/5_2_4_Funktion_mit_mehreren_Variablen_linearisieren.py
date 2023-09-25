import sympy as sy

from tools import encodeURL

x1, x2, x3, x4, x5, x6, x7, x8, x9, a, b, c, d = sy.symbols('x1, x2, x3, x4, x5, x6, x7, x8, x9, a, b, c, d')

"""
=======================================================================================================================
INPUT
=======================================================================================================================
"""

# ACHTUNG: Für sinus/cosinus/Exponentialfunktion immer sy.sin/sy.cos/sy.exp/sy.ln/sy.abs verwenden!
f = sy.Matrix([
    (2 * x1 - 3 * x2 + 4) * sy.cos(x3),
    4 * x1 - sy.sin(2 * x2 + x3) + 3
    # 0.1 * x1 ** 2 - 0.1 * x1 * x2 ** 2 + 0.05 * x2 ** 3 + 3
    # sy.ln(x2 / 4) + sy.exp((x3 / 2) - 1) - 1,
    # (x2 - 3)**2 - x3**3 + 7
])

x = sy.Matrix([x1, x2, x3])  # Wenn mehr oder weniger als 3 Variablen auftreten, diese Liste anpassen!
x0 = sy.Matrix([0, 0, sy.pi])  # Stelle, an welcher die Jacobi-Matrix ausgewertet werden soll, z.B. x0 bei Newton

"""
=======================================================================================================================
"""

print(f'Bilde die Jacobi-Matrix Df(x) für f(x) = {str(f)} mit x = {str(x)}.\n')

Df = f.jacobian(x)

print(f'Ganze Jacobi-Matrix: Df = {str(Df)}')
# print('LA TEX (Zum Anschauen eingeben unter https://www.codecogs.com/latex/eqneditor.php):')
print(sy.latex(Df))
url = "https://latex.codecogs.com/svg.image?" + encodeURL(sy.latex(Df))
print("LATEX: " + url)
print()

Dfx0 = Df
fx0 = f

# Ersetze alle xi-Variablen mit konkreten Werten
for i in range(x.shape[0]):
    Dfx0 = Dfx0.subs(x[i], x0[i])
    fx0 = fx0.subs(x[i], x0[i])

Dfx0_eval = Dfx0.evalf()
fx0_eval = fx0.evalf()

print(f'Funktion f ausgewertet an Stelle x0 = {str(x0)}: f(x0) = {str(fx0_eval)}')
print(f'Jacobi-Matrix ausgewertet an Stelle x0 = {str(x0)}: Df(x0) = {str(Dfx0_eval)}')
print(f'{str(fx0_eval[0])}+[{str(Dfx0_eval[0, 0])} {str(Dfx0_eval[0, 1])}]* X_VEKTOR')
# TODO: Replace X_Vektor with actual X Vektor (refer Theory SW2/3, Page 4)
print()

g = fx0_eval + Dfx0_eval * (x - x0)

print(f'Linearisierung g(x) = f(x0) + Df(x0) * (x - x0) = {str(g)}')
# print('LA TEX (Zum Anschauen eingeben unter https://www.codecogs.com/latex/eqneditor.php):')
print(sy.latex(g))
url = "https://latex.codecogs.com/svg.image?" + encodeURL(sy.latex(g))
print("LATEX: " + url)

print("=======================================================================================================================\n")
print("Von Hand:")

url1 = "https://latex.codecogs.com/svg.image?" + encodeURL(sy.latex(fx0_eval))
print("f(x0):", fx0_eval)
print("LATEX f(x0): " + url1)
print()

url2 = "https://latex.codecogs.com/svg.image?" + encodeURL(sy.latex(Dfx0_eval))
print("Df(x0):", str(Dfx0_eval))
print("LATEX Df(x0): " + url2)

print()
url3 = "https://latex.codecogs.com/svg.image?" + encodeURL(sy.latex((x - x0)))
print("(x - x0):", (x - x0))
print("LATEX (x - x0): " + url3)

print()
url4 = "https://latex.codecogs.com/svg.image?" + encodeURL(sy.latex(Df @ (x - x0)))
print("Df @ (x - x0):", Df @ (x - x0))
print("LATEX Df @ (x - x0): " + url4)

print()
url5 = "https://latex.codecogs.com/svg.image?" + encodeURL(sy.latex(g))
print(f'Linearisierung g(x) = f(x0) + Df(x0) @ (x - x0)')
print("g(x):", g)
print("LATEX g: " + url5)
print()
print()

exit()