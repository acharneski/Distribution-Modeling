package org.simiacryptus.distribution;

public class StringUtil
{

  public static String indent(String text, String indent)
  {
    return indent + text.replaceAll("\n", "\n" + indent);
  }

}
