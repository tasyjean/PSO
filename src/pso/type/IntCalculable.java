package pso.type;

/** A Calculable for integers. */
public class IntCalculable implements Calculable {
  public static final int MIN_VALUE = Integer.MIN_VALUE;
  
  private int value;
  
  public IntCalculable() {}
  
  public IntCalculable(int value) { set(value); }
  
  public void copyFrom(Calculable c) { value = ((IntCalculable) c).value; }
  
  /** Set the value of this IntCalculable. */
  public void set(int value) { this.value = (int) value; }
  
  /** Return the value of this IntCalculable. */
  public int get() { return value; }
  
  /** Returns true iff o is an IntCalculable with the same value. */
  public boolean equals(Object o) {
    if (!(o instanceof IntCalculable))
      return false;
    IntCalculable other = (IntCalculable)o;
    return (this.value == other.value);
  }
  
  public int hashCode() {
    return value;
  }

  public String toString() {
    return Integer.toString(value);
  }
  
  @Override
  public Calculable add(Calculable c) {
    int sum = this.value + ((IntCalculable)c).value;
    return (new IntCalculable(sum));
  }

  @Override
  public Calculable subtract(Calculable c) {
    this.value -= ((IntCalculable)c).value;
    return this;
  }

  @Override
  public Calculable multiply(Calculable c) {
    int product = this.value * ((IntCalculable)c).value;
    return (new IntCalculable(product));
  }

  @Override
  public int compareTo(Calculable c) {
    int thisValue = this.value;
    int thatValue = ((IntCalculable)c).value;
    return (thisValue < thatValue ? -1 : (thisValue == thatValue ? 0 : 1));
  }

  @Override
  public Calculable clone() {
    return (new IntCalculable(value));
  }

  @Override
  public Calculable multiplyDouble(double d) {
    return (new IntCalculable((int) (value * d)));
  }

}
