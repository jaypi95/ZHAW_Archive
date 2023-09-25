import math
import sys
from collections import Counter

num_of_arguments = len(sys.argv)
list_of_files = sys.argv



def calc_HF(filepath):
    list_of_frequency = Counter(read_file(filepath))
    length_of_file = len(read_file(filepath))

    sum_of_hf = 0
    for item in list_of_frequency:
        item_frequency = list_of_frequency.get(item) / length_of_file
        sum_of_hf += item_frequency * math.log2(item_frequency)

    sum_of_hf = sum_of_hf * (-1)
    return sum_of_hf


def read_file(filepath):
    file = open(filepath, 'rb')
    bits = file.read()
    file.close()
    return bits

for file in sys.argv[1:]:
    print(f"{file}:")
    print(calc_HF(file))
    print("\n")

