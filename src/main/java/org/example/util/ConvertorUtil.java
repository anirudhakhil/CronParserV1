package org.example.util;

import java.util.List;
import java.util.stream.Collectors;

public class ConvertorUtil {

  /**
   * Utility to convert a list of integers into a string of space separated values.
   */
  public static String joinValues(List<Integer> values) {
    return values.stream()
            .map(String::valueOf)
            .collect(Collectors.joining(" "));
  }

}
