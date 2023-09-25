import numpy as np
import sympy as sy

x1, x2, x3, x4, x5, x6, x7, x8, x9, y0, y1, y2, y3, y4, y5, y6, y7, y8, y9, z1, z2, z3, z4, z5, z6, z7, z8, z9 = sy.symbols(
    'x1 ,x2 ,x3 ,x4 ,x5 ,x6 ,x7 ,x8 ,x9 ,y0 ,y1 ,y2 ,y3 ,y4 ,y5 ,y6 ,y7 ,y8 ,y9 ,z1 ,z2 ,z3 ,z4 ,z5 ,z6 ,z7 ,z8 ,z9')

"""
=======================================================================================================================
INPUT
=======================================================================================================================
"""

# Funktion nach höchster Ableitung auflösen vor dem Einsetzen und Ableitungen mit z ersetzten im Schema: y^(i) = z_(i+1)
# f = (2 * x1 * z3 - (z2 ** 2)) / z1
# f = (-5 * y1 ** 2 - 0.1 * x1 - 570000) / x2
#f = 10 * sy.exp(-x1) - 5 * y2 - 8 * y1 - 6 * y0
f = (-5 * y1**2 - 0.1 * x1 - 570000) / 97000

# Startwerte
z0 = np.array([
    [2],
    [0],
    [0]], dtype=np.float64)

"""
=======================================================================================================================
"""
hoechsteAbleitung = z0.shape[0]

firstSetOfZs = []
secondSetOfZs = []

fsubs = f.subs({y0: z1, y1: z2, y2: z3, y3: z4, y4: z5, y5: z6, y6: z7, y7: z8, y8: z9})

for i in range(hoechsteAbleitung):
    derivationMarks = '\'' * i
    firstSetOfZs.append(f"z_{i + 1}(x) = y{derivationMarks}(x)")
    print(firstSetOfZs[i])
print("=======")
for i in range(1, hoechsteAbleitung + 1):
    derivationMarks = '\'' * i
    secondSetOfZs.append(f"z_{i}'(x) = y{derivationMarks}(x)")
    print(secondSetOfZs[i - 1])
print("=======")

for i in range(hoechsteAbleitung):
    if i + 1 != hoechsteAbleitung:
        print(f"{secondSetOfZs[i].split('=')[0]} = {firstSetOfZs[i + 1].split('=')[0]}")

print(f"{secondSetOfZs[i].split('=')[0]} = {fsubs}")
print("=======")

z_Abl = np.array([[secondSetOfZs[i].split('(x) =')[0]] for i in range(hoechsteAbleitung)], dtype=object)
print(f"z' = {z_Abl}")
z = np.array([
    [firstSetOfZs[i + 1].split('(x) =')[0] for i in range(hoechsteAbleitung - 1)],
    [fsubs]], dtype=object)
print(f"= {z}")
print(f"z0 = {z0}")

exit()
