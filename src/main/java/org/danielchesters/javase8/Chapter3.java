package org.danielchesters.javase8;

import java.util.Comparator;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by daniel on 08/05/14.
 */
public class Chapter3 {

    //Exercise 1
    public static void logIf(Level level, Supplier<Boolean> condition, Supplier<String> message) {
        Logger logger = Logger.getGlobal();
        if (logger.isLoggable(level) && condition.get()) {
            logger.log(level, message.get());
        }
    }

    public static void exercise1() {
        System.out.println("Exercise 1");
        logIf(Level.INFO, () -> true, () -> "Test");
    }

    //Exercise 2
    public static void withLock(ReentrantLock lock, Runnable runnable) {
        lock.lock();
        try {
            runnable.run();
        } finally {
            lock.unlock();
        }
    }

    public static void exercise2() {
        System.out.println("Exercise 2");
        ReentrantLock lock = new ReentrantLock();
        withLock(lock, () -> System.out.println("Lock"));
        withLock(lock, () -> System.out.println("Lock2"));
    }

    //Exercise 3
    public static void assertMethod(Supplier<Boolean> condition) {
        if (!condition.get()) {
            throw new AssertionError();
        }
    }

    public static void exercise3() {
        System.out.println("Exercise 3");
        assertMethod(() -> Math.random() < 0.5);
    }

    //Exercise 4 : not code

    //Exercise 7 : I am not sure that it is the solution
    public static Comparator<String> stringComparator(Comparator<String> comparator1, Comparator<String> comparator2) {
        return (s1, s2) -> {
            int resultComparator1 = comparator1.compare(s1, s2);
            if (resultComparator1 == 0) {
                return comparator2.compare(s1, s2);
            } else {
                return resultComparator1;
            }
        };
    }

    public static void main(String... args) {
        exercise1();
        exercise2();
        exercise3();
    }
}
