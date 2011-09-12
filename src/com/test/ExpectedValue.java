package com.test;

import java.util.Map;

/**
 * Мат ожидание
 * User: nikolai
 * Date: 26.08.11
 * Time: 22:48
 */
public class ExpectedValue{
    private final double expectedValue;
    public ExpectedValue(Distribution distrib) {
        Map<Integer, Double> distribution = distrib.getDistribution();
        double expected = 0;
        for (Integer key : distribution.keySet()) {
            double probability = distribution.get(key);
            expected += key * probability;
        }
        expectedValue = expected;
    }

    public String toString() {
        return String.valueOf(expectedValue);
    }

    public double getValue() {
        return expectedValue;
    }

}
