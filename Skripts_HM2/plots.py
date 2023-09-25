import numpy as np
import matplotlib.pyplot as plt
from matplotlib import cm
from mpl_toolkits.mplot3d import Axes3D

"""
=======================================================================================================================
INPUT
=======================================================================================================================
"""
GRAVITATIONAL_CONSTANT = 9.81

def w(V, A, g):
    return V ** 2 * np.sin(2 * A) / g

v = np.linspace(0, 100, 100)
a = np.linspace(0, np.pi / 2, 100)

# WICHTIG!
# In der Plot-Funktion muss man die spezifische Funktion anpassen!

"""
=======================================================================================================================
"""

def plot_3D(x, y):
    # Vorbereitung
    X, Y = np.meshgrid(x, y)

    # Spezifische Funktion
    Z = w(X, Y, GRAVITATIONAL_CONSTANT)

    # 1. Plot (Gitter)
    fig = plt.figure(1)  # Erster Befehl: Figur erzeugen (Nur Rahmen)
    ax = fig.add_subplot(111, projection='3d')
    ax.set_title('Wireframe')
    ax.set_xlabel("x")
    ax.set_ylabel("y")
    ax.set_zlabel("z")
    ax.plot_wireframe(X, Y, Z)
    plt.show()

    # 2. Plot Flaeche
    fig = plt.figure(2)
    ax = fig.add_subplot(111, projection='3d')
    ax.set_title("Surface")
    ax.set_xlabel("x")
    ax.set_ylabel("y")
    ax.set_zlabel("z")
    surf = ax.plot_surface(X, Y, Z, cmap=cm.coolwarm, linewidth=0, antialiased=False)
    plt.show()

    # 3. Plot Hoehenlinien
    fig = plt.figure(3)
    ax = fig.add_subplot(111)
    cont = plt.contour(X, Y, Z)
    plt.colorbar(cont)
    ax.clabel(cont, inline=True, fontsize=10)
    ax.set_title('Hoehenlinien 2D')
    ax.set_xlabel("x")
    ax.set_ylabel("y")
    plt.show()

    # 4. Plot 3D Hoehenlinien
    fig = plt.figure(4)
    ax = fig.add_subplot(111, projection='3d')
    CS = ax.contour(X, Y, Z)
    ax.clabel(CS, inline=True, fontsize=10)
    ax.set_title('Hoehenlinien 3D')
    ax.set_xlabel("x")
    ax.set_ylabel("y")
    ax.set_zlabel("z")
    plt.show()

    # what do the numbers mean in add_subplots()??
    # https://stackoverflow.com/questions/3584805/in-matplotlib-what-does-the-argument-mean-in-fig-add-subplot111

    # (0.125,0.11;0.775x0.77) mit 1019.2396816247451
    print(f"An Koordinate {np.max(ax)} hat Funktion ein (lokales) Maximum mit dem Wert {np.max(Z)}")
    plt.show()


plot_3D(v, a)
