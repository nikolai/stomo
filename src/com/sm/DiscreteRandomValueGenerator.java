package com.sm;

import java.util.Map;
import java.util.Set;
import java.util.prefs.PreferenceChangeListener;

/**
 * User: smirnov-n
 * Date: 08.09.11
 * Time: 16:49
 */
public class DiscreteRandomValueGenerator {
    private final DistributionFunction distributionFunction;

    protected DiscreteRandomValueGenerator(DistributionTable distributionTable){
        distributionFunction = DistributionFunction.createByTable(distributionTable);
    }

    public static DiscreteRandomValueGenerator get(DistributionTable distributionTable) {
        return new DiscreteRandomValueGenerator(distributionTable);
    }

    public static DiscreteRandomValueGenerator get(int[] discreteValues, double[] probabilities) {
        return get(new DistributionTable(discreteValues, probabilities));
    }

    public DiscreteValue getNext() {
        double r = Math.random();
        Set<DiscreteValue> sortedValues = distributionFunction.getDistributionTable().sortedValues();

        for (DiscreteValue val : sortedValues) {
            if (r <= distributionFunction.eval(val)) {
                return val;
            }
        }
        throw new RuntimeException("Distribution function must be evaluated in 0..1 for all discrete values: " + r);

    }

    public DistributionFunction getDistributionFunction() {
        return distributionFunction;
    }
}
