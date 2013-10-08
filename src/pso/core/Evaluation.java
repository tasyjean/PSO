package pso.core;

import pso.type.Calculable;

public abstract class Evaluation {
  public abstract Calculable evaluate(Calculable[] position);
}
