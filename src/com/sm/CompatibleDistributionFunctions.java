package com.sm;

import java.util.Set;
import java.util.TreeSet;

/**
 * User: smirnov-n
 * Date: 13.09.11
 * Time: 13:25
 */
public class CompatibleDistributionFunctions {
    private final Set<DiscreteValue> discreteValueSet;
    private final DistributionFunction[] compatibleDistributionFunctions;

    public CompatibleDistributionFunctions(DistributionFunction... compatibleDistributionFunctions) {
        this.compatibleDistributionFunctions = compatibleDistributionFunctions;
        assert compatibleDistributionFunctions != null && compatibleDistributionFunctions.length > 1;

        // prepare discrete values set
        discreteValueSet = new TreeSet<DiscreteValue>();
        for (DistributionFunction df : compatibleDistributionFunctions) {
            discreteValueSet.addAll(df.getDistributionTable().sortedValues());
        }
    }

    public Set<DiscreteValue> getDiscreteValueSet() {
        return discreteValueSet;
    }

    public DistributionFunction[] getDistributionFunctions() {
        return compatibleDistributionFunctions;
    }

    public int getSize() {
        return compatibleDistributionFunctions.length;
    }
}
