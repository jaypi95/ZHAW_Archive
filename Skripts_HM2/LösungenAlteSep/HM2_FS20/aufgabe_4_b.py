import sympy as sy
import numpy as np

x, a, b, c, d = sy.symbols('x a b c d')
''' INPUT '''
s0 = a + 2 * x + c * x ** 2 + 0.5 * x ** 3
s1 = 6 + b*(x - 2) + 2*((x - 2) ** 2) + d*((x - 2) ** 3)

''' INPUT '''

ds0 = sy.diff(s0, x)
dds0 = sy.diff(ds0, x)
ddds0 = sy.diff(dds0, x)

ds1 = sy.diff(s1, x)
dds1 = sy.diff(ds1, x)
ddds1 = sy.diff(dds1, x)

print('S0()\':' + str(ds0))
print('S0()\'\':' + str(dds0))
print('S0()\'\'\':' + str(ddds0))

print('S1()\':' + str(ds1))
print('S1()\'\':' + str(dds1))
print('S1()\'\'\':' + str(ddds1))


ds0 = ds0.subs({c:-1, d: 0.5, a:2, x:2})
ds1 = ds1.subs({c:-1, d: 0.5, a:2, x:2})
print(str(ds1-ds0), '= 0')

'''
LGS auflösen.
1. S0(2)\'\'\' = S1(2)\'\'\'   => 3 = 6d <> 0.5 = d
2. S0(x)\'\'\' = S1(x)\'\'\'   x wählen (2) => 2c + 6 = 4 <> c = -1
3. S0(2) = S1(2)     => a+4 = 6  <> a = 2
3. S0(2)' = S1(2)'     => b = 4
'''