package com.thoughtworks.capability.gtb;

import junit.framework.TestCase;
import org.junit.Assert;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MeetingSystemV3Test extends TestCase {

    public void testNextMeetingTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < 24; i++) {
            System.out.printf("Case %d:\n", i+1);
            String londonMeetingTime = String.format("2020-09-18 %02d:30:00", i);
            MeetingSystemV3.nextMeetingTime(londonMeetingTime);
            System.out.println();
        }
    }
}