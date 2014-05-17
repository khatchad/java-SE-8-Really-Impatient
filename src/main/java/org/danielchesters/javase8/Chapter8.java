package org.danielchesters.javase8;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Created by daniel on 17/05/14.
 */
public class Chapter8 {
    //Exercise 2
    public static void exercise2() {
        System.out.println("Exercise 2");
        //System.out.println(Math.negateExact(Integer.MIN_VALUE));
    }

    //Exercise 3
    public static int gcd1(int a, int b) {
        if (b == 0) {
            return a;
        } else {
            return gcd1(b, a % b);
        }
    }

    public static int gcd2(int a, int b) {
        if (b == 0) {
            return a;
        } else {
            return gcd2(b, Math.floorMod(a, b));
        }
    }

    public static int gcd3(int a, int b) {
        if (b == 0) {
            return a;
        } else {
            return gcd3(b, rem(a, b));
        }
    }

    private static int rem(int a, int b) {
        return Integer.remainderUnsigned(a, b);
    }

    public static void exercise3() {
        System.out.println("Exercise 3");
        System.out.println(gcd1(20, -2));
        System.out.println(gcd2(20, -2));
        System.out.println(gcd3(20, -2));
    }

    //Exercise 5
    private static List<String> getWordsFromFile(String filename) throws IOException, URISyntaxException {
        String contents = new String(Files.readAllBytes(new File(Chapter8.class.getClassLoader().getResource(filename).toURI()).toPath()), StandardCharsets.UTF_8);
        return Arrays.asList(contents.split("[\\P{L}]+"));
    }

    public static void exercise5() throws IOException, URISyntaxException {
        System.out.println("Exercise 5");
        List<String> words = new ArrayList<>(getWordsFromFile("war-and-peace.txt"));
        System.out.println("With Stream");
        long start = System.currentTimeMillis();
        System.out.printf("Count of long words : %d%n", words.stream().filter(w -> w.length() > 12).count());
        long duration = System.currentTimeMillis() - start;
        System.out.printf("Duration : %d%n", duration);

        System.out.println("Without Stream");
        start = System.currentTimeMillis();
        words.removeIf(w -> w.length() <= 12);
        System.out.printf("Count of long words : %d%n", words.size());
        duration = System.currentTimeMillis() - start;
        System.out.printf("Duration : %d%n", duration);
    }

    //Exercise 9
    public static Stream<String> scannerToLines(Scanner scanner) {
        Iterator<String> iter = new Iterator<String>() {
            String nextLine = null;

            @Override
            public boolean hasNext() {
                if (nextLine != null) {
                    return true;
                } else {
                    nextLine = scanner.nextLine();
                    return (nextLine != null);
                }
            }

            @Override
            public String next() {
                if (nextLine != null || hasNext()) {
                    String line = nextLine;
                    nextLine = null;
                    return line;
                } else {
                    throw new NoSuchElementException();
                }
            }
        };
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(
                iter, Spliterator.ORDERED | Spliterator.NONNULL), false);
    }

    public static Stream<String> scannerToWords(Scanner scanner) {
        Iterator<String> iter = new Iterator<String>() {
            String nextWord = null;

            @Override
            public boolean hasNext() {
                if (nextWord != null) {
                    return true;
                } else {
                    nextWord = scanner.next();
                    return (nextWord != null);
                }
            }

            @Override
            public String next() {
                if (nextWord != null || hasNext()) {
                    String line = nextWord;
                    nextWord = null;
                    return line;
                } else {
                    throw new NoSuchElementException();
                }
            }
        };
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(
                iter, Spliterator.ORDERED | Spliterator.NONNULL), false);
    }

    public static IntStream scannerToIntegers(Scanner scanner) {
        Iterator<Integer> iter = new Iterator<Integer>() {
            int nextInt;

            @Override
            public boolean hasNext() {
                nextInt = scanner.nextInt();
                return (scanner.hasNext());
            }

            @Override
            public Integer next() {
                if (hasNext()) {
                    return nextInt;
                } else {
                    throw new NoSuchElementException();
                }
            }
        };
        return StreamSupport.intStream(Spliterators.spliteratorUnknownSize((PrimitiveIterator.OfInt) iter, Spliterator.ORDERED | Spliterator.NONNULL), false);
    }

    public static DoubleStream scannerToDoubles(Scanner scanner) {
        Iterator<Double> iter = new Iterator<Double>() {
            double nextDouble;

            @Override
            public boolean hasNext() {
                nextDouble = scanner.nextInt();
                return (scanner.hasNext());
            }

            @Override
            public Double next() {
                if (hasNext()) {
                    return nextDouble;
                } else {
                    throw new NoSuchElementException();
                }
            }
        };
        return StreamSupport.doubleStream(Spliterators.spliteratorUnknownSize((PrimitiveIterator.OfDouble) iter, Spliterator.ORDERED | Spliterator.NONNULL), false);
    }

    //Exercise 11
    public static InputStream connect(URL url, String username, String password) throws IOException {
        byte[] bytes = new String(username + ":" + password).getBytes();
        URLConnection connection = url.openConnection();
        Base64.Encoder encoder = Base64.getEncoder();
        connection.setRequestProperty("Authorization", "Basic " + encoder.encodeToString(bytes));
        connection.connect();
        return connection.getInputStream();
    }

    public static void main(String... args) throws IOException, URISyntaxException {
        exercise2();
        exercise3();
        exercise5();
    }
}
