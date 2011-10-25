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
        checkDT(distributionTable);
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

    public static void checkDT(DistributionTable dt) {
        double sum = 0;
        for (int i = 0; i < dt.size(); i++) {
            sum += dt.getProbabilityInRow(i).getValue();
        }
        double error = 0.0000000001;
        if (Math.abs(sum - 1) > error) {
            throw new IllegalArgumentException("Invalid distribution table!\n" + dt);
        }
    }

    public static <T extends Comparable> DistributionFunction<T> createByTable(DistributionTable<T> distributionTable) {
        return new DistributionFunction<T>(distributionTable);
    }

    public double eval(DiscreteValue<T> discreteValue) {
//        safeDistributionFunctionCalc();

        if (distributionFunction.get(discreteValue) == null){
            if (distributionFunction.firstKey().compareTo(discreteValue) > 0) {
                return 0;
            }
            if (distributionFunction.lastKey().compareTo(discreteValue) < 0) {
                return 1;
            }
        }
        return distributionFunction.get(discreteValue).getValue();
    }

    public DistributionTable<T> getDistributionTable() {
        return distributionTable;
    }
}
