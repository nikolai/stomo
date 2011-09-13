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
        this.distributionTable = distributionTable;
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
    
    private DistributionFunction(Experiment experiment) {
        DiscreteValue[] vals = experiment.getMeasurements();
        // calc count
        Map<DiscreteValue, Integer> count = new TreeMap();
        for (DiscreteValue val : vals) {
            if (count.containsKey(val)) {
                int number = count.get(val);
                count.put(val, ++number);
            } else {
                count.put(val, 1);
            }
        }

        // calc distribution
        this.distributionTable = new DistributionTable();
        for (DiscreteValue key : count.keySet()) {
            Probability p = new Probability(count.get(key) / (double) experiment.getSize());
            this.distributionTable.put(key, p);
        }
    }
    public static DistributionFunction createByExperiment(Experiment experiment) {
        return new DistributionFunction(experiment);
    }

    private DistributionFunction(CompatibleExperiments compatibleExperiments) {

        // calc count
        Map<DiscreteValue, Integer> count = new TreeMap();

        int numOfMeasurements = compatibleExperiments.getSizeOfExperiments();
        for (int i = 0; i < numOfMeasurements; i++) {

            DiscreteValue[] eventsData = compatibleExperiments.getExperimentsData(i);
            DiscreteValue max = SMMath.max(eventsData);
            Integer number = count.get(max);

            number = number != null ? number+1 : 1;
            count.put(max, number);
        }

        // calc distribution
        this.distributionTable = new DistributionTable();
        for (DiscreteValue key : count.keySet()) {
            Probability p = new Probability(count.get(key) / (double) compatibleExperiments.getSizeOfExperiments());
            this.distributionTable.put(key, p);
        }
    }
    public static DistributionFunction createByCompatibleExperiments(CompatibleExperiments compatibleExperiments) {
        return new DistributionFunction(compatibleExperiments);
    }

    /**
     * Analytical distribution function of several compatibles (AND-parallelism)
     * */
    private DistributionFunction(CompatibleDistributionFunctions compatibleDistributionFunctions) {
        // calc distribution
        this.distributionTable = new DistributionTable();

        double previousEvaluationOfDF = 0;
        for (DiscreteValue discreteVal : compatibleDistributionFunctions.getDiscreteValueSet()) {
            double multiplication = 1;
            for (DistributionFunction df : compatibleDistributionFunctions.getDistributionFunctions()) {
                multiplication *= df.eval(discreteVal);
            }

            Probability p = new Probability(multiplication - previousEvaluationOfDF);
            this.distributionTable.put(discreteVal, p);

            previousEvaluationOfDF = multiplication;
        }
    }

    public static DistributionFunction createByCompatibleDistributionFunctions(CompatibleDistributionFunctions cdf) {
        return new DistributionFunction(cdf);
    }


    public double eval(DiscreteValue discreteValue) {
        safeDistributionFunctionCalc();
        return distributionFunction.get(discreteValue).getValue();
    }

    public DistributionTable getDistributionTable() {
        return distributionTable;
    }
}
