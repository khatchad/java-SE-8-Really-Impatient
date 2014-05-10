package org.danielchesters.javase8;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by daniel on 10/05/14.
 */
public class Chapter5 {

    //Exercise 1
    public static void exercise1() {
        System.out.println("Exercise 1");
        LocalDate programmersDay2014 = getProgrammersDay(2014);
        LocalDate programmersDay2016 = getProgrammersDay(2016);
        System.out.println(programmersDay2014);
        System.out.println(programmersDay2016);
    }

    private static LocalDate getProgrammersDay(int year) {
        Period durationOf255Days = Period.ofDays(255);
        return LocalDate.of(year, Month.JANUARY, 1).plus(durationOf255Days);
    }

    //Exercise 2
    public static void exercise2() {
        System.out.println("Exercise 2");
        LocalDate date = LocalDate.of(2000, 2, 29);
        System.out.println(date.plusYears(4));
        System.out.println(date.plusYears(1).plusYears(1).plusYears(1).plusYears(1));
    }

    //Exercise 3
    public static TemporalAdjuster next(Predicate<LocalDate> predicate) {
        return TemporalAdjusters.ofDateAdjuster(w -> {
            LocalDate date = w;
            do {
                date = date.plusDays(1);
            } while (!predicate.test(date));
            return date;
        });
    }

    public static void exercise3() {
        System.out.println("Exercise 3");
        System.out.println(LocalDate.now().with(next(d -> d.getDayOfWeek().getValue() < 6)));
    }

    //Exercise 4 (need improve for the weeks management)
    public static void cal(int month, int year) {
        LocalDate firstDayOfMonth = LocalDate.of(year, month, 1);
        LocalDate firstDayOfNextMonth = firstDayOfMonth.with(TemporalAdjusters.firstDayOfNextMonth());
        LocalDate dateLoop = firstDayOfMonth;
        int[][] dates = new int[7][6];
        while (dateLoop.isBefore(firstDayOfNextMonth)) {
            dates[dateLoop.getDayOfWeek().getValue() - 1][dateLoop.get(WeekFields.ISO.weekOfMonth())] = dateLoop.getDayOfMonth();
            dateLoop = dateLoop.plusDays(1);
        }

        for (int w = 0; w < 6; w++) {
            for (int d = 0; d < 7; d++) {
                int i = dates[d][w];
                if (i == 0) {
                    System.out.print("   ");
                } else {
                    System.out.printf("%2d ", i);
                }
            }
            System.out.println();
        }

    }

    public static void exercise4() {
        System.out.println("Exercise 4");
        cal(3, 2013);
        cal(5, 2014);
    }

    //Exercise 5
    public static void daysBeenAlive(LocalDate birthday) {
        long period = birthday.until(LocalDate.now(), ChronoUnit.DAYS);
        System.out.printf("%d days since you are born%n", period);
    }

    public static void exercise5() {
        System.out.println("Exercise 5");
        daysBeenAlive(LocalDate.of(1985, 4, 28));
    }

    //Exercise 6
    public static void exercise6() {
        System.out.println("Exercise 6");
        LocalDate startDate = LocalDate.of(1900, 1, 1);
        LocalDate stopDate = LocalDate.of(2000, 1, 1);
        LocalDate date = startDate;
        List<LocalDate> listOfFridayThe13th = new ArrayList<>();
        while (date.isBefore(stopDate)) {
            date = date.with(next(d -> d.getDayOfWeek().equals(DayOfWeek.FRIDAY) && d.getDayOfMonth() == 13));
            listOfFridayThe13th.add(date);
        }
        listOfFridayThe13th.forEach(System.out::println);
    }

    //Exercise 7
    public static class TimeInterval {
        public LocalTime startTime;
        public LocalTime endTime;

        public TimeInterval(LocalTime startTime, LocalTime endTime) {
            this.startTime = startTime;
            this.endTime = endTime;
        }
    }

    public static boolean intervalOverlap(TimeInterval firstInterval, TimeInterval secondInterval) {
        if (firstInterval.startTime.isBefore(secondInterval.startTime)) {
            return secondInterval.startTime.isBefore(firstInterval.endTime);
        } else {
            return firstInterval.startTime.isBefore(secondInterval.endTime);
        }
    }

    public static void exercise7() {
        System.out.println("Exercise 7");
        LocalTime startTime1 = LocalTime.of(10, 00);
        LocalTime stopTime1 = LocalTime.of(11, 00);
        LocalTime startTime2 = LocalTime.of(10, 30);
        LocalTime stopTime2 = LocalTime.of(11, 30);
        TimeInterval timeInterval1 = new TimeInterval(startTime1, stopTime1);
        TimeInterval timeInterval2 = new TimeInterval(startTime2, stopTime2);
        System.out.println(intervalOverlap(timeInterval1, timeInterval2));
    }

    //Exercise 8
    public static void exercise8() {
        System.out.println("Exercise 8");
        Instant now = Instant.now();
        ZoneId.getAvailableZoneIds().stream().map(zone -> ZoneId.of(zone).getRules().getOffset(now)).collect(Collectors.toSet()).forEach(System.out::println);
    }

    //Exercise 9
    public static void exercise9() {
        System.out.println("Exercise 9");
        Instant now = Instant.now();
        ZoneId.getAvailableZoneIds().stream().map(zone -> ZoneId.of(zone).getRules().getOffset(now)).filter(offset -> offset.getTotalSeconds() % 3600 != 0)
                .collect(Collectors.toSet()).forEach(System.out::println);
    }

    public static void main(String... args) {
        exercise1();
        exercise2();
        exercise3();
        exercise4();
        exercise5();
        exercise6();
        exercise7();
        exercise8();
        exercise9();
    }
}
