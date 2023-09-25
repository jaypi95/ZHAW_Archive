import numpy as np
import matplotlib.pyplot as plt
'''INPUT'''
x_in = np.array([0, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110], dtype=np.float64)
y_in = np.array([76, 92, 106, 123, 137, 151, 179, 203, 227, 250, 281, 309], dtype=np.float64)
grad = 2
'''INPUT'''

np.set_printoptions(suppress=True)
coeff2 = np.polyfit(x_in, y_in, grad) # polynom 4. Grades. da 6 Datenpunkte -2
print('2. grades: x^1 bis x^n: ', coeff2)

coeff3 = np.polyfit(x_in, y_in, 3)
print('3. grades: x^1 bis x^n: ', coeff3)


plt.figure(1)
plt.grid()
plt.plot(x_in, np.polyval(coeff2, x_in), '--',zorder=0, label='p2')
plt.plot(x_in, np.polyval(coeff3, x_in), ':', zorder=0, label='p1')
plt.scatter(x_in, y_in, marker='x', color='r', zorder=1, label='measured')
plt.legend()
plt.show()

print(y_in-np.polyval(coeff2, x_in))
print(y_in-np.polyval(coeff3, x_in))