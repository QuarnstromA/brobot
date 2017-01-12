package com.github.quarnstroma.brobot.scheduler;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import java.time.LocalDateTime;

public class SchedulerTest
{
    @Test
    public void computeDelayOneHourTest()
    {
        Long oneHour = 60L*60L;
        Long calcedHour = Scheduler.computeDelayToNextTime(LocalDateTime.now().getHour()+1,
                LocalDateTime.now().getMinute(),
                LocalDateTime.now().getSecond());
        assertEquals(oneHour, calcedHour);
    }

    @Test
    public void computeDelayOneDayTest()
    {
        Long oneHour = 60L*60L*24L-1;
        Long calcedHour = Scheduler.computeDelayToNextTime(LocalDateTime.now().getHour(),
                LocalDateTime.now().getMinute(),
                LocalDateTime.now().getSecond()-1);
        assertEquals(oneHour, calcedHour);
    }
}
