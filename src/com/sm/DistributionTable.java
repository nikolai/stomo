package com.sm;

import java.util.*;

/**
 * User: smirnov-n
 * Date: 08.09.11
 * Time: 16:51
 */
public class DistributionTable<T extends Comparable> {
    private SortedMap<DiscreteValue<T>, Probability> table = new TreeMap<DiscreteValue<T>, Probability>();
    private Map.Entry<DiscreteValue<T>, Probability>[] cachedEntries;

    public DistributionTable() {
    }

    public DistributionTable(int[] discreteValues, double[] probabilities) {
        if (discreteValues.length != probabilities.length) {
            throw new IllegalArgumentException("invalid length");
        }
        for (int i=0; i < discreteValues.length; i++) {
            put(new IntDiscreteValue(discreteValues[i]), new Probability(probabilities[i]) );
        }
        validate();
    }

    public Set<DiscreteValue<T>> sortedValues() {
        return table.keySet();
    }

    private void safeInitCache() {
        if (cachedEntries == null) {
            cachedEntries = table.entrySet().toArray(new Map.Entry[0]);
        }
    }

    public DiscreteValue<T> getDiscreteValueInRow(int rowNum) {
        safeInitCache();
        return cachedEntries[rowNum].getKey();
    }

    public Probability getProbabilityInRow(int rowNum) {
        safeInitCache();
        return cachedEntries[rowNum].getValue();
    }

    public Probability getProbability(DiscreteValue dv) {
        return table.get(dv);
    }

    private static String separator = "\t|\t";
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Val" + separator + "Probability\n");
        for (Map.Entry<DiscreteValue<T>, Probability> entry : table.entrySet()) {
            if (entry.getValue().getValue() != 0) {
                sb.append(entry.getKey() + separator + entry.getValue() + "\n");
            }
        }

        return sb.toString();
    }

    public void put(DiscreteValue key, Probability p) {
        cachedEntries = null;
        table.put(key, p);
    }

    public int size() {
        safeInitCache();
        return cachedEntries.length;
    }

    public void validate() {
        double sum = 0;
        for (int i = 0; i < size(); i++) {
            sum += getProbabilityInRow(i).getValue();
        }
        double error = 0.0000000001;
        if (Math.abs(sum - 1) > error) {
            throw new IllegalArgumentException("Invalid distribution table!\n" + this);
        }
    }
}
