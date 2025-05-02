package org.example.parsers;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is a wrapper for Parsing. We pass the field to be parsed separated by commas.
 */
public class FieldParserWrapper extends FieldParser {

  //This factory is just used to get the appropriate parser for each subfield
  private final ParserFactory parserFactory;

  public FieldParserWrapper(String field, int min, int max, String fieldType, ParserFactory parserFactory) {
    super(field, min, max, fieldType);
    this.parserFactory = parserFactory;
  }

  @Override
  public List<Integer> parse() {
    for (String subField : field.split(",")) {
        FieldParser parser = parserFactory.getParser(subField.trim(), min, max, fieldType);
        results.addAll(parser.parse());
    }
    return new ArrayList<>(results);
  }
  //20-24,1-30,10 1 1 1
  //
}
