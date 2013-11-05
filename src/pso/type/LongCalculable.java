package pso.type;

/** A Calculable for long. */
public class LongCalculable implements Calculable {
  public static final long MIN_VALUE = Long.MIN_VALUE;
  
  private long value;
  
  public LongCalculable() {}
  
  public LongCalculable(long value) { this.value = value; }
  
  public void copyFrom(Calculable c) { value = ((LongCalculable) c).value; }
  
  /** Set the value of this LongCalculable. */
  public void set(long value) { this.value = (long) value; }
  
  /** Return the value of this LongCalculable. */
  public long get() { return value; }
  
  /** Returns true iff o is an LongCalculable with the same value. */
  public boolean equals(Object o) {
    if (!(o instanceof LongCalculable))
      return false;
    LongCalculable other = (LongCalculable) o;
    return (this.value == other.value);
  }
  
  public int hashCode() {
    return (int) value;
  }

  public String toString() {
    return Long.toString(value);
  }
  
  @Override
  public Calculable add(Calculable c) {
    long sum = this.value + ((LongCalculable) c).value;
    return (new LongCalculable(sum));
  }

  @Override
  public Calculable subtract(Calculable c) {
    this.value -= ((LongCalculable) c).value;
    return this;
  }

  @Override
  public Calculable multiply(Calculable c) {
    long product = this.value * ((LongCalculable) c).value;
    return (new LongCalculable(product));
  }

  @Override
  public int compareTo(Calculable c) {
    long thisValue = this.value;
    long thatValue = ((LongCalculable) c).value;
    return (thisValue < thatValue ? -1 : (thisValue == thatValue ? 0 : 1));
  }

  @Override
  public Calculable clone() {
    return (new LongCalculable(value));
  }

  @Override
  public Calculable multiplyDouble(double d) {
    return (new LongCalculable((long) (value * d)));
  }

}
