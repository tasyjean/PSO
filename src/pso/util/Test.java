package pso.util;

import pso.core.PSO;
import pso.core.Quantum;
import pso.type.FloatCalculable;

/** An example of how to use PSO for optimization */
public class Test {
  public static void main(String args[]) throws InstantiationException, 
      IllegalAccessException {
    long t1 = System.currentTimeMillis();
    
    PSO pso = new PSO(-10, 10, 2, 20, 200);
    Quantum optimal = pso.optimize(new Functions.Beale());
    
    long t2 = System.currentTimeMillis();
    
    System.out.println(((FloatCalculable)optimal.getFitness()).get());
    for (int i = 0; i < optimal.getPosition().length; ++i) 
      System.out.println(optimal.getPosition()[i]);
    System.out.println("Time used " + (t2 - t1) + " ms.");
  }

}
