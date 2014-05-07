package org.danielchesters.javase8;

import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by daniel on 06/05/14.
 */
public class Chapter2 {

    //Exercise 4
    public static void exercise4() {
        System.out.println("Exercise 4");
        int[] values = {1, 4, 9, 16};
        Stream<Object> s = Stream.of(values);
        IntStream intStream = IntStream.of(values);
        System.out.println(s.peek(System.out::println).count());
        System.out.println(intStream.peek(System.out::println).count());
    }

    //Exercise 5
    public static Stream<Long> randomStream(long a, long c, long m, long seed) {
        return Stream.iterate(seed, l -> (a * l + c) % m);
    }

    public static void exercise5() {
        System.out.println("Exercise 5");
        Stream<Long> stream = randomStream(25214903917l, 11, (long) Math.pow(2, 48), System.currentTimeMillis());
        stream.limit(5).forEach(System.out::println);
    }

    //Exercise 6
    public static Stream<Character> characterStream(String s) {
        return IntStream.rangeClosed(0, s.length() - 1).mapToObj(s::charAt);
    }

    public static void exercise6(){
        System.out.println("Exercise 6");
        characterStream("toto").forEach(System.out::println);
    }

    //Exercise 7 (reflection)
    public static <T> boolean isFinite(Stream<T> stream) {
        return false;
    }

    //Exercise 8 (not my solution)
    public static <T> Stream<T> zip(Stream<T> first, Stream<T> second) {
        Iterator<T> iterSecond = second.iterator();
        return first.flatMap(t -> {
                    if (iterSecond.hasNext()) {
                        return Arrays.asList(t, iterSecond.next()).stream();
                    } else {
                        first.close();
                        return null;
                    }
                }
        );
    }

    public static void exercise8() {
        System.out.println("Exercise 8");
        Stream<Integer> first = Stream.of(1, 2, 3, 4, 5, 6);
        Stream<Integer> second = Stream.of(1, 2, 3, 4, 5, 6, 7);
        zip(first, second).forEach(System.out::println);
    }

    public static void main(String... args) {
        exercise4();
        exercise5();
        exercise6();
        exercise8();
    }
}
