package pso.type;

/** A Calculable for floats. */
public class FloatCalculable implements Calculable {

  private float value;
  
  public FloatCalculable() {}
  
  public FloatCalculable(float value) { this.value = value; }
  
  /** Set the value of this FloatCalculable. */
  public void set(float value) { this.value = value; }
  
  /** Return the value of this FloatCalculable. */
  public float get() { return value; }
  
  /** Returns true iff o is a FloatCalculable with the same value. */
  public boolean equals(Object o) {
    if (!(o instanceof FloatCalculable))
      return false;
    FloatCalculable other = (FloatCalculable) o;
    return (this.value == other.value);
  }
  
  public int hashCode() {
    return Float.floatToIntBits(value);
  }
  
  public String toString() {
    return Float.toString(value);
  }

  @Override
  public void copyFrom(Calculable c) {
    value = ((FloatCalculable) c).value;
  }
  
  @Override
  public Calculable add(Calculable c) {
    float sum = this.value + ((FloatCalculable) c).value;
    return (new FloatCalculable(sum));
  }

  @Override
  public Calculable subtract(Calculable c) {
    float diff = this.value - ((FloatCalculable) c).value;
    return (new FloatCalculable(diff));
  }

  @Override
  public Calculable multiply(Calculable c) {
    float product = this.value * ((FloatCalculable) c).value;
    return (new FloatCalculable(product));
  }

  @Override
  public int compareTo(Calculable c) {
    float thisValue = this.value;
    float thatValue = ((FloatCalculable) c).value;
    return (thisValue < thatValue ? -1 : (thisValue == thatValue ? 0 : 1));
  }

  @Override
  public Calculable clone() {
    return (new FloatCalculable(value));
  }

  @Override
  public Calculable multiplyDouble(double d) {
    return (new FloatCalculable((float) (value * d)));
  }
}
