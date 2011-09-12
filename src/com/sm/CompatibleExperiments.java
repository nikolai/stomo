package com.sm;

import java.util.ArrayList;
import java.util.List;

/**
 * User: smirnov-n
 * Date: 09.09.11
 * Time: 19:00
 */
public class CompatibleExperiments {
    private final Experiment[] experiments;

    public CompatibleExperiments(Experiment... experiments) {
        this.experiments = experiments;
        assert experiments.length > 1;

        // assert that experiments have equal sizes
        int trackSize = experiments[0].getSize();
        for (int i=1; i < experiments.length; i++) {
            if (trackSize != experiments[i].getSize()) {
                throw new RuntimeException("All experiments should be of the same size");
            }
        }
    }

    public int getNumberOfExperiments() {
        return experiments.length;
    }

    public int getSizeOfExperiments() {
        return experiments[0].getSize();
    }

    public DiscreteValue[] getExperimentsData(int measurementIndex) {
        List<DiscreteValue> eventsData = new ArrayList<DiscreteValue>(getNumberOfExperiments());

        for (Experiment exp : experiments) {
            eventsData.add(exp.getMeasurements()[measurementIndex]);
        }
        return eventsData.toArray(new DiscreteValue[0]);
    }
}
