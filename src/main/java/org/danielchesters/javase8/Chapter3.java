package org.danielchesters.javase8;

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

    public static void main(String... args) {
        exercise1();
        exercise2();
    }
}
