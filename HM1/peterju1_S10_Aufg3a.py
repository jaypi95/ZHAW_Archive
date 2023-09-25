import numpy as np


def jacobi_gauss_seidel(A, b, x0, tol, opt):
    D = np.diag(np.diag(A))
    L = np.tril(A, -1)
    R = np.triu(A, 1)

    B = -np.linalg.inv(D) @ (L + R)

    if opt == "jacobi":
        x = jacobi_iteration(A, b, x0, tol)
    else:
        x = gauss_seidel_iteration(A, b, x0, tol)

    n2 = a_priori(B, x0, x[1], tol)

    return x[-1], len(x) - 1, n2


def jacobi_iteration(A, b, x0, tol):
    err = 0
    x = [x0]

    while len(x) < 2 or err > tol:
        x_new = np.zeros(b.shape[0])
        for i in range(b.shape[0]):
            sum = 0
            for j in range(b.shape[0]):
                if i == j:
                    continue
                sum += A[i, j] * x[-1][j]

            x_new[i] = (1 / A[i, i]) * (b[i] - sum)

        x.append(x_new)

    return x


def gauss_seidel_iteration(A, b, x0, tol):
    err = 0
    x = [x0]

    while len(x) < 2 or err > tol:
        x_new = np.zeros(b.shape[0])
        for i in range(b.shape[0]):
            sum = 0
            for j in range(b.shape[0]):
                if i == j:
                    continue
                xj = x_new[j] if j < i else x[-1][j]
                sum += A[i, j] * xj

            x_new[i] = (1 / A[i, i]) * (b[i] - sum)

        x.append(x_new)

    return x


def a_priori(B, x0, x1, tol):
    b_norm = np.linalg.norm(B, np.inf)
    minimum = np.log((-(b_norm - 1) * tol) / (np.linalg.norm(x1 - x0, np.inf))) / np.log(b_norm)
    return minimum
