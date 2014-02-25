package com.sm;

import java.util.*;

/**
 * User: smirnov-n
 * Date: 09.09.11
 * Time: 19:00
 */
public class CompatibleExperiments {
    private final Experiment[] experiments;

    public CompatibleExperiments(Experiment... experiments) {
        this.experiments = experiments;
        assert experiments.length > 0;

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

    public CompatibleExperiments run(int count) {
        assert count >= 0;
        for (Experiment exp : experiments) {
            MeasurementsRepository.instance.safeRun(exp, count);
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

    public Experiment getExperiment(int i) {
        return experiments[i];
    }

    private static class MeasurementsRepository {
        private static MeasurementsRepository instance = new MeasurementsRepository();
        private Map<DiscreteRandomValueGenerator, DiscreteValue[]> repo
                = new HashMap<DiscreteRandomValueGenerator, DiscreteValue[]>();
        private MeasurementsRepository(){}

        public boolean safeRun(Experiment exp, int count) {
            DiscreteRandomValueGenerator g = exp.getGenerator();
            // if already run - return measurements
            if (repo.containsKey(g)) {
                DiscreteValue[] measurements = repo.get(g);
                if (measurements.length == count) {
                    exp.setMeasurements(measurements);
                    return true;
                }
            }

            // else - run and save measurements
            exp.run(count);
            repo.put(g, exp.getMeasurements().clone());
            return false;
        }


    }
}
