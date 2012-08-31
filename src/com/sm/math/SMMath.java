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

    public static DiscreteValue min(DiscreteValue[] eventsData) {
        Arrays.sort(eventsData);
        return eventsData[0];
    }

    /**
     * returns the M minimal value
     * */
    public static DiscreteValue min(int M, DiscreteValue[] eventsData) {
        assert M-1 <= eventsData.length && M >= 1;
        Arrays.sort(eventsData);
        return eventsData[M-1];
    }

    public static DiscreteValue<Integer> sum(DiscreteValue<Integer>[] eventsData) {
        int sum = 0;
        for (DiscreteValue<Integer> dv : eventsData) {
            sum += dv.getValue();
        }
        return new DiscreteValue(sum);
    }
}
