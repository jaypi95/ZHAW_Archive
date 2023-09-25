# ---------------------------------------------------------------------------
# HM 2 SW 13 LOESESN DES DGL-SYSTEMS ZUM GEDAEMPFTEN HARMONISCHEN OSZILLATOR
#            MIT MODIFIZIERTEM EULERVERFAHREN / adel
# ---------------------------------------------------------------------------

import numpy as np
import matplotlib.pyplot as plt

# Vorbemerkung

# VEKTOREN werden ZEILENWEISE als EINDIMENSIONALE Arrays implementiert.

# Groessen

# Zeit t in s
# Ort s in m
# Geschwindigkeit v in m/s
# Zustandsvektor z = [s, v] 

# Parameter

m = 1. # Masse in kg
k = 4. # Federkomstante in N/m
r = 2. # Reibungskoeffizient in N*s/m

# Richtungsfeld

def f(t, z):
    return np.array([z[1], -k/m*z[0] - r/m*z[1]])                                   

# Anfangswerte

t0 = 0
z0 = np.array([1., -1.])

# Exakte Loesung

def zexakt(t):
    return np.array([np.exp(-t)* np.cos(3**0.5*t),
                    -np.exp(-t)*(np.cos(3**0.5*t) + 3**0.5*np.sin(3**0.5*t))])

# Endwert und Schrittweite

tn = 4.
h = 0.4

# Implementation Modifiziertes Eulerverfahren

def euler_modifiziert(f, t0, z0, tn, h):
    # Loest DGL-System z' = f(t, z) zum AW z(t0) = z0 mit Modifiziertem
    # Eulerverfahren auf Intervall t0 <= t <= tn mit Schrittwqeite h
    # Gibt t-Werte t0, .., tn als Vektor zurueck
    # Gibt z-Vektoren z0, ..., zn als Zeilen einer (n+1) x 2 - Matrix zurueck
    n = np.int((tn - t0)/h)
    t = np.zeros(n+1)
    z = np.zeros([n+1, 2])
    z[0, :] = z0
    for i in range(n):
        k1 = f(t[i], z[i,:])
        k2 = f(t[i] + h, z[i,:] + h*k1)
        k = (k1 + k2)/2.
        t[i+1] = t[i] + h
        z[i+1,:] = z[i,:] + h*k
    return t, z

# Durchfuehrung Modifiziertes Eulerverfahren

t, z = euler_modifiziert(f, t0, z0, tn, h)
print('t =\n', t)
print('z =\n', z)

# t =
#  [0.  0.4 0.8 1.2 1.6 2.  2.4 2.8 3.2 3.6 4. ]
# z =
#  [[ 1.         -1.        ]
#   [ 0.44       -1.16      ]
#   [ 0.0208     -0.6544    ]
#   [-0.142912   -0.150848  ]
#   [-0.13338368  0.10702592]
#   [-0.06501468  0.14945352]
#   [-0.00834114  0.0923048 ]
#   [ 0.01648118  0.02646845]
#   [ 0.01755963 -0.01052824]
#   [ 0.00941377 -0.01896289]
#   [ 0.00185027 -0.0128298 ]]

# Plot

plt.plot(t, z[:,0], '.', color='red', markersize=8, label='s(t) numerisch')
plt.plot(t, z[:,1], '.', color='blue', markersize=8, label='v(t) numerisch')

tplot = np.arange(t0, tn, 0.01)
zplot = zexakt(tplot)
plt.plot(tplot, zplot[0], color='red', label='s(t) exakt')
plt.plot(tplot, zplot[1], color='blue', label='v(t) exakt')

plt.xlabel('Zeit t in s')
plt.ylabel('Ort s in m bzw. Geschwindigkeit v in m/s')
plt.grid()
plt.legend()



