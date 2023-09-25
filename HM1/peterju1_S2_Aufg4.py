def calc_eps():
    x = 1.0

    while True:
        x = x / 2
        if 1.0 + x == 1.0:
            print(x)
            break


calc_eps()

exit(0)
