import sympy as sy

x, y = sy.symbols('x y')

valX = 1
valY = 2

#f = sy.ln(x + y ** 2) - sy.exp(2 * x * y) + 3 * x
f = x**2 * y**2 + x + 2*y

# f = x**3 + x * y + sy.sin(y)  # ACHTUNG: Für sinus/cosinus/Exponentialfunktion immer sy.sin/sy.cos/sy.exp verwenden!
print("Ausgangslage===================================================")
print('Werte für x = ' + str(valX) + ' und y = ' + str(valY) + ':')
print('f = ' + str(f))
print('f(x = ' + str(valX) + ', y = ' + str(valY) + ') = ' + str(f.subs({x: valX, y: valY})))

# 1. Partielle Ableitung nach x
dfx1 = sy.diff(f, x)
print("1. Partielle Ableitung nach x ==================================")
print('dfx1 = ' + str(dfx1))
print('dfx1(x = ' + str(valX) + ', y = ' + str(valY) + ') = ' + str(dfx1.subs({x: valX, y: valY})))

# 1. Partielle Ableitung nach y
print("1. Partielle Ableitung nach y ==================================")
dfy1 = sy.diff(f, y)
print('dfy1 = ' + str(dfy1))
print('dfy1(x = ' + str(valX) + ', y = ' + str(valY) + ') = ' + str(dfy1.subs({x: valX, y: valY})))

# 2. Partielle Ableitung wird eigentlich nicht gebraucht
# 2. Partielle Ableitung nach x
"""print("2. Partielle Ableitung nach x ==================================")
dfx2 = sy.diff(f, x, x)
print('dfx2 = ' + str(dfx2))
print('dfx2(x = ' + str(valX) + ', y = ' + str(valY) + ') = ' + str(dfx2.subs({x: valX, y: valY})))

# 2. Partielle Ableitung nach y
print("2. Partielle Ableitung nach y ==================================")
dfy2 = sy.diff(f, y, y)
print('dfy2 = ' + str(dfy2))
print('dfy2(x = ' + str(valX) + ', y = ' + str(valY) + ') = ' + str(dfy2.subs({x: valX, y: valY})))

# Partielle Ableitung nach x und y
print("Partielle Ableitung nach x und y ==================================")
dfxy = sy.diff(f, x, y)
print('dfxy = ' + str(dfxy))
print('dfxy(x = ' + str(valX) + ', y = ' + str(valY) + ') = ' + str(dfxy.subs({x: valX, y: valY})))
"""
