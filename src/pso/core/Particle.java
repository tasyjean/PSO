package pso.core;

import java.lang.reflect.Array;

import pso.type.Calculable;

/**
 * A Particle is a moving quantum with velocity in the search space. 
 * It keeps a record of previous position it has ever passed and generated
 * the highest fitness so far.
 */
public class Particle extends Quantum {

  protected Calculable[] vel;
  protected Quantum lBest;

  public Particle() {}

  public Particle(Class<? extends Calculable> fitnessClass,
                  Class<? extends Calculable> spaceClass,
                  int dimension) throws InstantiationException, 
                                        IllegalAccessException {
    super(fitnessClass, spaceClass, dimension);
    vel = (Calculable[]) Array.newInstance(spaceCls, dimension);
    lBest = new Quantum(fitnessClass, spaceClass, dimension);
  }

  public Particle(Particle particle) {
    fitnessCls = particle.fitnessCls;
    spaceCls = particle.spaceCls;
    dimen = particle.dimen;
    pos = (Calculable[]) Array.newInstance(spaceCls, dimen);
    for (int i = 0; i < dimen; ++i)
      pos[i].copyFrom(particle.pos[i]);
    fit = particle.fit.clone();
    lBest = particle.lBest.clone();
  }
}
