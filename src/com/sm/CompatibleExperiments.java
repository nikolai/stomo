package com.sm;

import sun.reflect.generics.tree.ArrayTypeSignature;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

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

    public Set<DiscreteValue> getDiscreteValueSet() {
        List<DistributionFunction> dfList = new ArrayList<DistributionFunction>(getNumberOfExperiments());
        for (Experiment exp : experiments) {
            dfList.add(ModellingDF.get().createSingle(exp));
        }
        return new CompatibleDistributionFunctions(dfList.toArray(new DistributionFunction[0])).getDiscreteValueSet();
    }

    public CompatibleExperiments run(int count) {
        assert count >= 0;
        for (int i = 0; i < count; i++) {
            for (Experiment exp : experiments) {
                exp.runOnce();
            }
        }
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < getNumberOfExperiments(); i++) {
            sb.append(i + "|" + Arrays.toString(experiments[i].getMeasurements()) + "\n");

        }
        return sb.toString();
    }
}
