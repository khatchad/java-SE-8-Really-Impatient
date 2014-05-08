package org.danielchesters.javase8;

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

    public static void main(String... args) {
        exercise1();
    }
}
