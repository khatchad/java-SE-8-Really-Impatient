package org.danielchesters.javase8;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
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

    //Exercise 9 : I suppose that fields are String
    public static Comparator<Object> lexicographicComparator(String... fieldNames) {
        return (Object o1, Object o2) -> {
            int result = 0;
            try {
                for (String fieldName : fieldNames) {
                    Field field = o1.getClass().getDeclaredField(fieldName);
                    field.setAccessible(true);
                    String field1 = (String) field.get(o1);
                    String field2 = (String) field.get(o2);
                    result = field1.compareTo(field2);
                    if (result != 0) {
                        break;
                    }
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
            return result;
        };
    }

    static class Test {
        String lastName;
        String firstName;

        public Test(String lastName, String firstName) {
            this.lastName = lastName;
            this.firstName = firstName;
        }

        public String toString() {
            return firstName + " " + lastName;
        }
    }

    public static void exercise9() {
        System.out.println("Exercise 9");

        List<Test> tests = Arrays.asList(new Test("Test", "Test"), new Test("Chesters", "Daniel"));
        tests.sort(lexicographicComparator("lastName", "firstName"));
        tests.forEach(System.out::println);
    }

    //Exercise 13 & 14 : maybe one day...

    //Exercise 16
    public static <T> void doInOrderAsync(Supplier<T> first, BiConsumer<T, Throwable> second) {
        Thread t = new Thread() {
            public void run() {
                try {
                    T result = first.get();
                    second.accept(result, null);
                } catch (Throwable t) {
                    second.accept(null, t);
                }
            }
        };
        t.start();
    }

    //Exercise 17
    public static void doInParallelAsync(Runnable first, Runnable second, Consumer<Throwable> handler) {
        Thread t = new Thread() {
            public void run() {
                try {
                    ExecutorService pool = Executors.newCachedThreadPool();
                    pool.submit(first);
                    pool.submit(second);
                    pool.shutdown();
                    pool.awaitTermination(1, TimeUnit.HOURS);
                } catch (Throwable t) {
                    handler.accept(t);
                }
            }
        };
        t.start();
    }

    public static void main(String... args) {
        exercise1();
        exercise2();
        //I am not run always exercise 3 method, because it is throw an error 50% of time
        exercise9();
    }
}
