import numpy as np
import matplotlib.pyplot as plt

"""
----------------USER INPUT----------------
"""
punkteAnSEP = 30
vornoteQuizz = 6
"""
----------------USER INPUT END----------------
"""

"""
----------------DEFAULT VALS----------------
"""
note4Aufgerundet = 27.5
note5Aufgerundet = 37.5
noteMaximal = 50
noteGabriel = 70
defaultMarkerSize = 10
"""
----------------DEFAULT VALS END----------------
"""

def note(punkte, vornote = 6):
    return ((punkte/noteMaximal)*5+1)*0.8 + 0.2*vornote

x_vals = np.arange(0, noteGabriel, 1)
y_vals = note(x_vals)

plt.figure(1)
plt.plot(x_vals, y_vals, color='r')
plt.plot(note4Aufgerundet, note(note4Aufgerundet, vornoteQuizz), color='g', marker='o', linestyle='', markersize=defaultMarkerSize)
plt.plot(note5Aufgerundet, note(note5Aufgerundet, vornoteQuizz), color='pink', marker='o', linestyle='', markersize=defaultMarkerSize)
plt.plot(noteMaximal, note(noteMaximal, vornoteQuizz), color='orange', marker='o', linestyle='', markersize=defaultMarkerSize)
plt.plot(noteGabriel, note(noteGabriel, 6), color='black', marker='o', linestyle='', markersize=defaultMarkerSize)
plt.plot(punkteAnSEP, note(punkteAnSEP, vornoteQuizz), color='cyan', marker='o', linestyle='', markersize='7')
plt.xlabel("Punkte")
plt.ylabel("Note")
plt.title("SWEN SEP")
plt.grid(visible=True)

plt.legend([
    "Est. Notenschlüssel"
    ,"4er"
    ,"5er"
    ,"6er"
    ,"Gabriel"
    ,"Selbsteinschätzung"
])

plt.show()
exit()
