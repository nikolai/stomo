package com.test;

import java.util.Map;

/**
 * Дисперсия
 * User: nikolai
 * Date: 26.08.11
 * Time: 22:48
 */
public class Variance {
    private final double variance;

    public Variance(Distribution distrib) {
        ExpectedValue expected = new ExpectedValue(distrib);

        double var = 0;
        Map<Integer, Double> distribution = distrib.getDistribution();

        for (Integer key : distribution.keySet()) {
            var += Math.pow(key - expected.getValue(), 2) * distribution.get(key);
        }
        variance = var;
    }
    public String toString() {
        return String.valueOf(variance);
    }
}
