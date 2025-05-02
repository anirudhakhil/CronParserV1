package org.example;

import org.example.parsers.FieldParserWrapper;
import org.example.parsers.ParserFactory;
import org.example.resources.CronExpressionResponseResource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

public class CronParser {

  public static CronExpressionResponseResource parseCronExpression(String cronExpression) {
    String[] parts = cronExpression.trim().split("\\s+", 6);
    if (parts.length < 6) {
      throw new IllegalArgumentException("Cron expression must have at least 6 parts: minute hour dayOfMonth month dayOfWeek command");
    }
    ParserFactory parserFactory = new ParserFactory();

    List<Integer> minutes = new FieldParserWrapper(parts[0], 0, 59, "minute", parserFactory).parse();
    List<Integer> hours = new FieldParserWrapper(parts[1], 0, 23, "hour", parserFactory).parse();
    List<Integer> months = new FieldParserWrapper(parts[3], 1, 12, "month", parserFactory).parse();
    List<Integer> dayOfMonths = new FieldParserWrapper(parts[2], 1, 31, "dayOfMonth", parserFactory).parse();
    int m = months.get(0);
    Month month = Month.of(m);
    LocalDateTime localDateTime = LocalDateTime.of(2025, month, dayOfMonths.get(0), hours.get(0), 0, 0);

    for(Integer min : minutes) {
      System.out.println(localDateTime.plusMinutes(min));
    }


    return new CronExpressionResponseResource(
            new FieldParserWrapper(parts[0], 0, 59, "minute",  parserFactory).parse(),
            new FieldParserWrapper(parts[1], 0, 23, "hour",  parserFactory).parse(),
            new FieldParserWrapper(parts[2], 1, 31, "dayOfMonth",  parserFactory).parse(),
            new FieldParserWrapper(parts[3], 1, 12, "month",  parserFactory).parse(),
            new FieldParserWrapper(parts[4], 0, 6, "dayOfWeek",  parserFactory).parse(),
            parts[5]
    );
  }

}

//*/15 0 1,15 * 1-5 /usr/bin/find

//15 30 45 60
//0
//1 15
//1 2 3 4 5 6 7 8 9 10 11 12
//1 2 3 4 5
//
//JAN 1 00:15 TUE
//JAN 1 00:30 TUE
//JAN 1 00:45 TUE
//JAN 1 01:00 TUE
//00:60
//00:15
