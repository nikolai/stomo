package com.sm.model.impl;

import com.sm.*;
import com.sm.model.StoModellingResult;

/**
 * User: mikola
 * Date: 26.10.12
 * Time: 18:31
 */
public class StoModellingResultImpl implements StoModellingResult {
    private final DistributionTable<Integer> distributionTable;
    private final ExpectedValue expectedValue;
    private final Variance variance;

    public StoModellingResultImpl(DistributionFunction df) {
        this.distributionTable = df.getDistributionTable();
        this.expectedValue = new ExpectedValue(df);
        this.variance = new Variance(df);
    }

    public DistributionTable getDistributionTable() {
        return distributionTable;
    }

    public ExpectedValue getExpectedValue() {
        return expectedValue;
    }

    public Variance getVariance() {
        return variance;
    }

    public Probability evalRisk(int time) {
        double sum = 0;
        for (DiscreteValue<Integer> dv : distributionTable.sortedValues()) {
            if (dv.getValue() <= time) {
                sum += distributionTable.getProbability(dv).getValue();
            }
        }
        return new Probability(1 - sum);
    }

    @Override
    public String toString() {
        return "\nDistribution table:\n" + distributionTable.toString()
                + "\nExpected value: " + expectedValue
                + "\nVariance: " + variance;
    }
}
