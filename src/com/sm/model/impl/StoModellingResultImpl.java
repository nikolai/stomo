package com.sm.model.impl;

import com.sm.DistributionTable;
import com.sm.ExpectedValue;
import com.sm.Variance;
import com.sm.model.StoModellingResult;

/**
 * User: mikola
 * Date: 26.10.12
 * Time: 18:31
 */
public class StoModellingResultImpl implements StoModellingResult {
    private final DistributionTable distributionTable;
    private final ExpectedValue expectedValue;
    private final Variance variance;

    public StoModellingResultImpl(DistributionTable distributionTable, ExpectedValue expectedValue, Variance variance) {
        this.distributionTable = distributionTable;
        this.expectedValue = expectedValue;
        this.variance = variance;
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

    @Override
    public String toString() {
        return "\nDistribution table:\n" + distributionTable.toString()
                + "\nExpected value: " + expectedValue
                + "\nVariance: " + variance;
    }
}
