package com.thoughtworks.capability.gtb;

import java.time.LocalDate;
import java.time.Period;

/**
 * 计算任意日期与下一个劳动节相差多少天
 *
 * @author itutry
 * @create 2020-05-15_16:33
 */
public class Practice1 {

  public static long getDaysBetweenNextLaborDay(LocalDate date) {
    LocalDate labourDay = LocalDate.of(date.getYear(),5,1);
    LocalDate nextLabourDay = labourDay;
    if (labourDay.isBefore(date)) {
      nextLabourDay = labourDay.plusYears(1);
    }
    return nextLabourDay.toEpochDay() - date.toEpochDay();
  }
}


