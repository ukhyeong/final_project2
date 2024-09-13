package org.zerock.myapp.util;

import java.util.Random;


public class RandomNumberGenerator {
    private static Random randomNumberGenerator;

    static {
        randomNumberGenerator = new Random();
    } // static initializer


    public static int generateOneInt(int originInclusive, int boundExclusive) { // Half-open
        randomNumberGenerator.setSeed(randomNumberGenerator.nextLong());
        return randomNumberGenerator.nextInt(originInclusive, boundExclusive);
    } // generateOneInt

    public static long generateOneLong(long originInclusive, long boundExclusive) { // Half-open
        randomNumberGenerator.setSeed(randomNumberGenerator.nextLong());
        return randomNumberGenerator.nextLong(originInclusive, boundExclusive);
    } // generateOneLong

    public static int[] generateIntArray(int size, int originInclusive, int boundExclusive) { // Half-open
        randomNumberGenerator.setSeed(randomNumberGenerator.nextLong());
        return randomNumberGenerator.ints(size, originInclusive, boundExclusive).toArray();
    } // generateIntArray

    public static long[] generateLongArray(int size, int originInclusive, int boundExclusive) { // Half-open
        randomNumberGenerator.setSeed(randomNumberGenerator.nextLong());
        return randomNumberGenerator.longs(size, originInclusive, boundExclusive).toArray();
    } // generateLongArray

} // end class
