package org.simiacyptus.distribution;

import java.util.Random;

public class GaussianDistribution
{

  private final int dimensions;
  private final Random random = new Random();

  public GaussianDistribution(int dimensions)
  {
    this.dimensions = dimensions;
  }

  public double[] getPoint()
  {
    double[] point = new double[dimensions];
    for(int i=0;i<dimensions;i++)
    {
      point[i] = random.nextGaussian();
    }
    return point;
  }

}
