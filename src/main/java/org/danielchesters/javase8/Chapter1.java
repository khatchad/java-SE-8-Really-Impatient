package org.danielchesters.javase8;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Created by daniel on 06/05/14.
 */
public class Chapter1 {

    public List<File> subdirectory(File directory) {
        if (directory == null || !directory.isDirectory()) {
            return Collections.emptyList();
        } else {
            List<File> subdirectory = new ArrayList<>();
            for (File f : directory.listFiles()) {
                if (f.isDirectory()) {
                    subdirectory.add(f);
                }
            }
            return subdirectory;
        }
    }

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

    public static void main(String... args) {
//        new Thread(Chapter1.unckeck(() -> {
//            System.out.println("Zzzz!!");
//            Thread.sleep(1000);
//            System.out.println("!!!!");
//        })).start();
//        new Thread(andThen(
//                () -> System.out.println("1"),
//                () -> System.out.println("2")
//        )).start();

//        exercice8();
    }

    public static Runnable andThen(Runnable r1, Runnable r2) {
        return () -> {
            r1.run();
            r2.run();
        };
    }

    public static void exercice8() {
        String[] names = {"Peter", "Paul", "Mary"};
        List<Runnable> runners = new ArrayList<>();
        for (String name : names) {
            runners.add(() -> System.out.println(name));
        }

        for (Runnable runner : runners) {
            new Thread(runner).start();
        }
    }


    public interface Collection2<E> extends Collection<E> {
        default void forEachIf(Consumer<E> action, Predicate<E> filter) {
            forEach(
                    e -> {
                        if (filter.test(e)) action.accept(e);
                    }
            );
        }
    }
}
