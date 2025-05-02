package org.example.parsers;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is a parser for range fields. It checks if the value is within the range and returns the values.
 */
public class RangeFieldParser extends FieldParser {

  public RangeFieldParser(String field, int min, int max, String fieldType) {
    super(field, min, max, fieldType);
  }

  @Override
  public List<Integer> parse() {
    for (String subField : field.split(",")) {
      handleRange(subField.trim());
    }
    return new ArrayList<>(results);
  }

  private void handleRange(String sub) {
    //Indicates range from given starting point to ending point
    // Example: 9-12 indicates 9,10,11,12
    String[] range = sub.split("-");
    if (range.length != 2) {
      throw new IllegalArgumentException("Invalid range passed in: " + sub + "for: " + fieldType);
    }
    int start;
    int end;
    try {
      start = Integer.parseInt(range[0]);
      end = Integer.parseInt(range[1]);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid value provided : " + sub + " for: " + fieldType, e);
    }
    if (start > end) {
      throw new IllegalArgumentException("Range start must be less than or equal to end in: " + sub + "for: " + fieldType);
    }
    if(start < min || end > max) {
      throw new IllegalArgumentException("Given range is invalid in: " + sub + " for: " + fieldType);
    }
    for (int i = start; i <= end; i++) {
      results.add(i);
    }
  }

}
