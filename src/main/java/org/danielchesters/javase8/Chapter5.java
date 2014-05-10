package org.danielchesters.javase8;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.function.Predicate;

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
            } while (predicate.test(date));
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

    public static void main(String... args) {
        exercise1();
        exercise2();
        exercise3();
        exercise4();
        exercise5();
    }
}
