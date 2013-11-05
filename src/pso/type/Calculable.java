package pso.type;

/**
 * Any <code>fitness</code> or <code>space</code> type in PSO implements this 
 * interface to make it calculated and compared.
 */
public interface Calculable {
  
  /** Copy from another Calculable. */
  public abstract void copyFrom(Calculable c);
  
  /** Addition. */
  public abstract Calculable add(Calculable c);
  
  /** Subtraction. */
  public abstract Calculable subtract(Calculable c);
  
  /** Multiplication. */
  public abstract Calculable multiply(Calculable c);
  
  /** Compares two Calculable. */
  public abstract int compareTo(Calculable c);

  /** Multiply with a double */
  public abstract Calculable multiplyDouble(double d);
  
  /** Return a deep copy of this Calculable. */
  public abstract Calculable clone();
  
}
