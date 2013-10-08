package pso.core;

import java.lang.reflect.Array;

import pso.type.Calculable;
import pso.type.FloatCalculable;

/**
 * A Quantum is a static point in the search space. It has position
 * and the fitness of this position.
 */
public class Quantum {

  protected Class<? extends Calculable> fitnessCls;
  protected Class<? extends Calculable> spaceCls;
  
  protected int dimen;
  protected Calculable[] pos;
  protected Calculable fit;

  public Quantum() {}

  /**
   * Construct a Quantum.
   * @param fitnessClass Fitness class
   * @param spaceClass Space class
   * @param dimension Dimension of the space
   * @throws InstantiationException
   * @throws IllegalAccessException
   */
  public Quantum(Class<? extends Calculable> fitnessClass,
                 Class<? extends Calculable> spaceClass,
                 int dimension) throws InstantiationException, 
                                       IllegalAccessException {
    fitnessCls = fitnessClass;
    spaceCls = spaceClass;
    dimen = dimension;
    pos = (Calculable[]) Array.newInstance(spaceCls, dimen);
    for (int i = 0; i < dimen; ++i)
      pos[i] = spaceCls.newInstance();
    fit = fitnessCls.newInstance();
  }

  /**
   * Construct a Quantum with another Quantum.
   * @param quantum
   */
  public Quantum(Quantum quantum) {
    fitnessCls = quantum.fitnessCls;
    spaceCls = quantum.spaceCls;
    dimen = quantum.dimen;
    pos = (Calculable[]) Array.newInstance(spaceCls, dimen);
    for (int i = 0; i < dimen; ++i)
      pos[i] = quantum.pos[i].clone();
    fit = quantum.fit.clone();
  }

  /** Set the position of this Quantum. */
  public void setPosition(Calculable[] position) {
    pos = position;
  }

  /** Get the position for a given dimension. */
  public Calculable getPosition(int dimen) {
    return pos[dimen];
  }
  
  public Calculable[] getPosition() {
    return pos;
  }

  public void setFitness(Calculable fitness) {
    fit = fitness;
  }

  public Calculable getFitness() {
    return fit;
  }
  
  public void copyPosition(Calculable[] position) {
    for (int i = 0; i < dimen; ++i)
      pos[i].copyFrom(position[i]);
  }

  public void copyFitness(Calculable fitness) {
    fit.copyFrom(fitness);
  }
  
  public void copyFrom(Quantum quantum) {
    copyPosition(quantum.pos);
    copyFitness(quantum.fit);
  }
  
  public Quantum clone() {
    Quantum quantum = null;
    try {
      quantum = new Quantum(fitnessCls, spaceCls, pos.length);
      for (int i = 0; i < dimen; ++i)
        quantum.pos[i].copyFrom(pos[i]);
      quantum.fit.copyFrom(fit);
    } catch (IllegalAccessException | IllegalArgumentException
        | SecurityException | InstantiationException e) {
      e.printStackTrace();
    }
    return quantum;
  }
  
  public static void main (String[] args) 
      throws InstantiationException, IllegalAccessException {
    Quantum q = new Quantum(FloatCalculable.class, FloatCalculable.class, 2);
    
    System.out.println(q.pos[0].getClass());
  }
}
