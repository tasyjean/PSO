package pso.core;

import pso.type.Calculable;

/** 
 * All evaluation functions shoulde extends class Evaluation and 
 * the evaluate() method.
 */
public abstract class Evaluation {
  public abstract Calculable evaluate(Calculable[] position);
}
