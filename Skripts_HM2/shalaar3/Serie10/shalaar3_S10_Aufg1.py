import numpy as np

from Serie09.shalaar3_S9_Aufg3 import shalaar3_S9_Aufg3 as a3

m = 97000.

f = lambda v: m / (-5 * v ** 2 - 570000)
f2 = lambda v: f(v) * v

t_end = a3(f, 100, 0, 4)
print(f"t_end = {t_end}")

x_end = a3(f2, 100, 0, 4)
print(f"x_end = {x_end}")

exit()
