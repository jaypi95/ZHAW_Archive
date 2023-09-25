import numpy as np


def peterju1_S8_Aufg3a(x, y):
    Tf = 0
    for i in range(0, x.size - 1):
        Tf = Tf + (y[i] + y[i + 1]) / 2 * (x[i + 1] - x[i])
    return Tf


rad = np.array([0.0, 800.0, 1200.0, 1400.0, 2000.0, 3000.0, 3400.0, 3600.0, 4000.0, 5000.0, 5500.0, 6370.0], dtype="float")
den = np.array([13000.0, 12900.0, 12700.0, 12000.0, 11650.0, 10600.0, 9900.0, 5500.0, 5300.0, 4750.0, 4500.0, 3300.0], dtype="float")
rad_convert = 1000.0 * rad # convert radius from km to m because density is in kg/m^3

func = den * 4.0 * np.pi * rad_convert ** 2

Tf_neq = peterju1_S8_Aufg3a(rad_convert, func)
Tf_ref = 5.976e24

err_abs = np.abs(Tf_neq - Tf_ref)
err_rel = err_abs / Tf_ref

print('Calculated mass: ', Tf_neq)
print('Absolute Error: ', err_abs)
print('Relative Error: ', err_rel)

# Calculated mass:  6.02609577351443e+24
# Absolute Error:  5.009577351442909e+22
# Relative Error:  0.008382826893311428