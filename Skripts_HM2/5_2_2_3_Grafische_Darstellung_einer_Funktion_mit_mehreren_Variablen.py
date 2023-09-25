import numpy as np
import matplotlib.pyplot as plt
from matplotlib import cm


"""
=======================================================================================================================
INPUT
=======================================================================================================================
"""

# Funktionsdefinition:
def f(x, y):
    return np.sin(x) * np.cos(x * 0.5 * y) + x + 0.5 * y - 0.2 * x * y


# Wertebereich definieren:
xmin = -3
xmax = 3
ymin = -3
ymax = 3

# Werte für Schnittkurve definieren:
schnittkurveX = -0.6
schnittkurveY = 1

"""
=======================================================================================================================
"""

def plot5223(n, x, y, z):
    # Wireframe
    fig = plt.figure(n)
    ax = fig.add_subplot(111, projection='3d')
    ax.plot_wireframe(x, y, z)
    plt.title('Wireframe' + str(n))
    ax.set_xlabel('x')
    ax.set_ylabel('y')
    ax.set_zlabel('z')
    plt.grid()
    plt.show()

    # Colormap
    fig = plt.figure(n + 1)
    ax = fig.add_subplot(111, projection='3d')
    surf = ax.plot_surface(x, y, z, cmap=cm.coolwarm)
    fig.colorbar(surf)
    plt.title('Colormap' + str(n))
    ax.set_xlabel('x')
    ax.set_ylabel('y')
    ax.set_zlabel('z')
    plt.grid()
    plt.show()

    # Höhenlinien
    fig = plt.figure(n + 2)
    cont = plt.contour(x, y, z)
    fig.colorbar(cont)
    plt.title('Höhenlinien' + str(n))
    plt.xlabel('x')
    plt.ylabel('y')
    plt.grid()
    plt.show()


[x, y] = np.meshgrid(np.linspace(xmin, xmax), np.linspace(ymin, ymax))
z = f(x, y)

plot5223(1, x, y, z)

print("===========================")
print('f({}, {}) = {}'.format(schnittkurveX, schnittkurveY, f(schnittkurveX, schnittkurveY)))
print("===========================")

x_p = np.arange(xmin, xmax, 0.001)
y_p = np.arange(ymin, ymax, 0.001)

# TODO: test the shit out of the following code:
plt.figure(99)
plt.title('Schnittkurve: x = x_0 = ' + str(schnittkurveX))
plt.plot(x_p, f(schnittkurveX, y_p))
plt.scatter(schnittkurveY, f(schnittkurveX, schnittkurveY), c='r')
plt.legend(['Line', str(schnittkurveY) + ' | ' + str(f(schnittkurveX, schnittkurveY))])
plt.xlabel("y axis")
plt.ylabel("z axis")
plt.grid()
plt.show()

plt.figure(100)
plt.title('Schnittkurve: y = y_0 = ' + str(schnittkurveY))
plt.plot(y_p, f(x_p, schnittkurveY))
plt.scatter(schnittkurveX, f(schnittkurveX, schnittkurveY), c='r')
plt.legend(['Line', str(schnittkurveX) + ' | ' + str(f(schnittkurveX, schnittkurveY))])
plt.xlabel("x axis")
plt.ylabel("z axis")
plt.grid()
plt.show()

exit()
