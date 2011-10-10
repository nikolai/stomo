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
public class DistributionFunction {
    private SortedMap<DiscreteValue, Probability> distributionFunction;
    private final DistributionTable distributionTable;

    private DistributionFunction(DistributionTable distributionTable) {
        checkDT(distributionTable);
        this.distributionTable = distributionTable;
        safeDistributionFunctionCalc();
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

    public static DistributionFunction createByTable(DistributionTable distributionTable) {
        return new DistributionFunction(distributionTable);
    }

    private void safeDistributionFunctionCalc() {
        if (distributionFunction == null) {
            distributionFunction = new TreeMap<DiscreteValue, Probability>();
            distributionFunction.put(distributionTable.getDiscreteValueInRow(0), distributionTable.getProbabilityInRow(0));

            Probability previousDistrFuncEvaluation = distributionFunction.get(distributionFunction.firstKey());
            for (int i = 1; i < distributionTable.size(); i++) {
                Probability sum = previousDistrFuncEvaluation.plus(distributionTable.getProbabilityInRow(i));
                distributionFunction.put(distributionTable.getDiscreteValueInRow(i), sum);
                previousDistrFuncEvaluation = sum;
            }
        }
    }

    public double eval(DiscreteValue discreteValue) {
        safeDistributionFunctionCalc();

        if (distributionFunction.get(discreteValue) == null && distributionFunction.firstKey().compareTo(discreteValue) > 0) {
            return 0;
        }
        return distributionFunction.get(discreteValue).getValue();
    }

    public DistributionTable getDistributionTable() {
        return distributionTable;
    }
}
