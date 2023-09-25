import numpy as np
import matplotlib.pyplot as plt
from matplotlib import cm
from mpl_toolkits.mplot3d import Axes3D

GRAVITATIONAL_CONSTANT = 9.81

def w(V, A, g):
    return V ** 2 * np.sin(2 * A) / g


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

    # 2. Plot (Flaeche)
    fig = plt.figure(2)
    ax = fig.add_subplot(111, projection='3d')
    ax.set_title("Surface")
    ax.set_xlabel("x")
    ax.set_ylabel("y")
    ax.set_zlabel("z")
    surf = ax.plot_surface(X, Y, Z, cmap=cm.coolwarm, linewidth=0, antialiased=False)

    # 3. Plot (Hoehenlinien)
    fig = plt.figure(3)
    ax = fig.add_subplot(111)
    cont = plt.contour(X, Y, Z)
    plt.colorbar(cont)
    ax.clabel(cont, inline=True, fontsize=10)
    ax.set_title('Hoehenlinien 2D')
    ax.set_xlabel("x")
    ax.set_ylabel("y")

    # 4. Plot (Hoehenlinien 3D)
    fig = plt.figure(4)
    ax = fig.add_subplot(111, projection='3d')
    CS = ax.contour(X, Y, Z)
    ax.clabel(CS, inline=True, fontsize=10)
    ax.set_title('Hoehenlinien 3D')
    ax.set_xlabel("x")
    ax.set_ylabel("y")
    ax.set_zlabel("z")

    # what do the numbers mean in add_subplots()??
    # https://stackoverflow.com/questions/3584805/in-matplotlib-what-does-the-argument-mean-in-fig-add-subplot111

    # (0.125,0.11;0.775x0.77) mit 1019.2396816247451
    print(f"An Koordinate {np.max(ax)} hat Funktion ein (lokales) Maximum mit dem Wert {np.max(Z)}")
    plt.show()


v = np.linspace(0, 100, 100)
a = np.linspace(0, np.pi / 2, 100)

plot_3D(v, a)

#
# def wireframe():
#     print("tbd")
#
#
# def surface():
#     print("tbd")
#
#
# def contour_lines():
#     print("tbd")
#
#
# def howTo():
#     # 1. Plot (Gitter)
#     fig = plt.figure(1)  # Erster Befehl: Figur erzeugen (Nur Rahmen)
#     ax = Axes3D(fig)  # Inhalt erzeugen
#     x = np.linspace(-2, 2, 15)
#     y = np.linspace(-2, 2, 15)
#     X, Y = np.meshgrid(x, y)
#     Z = X ** 2 + Y
#     ax.plot_wireframe(X, Y, Z)
#     ax.set_xlabel("x")
#     ax.set_ylabel("y")
#     ax.set_zlabel("z")
#
#     # Hoehenlinien
#     fig = plt.figure(2)
#     hoehen = np.linspace(-2, 2, 9)
#     cont = plt.contour(X, Y, Z, hoehen)
#     plt.colorbar(cont)
#
#     plt.show()
