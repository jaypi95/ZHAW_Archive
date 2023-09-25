import numpy as np
import sympy as sp
import matplotlib.pyplot as plt

# aufgabe a
G, N, c = sp.symbols('G N c')

f = G / ((G - N) / N * sp.exp(-0 * c) + 1)

print('N(O) = ', sp.simplify(f))

f = G / ((G - N) / N * sp.exp(-sp.oo) + 1)  # exp(-inf) geht gegen Null, darum kann c ignoriert werden
print('limt->oo(N(t)) = ', sp.simplify(f))

#
#
# aufgabe b
x = np.array([0, 14, 28, 42, 56])
y = np.array([29, 2072, 15798, 25854, 28997])
plt.grid()
plt.scatter(x, y, marker='x', color='r', zorder=1, label='measured')
plt.legend()
plt.show()

# N0 = N(0) aus a) => plot reinzoomen bis N0 = 29
# G => 30_000, ablesen bzw sehr grosszügen schätzen und Graph weiterziehen


#
#
# aufgabe c
# zweiter Punkt(14,2072): 2072 = 29 *exp(c*14)
# auflösen nach c
# 2072              = 29 * exp(c*14)    // 29
# 2072/29           = exp(c*14)         // ln()
# ln(2072/29)       = c * 14            // :14
# ln(2072/29)/14    = c
# 0.3049266         = c


