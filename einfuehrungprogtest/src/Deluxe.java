class Deluxe extends Product
        implements One, Two {
    int profit = 0;

    Deluxe(int c, int p) {
        super(c);
        profit = 10 + p;
    }

    public int value(int factor) {
        return 11 + factor * profit;
    }
}
