package org.simiacryptus.distribution;

import java.util.Arrays;

public class Bounds
{

  private final double[][] bounds;

  public Bounds(int dimensions)
  {
    this.bounds = new double[dimensions][];
    for(int i=0; i<dimensions; i++)
    {
      bounds[i] = new double[]{Double.MAX_VALUE,-Double.MAX_VALUE};
    }
  }

  public void add(double[] point)
  {
    assert(bounds.length == point.length);
    for(int i=0; i<bounds.length; i++)
    {
      if(point[i] < bounds[i][0])
      {
        bounds[i][0] = point[i];
      }
      if(point[i] > bounds[i][1])
      {
        bounds[i][1] = point[i];
      }
    }
  }

  @Override
  public String toString()
  {
    StringBuilder builder = new StringBuilder();
    builder.append("[");
    for(double[] d : bounds)
    {
      builder.append(Arrays.toString(d));
      builder.append(";");
    }
    builder.append("]");
    return builder.toString();
  }

  public Bounds slice(int index, double value)
  {
    Bounds copy = new Bounds(bounds.length);
    for(int i=0; i<bounds.length; i++)
    {
      copy.bounds[i] = bounds[i];
    }
    copy.bounds[index][0] = value;
    copy.bounds[index][1] = value;
    return copy;
  }

  public double[] getPoint()
  {
    double[] point = new double[bounds.length];
    for(int i=0; i<bounds.length; i++)
    {
      double offset = bounds[i][0];
      double span = bounds[i][1] - offset;
      point[i] = offset + Math.random() * span;
    }
    return point;
  }

  public boolean intersects(Bounds b)
  {
    for(int i=0; i<bounds.length; i++)
    {
      if(bounds[i][0] > b.bounds[i][1]) return false;
      if(bounds[i][1] < b.bounds[i][0]) return false;
    }
    return true;
  }

  public boolean contains(double[] point)
  {
    for(int i=0; i<bounds.length; i++)
    {
      if(bounds[i][0] > point[i]) return false;
      if(bounds[i][1] < point[i]) return false;
    }
    return true;
  }

}
