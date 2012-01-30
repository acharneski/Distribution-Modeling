package org.simiacryptus.distribution;

import java.util.Arrays;

public class ScalarDistribution
{
  final double moments[] = {0., 0.};
  
  public void add(double value)
  {
    for(int i=0;i<moments.length;i++)
    {
      moments[i] += Math.pow(value, i);
    }
  }
  
  public double getMean()
  {
    return moments[1] / moments[0];
  }

  @Override
  public String toString()
  {
    StringBuilder builder = new StringBuilder();
    builder.append(Arrays.toString(moments));
    return builder.toString();
  }

}
