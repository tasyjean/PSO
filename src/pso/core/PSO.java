package pso.core;

import java.lang.reflect.Array;
import java.util.Random;

import pso.type.Calculable;
import pso.type.FloatCalculable;

/**
 * Particle swarm optimization
 */
public class PSO {

  private static final double C1_B = 2.5f;
  private static final double C1_E = 0.5f;
  private static final double C2_B = 0.5f;
  private static final double C2_E = 2.5f;
  private static final double W_B = 0.9f;
  private static final double W_E = 0.4f;

  private Class<? extends Calculable> fitnessCls;
  private Class<? extends Calculable> spaceCls;
  
  private int dimen;
  private int swarmSize;
  private int maxItNum;

  private Particle[] swarm;
  private Quantum gBest;
  private Calculable[][] bounds;

  private Random random = new Random();
  private double c1 = C1_B;
  private double c2 = C2_B;
  private double w = W_B;

  private PSO(Class<? extends Calculable> fitnessClass,
              Class<? extends Calculable> spaceClass,
              int dimension, int swarmSize, int maxItNum) 
                 throws InstantiationException, IllegalAccessException {
    fitnessCls = fitnessClass;
    spaceCls = spaceClass;
    
    this.dimen = dimension;
    this.swarmSize = swarmSize;
    this.maxItNum = maxItNum;

    swarm = (Particle[]) Array.newInstance(Particle.class, swarmSize);
    for (int i = 0; i < swarmSize; ++i)
      swarm[i] = new Particle(fitnessCls, spaceCls, dimension);
    gBest = new Quantum(fitnessCls, spaceCls, dimension);
  }

  public PSO(Class<? extends Calculable> fitnessClass,
             Class<? extends Calculable> spaceClass, 
             Calculable lowBound, Calculable upBound, 
             int dimension, int swarmSize, int maxItNum) 
                 throws InstantiationException, IllegalAccessException {
    this(fitnessClass, spaceClass, dimension, swarmSize, maxItNum);
    bounds = new Calculable[dimension][2];
    for (int i = 0; i < dimension; ++i) {
      bounds[i][0] = lowBound;
      bounds[i][1] = upBound;
    }
  }
  
  public PSO(Class<? extends Calculable> fitnessClass,
             Class<? extends Calculable> spaceClass, 
             Calculable[][] bounds, int dimension, int swarmSize, int maxItNum) 
                 throws InstantiationException, IllegalAccessException {
    this(fitnessClass, spaceClass, dimension, swarmSize, maxItNum);
    this.bounds = bounds;
  }
  
  /** Constructor of PSO with default FloatCalculable fitness and space. */
  public PSO(float lowBound, float upBound, int dimension, int swarmSize, 
      int maxItNum) throws InstantiationException, IllegalAccessException {
    this(FloatCalculable.class, FloatCalculable.class, 
        new FloatCalculable(lowBound), new FloatCalculable(upBound),
        dimension, swarmSize, maxItNum);
  }
  
  /** Constructor of PSO with default FloatCalculable fitness and space. */
  public PSO(float[][] bounds, int dimension, int swarmSize, int maxItNum)  
      throws InstantiationException, IllegalAccessException {
    this(FloatCalculable.class, FloatCalculable.class, dimension, swarmSize, 
        maxItNum);
    
    Calculable[][] calcBounds = new FloatCalculable[dimension][2];
    for (int i = 0; i < dimension; ++i) {
      calcBounds[i][0] = new FloatCalculable(bounds[i][0]);
      calcBounds[i][1] = new FloatCalculable(bounds[i][1]);
    }
    this.bounds = calcBounds;
  }
  
  /** Initialization of swarm */
  private void initialize(Evaluation eval) {
    int gbest = 0;
    for (int i = 0; i < swarmSize; ++i) {
      for (int j = 0; j < dimen; ++j) {
        Calculable width = bounds[j][1].subtract(bounds[j][0]);
        swarm[i].pos[j] = 
            width.multiplyDouble(random.nextDouble()).add(bounds[j][0]);
        
        Calculable minV = bounds[j][0].subtract(swarm[i].getPosition()[j]);
        Calculable maxV = bounds[j][1].subtract(swarm[i].getPosition()[j]);

        double r = random.nextDouble();
        swarm[i].vel[j] = maxV.subtract(minV).multiplyDouble(r).add(minV);
      }
      swarm[i].fit = eval.evaluate(swarm[i].pos);
      swarm[i].lBest.copyPosition(swarm[i].pos);
      swarm[i].lBest.copyFitness(swarm[i].fit);

      if (swarm[i].fit.compareTo(swarm[gbest].fit) > 0)
        gbest = i;
    }

    gBest.copyFrom(swarm[gbest]);
  }

  /** Update PSO coefficients with iteration. */
  private void updateParams(int iteration) {
    c1 = (C1_E - C1_B) * iteration / maxItNum + C1_B;
    c2 = (C2_E - C2_B) * iteration / maxItNum + C2_B;
    w = (W_E - W_B) * iteration / maxItNum + W_B;
  }

  /** Update swarm with iteration. */
  private void update(Evaluation eval, int iteration) {
    updateParams(iteration);

    int gbest = -1;
    for (int i = 0; i < swarmSize; ++i) {
      for (int j = 0; j < dimen; ++j) {
        double r1 = random.nextDouble();
        double r2 = random.nextDouble();
        
        Calculable v = swarm[i].vel[j].multiplyDouble(w);
        Calculable s = swarm[i].lBest.pos[j].subtract(swarm[i].pos[j]);
        Calculable l = gBest.pos[j].subtract(swarm[i].pos[j]);
        swarm[i].vel[j] = 
            v.add(s.multiplyDouble(c1 * r1)).add(l.multiplyDouble(c2 * r2));
        
        Calculable oldPj = swarm[i].pos[j].clone();
        swarm[i].pos[j] = swarm[i].pos[j].add(swarm[i].vel[j]);
        
        if (swarm[i].pos[j].compareTo(bounds[j][0]) < 0 
            || swarm[i].pos[j].compareTo(bounds[j][1]) > 0) {
          double r = random.nextDouble();
          swarm[i].vel[j] = (swarm[i].pos[j].compareTo(bounds[j][0]) < 0
              ? (bounds[j][0].subtract(oldPj)).multiplyDouble(r) 
              : (bounds[j][1].subtract(oldPj)).multiplyDouble(r));
          swarm[i].pos[j] = oldPj.add(swarm[i].vel[j]);
        }
      }
      
      swarm[i].fit = eval.evaluate(swarm[i].pos);
      
      if (swarm[i].fit.compareTo(swarm[i].lBest.fit) > 0) {
        swarm[i].lBest.copyFitness(swarm[i].fit);
        swarm[i].lBest.copyPosition(swarm[i].pos);
        if (swarm[i].fit.compareTo(gBest.fit) > 0) {
          gBest.copyFitness(swarm[i].fit);
          gbest = i;
        }
      }
    }
    
    if (gbest >= 0)
      gBest.copyFrom(swarm[gbest]);
  }
  
  /** Optimization with given evaluation function */
  public Quantum optimize(Evaluation eval) {
    initialize(eval);
    for (int i = 0; i < maxItNum; ++i)
      update(eval, i);
    return gBest;
  }
}
