package org.example.parsers;

/**
 * This is a factory class responsible for creating the appropriate parser based on the input field.
 */
public class ParserFactory {

  public FieldParser getParser(String field, int min, int max, String fieldType) {

    if (field.contains("/")) {
      return new StepFieldParser(field, min, max, fieldType);
    } else if (field.equals("*")) {
      return new WildcardFieldParser(field, min, max, fieldType);
    } else if (field.contains("-")) {
      return new RangeFieldParser(field, min, max, fieldType);
    } else {
      return new NumberFieldParser(field, min, max, fieldType);
    }

  }

}
