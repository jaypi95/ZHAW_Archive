import numpy as np
import matplotlib.pyplot as plt

"""
=======================================================================================================================
INPUT
=======================================================================================================================
"""

x_offset_Enabled = False

x_in = np.array([1981, 1984, 1989, 1993, 1997, 2000, 2001, 2003, 2004, 2010])
y_in = np.array([0.5, 8.2, 15, 22.9, 36.6, 51, 56.3, 61.8, 65, 76.7], dtype=np.float64)

# Erster Plot
x_b_min = 1975
x_b_max = 2030 # * extrapolated value
x_b_arange_value = 0.1

# Zweiter Plot
x_c_min = 1981
x_c_max = 2010.1 # + 0.1, damit Kurve bis zum Wert geht
x_c_arange_value = 0.1

x_c_ylim_b = 250
x_c_ylim_a = -100
"""
=======================================================================================================================
"""
if x_offset_Enabled:
    # offsetValue = 1850
    offsetValue = x_in[0]
    x_b_min = x_b_min - offsetValue
    x_b_max = x_b_max - offsetValue  # * extrapolated value
    x_c_min = x_c_min - offsetValue
    x_c_max = x_c_max - offsetValue  # + 0.1, damit Kurve bis zum Wert geht
    x_in = np.array([item - offsetValue for item in x_in])
coeff = np.polyfit(x_in, y_in, x_in.shape[0] - 1)

x_b = np.arange(x_b_min, x_b_max, x_b_arange_value)
x_c = np.arange(x_c_min, x_c_max, x_c_arange_value)
print(f"{x_c_min=}")
print(f"{x_c_max=}")
print(f"{x_c_arange_value=}")
print(f"{x_c=}")
print(f"{coeff=}")

plt.figure(1)
plt.grid()
plt.title(f'Interpolation (Polyfit) (Mit X Offset: {x_offset_Enabled})')
plt.plot(x_b, np.polyval(coeff, x_b), zorder=0, linewidth=3,  label='polyfit')
plt.scatter(x_in, y_in, marker='x', color='r', zorder=9, label='measured')
plt.scatter(x_b_max, np.polyval(coeff, x_b_max), marker='o', color='fuchsia', zorder=1, label='extrapolated', lw=3)
plt.legend()
plt.show()

""" 
b) Was ist der Schätzwert für 2020, basierend auf Ihrem Resultat aus a)? Ist das realistisch, und können solche
   Polynome hoher Ordnung für Schätzwerte ausserhalb des Intervalls der vorhandenen Datenwerte benutzt
   werden? 

A: Der interpolierte Wert beträgt ca. 125, was bedeuten würde, dass 125% aller Haushalte einen PC besitzen
   Dies ist offensichtlich keine sinnvolle Angabe. Diese Methode ist wohl nur für die Interpolation, aber nicht
   für die Extrapolation von fehlenden Werten sinnvoll.

"""


def lagrange_int(x, y, x_int):
    return [np.sum([y[i] * np.prod([(x_int_i - x[j] * 1.0) / (x[i] - x[j]) for j in range(x.shape[0]) if j != i]) for i in range(x.shape[0])]) for x_int_i in x_int]


plt.figure(2)
plt.grid()
plt.title(f'Lagrange-Polynom (Mit X Offset: {x_offset_Enabled})')
plt.plot(x_c, lagrange_int(x_in, y_in, x_c), zorder=0, linewidth=4, label="".join([chr(item) for item in [108,97,103,114,97,110,103,101,115,32,100,105,99,107]]))
plt.plot(x_c, np.polyval(coeff, x_c), zorder=1, label='polyfit', linestyle='--')
plt.scatter(x_in, y_in, marker='x', color='r', zorder=2, label='measured')
plt.ylim([x_c_ylim_a, x_c_ylim_b])
plt.legend()
plt.show()

"""
c) Was stellen Sie im Vergleich der beiden Methoden fest? 

A: Das Lagrange-Polynom oszilliert im Bereich der äusseren Messwerte stark und bietet dort auch bei der Interpolation
   keine guten Näherungswerte.
"""
exit()
