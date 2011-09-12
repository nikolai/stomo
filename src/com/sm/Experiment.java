package com.sm;

import java.util.LinkedList;
import java.util.List;

/**
 * User: smirnov-n
 * Date: 08.09.11
 * Time: 20:23
 */
public class Experiment {
    private final List<DiscreteValue> measurements = new LinkedList<DiscreteValue>();
    private DiscreteValue[] measurementsArray;

    public void addMeasurement(DiscreteValue value) {
        measurementsArray = null;
        measurements.add(value);
    }

    public DiscreteValue[] getMeasurements() {
        if (measurementsArray == null) {
            measurementsArray = measurements.toArray(new DiscreteValue[0]);
        }
        return measurementsArray;
    }

    public int getSize() {
        return measurements.size();
    }
}
