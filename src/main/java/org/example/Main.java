package org.example;

import org.example.resources.CronExpressionResponseResource;
import org.example.util.ConvertorUtil;

import java.util.Arrays;

public class Main {
  public static void main(String[] args) {
    if (args.length == 0) {
      System.err.println("Cron expression has not been passed in the arguments");
      System.err.println("Example: \"*/15 0 1,15 * 1-5 command\" (Must be in quotes)");
      return;
    }

    try {
      CronExpressionResponseResource cronResponse = CronParser.parseCronExpression(args[0]);
      System.out.printf("%-14s %s%n", "minute", ConvertorUtil.joinValues(cronResponse.getMinutes()));
      System.out.printf("%-14s %s%n", "hour", ConvertorUtil.joinValues(cronResponse.getHours()));
      System.out.printf("%-14s %s%n", "day of month", ConvertorUtil.joinValues(cronResponse.getDayOfMonth()));
      System.out.printf("%-14s %s%n", "month", ConvertorUtil.joinValues(cronResponse.getMonth()));
      System.out.printf("%-14s %s%n", "day of week", ConvertorUtil.joinValues(cronResponse.getDayOfWeek()));
      System.out.printf("%-14s %s%n", "command", cronResponse.getCommand());
    } catch(Exception e) {
      e.printStackTrace();
    }
  }
}