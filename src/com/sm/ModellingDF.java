package com.sm;

import com.sm.logging.LogService;
import com.sm.math.SMMath;

import java.util.Map;
import java.util.TreeMap;

/**
 * User: smirnov-n
 * Date: 19.09.11
 * Time: 16:08
 */
public class ModellingDF implements DFCreator{
    public static final int STD_RUN_COUNT = Integer.parseInt(System.getProperty("montecarlo.run.count", "300000"));

    static {
        LogService.get().config(("montecarlo.run.count = " + STD_RUN_COUNT));
    }

    private final RunExperimentService runService = RunExperimentService.get();

    private ModellingDF(){}
    public static ModellingDF get() { return new ModellingDF(); }

    public static DistributionTable constructDT(Map<DiscreteValue, Integer> countTable, int experimentCount) {
        DistributionTable distributionTable = new DistributionTable();
        for (DiscreteValue key : countTable.keySet()) {
            Probability p = new Probability(countTable.get(key) / (double) experimentCount);
            distributionTable.put(key, p);
        }
        return distributionTable;
    }

//    public DistributionFunction createAND(CompatibleExperiments compatibleExperiments) {
//        return template(compatibleExperiments, new SelectionAlgo(){
//            public DiscreteValue getAlgoValue(DiscreteValue[] eventsData) {
//                return SMMath.max(eventsData);
//            }
//        });
//    }

    private static DistributionFunction createSingle(IExperiment experiment) {
        if (experiment == null || experiment.getSize() <= 0) {
            throw new IllegalArgumentException("empty experiment data");
        }
        DiscreteValue[] vals = experiment.getMeasurements();
        // calc count
        Map<DiscreteValue, Integer> count = new TreeMap();
        for (DiscreteValue val : vals) {
            Integer number = count.get(val);
            number = number != null ? number + 1 : 1;
            count.put(val, number);
        }
        // calc distribution
        return DistributionFunction.createByTable(constructDT(count, experiment.getSize()));
    }

    public DistributionFunction createAlternative(CompatibleDistributionFunctions cdf, Probability[] probs) {
        int index = 0;
        AlternativeExperiment[] experiments = new AlternativeExperiment[probs.length];
        for (DistributionFunction df : cdf.getDistributionFunctions()) {
            DiscreteRandomValueGenerator generator = new DiscreteRandomValueGenerator(df.getDistributionTable());
            experiments[index] = new AlternativeExperiment(generator, probs[index++]);
        }
        IExperiment runExperiment = RunAlternativeService.getOne().run((AlternativeExperiment[]) experiments);
        return createSingle(runExperiment);
    }

    public DistributionFunction createAND(CompatibleDistributionFunctions<Integer> cdf) {
        return template(cdf, new SelectionAlgo(){
            public DiscreteValue getAlgoValue(DiscreteValue[] eventsData) {
                return SMMath.max(eventsData);
            }
        });
    }

    public DistributionFunction createOR(CompatibleDistributionFunctions<Integer> cdf) {
        return template(cdf, new SelectionAlgo(){
            public DiscreteValue getAlgoValue(DiscreteValue[] eventsData) {
                return SMMath.min(eventsData);
            }
        });
    }

    public DistributionFunction createMN(CompatibleDistributionFunctions cdf, final int M) {
        return template(cdf, new SelectionAlgo(){
            public DiscreteValue getAlgoValue(DiscreteValue[] eventsData) {
                return SMMath.min(M, eventsData);
            }
        });
    }
//
//    private DistributionFunction template(CompatibleExperiments compatibleExperiments, SelectionAlgo algo) {
//        // calc count
//        Map<DiscreteValue, Integer> count = new TreeMap();
//
//        int numOfMeasurements = compatibleExperiments.getSizeOfExperiments();
//        for (int i = 0; i < numOfMeasurements; i++) {
//
//            DiscreteValue[] eventsData = compatibleExperiments.getExperimentsData(i);
//            DiscreteValue algoValue = algo.getAlgoValue(eventsData);
//            Integer number = count.get(algoValue);
//
//            number = number != null ? number+1 : 1;
//            count.put(algoValue, number);
//        }
//
//        // calc distribution
//        return DistributionFunction.createByTable(constructDT(count, numOfMeasurements));
//    }

    public DistributionFunction createSequenceProcessing(CompatibleDistributionFunctions<Integer> cdf) {
        return template(cdf, new SelectionAlgo() {
            public DiscreteValue getAlgoValue(DiscreteValue[] eventsData) {
                return SMMath.sum(eventsData);
            }
        });
    }

    private DistributionFunction template(CompatibleDistributionFunctions cdf, SelectionAlgo algo) {
        int index = 0;
        Experiment[] experiments = new Experiment[cdf.getSize()];
        for (DistributionFunction df : cdf.getDistributionFunctions()) {
            DiscreteRandomValueGenerator generator = new DiscreteRandomValueGenerator(df.getDistributionTable());
            experiments[index] = new Experiment(generator);
            index++;
        }

        CompatibleExperiments compatibleExperiments = new CompatibleExperiments(experiments).run(STD_RUN_COUNT);
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
