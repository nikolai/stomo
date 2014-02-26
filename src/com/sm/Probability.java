package com.sm;

/**
 * User: smirnov-n
 * Date: 08.09.11
 * Time: 16:52
 */
public class Probability implements Comparable<Probability>{
    public static final double round = Math.pow(10, Integer.parseInt(System.getProperty("probability.accuracy", "4")));
    private final double value;

    public Probability(double value) {
        this.value = round(value < 0 ? 0 : value);
    }

    public static double round(double value) {
        return Math.round (value * round)/ round;
    }

    public Probability plus(Probability operand) {
        return new Probability(value + (operand != null?operand.value: 0));
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public int compareTo(Probability o) {
        return Double.compare(
                this.getValue(),
                o.getValue()
        );
    }


    public Probability invert() {
        return new Probability(1-value);
    }
}
