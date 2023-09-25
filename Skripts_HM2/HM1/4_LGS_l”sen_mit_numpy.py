import numpy as np

"""==================== INPUT ===================="""
A = np.array([[-18,  -3.6],
               [-3.6,   5.32]],
              dtype=np.float64)
b = np.array([1.42, 1.044], dtype=np.float64)
"""==============================================="""

print("x = " + str(np.linalg.solve(A, b)))
