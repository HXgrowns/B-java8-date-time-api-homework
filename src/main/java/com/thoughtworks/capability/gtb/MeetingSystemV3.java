package com.thoughtworks.capability.gtb;

import java.time.*;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

/**
 * 脑洞会议系统v3.0
 * 1.当前会议时间"2020-04-01 14:30:00"表示伦敦的本地时间，而输出的新会议时间是芝加哥的本地时间
 *   场景：
 *   a:上个会议是伦敦的同事定的，他在界面上输入的时间是"2020-04-01 14:30:00"，所以我们要解析的字符串是伦敦的本地时间
 *   b:而我们在当前时区(北京时区)使用系统
 *   c:我们设置好新会议时间后，要发给芝加哥的同事查看，所以格式化后的新会议时间要求是芝加哥的本地时间
 * 2.用Period来实现下个会议时间的计算
 *
 * @author itutry
 * @create 2020-05-19_18:43
 */
public class MeetingSystemV3 {
  private static final String LONDON_ZOOM_ID = "Europe/London";
  private static final String SHANGHAI_ZOOM_ID = "Asia/Shanghai";
  private static final String CHICAGO_ZOOM_ID = "America/Chicago";

  public static String nextMeetingTime(String timeStr) {
    System.out.printf("伦敦会议时间时间：%s\n", timeStr);

    // 根据格式创建格式化类
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    // 从字符串解析得到会议时间
    LocalDateTime meetingTime = LocalDateTime.parse(timeStr, formatter);

    ZonedDateTime londonTime = ZonedDateTime.of(meetingTime, ZoneId.of(LONDON_ZOOM_ID));
    ZonedDateTime shanghaiTime = londonTime.withZoneSameInstant(ZoneId.of(SHANGHAI_ZOOM_ID));

    LocalDateTime now = LocalDateTime.now();
    LocalTime nowTime = now.toLocalTime();
    LocalDateTime nextMeetingTime = now;
    if (nowTime.isAfter(shanghaiTime.toLocalTime())) {
      nextMeetingTime = addDays(shanghaiTime, now);
    }

    int newDayOfYear = nextMeetingTime.getDayOfYear();
    ZonedDateTime shanghaiNextMeetingTime = shanghaiTime.withDayOfYear(newDayOfYear);
    ZonedDateTime chicagoNextMeetingTime = shanghaiNextMeetingTime.withZoneSameInstant(ZoneId.of(CHICAGO_ZOOM_ID));

    // 格式化新会议时间
    String nextMeetingTimeStr = formatter.format(chicagoNextMeetingTime);
    System.out.printf("芝加哥下次会议时间：%s\n", nextMeetingTimeStr);
    return nextMeetingTimeStr;
  }

  private static LocalDateTime addDays(ZonedDateTime shanghaiTime,LocalDateTime current) {
    LocalDate oldLocalDate = shanghaiTime.toLocalDate();
    LocalDate currentDate = current.toLocalDate();
    Period between = Period.between(oldLocalDate, currentDate);
    return LocalDateTime.from(between.addTo(shanghaiTime.toLocalDateTime())).plusDays(1);
  }

  public static void main(String[] args) {
    nextMeetingTime("2020-04-01 14:30:00");
  }
}
