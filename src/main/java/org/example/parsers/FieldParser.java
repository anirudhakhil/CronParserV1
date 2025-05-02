package org.example.parsers;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * This class is the base class for all parsers. It holds the common properties and methods for all parsers.
 * The subclasses will implement the parse method.
 */
public abstract class FieldParser {

  protected final String field;
  protected final int min;
  protected final int max;
  protected final String fieldType;
  protected final Set<Integer> results = new TreeSet<>();

  public FieldParser(String field, int min, int max, String fieldType) {
    this.field = field;
    this.min = min;
    this.max = max;
    this.fieldType = fieldType;
  }

  public abstract List<Integer> parse();

}
