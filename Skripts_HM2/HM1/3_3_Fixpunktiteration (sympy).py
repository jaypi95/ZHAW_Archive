import numpy as np
import matplotlib.pyplot as plt

def F(x):
    return (x-0.3)**(1/3)

iterationen = 100

x = 0.7

for i in range(iterationen):
    x = F(x)

print(x)



a = np.linespace(0.7,0.8)
b = F(a)
plt.plot(a,a,a,b)
plt.legend(['y=x', 'y=F(x)'])
plt.show()

