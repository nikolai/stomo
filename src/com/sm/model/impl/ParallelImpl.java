package com.sm.model.impl;

import com.sm.AnalyticalDF;
import com.sm.CompatibleDistributionFunctions;
import com.sm.DistributionFunction;
import com.sm.model.Parallel;

/**
 * User: mikola
 * Date: 26.10.12
 * Time: 20:17
 */
public class ParallelImpl extends AbstractActionContainer implements Parallel {
    private int countOfWaitedResults = Integer.MAX_VALUE; /* it means - all results should be waited */

    public void setCountOfWaitedResults(int count) {
        if (count < 1) {
            throw new IllegalArgumentException("Count of waited results should be positive number");
        }
        countOfWaitedResults = count;
    }

    public DistributionFunction getDistributionFunction() {
        CompatibleDistributionFunctions cdf = getCompatibleDistributionFunctions();
        final int actionsCount = cdf.getSize();
        if (actionsCount < countOfWaitedResults) {
            countOfWaitedResults = actionsCount;
        }

        if (countOfWaitedResults == 1) {
            return AnalyticalDF.get().createOR(cdf);
        }
        if (countOfWaitedResults < actionsCount) {
            return AnalyticalDF.get().createMN(cdf, countOfWaitedResults);
        }
        return AnalyticalDF.get().createAND(cdf);
    }
}
