package org.simiacryptus.distribution;

public class AxisSplitter
{
  public final int index;
  public final double threshold;
  public final ScalarDistribution distribution = new ScalarDistribution();

  public AxisSplitter(int index, double threshold)
  {
    this.index = index;
    this.threshold = threshold;
  }

  public boolean test(double[] point)
  {
    double value = value(point);
    distribution.add(value);
    return value < threshold;
  }

  protected double value(double[] point)
  {
    return point[index];
  }

  @Override
  public String toString()
  {
    StringBuilder builder = new StringBuilder();
    builder.append("v[");
    builder.append(index);
    builder.append("] < ");
    builder.append(threshold);
    builder.append("; distribution=");
    builder.append(distribution);
    builder.append("");
    return builder.toString();
  }
  
  
}
