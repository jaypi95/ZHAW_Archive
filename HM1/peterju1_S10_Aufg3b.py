from time import perf_counter

import numpy as np

import peterju1_S10_Aufg3a
import peterju1_S6_Aufg2

dim = 3000
A = np.diag(np.diag(np.ones((dim, dim)) * 4000)) + np.ones((dim, dim))
dum1 = np.arange(1, int(dim / 2 + 1), dtype=np.float64).reshape((int(dim / 2), 1))
dum2 = np.arange(int(dim / 2), 0, -1, dtype=np.float64).reshape((int(dim / 2), 1))
x = np.append(dum1, dum2, axis=0)
b = A @ x
x0 = np.zeros((dim, 1))
tol = 1e-4

# Time the Gauss implementation from series 6
t1_start = perf_counter()
A, x = peterju1_S6_Aufg2.S6_Aufg2(A, b)
t1_stop = perf_counter()

print("Serie 6 A: ")
print(A)
print("Serie 6 x: ")
print(x)
print("Elapsed time: ")
print(t1_stop - t1_start)

# Time the Jacobi implementation
t2_start = perf_counter()
xn, n, n2 = peterju1_S10_Aufg3a.jacobi_gauss_seidel(A, b, x0, tol, "jacobi")
t2_stop = perf_counter()

print("Jacobi Iteration, Iterationvector: ")
print(xn)
print("After n iterations")
print(n)
print("a_priori approximation: ")
print(n2)
print("Elapsed time: ")
print(t2_stop - t2_start)

# Time the Gauss Seidel implementation
t3_start = perf_counter()
xn, n, n2 = peterju1_S10_Aufg3a.jacobi_gauss_seidel(A, b, x0, tol, "seidel")
t3_stop = perf_counter()

print("Gauss Seidel Iteration, Iterationvector: ")
print(xn)
print("After n iterations")
print(n)
print("a_priori approximation: ")
print(n2)
print("Elapsed time: ")
print(t3_stop - t3_start)

print("\n")
print("Comparison: ")
print("Gauss from Series 6: ")
print(t1_stop - t1_start)
print("Jacobi: ")
print(t2_stop - t2_start)
print("Gauss Seidel: ")
print(t3_stop - t3_start)

# Recorded on my machine:
# Gauss from Series 6: 36.39455910000106
# Jacobi: 18.324931012999514
# Gauss Seidel: 11.248789418001252

