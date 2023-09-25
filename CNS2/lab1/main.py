import math

import numpy as np
from matplotlib import pyplot as plt

C = range(1, 1001)
TSymbol = 0.001
TSample = 10 ** -6
k = range(0, 1000)

F1 = 3000
F2 = 6000
A1 = 0.9
A2 = 1.1
Phase1 = 95  # degrees
Phase2 = 12  # degrees
phi1 = math.radians(Phase1)
phi2 = math.radians(Phase2)


def FSubcarrier(C):
    return C * 1000  # 1kHz


# Subcarrier signals
def XN(t, A, f, phi):
    return A * math.cos(2 * math.pi * f * t + phi)


# Transmitted Signals
def S(t, a1, a2, f1, f2, phi1, phi2):
    return XN(t, a1, f1, phi1) + XN(t, a2, f2, phi2)


# Demodulation
## In-Phase component
def I(a1, a2, f1, f2, phi1, phi2, subcarrier):
    sum = 0
    for i in k:
        sum += 2 * S(i * TSample, a1, a2, f1, f2, phi1, phi2) * math.cos(2 * math.pi * subcarrier * (i * TSample))

    return sum / 1000


## quadrature component
def Q(a1, a2, f1, f2, phi1, phi2, subcarrier):
    sum = 0
    for i in k:
        sum += 2 * S(i * TSample, a1, a2, f1, f2, phi1, phi2) * math.sin(-2 * math.pi * subcarrier * (i * TSample))

    return sum / 1000


# Reconstructed Signal
## Reconstructed Amplitude
def AN(a1, a2, f1, f2, phi1, phi2, subcarrier):
    return math.sqrt(I(a1, a2, f1, f2, phi1, phi2, subcarrier) ** 2 + Q(a1, a2, f1, f2, phi1, phi2, subcarrier) ** 2)


## Reconstructed Phase
def PN(a1, a2, f1, f2, phi1, phi2, subcarrier):
    return math.degrees(
        math.atan(Q(a1, a2, f1, f2, phi1, phi2, subcarrier) / I(a1, a2, f1, f2, phi1, phi2, subcarrier)))


# plot the result of XN
for i in k:
    y1 = XN(i * TSample, A1, F1, phi1)
    y2 = XN(i * TSample, A2, F2, phi2)
    plt.plot(i, y1, linewidth=0.2, color='blue', marker='.')
    plt.plot(i, y2, linewidth=0.2, color='red', marker='.')
plt.ylim(-1.5, 1.5)
plt.title("QAM Signals")
plt.show()

# plot the result of S
for i in k:
    y = S(i * TSample, A1, A2, F1, F2, phi1, phi2)
    plt.plot(i, y, linewidth=0.5, color='blue', marker='.')

plt.ylim(-2.5, 1.5)
plt.title("Transmitted Signal")
plt.show()

# print the result of I

y1 = I(A1, A2, F1, F2, phi1, phi2, F1)
y2 = I(A1, A2, F1, F2, phi1, phi2, F2)
print(f"I1 = {y1}, I2 = {y2}")

# print the result of Q
y1 = Q(A1, A2, F1, F2, phi1, phi2, F1)
y2 = Q(A1, A2, F1, F2, phi1, phi2, F2)
print(f"Q1 = {y1}, Q2 = {y2}")

# print the result of AN
y1 = AN(A1, A2, F1, F2, phi1, phi2, F1)
y2 = AN(A1, A2, F1, F2, phi1, phi2, F2)
print(f"AN1 = {y1}, AN2 = {y2}")

# print the result of PN
y1 = PN(A1, A2, F1, F2, phi1, phi2, F1)
y2 = PN(A1, A2, F1, F2, phi1, phi2, F2)
print(f"PN1 = {y1}, PN2 = {y2}")


# Plot the cosine of both frequencies
def cos(F, i):
    return math.cos(2 * math.pi * F * i * TSample)


for i in C:
    y1 = cos(F1, i)
    y2 = cos(F2, i)
    plt.plot(i, y1, linewidth=0.2, color='blue', marker='.')
    plt.plot(i, y2, linewidth=0.2, color='red', marker='.')

plt.title("Carrier Frequencies")
plt.show()


# Product cancels if integrated

for i in C:
    y1 = cos(F1, i)
    y2 = cos(F2, i)
    plt.plot(i, y1 * y2, linewidth=0.2, color='blue', marker='.')

plt.ylim(-1.7, 1.7)

plt.text(0.5, -1.5, "I am unable to make it fill the area below the curve", fontsize=12, color='red')
plt.title("Product cancels if integrated")
plt.show()



# plot the result of the demodulated signal
for i in k:
    y1 = S(i * TSample, A1, A2, F1, F2, phi1, phi2) * math.cos(2 * math.pi * F1 * i * TSample)
    y2 = -S(i * TSample, A1, A2, F1, F2, phi1, phi2) * math.sin(-2 * math.pi * F1 * i * TSample)
    plt.plot(i, y1, linewidth=0.5, color='blue', marker='.')
    plt.plot(i, y2, linewidth=0.5, color='red', marker='.')

plt.ylim(-2.5, 1.5)
plt.title("Demod1(t) (I,Q)")
plt.show()