package com.sm;

/**
 * User: Nikolai
 * Date: 23.10.11
 * Time: 16:12
 */
public class ExpectedValue {
    private final double expectedValue;
    public ExpectedValue(DistributionFunction df) {
        DistributionTable distribution = df.getDistributionTable();
        double expected = 0;
        int dtSize =  distribution.size();
        int i = 0;
        while (i < dtSize) {
            DiscreteValue val = distribution.getDiscreteValueInRow(i);
            Probability prob = distribution.getProbabilityInRow(i);
            expected += val.getValue() * prob.getValue();
            i++;
        }
        expectedValue = Probability.round(expected);
    }

    public String toString() {
        return String.valueOf(expectedValue);
    }

    public double getValue() {
        return expectedValue;
    }
}
