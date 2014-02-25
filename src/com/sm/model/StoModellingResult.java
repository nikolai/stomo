package com.sm.model;

import com.sm.DistributionTable;
import com.sm.ExpectedValue;
import com.sm.Probability;
import com.sm.Variance;

/**
 * User: mikola
 * Date: 26.10.12
 * Time: 18:16
 */
public interface StoModellingResult {
    DistributionTable getDistributionTable();
    ExpectedValue getExpectedValue();
    Variance getVariance();
    Probability evalRisk(int time);
}
