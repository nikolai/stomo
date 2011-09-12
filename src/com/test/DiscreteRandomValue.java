package com.test;

/**
 * User: nikolai
 * Date: 26.08.11
 * Time: 22:48
 */
public class DiscreteRandomValue {
    private final int [] values;
    private final double [] distributionFunction;

    public DiscreteRandomValue(DiscreteRandomValue rval) {
        values = rval.values;
        distributionFunction = rval.distributionFunction;
    }

    public DiscreteRandomValue(int[] values, double[] probabilities) {
        assert values.length == probabilities.length && values.length > 0;

        this.values = values;

        distributionFunction = new double[values.length];
        distributionFunction[0] = probabilities[0];
        for (int i = 1; i < values.length; i++) {
            distributionFunction[i] = distributionFunction[i-1] + probabilities[i];
        }
    }

    public int getNext() {
        double r = Math.random();
        for (int i = 0; i < values.length; i++) {
            if (r <= distributionFunction[i]) {
                return values[i];
            }
        }
        throw new RuntimeException();
    }
}
