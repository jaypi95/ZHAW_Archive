import numpy as np
import matplotlib.pyplot as plt

from Serie08.shalaar3_S8_Aufg3 import Serie3_Aufg3a as a3


def doStuff(x, y):
    inArray = np.zeros(x.size)
    for i in range(x.size):
        inArray[i] = a3(x[0:i], y[0:i])
    return inArray


def plotStuff(id, data, ylabel, legend):
    plt.figure(id)
    for dat in data:
        plt.plot(dat[0], dat[1])
    plt.xlabel('Zeit t')
    plt.ylabel(ylabel)
    plt.grid()
    plt.legend(legend)
    plt.title(ylabel)
    plt.show()


v_rel = 2600.
m_A = 300000.
m_E = 80000.
t_E = 190
g = 9.81
mue = (m_A - m_E) / t_E

t = np.arange(0, t_E)
a = v_rel * mue / (m_A - mue * t) - g

v_num = doStuff(t, a)
h_num = doStuff(t, v_num)

v_exact = v_rel * np.log(m_A / (m_A - mue * t)) - g * t
h_exact = -v_rel * (m_A - mue * t) / mue * np.log(m_A / (m_A - mue * t)) + v_rel * t - 0.5 * g * t ** 2

plotStuff(1, [[t, a]], 'Beschleunigung', [])
plotStuff(1, [[t, v_num], [t, v_exact]], 'Geschwindigkeit', ['num', 'exact'])
plotStuff(1, [[t, h_num], [t, h_exact]], 'Höhe', ['num', 'exact'])

exit()
