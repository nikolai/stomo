package com.sm;

import com.sm.math.SMMath;

import java.util.Map;
import java.util.TreeMap;

/**
 * User: smirnov-n
 * Date: 19.09.11
 * Time: 16:08
 */
public class ModellingDF {
    private ModellingDF(){}
    public static ModellingDF get() { return new ModellingDF(); }

    public DistributionFunction createSingle(IExperiment experiment) {
        if (experiment == null || experiment.getSize() <= 0) {
            throw new IllegalArgumentException("empty experiment data");
        }
        DiscreteValue[] vals = experiment.getMeasurements();
        // calc count
        Map<DiscreteValue, Integer> count = new TreeMap();
        for (DiscreteValue val : vals) {
            Integer number = count.get(val);
            number = number != null ? number+1 : 1;
            count.put(val, number);
        }
        // calc distribution
        return DistributionFunction.createByTable(constructDT(count, experiment.getSize()));
    }

    public DistributionFunction createAndParallelism(CompatibleExperiments compatibleExperiments) {
        return template(compatibleExperiments, new SelectionAlgo(){
            public DiscreteValue getAlgoValue(DiscreteValue[] eventsData) {
                return SMMath.max(eventsData);
            }
        });
    }

    public DistributionFunction createOrParallelism(CompatibleExperiments compatibleExperiments) {
        return template(compatibleExperiments, new SelectionAlgo(){
            public DiscreteValue getAlgoValue(DiscreteValue[] eventsData) {
                return SMMath.min(eventsData);
            }
        });
    }

    public DistributionFunction createMNParallelism(CompatibleExperiments compatibleExperiments, final int M) {
        return template(compatibleExperiments, new SelectionAlgo(){
            public DiscreteValue getAlgoValue(DiscreteValue[] eventsData) {
                return SMMath.min(M, eventsData);
            }
        });
    }


    public DistributionFunction createSequenceProcessing(CompatibleExperiments compatibleExperiments) {
        return template(compatibleExperiments, new SelectionAlgo(){
            public DiscreteValue getAlgoValue(DiscreteValue[] eventsData) {
                return SMMath.sum(eventsData);
            }
        });
    }

    private DistributionTable constructDT(Map<DiscreteValue, Integer> countTable, int experimentCount) {
        DistributionTable distributionTable = new DistributionTable();
        for (DiscreteValue key : countTable.keySet()) {
            Probability p = new Probability(countTable.get(key) / (double) experimentCount);
            distributionTable.put(key, p);
        }
        return distributionTable;
    }

    private DistributionFunction template(CompatibleExperiments compatibleExperiments, SelectionAlgo algo) {
        // calc count
        Map<DiscreteValue, Integer> count = new TreeMap();

        int numOfMeasurements = compatibleExperiments.getSizeOfExperiments();
        for (int i = 0; i < numOfMeasurements; i++) {

            DiscreteValue[] eventsData = compatibleExperiments.getExperimentsData(i);
            DiscreteValue algoValue = algo.getAlgoValue(eventsData);
            Integer number = count.get(algoValue);

            number = number != null ? number+1 : 1;
            count.put(algoValue, number);
        }

        // calc distribution
        return DistributionFunction.createByTable(constructDT(count, numOfMeasurements));
    }

    private abstract static class SelectionAlgo {
        public abstract DiscreteValue getAlgoValue(DiscreteValue[] eventsData);
    }
}
