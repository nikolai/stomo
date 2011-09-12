package com.sm;

/**
 * User: smirnov-n
 * Date: 08.09.11
 * Time: 16:52
 */
public class Probability {
    private final double value;

    public Probability(double value) {
        this.value = value;
    }

    public Probability plus(Probability operand) {
        return new Probability(value + operand.value);
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
