import numpy as np
import matplotlib.pyplot as plt

"""
=======================================================================================================================
INPUT
=======================================================================================================================
"""


def f(x, y):
    return 1. - y / x


def y_exact(x):
    return (x / 2.0) + 9.0 / (2.0 * x)


# region Data for general values
showClassicRK = 0  # 0 = no, 1 = yes
showGenericRK = 0  # 0 = no, 1 = yes
showCustomRK = 1  # 0 = no, 1 = yes

# Data for Runge-Kutta-Verfahren
a = 1
b = 6
y0 = 5
x0 = 1
h = 0.01
# h = ((b - a) * 1.0) / n
# endregion

# region Data for generic Runge-Kutta-Verfahren (Generisches Verfahren)
aGenericRK = np.array([
    [0, 0, 0, 0],
    [0.5, 0, 0, 0],
    [0.0, 0.5, 0, 0],
    [0.0, 0.0, 1, 0]
], dtype=np.float64)
bGenericRK = np.array([1 / 6, 1 / 3, 1 / 3, 1 / 6], dtype=np.float64)
cGenericRK = np.array([0, 0.5, 0.5, 1], dtype=np.float64)
stepsGenericRK: int = 4
coeffGenericRK: float = 1
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

# region Data for classic Runge-Kutta-Verfahren
aClassicRK = np.array([
    [0, 0, 0, 0],
    [0.5, 0, 0, 0],
    [0.0, 0.5, 0, 0],
    [0.0, 0.0, 1, 0]
], dtype=np.float64)
bClassicRK = np.array([1, 2, 2, 1], dtype=np.float64)
cClassicRK = np.array([0, 0.5, 0.5, 1], dtype=np.float64)
stepsClassicRK: int = 4
coeffClassicRK: float = 1 / 6
# endregion

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


def interpolate_runge_kutta_classic(f: callable, x: np.ndarray, h: float, y0: float, x0: float):
    y = np.full(x.shape[0], 0, dtype=np.float64)
    xData = np.full(x.shape[0], 0, dtype=np.float64)
    y[0] = y0
    xData[0] = x0
    print(f'Klassisches Runge-Kutta-Verfahren:')

    for i in range(xData.shape[0] - 1):
        k1 = f(xData[i] + cClassicRK[0] * h, y[i])
        k2 = f(xData[i] + cClassicRK[1] * h, y[i] + h * (aClassicRK[1, 0] * k1))
        k3 = f(xData[i] + cClassicRK[2] * h, y[i] + h * (aClassicRK[2, 0] * k1 + aClassicRK[2, 1] * k2))
        k4 = f(xData[i] + cClassicRK[3] * h,
               y[i] + h * (aClassicRK[3, 0] * k1 + aClassicRK[3, 1] * k2 + aClassicRK[3, 2] * k3))
        xData[i + 1] = xData[i] + h
        y[i + 1] = y[i] + h * coeffClassicRK * (
                    bClassicRK[0] * k1 + bClassicRK[1] * k2 + bClassicRK[2] * k3 + bClassicRK[3] * k4)

        print(f'k1 = f(x{[i]} + {cClassicRK[0]}*{h}, y{[i]}) = f({xData[i] + cClassicRK[0] * h}, {y[i]}) = {k1}')
        print(
            f'k2 = f(x{[i]} + {cClassicRK[1]}*{h}, y{[i]} + {h} * {aClassicRK[1][0]} * {k1}) = f({xData[i] + cClassicRK[1] * h}, {y[i] + h * aClassicRK[1][0] * k1}) = {k2}')
        print(
            f'k3 = f(x{[i]} + {cClassicRK[2]}*{h}, y{[i]} + {h} * ({aClassicRK[2][0]} * {k1} + {aClassicRK[2][1]} * {k2}) = f({xData[i] + cClassicRK[2] * h}, {y[i] + h * (aClassicRK[2][0] * k1 + aClassicRK[2][1] * k2)}) = {k3}')
        print(
            f'k4 = f(x{[i]} + {cClassicRK[3]}*{h}, y{[i]} + {h} * ({aClassicRK[3][0]} * {k1} + {aClassicRK[3][1]} * {k2} + {aClassicRK[3][2]} * {k3}) = f({xData[i] + cClassicRK[3] * h}, {y[i] + h * (aClassicRK[3][0] * k1 + aClassicRK[3][1] * k2 + aClassicRK[3][2] * k3)}) = {k4}')
        print('')
        print(f'x[{i + 1}] = x[{i}] + h = {xData[i]} + {h} = {xData[i + 1]}')
        print(
            f'y[{i + 1}] = y[{i}] + h*{coeffClassicRK}*({bClassicRK[0]}*k1 + {bClassicRK[1]}*k2 + {bClassicRK[2]}*k3 + {bClassicRK[3]}k4)) = {y[i]} + {h}*{coeffClassicRK}*({k1} + {bClassicRK[1]}*{k2} + {bClassicRK[2]}*{k3} + {bClassicRK[3]}*{k4}) = {y[i + 1]}')
        print('=======================================================')

    print(f'Butcher-Tableau für Klassisches Runge-Kutta-Verfahren:')
    generate_Butcher_Tableau(aClassicRK, bClassicRK, cClassicRK, stepsClassicRK)
    return xData, y


def interpolate_runge_kutta_generic(f, x, h, y0, x0):
    y = np.full(x.shape[0], 0, dtype=np.float64)
    xData = np.full(x.shape[0], 0, dtype=np.float64)
    y[0] = y0
    xData[0] = x0

    print(f'Generic Runge-Kutta-Verfahren:')

    for i in range(xData.shape[0] - 1):
        k = np.full(stepsGenericRK, 0, dtype=np.float64)
        xData[i + 1] = xData[i] + h

        for n in range(stepsGenericRK):
            k[n] = f(xData[i] + (cGenericRK[n] * h), y[i] + h * np.sum([aGenericRK[n][m] * k[m] for m in range(n - 1)]))
            print(f'k{n} = f(x{[i]} + (c{n} * h), y{[i]} + h * ({n}-1)_∑_m=1(a_(n*m) * k_m))\n\t= {k[n]}')

        y[i + 1] = y[i] + h * coeffGenericRK * np.sum([bGenericRK[n] * k[n] for n in range(stepsGenericRK)])
        print('')

        print(f'x[{i + 1}] = x[{i}] + h = {xData[i]} + {h} = {xData[i + 1]}')
        print(f'y[{i + 1}] = y[{i}] + h * ({n}-1)_∑_n=1(b{n} * k{n})\n\t= {y[i + 1]}')

        print('-----Generic-------------------------------------------------')

    print(f'Butcher-Tableau für generic Verfahren:')
    generate_Butcher_Tableau(aGenericRK, bGenericRK, cGenericRK, stepsGenericRK)

    return xData, y


def interpolate_runge_kutta_custom(f, x, h, y0, x0):
    y = np.full(x.shape[0], 0, dtype=np.float64)
    xData = np.full(x.shape[0], 0, dtype=np.float64)
    xData[0] = x0
    y[0] = y0

    print(f'Custom Runge-Kutta-Verfahren:')
    for i in range(xData.shape[0] - 1):
        k1 = f(xData[i] + cCustomRK[0] * h, y[i])
        k2 = f(xData[i] + cCustomRK[1] * h, y[i] + h * (aCustomRK[1, 0] * k1))
        k3 = f(xData[i] + cCustomRK[2] * h, y[i] + h * (aCustomRK[2, 0] * k1 + aCustomRK[2, 1] * k2))
        k4 = f(xData[i] + cCustomRK[3] * h,
               y[i] + h * (aCustomRK[3, 0] * k1 + aCustomRK[3, 1] * k2 + aCustomRK[3, 2] * k3))
        xData[i + 1] = xData[i] + h
        y[i + 1] = y[i] + h * coeffCustomRK * (
                    bCustomRK[0] * k1 + bCustomRK[1] * k2 + bCustomRK[2] * k3 + bCustomRK[3] * k4)

        print(f'k1 = f(x{[i]} + {cCustomRK[0]}*{h}, y{[i]}) = f({xData[i] + cCustomRK[0] * h}, {y[i]}) = {k1}')
        print(
            f'k2 = f(x{[i]} + {cCustomRK[1]}*{h}, y{[i]} + {h} * {aCustomRK[1][0]} * {k1}) = f({xData[i] + cCustomRK[1] * h}, {y[i] + h * aCustomRK[1][0] * k1}) = {k2}')
        print(
            f'k3 = f(x{[i]} + {cCustomRK[2]}*{h}, y{[i]} + {h} * ({aCustomRK[2][0]} * {k1} + {aCustomRK[2][1]} * {k2}) = f({xData[i] + cCustomRK[2] * h}, {y[i] + h * (aCustomRK[2][0] * k1 + aCustomRK[2][1] * k2)}) = {k3}')
        print(
            f'k4 = f(x{[i]} + {cCustomRK[3]}*{h}, y{[i]} + {h} * ({aCustomRK[3][0]} * {k1} + {aCustomRK[3][1]} * {k2} + {aCustomRK[3][2]} * {k3}) = f({xData[i] + cCustomRK[3] * h}, {y[i] + h * (aCustomRK[3][0] * k1 + aCustomRK[3][1] * k2 + aCustomRK[3][2] * k3)}) = {k4}')
        print('')
        print(f'x[{i + 1}] = x[{i}] + h = {xData[i]} + {h} = {xData[i + 1]}')
        print(
            f'y[{i + 1}] = y[{i}] + h*{coeffCustomRK}*({bCustomRK[0]}*k1 + {bCustomRK[1]}*k2 + {bCustomRK[2]}*k3 + {bCustomRK[3]}k4)) = {y[i]} + {h}*{coeffCustomRK}*({k1} + {bCustomRK[1]}*{k2} + {bCustomRK[2]}*{k3} + {bCustomRK[3]}*{k4}) = {y[i + 1]}')
        print('=====Custom=================================================')

    print(f'Butcher-Tableau für custom Runge-Kutta-Verfahren:')
    generate_Butcher_Tableau(aCustomRK, bCustomRK, cCustomRK, stepsCustomRK)
    return xData, y


x_axis = np.arange(a, b + h, step=h, dtype=np.longdouble)

calculatedDataX = []
calculatedDataY = []
titleData = []

if showClassicRK == 1:
    x_classic, y_classic = interpolate_runge_kutta_classic(f, x_axis, h, y0, x0)
    calculatedDataX.append(x_classic)
    calculatedDataY.append(y_classic)
    titleData.append('Klassisches RK')

if showGenericRK == 1:
    x_generic, y_generic = interpolate_runge_kutta_generic(f, x_axis, h, y0, x0)
    calculatedDataX.append(x_generic)
    calculatedDataY.append(y_generic)
    titleData.append('Generisch RK')

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
plt.plot(x_axis, y_exact(x_axis), linestyle='-', zorder=-10, linewidth=3.0, label='Exact')
plt.legend()
plt.grid()
plt.show()

plt.figure(1)
plt.title('Absolute error to exact solution')
# TODO BAACHSIL X IST FALSCH SOMEHOW MUSS DAS VERBESSERT WERDEN, DAMIT RICHTIGE WERTE GEPLOTTET WERDEN
for i in range(len(calculatedDataX)):
    plt.semilogy(calculatedDataX[i], np.abs(calculatedDataY[i] - y_exact(calculatedDataX[i])), linestyle=lineStyles[i],
                 label=titleData[i])
plt.legend()
plt.grid()
plt.show()

# print('x = ' + str(x))
# print('y_c = ' + str(y_c))
# print('y_g = ' + str(y_g))
# print('y_u = ' + str(y_u))

# fds = scipy.integrate.RK45(f, a, b, y0, h)

exit()
