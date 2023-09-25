import numpy as np


def Serie3_Aufg3a(x, y):
    n = np.size(x) - 1

    T = 0
    for i in range(n):
        T = T + (x[i + 1] - x[i]) * (y[i + 1] + y[i]) / 2
    return T


radius = np.array([
    0,
    800,
    1200,
    1400,
    2000,
    3000,
    3400,
    3600,
    4000,
    5000,
    5500,
    6370
], dtype=float)
rho = np.array([
    13000,
    12900,
    12700,
    12000,
    11650,
    10600,
    9900,
    5500,
    5300,
    4750,
    4500,
    3300
], dtype=float)

masse_literature = 5.972e24
radiusInMeter = radius * 1000
y = rho * 4 * np.pi * radiusInMeter ** 2

masse_calc = Serie3_Aufg3a(radius, y)

print(f"Berechnete Masse: {masse_calc}")
print(f"Absoluter Fehler: {abs(masse_calc - masse_literature)}")
print(f"Relativer Fehler: {abs(masse_calc - masse_literature) / masse_literature}")
