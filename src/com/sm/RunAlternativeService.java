package com.sm;

import com.sm.util.CoinsUtil;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * User: mikola
 * Date: 24.10.12
 * Time: 16:59
 */
public class RunAlternativeService {

    private RunAlternativeService() {
    }
    public static RunAlternativeService getOne() {
        return new RunAlternativeService();
    }

    public IExperiment run(AlternativeExperiment... alternativeExperiments){
        assert alternativeExperiments != null && alternativeExperiments.length >= 2;

        Map<Probability, AlternativeExperiment> allProbabilities = new TreeMap<Probability, AlternativeExperiment>();
        for(AlternativeExperiment ae : alternativeExperiments) {
            allProbabilities.put(ae.getProbability(), ae);
        }

        CoinsUtil.checkFullProbability(allProbabilities.keySet());

        // run with the correspondence with Probability
        ManualExperiment me = new ManualExperiment();

        int runCount = 0;
        CoinsUtil<AlternativeExperiment> coinsUtil = new CoinsUtil(
                allProbabilities.values().toArray(),
                allProbabilities.keySet().toArray(new Probability[0]));

        while (runCount < RunExperimentService.STD_RUN_COUNT) {
            runCount++;
            AlternativeExperiment exp = coinsUtil.lookNext();
            me.addMesurement(exp.getGenerator().getNext());
        }

        return me;
    }

    private class ManualExperiment implements IExperiment {
        private final List<DiscreteValue> measurements = new LinkedList<DiscreteValue>();


        public DiscreteValue[] getMeasurements() {
            return measurements.toArray(new DiscreteValue[0]);
        }

        public int getSize() {
            return measurements.size();
        }

        public void addMesurement(DiscreteValue dv) {
            measurements.add(dv);
        }
    }

}
