package pso.type;

/** A Calculable for doubles. */
public class DoubleCalculable implements Calculable {

  private double value = 0.0;
  
  public DoubleCalculable() {}
  
  public DoubleCalculable(double value) { this.value = value; }
  
  /** Set the value of this DoubleCalculable. */
  public void set(double value) { this.value = value; }
  
  /** Return the value of this DoubleCalculable. */
  public double get() { return value; }
  
  /** Returns true iff o is a DoubleCalculable with the same value. */
  public boolean equals(Object o) {
    if (!(o instanceof DoubleCalculable))
      return false;
    DoubleCalculable other = (DoubleCalculable) o;
    return (this.value == other.value);
  }
  
  public int hashCode() {
    return (int) Double.doubleToLongBits(value);
  }
  
  public String toString() {
    return Double.toString(value);
  }

  @Override
  public void copyFrom(Calculable c) {
    value = ((DoubleCalculable) c).value;
  }
  
  @Override
  public Calculable add(Calculable c) {
    double sum = this.value + ((DoubleCalculable) c).value;
    return (new DoubleCalculable(sum));
  }

  @Override
  public Calculable subtract(Calculable c) {
    double diff = this.value - ((DoubleCalculable) c).value;
    return (new DoubleCalculable(diff));
  }

  @Override
  public Calculable multiply(Calculable c) {
    double product = this.value * ((DoubleCalculable) c).value;
    return (new DoubleCalculable(product));
  }

  @Override
  public int compareTo(Calculable c) {
    double thisValue = this.value;
    double thatValue = ((DoubleCalculable) c).value;
    return (thisValue < thatValue ? -1 : (thisValue == thatValue ? 0 : 1));
  }

  @Override
  public Calculable clone() {
    return (new DoubleCalculable(value));
  }

  @Override
  public Calculable multiplyDouble(double d) {
    return (new DoubleCalculable((float) (value * d)));
  }
}
