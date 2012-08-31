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
        if (discreteValues.length != probabilities.length) {
            throw new IllegalArgumentException("invalid length");
        }
        DistributionTable dt = new DistributionTable();
        for (int i=0; i < discreteValues.length; i++) {
            dt.put(new IntDiscreteValue(discreteValues[i]), new Probability(probabilities[i]) );
        }
        return get(dt);
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

    public DistributionFunction getDistributionFunction() {
        return distributionFunction;
    }
}
