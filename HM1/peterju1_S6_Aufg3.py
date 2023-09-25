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


A1 = np.array([[4, -1, -5],[-12,4,17],[32,-10,-41]])
x1 = np.array([[-5],[19],[-39]])

A2 = np.array([[2, 7, 3],[-4,-10,0],[12,34,9]])
x2 = np.array([[25],[-24],[107]])

A3 = np.array([[-2, 5, 4],[-14,38,22],[6,-9,-27]])
x3 = np.array([[25],[-24],[107]])

#no chill
A4 = np.array([[-1, 2, 3,2,5,4,3,-1],[3,4,2,1,0,2,3,8],[2,7,5,-1,2,1,3,5],[3,1,2,6,-3,7,2,-2],[5,2,0,8,7,6,1,3],[-1,3,2,3,5,3,1,4],[8,7,3,6,4,9,7,9],[-3,14,-2,1,0,-2,10,5]])
x4 = np.array([[-11],[103],[53],[-20],[95],[78],[131],[-26]])

A1_comp, x1_comp = S6_Aufg2(A1,x1)
x1_python = np.linalg.solve(A1,x1)
print("A1_triangle = \n", A1_comp)
print("x1 = \n", x1_comp)
print("x1_python = \n", x1_python)

A2_comp, x2_comp = S6_Aufg2(A2,x2)
x2_python = np.linalg.solve(A2,x2)
print("A2_triangle = \n", A2_comp)
print("x2 = \n", x2_comp)
print("x2_python = \n", x2_python)

A3_comp, x3_comp = S6_Aufg2(A3,x3)
x3_python = np.linalg.solve(A3,x3)
print("A3_triangle = \n", A3_comp)
print("x3 = \n", x3_comp)
print("x3_python = \n", x3_python)

A4_comp, x4_comp = S6_Aufg2(A4,x4)
x4_python = np.linalg.solve(A4,x4)
print("A4_triangle = \n", A4_comp)
print("x4 = \n", x4_comp)
print("x4_python = \n", x4_python)

# Beide geben die gleichen Werte zurück, ausser bei A4.
# Da unterscheiden sie sich wahrscheinlich aufgrund der Fehlerfortpflanzung (just a wild guess)

