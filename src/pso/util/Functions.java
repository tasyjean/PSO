package pso.util;

import pso.core.Evaluation;
import pso.type.Calculable;
import pso.type.FloatCalculable;

/**
 * A collection of example functions to be optimized.  
 */
public class Functions {
  
  public static class Sphere extends Evaluation {
    public Calculable evaluate(Calculable[] coordinate) {
      float value = 0;
      for (int i = 0; i < coordinate.length; ++i) {
        float f = (float) ((FloatCalculable) coordinate[i]).get();
        value += f * f;
      }
      return new FloatCalculable(-value);
    } 
  }
  
  public static class Beale extends Evaluation {
    public Calculable evaluate(Calculable[] coordinate) {
      float x = ((FloatCalculable) coordinate[0]).get();
      float y = ((FloatCalculable) coordinate[1]).get();
      
      float v = x * y;
      float v1 = (float) (1.5 - x + v);
      v *= y;
      float v2 = (float) (2.25 - x + v);
      v *= y;
      float v3 = (float) (2.625 - x + v);
      return new FloatCalculable(-(v1 * v1 + v2 * v2 + v3 * v3));
    }
  }
  
  /** Transfer a 2-D array of float to FloatCalculable. */
  public static FloatCalculable[][] transFloat(float[][] bounds) {
    FloatCalculable[][] calcBounds = new FloatCalculable[bounds.length][2];
    
    for (int i = 0; i < bounds.length; ++i) {
      for (int j = 0; j < 2; ++j) {
        calcBounds[i][j] = new FloatCalculable(bounds[i][j]);
      }
    }
    
    return calcBounds;
  }
}
