package org.example;

import static org.junit.jupiter.api.Assertions.*;

import org.example.resources.CronExpressionResponseResource;
import org.junit.jupiter.api.Test;

import java.util.Arrays;


import static org.junit.jupiter.api.Assertions.assertEquals;


public class CronExpressionParserTest {

  @Test
  void testValidCronExpression() {
    String cronExpression = "*/15 0 1,15 2 1-5 /usr/bin";
    CronExpressionResponseResource response = CronParser.parseCronExpression(cronExpression);

    assertEquals(Arrays.asList(0, 15, 30, 45), response.getMinutes());
    assertEquals(Arrays.asList(0), response.getHours());
    assertEquals(Arrays.asList(1, 15), response.getDayOfMonth());
    assertEquals(Arrays.asList(2), response.getMonth());
    assertEquals(Arrays.asList(1, 2, 3, 4, 5), response.getDayOfWeek());
    assertEquals("/usr/bin", response.getCommand());
  }

  @Test
  void testWildcardParsing() {
    String cronExpression = "* * * * * /usr/bin";
    CronExpressionResponseResource response = CronParser.parseCronExpression(cronExpression);

    assertEquals(60, response.getMinutes().size());
    assertEquals(24, response.getHours().size());
    assertEquals(31, response.getDayOfMonth().size());
    assertEquals(12, response.getMonth().size());
    assertEquals(7, response.getDayOfWeek().size());
    assertEquals("/usr/bin", response.getCommand());
  }

  @Test
  void testSingleValueParsing() {
    String cronExpression = "10 2 5 8 3 /usr/bin";
    CronExpressionResponseResource response = CronParser.parseCronExpression(cronExpression);

    assertEquals(Arrays.asList(10), response.getMinutes());
    assertEquals(Arrays.asList(2), response.getHours());
    assertEquals(Arrays.asList(5), response.getDayOfMonth());
    assertEquals(Arrays.asList(8), response.getMonth());
    assertEquals(Arrays.asList(3), response.getDayOfWeek());
    assertEquals("/usr/bin", response.getCommand());
  }

  @Test
  void testRangeParsing() {
    String cronExpression = "5-10 3-6 1-7 6-8 2-4 /usr/bin";
    CronExpressionResponseResource response = CronParser.parseCronExpression(cronExpression);

    assertEquals(Arrays.asList(5, 6, 7, 8, 9, 10), response.getMinutes());
    assertEquals(Arrays.asList(3, 4, 5, 6), response.getHours());
    assertEquals(Arrays.asList(1, 2, 3, 4, 5, 6, 7), response.getDayOfMonth());
    assertEquals(Arrays.asList(6, 7, 8), response.getMonth());
    assertEquals(Arrays.asList(2, 3, 4), response.getDayOfWeek());
    assertEquals("/usr/bin", response.getCommand());
  }

  @Test
  void testSubExpressionParsing_plainNumberAndRange() {
    String cronExpression = "5-10 1,3-6 1-7 6-8 2-4 /usr/bin";
    CronExpressionResponseResource response = CronParser.parseCronExpression(cronExpression);

    assertEquals(Arrays.asList(5, 6, 7, 8, 9, 10), response.getMinutes());
    assertEquals(Arrays.asList(1, 3, 4, 5, 6), response.getHours());
    assertEquals(Arrays.asList(1, 2, 3, 4, 5, 6, 7), response.getDayOfMonth());
    assertEquals(Arrays.asList(6, 7, 8), response.getMonth());
    assertEquals(Arrays.asList(2, 3, 4), response.getDayOfWeek());
    assertEquals("/usr/bin", response.getCommand());
  }

  @Test
  void testSubExpressionParsing_rangeAndStep() {
    String cronExpression = "5-10 3-6,10/2 1-7 6-8 2-4 /usr/bin";
    CronExpressionResponseResource response = CronParser.parseCronExpression(cronExpression);

    assertEquals(Arrays.asList(5, 6, 7, 8, 9, 10), response.getMinutes());
    assertEquals(Arrays.asList(3, 4, 5, 6, 10, 12, 14, 16, 18, 20, 22), response.getHours());
    assertEquals(Arrays.asList(1, 2, 3, 4, 5, 6, 7), response.getDayOfMonth());
    assertEquals(Arrays.asList(6, 7, 8), response.getMonth());
    assertEquals(Arrays.asList(2, 3, 4), response.getDayOfWeek());
    assertEquals("/usr/bin", response.getCommand());
  }

  @Test
  void testOverlappingValues() {
    String cronExpression = "5-10 1-8,5-10 1-7 6-8 2-4 /usr/bin";
    CronExpressionResponseResource response = CronParser.parseCronExpression(cronExpression);

    assertEquals(Arrays.asList(5, 6, 7, 8, 9, 10), response.getMinutes());
    assertEquals(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), response.getHours());
    assertEquals(Arrays.asList(1, 2, 3, 4, 5, 6, 7), response.getDayOfMonth());
    assertEquals(Arrays.asList(6, 7, 8), response.getMonth());
    assertEquals(Arrays.asList(2, 3, 4), response.getDayOfWeek());
    assertEquals("/usr/bin", response.getCommand());
  }

  @Test
  void testUnsortedValues() {
    String cronExpression = "17,8,1-4 1-8,5-10 1-7 6-8 2-4 /usr/bin";
    CronExpressionResponseResource response = CronParser.parseCronExpression(cronExpression);

    assertEquals(Arrays.asList(1, 2, 3, 4, 8, 17), response.getMinutes());
    assertEquals(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), response.getHours());
    assertEquals(Arrays.asList(1, 2, 3, 4, 5, 6, 7), response.getDayOfMonth());
    assertEquals(Arrays.asList(6, 7, 8), response.getMonth());
    assertEquals(Arrays.asList(2, 3, 4), response.getDayOfWeek());
    assertEquals("/usr/bin", response.getCommand());
  }


  @Test
  void testInvalidCronExpression() {
    String cronExpression = "*/15 0 *";
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      CronParser.parseCronExpression(cronExpression);
    });
    assertTrue(exception.getMessage().contains("Cron expression must have at least 6 parts"));
  }

  @Test
  void testInvalidRange() {
    String cronExpression = "10-5 2 3 4 5 /usr/bin";
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      CronParser.parseCronExpression(cronExpression);
    });
    assertTrue(exception.getMessage().contains("Range start must be less than or equal to end"));
  }

  @Test
  void testInvalidStep() {
    String cronExpression = "*/-5 1 1 1 1 /usr/bin";
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      CronParser.parseCronExpression(cronExpression);
    });
    assertTrue(exception.getMessage().contains("Step value must be greater than 0"));
  }

  @Test
  void testOutOfRangeForHour() {
    String cronExpression = "*/5 100 1 1 1 /usr/bin";
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      CronParser.parseCronExpression(cronExpression);
    });
    assertTrue(exception.getMessage().contains("Value 100 out of range for hour"));
  }

  @Test
  void testOutOfRangeForDayOfMonth() {
    String cronExpression = "*/5 1 80 1 1 /usr/bin";
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      CronParser.parseCronExpression(cronExpression);
    });
    assertTrue(exception.getMessage().contains("Value 80 out of range for dayOfMonth"));
  }

  @Test
  void testNonNumericValues() {
    String cronExpression = "* * a * * /usr/bin";
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      CronParser.parseCronExpression(cronExpression);
    });
    assertTrue(exception.getMessage().contains("Invalid value provided : a for: dayOfMonth"));
  }

  @Test
  void testStepValueAsZero() {
    String cronExpression = "2/0 * * * * /usr/bin";
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      CronParser.parseCronExpression(cronExpression);
    });
    assertTrue(exception.getMessage().contains("Step value must be greater than 0 in: 2/0 for minute"));
  }

  @Test
  void testInvalidRangeOutOfBounds() {
    String cronExpression = "*/15 0 1,15 * 1-8 /usr/bin/find";
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      CronParser.parseCronExpression(cronExpression);
    });
    assertTrue(exception.getMessage().contains("Given range is invalid in: 1-8 for: dayOfWeek"));
  }

}