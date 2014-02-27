package com.sm;

/**
 * User: mikola
 * Date: 26.10.12
 * Time: 18:19
 */
public class Variance<T extends Integer> {
    private final double variance;

    public Variance(DistributionFunction<T> df) {
        ExpectedValue expected = new ExpectedValue(df);
        double var = 0;

        for (DiscreteValue dv : df.getDistributionTable().sortedValues()) {
            if (df.getDistributionTable().getProbability(dv).getValue() > DistributionTable.ERROR) {
            var += Math.pow(dv.getValue() - expected.getValue(), 2) * df.eval(dv);
            }
        }
        variance = var;
    }

    public double getValue(){
        return variance;
    }

    public String toString() {
        return String.valueOf(variance);
    }

}
