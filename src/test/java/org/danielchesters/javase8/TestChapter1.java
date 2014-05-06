package org.danielchesters.javase8;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.util.*;

/**
 * Created by daniel on 06/05/14.
 */
@RunWith(JUnit4.class)
public class TestChapter1 {

    @Test
    public void testArraysSort() {
        Integer[] ints = {1, 8, 6, 8, 4, 5};

        Arrays.sort(ints, Comparator.<Integer>naturalOrder());
        Arrays.asList(ints).forEach(System.out::println);

    }

    @Test
    public void testArraysFilesSort() {
        File[] files = Chapter1.sortFilesWithDirectoryFirst(new File("/home/daniel/data/Documents/Developpement/java8/java8SE_really_impatient").listFiles());
        Arrays.asList(files).forEach(System.out::println);
    }

    @Test
    public void testRunnableUnckeck() {
        new Thread(Chapter1.unckeck(() -> {
            System.out.println("Zzzz!!");
            Thread.sleep(1000);
            System.out.println("!!!!");
        })).start();
    }

}
