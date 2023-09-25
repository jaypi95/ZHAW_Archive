package test.ch.zhaw.catan;

public class Tuple<X, Y> {
  public final X first;
  public final Y second;

  public Tuple(X x, Y y) {
    this.first = x;
    this.second = y;
  }

  @Override
  public String toString() {
    return "(" + first + "," + second + ")";
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }

    if (!(other instanceof Tuple)) {
      return false;
    }

    @SuppressWarnings("unchecked")
    Tuple<X, Y> otherCasted = (Tuple<X, Y>) other;

    // null is not a valid value for first and second tuple element
    return otherCasted.first.equals(this.first) && otherCasted.second.equals(this.second);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((first == null) ? 0 : first.hashCode());
    result = prime * result + ((second == null) ? 0 : second.hashCode());
    return result;
  }
}
