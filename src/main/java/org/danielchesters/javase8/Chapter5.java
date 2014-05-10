package org.danielchesters.javase8;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.temporal.TemporalAdjuster;
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
        return w -> {
            LocalDate date = (LocalDate) w;
            do {
                date = date.plusDays(1);
            } while (predicate.test(date));
            return date;
        };
    }

    public static void exercise3() {
        System.out.println("Exercise 3");
        System.out.println(LocalDate.now().with(next(d -> d.getDayOfWeek().getValue() < 6)));
    }

    public static void main(String... args) {
        exercise1();
        exercise2();
        exercise3();
    }
}
