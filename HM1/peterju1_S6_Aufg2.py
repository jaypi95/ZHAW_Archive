import numpy as np

def S6_Aufg2(A,b):
    A = np.copy(A)
    b = np.copy(b)

    #Shape finden
    n = np.shape(A)[0]

    for i in np.arange(0,n):
        j = i
        #überprüfen ob die stellen unterhalb 0 sind
        while j < n and A[j,i] == 0:
            j = j+1
        #vertausche zeilen
        if j != i:
            zeilen_tauschen(A,b,i,j)
        for j in np.arange(i+1, n):
            c = A[j,i]/A[i,i]
            minus(A,b,j,i,c)

    x = rueckwaerts_einsetzen(A,b)

    return A, x

def rueckwaerts_einsetzen(A,b):
    n = np.shape(A)[0]
    x = np.zeros([n,1])

    for i in np.arange(n-1,-1,-1):
        x[i] = (b[i]-A[i,i+1:n]@x[i+1:n])/A[i,i]
    return x

def minus(A,b,j,i,c):
    A[j,:] = A[j,:] - c * A[i,:]
    b[j] = b[j] - c * b[i]


def zeilen_tauschen(A,b,i,j):
    temp_line = np.copy(A[i,:])
    A[i,:] = A[j,:]
    A[j,:] = temp_line
    temp_line = np.copy(b[i])
    b[i] = b[j]
    b[j] = temp_line


A = np.array([[4, -1, -5],[-12,4,17],[32,-10,-41]])
b = np.array([[-5],[19],[-39]])

A_triangle, x = S6_Aufg2(A,b)

print("A_triangle = \n", A_triangle)
print("x = \n", x)

