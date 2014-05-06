package org.danielchesters.javase8;

import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by daniel on 06/05/14.
 */
public class Chapter2 {

    public static void streamOfInt() {
        int[] values = {1, 4, 9, 16};
        Stream<Object> s = Stream.of(values);
        IntStream intStream = IntStream.of(values);
        System.out.println(s.peek(System.out::println).count());
        System.out.println(intStream.peek(System.out::println).count());
    }

    public static Stream<Long> randomStream(Long a, Long c, Long m, Long seed) {
        return Stream.iterate(seed, (Long l) -> (a * l + c) % m);
    }

    public static Stream<Character> characterStream(String s) {
        return IntStream.rangeClosed(0, s.length() - 1).mapToObj(s::charAt);
    }

    public static void main(String... args) {
        Stream<Integer> first = Stream.of(1, 2, 3, 4, 5, 6);
        Stream<Integer> second = Stream.of(1, 2, 3, 4, 5, 6, 7);
        zip(first, second).forEach(System.out::println);
    }

    public static <T> boolean isFinite(Stream<T> stream) {
        return false;
    }

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
}
