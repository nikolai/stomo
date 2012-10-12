package com.sm;

import com.sm.math.SMMath;
import com.sm.profiling.AccumulatingProfiler;
import com.sm.profiling.SimpleProfiler;

import java.util.*;

/**
 * User: smirnov-n
 * Date: 08.09.11
 * Time: 16:55
 */
public class DistributionFunction<T extends Comparable> {
    private final SortedMap<DiscreteValue<T>, Probability> distributionFunction;
    private final DistributionTable distributionTable;

    private DistributionFunction(DistributionTable distributionTable) {
        distributionTable.validate();
        this.distributionTable = distributionTable;

        // calc distribution function
        distributionFunction = new TreeMap<DiscreteValue<T>, Probability>();
        distributionFunction.put(distributionTable.getDiscreteValueInRow(0), distributionTable.getProbabilityInRow(0));

        Probability previousDistrFuncEvaluation = distributionFunction.get(distributionFunction.firstKey());
        for (int i = 1; i < distributionTable.size(); i++) {
            Probability sum = previousDistrFuncEvaluation.plus(distributionTable.getProbabilityInRow(i));
            distributionFunction.put(distributionTable.getDiscreteValueInRow(i), sum);
            previousDistrFuncEvaluation = sum;
        }
    }

    public static <T extends Comparable> DistributionFunction<T> createByTable(DistributionTable<T> distributionTable) {
        distributionTable.validate();
        return new DistributionFunction<T>(distributionTable);
    }

    public double eval(DiscreteValue<T> discreteValue) {
//        safeDistributionFunctionCalc();
        Probability found = distributionFunction.get(discreteValue);

        if (found == null){
            if (distributionFunction.firstKey().compareTo(discreteValue) > 0) {
                return 0;
            }
            if (distributionFunction.lastKey().compareTo(discreteValue) < 0) {
                return 1;
            }
            // find first lower discreteValue. return its probability
            DiscreteValue[] sortedVals = (DiscreteValue[]) distributionTable.sortedValues().toArray(new DiscreteValue[0]);
            int index = sortedVals.length - 1;
            while (index >= 0) {
                if (sortedVals[index].compareTo(discreteValue) < 0) {
                    return distributionFunction.get(sortedVals[index]).getValue();
                }
                index--;
            }
            throw new IllegalStateException("nothing to do here!");
        } else {
            return found.getValue();
        }
    }

    public DistributionTable<T> getDistributionTable() {
        return distributionTable;
    }
}
