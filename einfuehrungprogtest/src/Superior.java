class Superior extends Product
        implements One {
    int profit = 0;

    Superior(int c) {
        super(c);
    }

    Superior(int c, int p) {
        super(c);
        profit = p;
    }

    public int value(int factor) {
        return factor * profit;
    }
}
