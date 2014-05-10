package org.danielchesters.javase8;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * Created by daniel on 10/05/14.
 */
public class Chapter6 {
    //Exercise 3
    public static void exercise3LongAdder() {
        try {
            LongAdder adder = new LongAdder();
            ExecutorService pool = Executors.newCachedThreadPool();
            for (int i = 0; i < 1000; i++) {
                pool.submit(() -> {
                    for (int j = 0; j < 100_000; j++) {
                        adder.increment();
                    }
                });
            }
            pool.shutdown();
            pool.awaitTermination(1, TimeUnit.HOURS);
            System.out.println(adder.sum());
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public static void exercise3AtomicLong() {
        try {
            AtomicLong atomicLong = new AtomicLong();
            ExecutorService pool = Executors.newCachedThreadPool();
            for (int i = 0; i < 1000; i++) {
                pool.submit(() -> {
                    for (int j = 0; j < 100_000; j++) {
                        atomicLong.getAndIncrement();
                    }
                });
            }
            pool.shutdown();
            pool.awaitTermination(1, TimeUnit.HOURS);
            System.out.println(atomicLong.get());
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public static void exercise3() {
        System.out.println("Exercise 3");
        long start = System.currentTimeMillis();
        exercise3AtomicLong();
        long duration = System.currentTimeMillis() - start;
        System.out.printf("Test with AtomicLong : %d%n", duration);
        start = System.currentTimeMillis();
        exercise3LongAdder();
        duration = System.currentTimeMillis() - start;
        System.out.printf("Test with LongAdder : %d%n", duration);
    }

    public static void main(String... args) {
        exercise3();
    }

}
