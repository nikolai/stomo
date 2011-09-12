package com.sm;

import java.util.Set;

/**
 * User: smirnov-n
 * Date: 08.09.11
 * Time: 16:49
 */
public class DiscreteRandomValueGenerator {
    private final DistributionFunction distributionFunction;

    private DiscreteRandomValueGenerator(DistributionTable distributionTable){
        distributionFunction = new DistributionFunction(distributionTable);
    }

    public static DiscreteRandomValueGenerator get(DistributionTable distributionTable) {
        return new DiscreteRandomValueGenerator(distributionTable);
    }

    public DiscreteValue getNext() {
        double r = Math.random();
        Set<DiscreteValue> sortedValues = distributionFunction.getDistributionTable().sortedValues();

        for (DiscreteValue val : sortedValues) {
            if (r <= distributionFunction.eval(val)) {
                return val;
            }
        }
        throw new RuntimeException("Distribution function must be evaluated in 0..1 for all discrete getMeasurements.");
    }

}
