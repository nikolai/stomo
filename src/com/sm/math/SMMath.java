package com.sm.math;

import com.sm.DiscreteValue;

import java.util.Arrays;
import java.util.TreeSet;

/**
 * User: smirnov-n
 * Date: 09.09.11
 * Time: 19:17
 */
public class SMMath {
    public static double max(double val, double... vals) {
        double max = val;
        for (double d : vals) {
            max = java.lang.Math.max(d, max);
        }
        return max;
    }

    public static DiscreteValue max(DiscreteValue[] eventsData) {
        Arrays.sort(eventsData);
        return eventsData[eventsData.length - 1];
//        DiscreteValue
//        double max = val;
//        for (double d : vals) {
//            max = java.lang.Math.max(d, max);
//        }
//        return max;
    }
}
