package org.example.parsers;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is a parser for step fields. The step indicates the increment value, i.e., every step point.
 * 0/15 indicates it starts from 0 and increments every 15 - 0, 15, 30, 45 etc.
 */
public class StepFieldParser extends FieldParser {

  public StepFieldParser(String field, int min, int max, String fieldType) {
    super(field, min, max, fieldType);
  }

  @Override
  public List<Integer> parse() {
    String[] stepParts = field.split("/");
    if (stepParts.length != 2) {
      throw new IllegalArgumentException("Invalid step passed in: " + field + "for :" + fieldType);
    }
    //Here the step indicates the increment value, i.e., every step point.
    //0/15 indicates it starts from 0 and increments every 15 - 0, 15, 30, 45 etc.
    int step;
    try {
      step = Integer.parseInt(stepParts[1]);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid step value in: " + field + "for: " + fieldType, e);
    }

    if (step <= 0) {
      throw new IllegalArgumentException("Step value must be greater than 0 in: " + field + " for " + fieldType);
    }

    String rangePart = stepParts[0];

    //Example - */15
    //In this case, we consider all the values supported by that field
    if (rangePart.equals("*")) {
      for (int i = min; i <= max; i += step) {
        results.add(i);
      }
    } else if (rangePart.contains("-")) {
      // '-' indicates range.
      // Example: 9-12 indicates 9, 10, 11, 12

      String[] range = rangePart.split("-");
      if (range.length != 2) {
        throw new IllegalArgumentException("Invalid range passed in: " + field + "for: " + fieldType);
      }
      int start;
      int end;
      try {
        start = Integer.parseInt(range[0]);
        end = Integer.parseInt(range[1]);
      }
      catch (NumberFormatException e) {
        throw new IllegalArgumentException("Invalid value provided : " + field + " for: " + fieldType, e);
      }

      //Added this condition to make sure that the range is valid.
      if (start > end) {
        throw new IllegalArgumentException("Range start must be less than or equal to end in: " + field + "for: " + fieldType);
      }
      //Starting point of the given range to ending point incremented step wise.
      //Example: 9-40/15 indicates 9, 24,39
      for (int i = start; i <= end; i += step) {
        //Added this condition to make sure that the values are always in legal range.
        if (i >= min && i <= max) {
          results.add(i);
        }
      }
    } else {
      //This indicates the range from starting given number to max value and incremented step wise
      //Example: 4/10 - 4,14,24,34,44 etc.
      int start;
      try {
        start = Integer.parseInt(rangePart);
      }
      catch (NumberFormatException e) {
        throw new IllegalArgumentException("Invalid value provided : " + field + " for: " + fieldType, e);
      }
      for (int i = start; i <= max; i += step) {
        if (i >= min && i <= max) {
          results.add(i);
        }
      }
    }
    return new ArrayList<>(results);
  }

}
