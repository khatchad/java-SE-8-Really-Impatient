package org.danielchesters.javase8;

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
          return gcd2(b, Math.floorMod(a,b));
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

    public static void main(String... args) {
        exercise2();
        exercise3();
    }
}
