package org.example.resources;

import java.util.List;

public class CronExpressionResponseResource {

  private List<Integer> minutes;

  private List<Integer> hours;

  private List<Integer> dayOfMonth;

  private List<Integer> month;

  private List<Integer> dayOfWeek;

  private String command;

  public CronExpressionResponseResource(List<Integer> minutes, List<Integer> hours, List<Integer> dayOfMonth,
                   List<Integer> month, List<Integer> dayOfWeek, String command) {
    this.minutes = minutes;
    this.hours = hours;
    this.dayOfMonth = dayOfMonth;
    this.month = month;
    this.dayOfWeek = dayOfWeek;
    this.command = command;
  }



  public List<Integer> getMinutes() {
    return minutes;
  }

  public void setMinutes(List<Integer> minutes) {
    this.minutes = minutes;
  }

  public List<Integer> getHours() {
    return hours;
  }

  public void setHours(List<Integer> hours) {
    this.hours = hours;
  }

  public List<Integer> getDayOfMonth() {
    return dayOfMonth;
  }

  public void setDayOfMonth(List<Integer> dayOfMonth) {
    this.dayOfMonth = dayOfMonth;
  }

  public List<Integer> getMonth() {
    return month;
  }

  public void setMonth(List<Integer> month) {
    this.month = month;
  }

  public List<Integer> getDayOfWeek() {
    return dayOfWeek;
  }

  public void setDayOfWeek(List<Integer> dayOfWeek) {
    this.dayOfWeek = dayOfWeek;
  }

  public String getCommand() {
    return command;
  }

  public void setCommand(String command) {
    this.command = command;
  }
}
