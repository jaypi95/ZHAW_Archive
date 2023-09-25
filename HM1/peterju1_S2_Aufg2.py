import numpy as np
import matplotlib.pyplot as plt

 # Teilaufgabe a

x = np.arange(1.99, 2.01, 0.0003992015968063872)

y_f1 = x ** 7 - 14 * x ** 6 + 84 * x ** 5 - 280 * x ** 4 + 560 * x ** 3 - 672 * x ** 2 + 448 * x - 128
y_f2 = (x - 2) ** 7

plt.xlim(1.989, 2.011)
plt.ylim(10**-12, -10**-12)
plt.xlabel("Intervall")
plt.ylabel("Y-Werte")
plt.grid()
plt.title("2a")

f1, = plt.plot(x, y_f1)
f2, = plt.plot(x, y_f2)

f1.set_label('f1(x)')
f2.set_label('f2(x)')
plt.legend()

plt.plot()
plt.show()

# F1 führt zu Auslöschung weswegen das Ergebnis ungenau wird.

# Teilaufgabe b

x = np.arange(-10**-14, 10**-14, 10**-17)

g = x / (np.sin(1 + x) - np.sin(1))

plt.xlim(-10**-14, 10**-14)
plt.title("2b")
plt.grid()

g_plot, = plt.plot(x, g)

g_plot.set_label('g(x)')


plt.show()

# Nein es ist nicht stabil. Die Funktion ist an dieser Stelle Lückenhaft.

# Teilaufgabe C

plt.ylim(80.7 * 1**-13, 81 * 1 ** -13)
trig = x / (2 * np.cos(1 + x/2) * np.sin(x/2))
plt.title("2c")
trig_plot, = plt.plot(x, trig)
plt.grid()

g_plot.set_label('trig')
plt.show()

# bei dieser Variante gibt es keine Auslöschung und darum gibt es keine Lücke im Graphen.

exit(0)
