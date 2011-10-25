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
            sb.append(entry.getKey() + separator + entry.getValue() + "\n");
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
}
