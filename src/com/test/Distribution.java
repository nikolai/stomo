package com.test;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * User: nikolai
 * Date: 26.08.11
 * Time: 22:48
 */
public class Distribution {
    private Map<Integer, Double> distribution;

    public Distribution(int[] vals1, int[] vals2) {
        assert vals1.length == vals2.length;


        // calc count
        Map<Integer, Integer> count = new TreeMap();
        for (int i = 0; i < vals1.length; i++) {
            int max = Math.max(vals1[i], vals2[i]);
            if (count.containsKey(max)) {
                int number = count.get(max);
                count.put(max, ++number);
            } else {
                count.put(max, 1);
            }
        }

        // calc distribution
        distribution = new HashMap(count);
        for (Integer key : count.keySet()) {
            double p = count.get(key) / (double) vals1.length;
            distribution.put(key, p);
        }
    }

    public Distribution(int[] vals) {
        // calc count
        Map<Integer, Integer> count = new TreeMap();
        for (int val : vals) {
            if (count.containsKey(val)) {
                int number = count.get(val);
                count.put(val, ++number);
            } else {
                count.put(val, 1);
            }
        }

        // calc distribution
        distribution = new HashMap(count);
        for (Integer key : count.keySet()) {
            double p = count.get(key) / (double) vals.length;
            distribution.put(key, p);
        }
    }

    public String toString() {
        return distribution.toString();
    }

    public Map<Integer, Double> getDistribution() {
        return new HashMap(distribution);
    }
}
