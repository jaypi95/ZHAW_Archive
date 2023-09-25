import math

import numpy as np
import numpy.linalg

def calc_system_of_equations(A, A_tilde, b, b_tilde):

    # Calculate x for the correct system of equations
    Q, R = numpy.linalg.qr(A)
    x = np.linalg.solve(R, np.transpose(Q)@b)

    # Calculate x-tilde for the disturbed(?) system of equations
    Q_tilde, R_tilde = numpy.linalg.qr(A_tilde)

    x_tilde = np.linalg.solve(R_tilde, np.transpose(Q_tilde)@b_tilde)

    return x, x_tilde

def dx_max(A, A_tilde, b, b_tilde):

    cond_A = numpy.linalg.cond(A, np.inf)

    A_norm = numpy.linalg.norm(A, np.inf)
    A_minus_A_tilde_norm = numpy.linalg.norm(A - A_tilde, np.inf)

    b_norm = numpy.linalg.norm(b, np.inf)
    b_minus_b_tilde_norm = numpy.linalg.norm(b - b_tilde, np.inf)

    norm_A_fraction = A_minus_A_tilde_norm / A_norm
    norm_b_fraction = b_minus_b_tilde_norm / b_norm

    error_condition = cond_A * norm_A_fraction

    if error_condition < 1:
        return (cond_A / (1 - cond_A * norm_A_fraction)) * (norm_A_fraction + norm_b_fraction)
    else:
        return numpy.nan


def dx_obs(x, x_tilde):

    x_norm = numpy.linalg.norm(x, np.inf)
    x_minus_x_tilde_norm = numpy.linalg.norm(x - x_tilde, np.inf)

    return x_minus_x_tilde_norm / x_norm


def peterju1_S9_Aufg2(A, A_tilde, b, b_tilde):
    x, x_tilde = calc_system_of_equations(A, A_tilde, b, b_tilde)

    max_rel_error = dx_max(A, A_tilde, b, b_tilde)

    real_rel_error = dx_obs(x, x_tilde)

    return x, x_tilde, max_rel_error, real_rel_error

A = np.array([[20, 30, 10],
              [10, 17, 6],
              [2, 3, 2]])
b = np.array([[5720],
              [3300],
              [836]])

A_tilde = A-0.1

b_tilde = b+100

x, x_tilde, max_dx, obs_dx = peterju1_S9_Aufg2(A, A_tilde, b, b_tilde)

print("x: ")
print(x)
print("x-tilde: ")
print(x_tilde)
print("Max relative error: ")
print(max_dx)
print("Real relative error: ")
print(obs_dx)