import numpy as np

# Input
A = np.array([[1, 1, 0],
              [3, -1, 2],
              [2, -1, 3]])

x = np.array([1, 0, 0])
x_plus1 = x
k=0
lam = np.nan

while np.linalg.norm(x_plus1 - x, 2) > 1e-4 or k == 0:
    x = x_plus1
    k = k+1
    x_plus1 = (A@x)/np.linalg.norm(A@x, 2)
    lam = (x.T@A@x)/(x.T@x)

    print(f"Iterations: {k}")
    print(f"Lambda: {lam}")
    print(f"x: {x_plus1}")

#eig comparison
eigVal, eigVec = np.linalg.eig(A)
max_value = np.argmax(eigVal)
print("\n")
print("Max Eigenvalue: ")
print(eigVal[max_value])
print("\n")
print("Corresponding Eigenvector: ")
print(eigVec[:,max_value])
print("\n")