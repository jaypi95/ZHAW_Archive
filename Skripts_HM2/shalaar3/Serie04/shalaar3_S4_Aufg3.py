import numpy as np
import matplotlib.pyplot as plt


# Teilaufgabe a + b
def plot_aufgabe(offset=0):
    x_axis = np.array(
        [1981, 1984, 1989, 1993, 1997, 2000, 2001, 2003, 2004, 2010]
        , dtype=float) - offset
    y_axis = np.array(
        [0.5, 8.2, 15, 22.9, 36.6, 51, 56.3, 61.8, 65, 76.7]
        , dtype=float)

    n = np.size(x_axis) - 1
    a = np.polyfit(x_axis, y_axis, n)

    x_plot = np.arange(1970, 2025, 0.1) - offset
    y_plot = np.polyval(a, x_plot)

    plt.plot(x_plot, y_plot, color='red')
    plt.plot(x_axis, y_axis, marker='o', markersize='5', linestyle='', color='black')

    plt.xlabel('Year')
    plt.ylabel('PC per Household')
    plt.title('Plot with Offset of ' + str(offset))
    plt.ylim([-100, 250])

    plt.grid()
    plt.show()
    y_error = np.polyval(a, x_axis) - y_axis

    print(f'y_error ({offset} Offset)', y_error)
    print("===========================")


plot_aufgabe()
plot_aufgabe(1981)


# Teilaufgabe c:
# Wenn wir den Plot ohne Offset betrachten, dann sehen wir,
# dass der Kurve beim X-Wert 2020 steiler nach oben geht, als die COVID-Kurve in Indien
