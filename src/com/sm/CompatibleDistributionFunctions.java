package com.sm;

import java.util.Set;
import java.util.TreeSet;

/**
 * User: smirnov-n
 * Date: 13.09.11
 * Time: 13:25
 */
public class CompatibleDistributionFunctions<T extends Comparable> {
    private final Set<DiscreteValue<T>> discreteValueSet;
    private final DistributionFunction[] compatibleDistributionFunctions;

    public CompatibleDistributionFunctions(DistributionFunction... compatibleDistributionFunctions) {
        this.compatibleDistributionFunctions = compatibleDistributionFunctions;
        assert compatibleDistributionFunctions != null && compatibleDistributionFunctions.length > 1;

        // prepare discrete values set
        discreteValueSet = new TreeSet<DiscreteValue<T>>();
        for (DistributionFunction df : compatibleDistributionFunctions) {
            discreteValueSet.addAll(df.getDistributionTable().sortedValues());
        }
    }

    public Set<DiscreteValue<T>> getDiscreteValueSet() {
        return discreteValueSet;
    }

    public DistributionFunction<T>[] getDistributionFunctions() {
        return compatibleDistributionFunctions;
    }

    public int getSize() {
        return compatibleDistributionFunctions.length;
    }
}
