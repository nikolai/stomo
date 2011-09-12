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
    private final SortedMap<DiscreteValue, Probability> distributionFunction = new TreeMap<DiscreteValue, Probability>();
    private final DistributionTable distributionTable;

    public DistributionFunction(DistributionTable distributionTable) {
        this.distributionTable = distributionTable;

        distributionFunction.put(distributionTable.getDiscreteValueInRow(0), distributionTable.getProbabilityInRow(0));

        Probability previousDistrFuncEvaluation = distributionFunction.get(distributionFunction.firstKey());
        for (int i = 1; i < distributionTable.size(); i++) {
            Probability sum = previousDistrFuncEvaluation.plus(distributionTable.getProbabilityInRow(i));
            distributionFunction.put(distributionTable.getDiscreteValueInRow(i), sum);
            previousDistrFuncEvaluation = sum;
        }
    }
    
    public DistributionFunction(Experiment experiment) {
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

    public DistributionFunction(CompatibleExperiments compatibleExperiments) {

        // calc count
        Map<DiscreteValue, Integer> count = new TreeMap();

        int numOfMeasurements = compatibleExperiments.getSizeOfExperiments();
        for (int i = 0; i < numOfMeasurements; i++) {

            DiscreteValue[] eventsData = compatibleExperiments.getExperimentsData(i);
            DiscreteValue max = SMMath.max(eventsData);
            Integer number = count.get(max);

            if (number == null) {
                number = 0;
            }

            count.put(max, ++number);
        }

        // calc distribution
        this.distributionTable = new DistributionTable();
        for (DiscreteValue key : count.keySet()) {
            Probability p = new Probability(count.get(key) / (double) compatibleExperiments.getSizeOfExperiments());
            this.distributionTable.put(key, p);
        }
    }

    public double eval(DiscreteValue discreteValue) {
        return distributionFunction.get(discreteValue).getValue();
    }

    public DistributionTable getDistributionTable() {
        return distributionTable;
    }
}
