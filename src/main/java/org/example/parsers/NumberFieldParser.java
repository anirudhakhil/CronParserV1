package org.example.parsers;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is a parser for number fields. It checks if the value is within the range and returns the value.
 */
public class NumberFieldParser extends FieldParser {

  public NumberFieldParser(String field, int min, int max, String fieldType) {
    super(field, min, max, fieldType);
  }

  @Override
  public List<Integer> parse() {
    int val;
    try {
      val = Integer.parseInt(field);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid value provided : " + field + " for: " + fieldType, e);
    }
    if (val < min || val > max) {
      throw new IllegalArgumentException("Value " + val + " out of range for " + fieldType);
    }
    results.add(val);
    return new ArrayList<>(results);
  }

}
