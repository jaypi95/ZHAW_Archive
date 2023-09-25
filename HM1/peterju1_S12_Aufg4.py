import numpy as np

# Input
# Aufgabe a
A = np.array([[1, -2, 0],
              [2, 0, 1],
              [0, -2, 1]])

k = 100
# Aufgabe b
A_new = np.array([[6, 1, 2, 1, 2],
              [1, 5, 0, 2, -1],
              [2, 0, 5, -1, 0],
              [1, 2, -1, 6, 1],
              [2, -1, 0, 1, 7]])


# qr zerlegung
def qr(A, k):
    A = np.copy(A)
    P = np.eye(np.shape(A)[0], np.shape(A)[1])

    for i in range(k):
        Q, R = np.linalg.qr(A)
        A = R@Q
        P = P@Q

    return A, P


# Funktionsaufruf
Ak, Pk = qr(A, k)

print("Ak: \n")
print(Ak)
print("\n")
print("Pk: \n")
print(Pk)

new_Ak, new_Pk = qr(A_new, k)

print("New Ak: \n")
print(new_Ak)
print("\n")
print("New Pk: \n")
print(new_Pk)

#Check if P is orthogonal
if (new_Pk.T == np.linalg.inv(new_Pk)).all:
    print("\n")
    print("P is orthogonal")

#Eigenvalues are all elements on the diagonal of A. The Eigenvectors are the columns of Pk
for i in np.arange(0, np.shape(new_Ak)[0]):
    print(new_Ak[i,i], new_Pk[:,i])

#4 c)
eigVal, eigVec = np.linalg.eig(A_new)
print("\n")
print("Eigenvalues: ")
print(eigVal)
print("\n")
print("Eigenvectors: ")
print(eigVec)
print("\n")

np.linalg.eig(new_Ak)

# the values are the same but not necessarily in the same order