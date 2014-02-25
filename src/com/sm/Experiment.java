package com.sm;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * User: smirnov-n
 * Date: 08.09.11
 * Time: 20:23
 */
public class Experiment implements IExperiment {
    private final List<DiscreteValue> measurements = new LinkedList<DiscreteValue>();
    private DiscreteValue[] measurementsArray;
    private final DiscreteRandomValueGenerator generator;

    public Experiment(DiscreteRandomValueGenerator generator) {
        this.generator = generator;
    }

    private void addMeasurement(DiscreteValue value) {
        measurementsArray = null;
        measurements.add(value);
    }

    public DiscreteValue[] getMeasurements() {
        if (measurementsArray == null) {
            measurementsArray = measurements.toArray(new DiscreteValue[0]);
        }
        return measurementsArray;
    }

    public void setMeasurements(DiscreteValue[] measurementsArray) {
        this.measurementsArray = measurementsArray;
    }

    public int getSize() {
        return getMeasurements().length;
    }

    public void run(int count) {
        assert count >= 0;
        for (int i = 0; i < count; i++) {
            runOnce();
        }
    }

    public void runOnce() {
        addMeasurement(generator.getNext());
    }

    public DiscreteRandomValueGenerator getGenerator() {
        return generator;
    }

    public DistributionFunction getDF() {
        DiscreteValue[] vals = getMeasurements();
        // calc count
        Map<DiscreteValue, Integer> count = new TreeMap();
        for (DiscreteValue val : vals) {
            Integer number = count.get(val);
            number = number != null ? number+1 : 1;
            count.put(val, number);
        }
        // calc distribution
        return DistributionFunction.createByTable(ModellingDF.constructDT(count, getSize()));
    }


}
