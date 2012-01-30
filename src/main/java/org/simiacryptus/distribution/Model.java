package org.simiacryptus.distribution;

public class Model
{

  public class Node
  {
    final Bounds bounds = new Bounds(dimensions);
    final AxisSplitter splitter;
    Node left;
    Node right;
    long count = 0;

    public Node(AxisSplitter splitter)
    {
      this(splitter, null, null);
    }

    public Node(AxisSplitter splitter, Node left, Node right)
    {
      super();
      this.splitter = splitter;
      this.left = left;
      this.right = right;
    }

    public Node()
    {
      this(new AxisSplitter(0, 0));
    }

    public void add(double[] point)
    {
      bounds.add(point);
      count++;
      if(splitter.test(point))
      {
        if(null != left)
        {
          left.add(point);
        }
      }
      else
      {
        if(null != right)
        {
          right.add(point);
        }
      }
    }

    public Node rebalance()
    {
      final AxisSplitter s = new AxisSplitter(splitter.index, splitter.distribution.getMean());
      final Node l;
      if(null != left)
      {
        l = left.rebalance();
      }
      else if(count > 10)
      {
        l = new Node(new AxisSplitter((splitter.index+1) % dimensions, 0));
      }
      else
      {
        l = null;
      }
      final Node r;
      if(null != right)
      {
        r = right.rebalance();
      }
      else if(count > 10)
      {
        r = new Node(new AxisSplitter((splitter.index+1) % dimensions, 0));
      }
      else
      {
        r = null;
      }
      return new Node(s, l, r);
    }

    @Override
    public String toString()
    {
      StringBuilder builder = new StringBuilder();
      builder.append("Node [splitter=");
      builder.append(splitter);
      builder.append(", count=");
      builder.append(count);
      builder.append(", bounds=");
      builder.append(bounds);
      if(null!=left) 
      {
        builder.append(", \nleft=\n");
        builder.append(StringUtil.indent(left.toString(), " "));
      }
      if(null!=left) 
      {
        builder.append(", \nright=\n");
        builder.append(StringUtil.indent(right.toString(), " "));
      }
      builder.append("\n]");
      return builder.toString();
    }

    public double getWeight(Bounds b)
    {
      double w = 0;
      if(bounds.intersects(b))
      {
        if(null == right && null == left)
        {
          w += count;
        }
        else
        {
          if(null != right)
          {
            w += right.getWeight(b);
          }
          if(null != left)
          {
            w += left.getWeight(b);
          }
        }
      }
      return w;
    }

    public double getWeight(double[] point)
    {
      double w = 0;
      if(bounds.contains(point))
      {
        if(null == right && null == left)
        {
          w += count;
        }
        else
        {
          if(null != right)
          {
            w += right.getWeight(point);
          }
          if(null != left)
          {
            w += left.getWeight(point);
          }
        }
      }
      return w;
    }
    
  }
  
  private final int dimensions;
  private Node root;

  public Model(int dimensions)
  {
    this.dimensions = dimensions;
    this.root = new Node();
  }

  public void add(double[] point)
  {
    assert(dimensions == point.length);
    root.add(point);
  }

  public void rebalance()
  {
    root = root.rebalance();
  }

  @Override
  public String toString()
  {
    StringBuilder builder = new StringBuilder();
    builder.append("Model [dimensions=");
    builder.append(dimensions);
    builder.append(", root\n");
    builder.append(StringUtil.indent(root.toString(), " "));
    builder.append("\n]");
    return builder.toString();
  }

  public Bounds getBounds()
  {
    return root.bounds;
  }

  public double getWeight(Bounds bounds)
  {
    return root.getWeight(bounds);
  }

  public double getWeight(double[] point)
  {
    return root.getWeight(point);
  }

}
