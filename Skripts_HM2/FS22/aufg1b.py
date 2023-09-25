import numpy as np
import matplotlib.pyplot as plt

"""
=======================================================================================================================
INPUT
=======================================================================================================================
"""


def f(x, y):
    return x ** 2 - y



# region Data for general values
showClassicRK = 0  # 0 = no, 1 = yes
showGenericRK = 0  # 0 = no, 1 = yes
showCustomRK = 1  # 0 = no, 1 = yes

# Data for Runge-Kutta-Verfahren
a = 0
b = 2
y0 = 1
x0 = 0
h = 0.1
# h = ((b - a) * 1.0) / n
# endregion


# region Data for custom Runge-Kutta-Verfahren (get data from Aufgabenstellung)
aCustomRK = np.array([
    [0, 0],
    [2/3, 0]
], dtype=np.float64)
bCustomRK = np.array([1/4, 3/4], dtype=np.float64)
cCustomRK = np.array([0, 2/3], dtype=np.float64)
stepsCustomRK: int = 2
coeffCustomRK: float = 1
# endregion


"""
=======================================================================================================================
"""



if showClassicRK == 0 and showGenericRK == 0 and showCustomRK == 0:
    raise Exception('No Runge-Kutta-Verfahren selected!')


def generate_Butcher_Tableau(coeffA: np.array, coeffB: np.array, coeffC: np.array, coeffS: int):
    butcherMatrix = coeffA.copy()
    butcherMatrix = np.c_[np.zeros(coeffS), butcherMatrix]
    butcherMatrix = np.r_[butcherMatrix, np.zeros((1, np.shape(butcherMatrix)[1]))]

    butcherMatrix[-1, :] = np.insert(coeffB, 0, 0, axis=0)

    butcherMatrix[:, 0] = np.append(coeffC, 0)

    print(butcherMatrix, flush=False)
    return butcherMatrix



def interpolate_runge_kutta_custom(f, x, h, y0, x0):
    y = np.full(x.shape[0], 0, dtype=np.float64)
    xData = np.full(x.shape[0], 0, dtype=np.float64)
    xData[0] = x0
    y[0] = y0

    print(f'Custom Runge-Kutta-Verfahren:')
    for i in range(xData.shape[0] - 1):
        k1 = f(xData[i] + cCustomRK[0] * h, y[i])
        k2 = f(xData[i] + cCustomRK[1] * h, y[i] + h * (aCustomRK[1, 0] * k1))
        xData[i + 1] = xData[i] + h
        y[i + 1] = y[i] + h * coeffCustomRK * (
                    bCustomRK[0] * k1 + bCustomRK[1] * k2)

        print(f'k1 = f(x{[i]} + {cCustomRK[0]}*{h}, y{[i]}) = f({xData[i] + cCustomRK[0] * h}, {y[i]}) = {k1}')
        print(
            f'k2 = f(x{[i]} + {cCustomRK[1]}*{h}, y{[i]} + {h} * {aCustomRK[1][0]} * {k1}) = f({xData[i] + cCustomRK[1] * h}, {y[i] + h * aCustomRK[1][0] * k1}) = {k2}')
        print('')
        print(f'x[{i + 1}] = x[{i}] + h = {xData[i]} + {h} = {xData[i + 1]}')
        print('=====Custom=================================================')

    print(f'Butcher-Tableau für custom Runge-Kutta-Verfahren:')
    generate_Butcher_Tableau(aCustomRK, bCustomRK, cCustomRK, stepsCustomRK)
    return xData, y


x_axis = np.arange(a, b + h, step=h, dtype=np.longdouble)

calculatedDataX = []
calculatedDataY = []
titleData = []


if showCustomRK == 1:
    x_custom, y_custom = interpolate_runge_kutta_custom(f, x_axis, h, y0, x0)
    calculatedDataX.append(x_custom)
    calculatedDataY.append(y_custom)
    titleData.append('Custom RK')

lineStyles = ['-', '--', '-.', ':']

plt.figure(0)
plt.title("Verfahren")
for i in range(len(calculatedDataX)):
    plt.plot(calculatedDataX[i], calculatedDataY[i], linestyle=lineStyles[i % len(lineStyles)], label=titleData[i])






"""
=======================================================================================================================
"""


def plot_slope_field(f, xmin, xmax, ymin, ymax, hx, hy):
    x = np.arange(xmin, xmax, step=hx, dtype=np.float64)
    y = np.arange(ymin, ymax, step=hy, dtype=np.float64)
    [x_grid, y_grid] = np.meshgrid(x, y)

    dy = f(x_grid, y_grid)
    dx = np.full((dy.shape[0], dy.shape[1]), 1, dtype=np.float64)

    plt.figure(0)

    plt.quiver(x_grid, y_grid, dx, dy, label='Slope Field')


plot_slope_field(f, 0, 2, 0, 2, 0.25, 0.25)  # f, xmin, xmax, ymin, ymax, hx, hy (Schrittweiten)
plt.xlim(0,2)
plt.ylim(0,2)
plt.grid()
plt.show()
exit()
