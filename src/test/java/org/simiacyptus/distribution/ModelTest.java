package org.simiacyptus.distribution;

import org.junit.Test;
import org.simiacryptus.distribution.Bounds;
import org.simiacryptus.distribution.Model;
import org.simiacryptus.distribution.ScalarDistribution;

public class ModelTest
{
  @Test
  public void test()
  {
    int dimensions = 3;
    GaussianDistribution trainingDistribution = new GaussianDistribution(dimensions);
    Model model = new Model(dimensions);
    for(int i=0;i<100;i++)
    {
      for(int j=0;j<1000;j++)
      {
        model.add(trainingDistribution.getPoint());
      }
      model.rebalance();
    }
    for(int j=0;j<10000;j++)
    {
      model.add(trainingDistribution.getPoint());
    }
    //System.out.println(model);
    ScalarDistribution distribution[] = {
        new ScalarDistribution(),
        new ScalarDistribution(),
        new ScalarDistribution()
    };
    Bounds bounds = model.getBounds().slice(1,0.3);
    double b = model.getWeight(bounds);
    for(int j=0;j<1000;j++)
    {
      double[] point = bounds.getPoint();
      double a = model.getWeight(point);
      if(0.5 < (Math.random() * (a/b)))
      {
        for(int i=0;i<distribution.length;i++)
        {
          distribution[i].add(point[i]);
        }
      }
    }
    for(int i=0;i<distribution.length;i++)
    {
      System.out.println(String.format("%s: %s", i, distribution[i]));;
    }
  }
}
