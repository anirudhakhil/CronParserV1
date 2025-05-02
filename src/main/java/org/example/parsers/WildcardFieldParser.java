package org.example.parsers;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is a parser for wildcard fields. It checks if the value is a '*' and returns all possible values.
 */
public class WildcardFieldParser extends FieldParser {

  public WildcardFieldParser(String field, int min, int max, String fieldType) {
    super(field, min, max, fieldType);
  }

  @Override
  public List<Integer> parse() {
    //If it's a *, it indicates all possible values for that field incremented step wise.
    for (int i = min; i <= max; i++) {
      results.add(i);
    }
    return new ArrayList<>(results);
  }

}
