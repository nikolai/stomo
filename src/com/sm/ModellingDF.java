package com.sm;

import com.sm.math.SMMath;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * User: smirnov-n
 * Date: 19.09.11
 * Time: 16:08
 */
public class ModellingDF {
    private ModellingDF(){}
    public static ModellingDF get() {
        return new ModellingDF();
    }

    public DistributionFunction createSingle(Experiment experiment) {
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
        DistributionTable distributionTable = new DistributionTable();
        for (DiscreteValue key : count.keySet()) {
            Probability p = new Probability(count.get(key) / (double) experiment.getSize());
            distributionTable.put(key, p);
        }
        return DistributionFunction.createByTable(distributionTable);
    }

    public DistributionFunction createAndParallelism(CompatibleExperiments compatibleExperiments) {
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
        DistributionTable distributionTable = new DistributionTable();
        for (DiscreteValue key : count.keySet()) {
            Probability p = new Probability(count.get(key) / (double) compatibleExperiments.getSizeOfExperiments());
            distributionTable.put(key, p);
        }

        return DistributionFunction.createByTable(distributionTable);
    }

    public DistributionFunction createOrParallelism(CompatibleExperiments compatibleExperiments) {
        // calc count
        Map<DiscreteValue, Integer> count = new TreeMap();

        int numOfMeasurements = compatibleExperiments.getSizeOfExperiments();
        for (int i = 0; i < numOfMeasurements; i++) {

            DiscreteValue[] eventsData = compatibleExperiments.getExperimentsData(i);
            DiscreteValue min = SMMath.min(eventsData);
            Integer number = count.get(min);

            number = number != null ? number+1 : 1;
            count.put(min, number);
        }

        // calc distribution
        DistributionTable distributionTable = new DistributionTable();
        for (DiscreteValue key : count.keySet()) {
            Probability p = new Probability(count.get(key) / (double) compatibleExperiments.getSizeOfExperiments());
            distributionTable.put(key, p);
        }

        return DistributionFunction.createByTable(distributionTable);
    }
}
