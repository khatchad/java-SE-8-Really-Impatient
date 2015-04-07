package org.danielchesters.javase8;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Created by daniel on 06/05/14.
 */
public class Chapter1 {
    //Exercise 1 (reflection)
    public static void exercise1() {
        System.out.println("Exercise 1");
        Integer[] ints = {1, 8, 6, 8, 4, 5};

        Arrays.sort(ints, Comparator.<Integer>naturalOrder());
        Arrays.asList(ints).forEach(System.out::println);
    }

    //Exercise 2
    public File[] subdirectoryWithFilter(File directory) {
        return directory.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isDirectory();
            }
        });
    }

    public File[] subdirectoryWithLambda(File directory) {
        return directory.listFiles(f -> f.isDirectory());
    }

    public File[] subdirectoryWithMethodReference(File directory) {
        return directory.listFiles(File::isDirectory);
    }

    //Exercise 3
    public String[] filterList(File directory, String extension) {
        return directory.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(extension);
            }
        });
    }

    public String[] filterListWithLambda(File directory, String extension) {
        return directory.list((dir, name) -> name.endsWith(extension));
    }

    //Exercise 4
    public static File[] sortFilesWithDirectoryFirst(File[] files) {
        Arrays.sort(files, (File f1, File f2) -> {
            if ((f1.isDirectory() && f2.isDirectory()) || (f1.isFile() && f2.isFile())) {
                return f1.getPath().compareTo(f2.getPath());
            } else if (f1.isDirectory() && f2.isFile()) {
                return -1;
            } else {
                return 1;
            }
        });
        return files;
    }

    public static void exercise4() {
        System.out.println("Exercise 4");
        File[] files = Chapter1.sortFilesWithDirectoryFirst(new File("/home/daniel/data/Documents/Developpement/java8/java8SE_really_impatient").listFiles());
        Arrays.asList(files).forEach(System.out::println);
    }

    //Exercise 5 : I don't have enough code with Runnable or some things equivalent

    //Exercise 6
    @FunctionalInterface
    interface RunnableEx {
        public void run() throws Exception;
    }

    public static Runnable unckeck(RunnableEx runner) {
        return () -> {
            try {
                runner.run();
            } catch (Exception ex) {
                System.err.println(ex);
            }
        };
    }

    public static void exercise6() {
        System.out.println("Exercise 6");
        new Thread(Chapter1.unckeck(() -> {
            System.out.println("Zzzz!!");
            Thread.sleep(1000);
            System.out.println("!!!!");
        })).start();

    }

    //Exercise 7
    public static Runnable andThen(Runnable r1, Runnable r2) {
        return () -> {
            r1.run();
            r2.run();
        };
    }

    public static void exercise7() {
        System.out.println("Exercise 7");
        new Thread(andThen(
                () -> System.out.println("1"),
                () -> System.out.println("2")
        )).start();
    }

    //Exercise 8
    public static void exercise8() {
        System.out.println("Exercise 8");
        String[] names = {"Peter", "Paul", "Mary"};
        List<Runnable> runners = new ArrayList<>();
        for (String name : names) {
            runners.add(() -> System.out.println(name));
        }

        for (Runnable runner : runners) {
            new Thread(runner).start();
        }
    }

    //Exercise 9
    private interface Collection2<E> extends Collection<E> {
        public default void forEachIf(Consumer<E> action, Predicate<E> filter) {
        	this.stream().filter(filter).forEach(action);
        }
    }
    
    private static class ArrayList2<E> extends ArrayList<E> implements Collection2<E> {
		private static final long serialVersionUID = 2365007795434175871L;
    }
    
    public static void exercise9() {
    	Collection2<String> col = new ArrayList2<>();
		
		col.add("Moe");
		col.add("Larry");
		col.add("Curly");
		
		col.forEachIf(System.out::println, s -> s.endsWith("y"));
    }

    //Exercise 10 : not code

    //Exercise 11 : Maybe one day…

    //Exercise 12 : not code

    public static void main(String... args) {
        exercise1();
        exercise4();
        exercise6();
        exercise7();
        exercise8();
    	exercise9();
    }






}
