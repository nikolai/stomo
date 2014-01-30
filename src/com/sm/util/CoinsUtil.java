package com.sm.util;

import com.sm.DiscreteValue;
import com.sm.Probability;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * User: mikola
 * Date: 24.10.12
 * Time: 18:45
 */
public class CoinsUtil<T> {
    private final List<T> pool;

    public CoinsUtil(T[] dvs, Probability[] ps) {
        assert dvs.length == ps.length;
        checkFullProbability(Arrays.asList(ps));
        pool = new ArrayList<T>(dvs.length);

        // fill pool
        for(int i=0; i< ps.length; i++) {
            double p = ps[i].getValue();
            int count = (int) Math.round(p*100);
            poolAdd(count, dvs[i]);
        }

        assert pool.size() == 100;
    }

    private void poolAdd(int count, T dv) {
        while (count-- > 0) {
            pool.add(dv);
        }
    }

    public static void checkFullProbability(Iterable<Probability> probs){
        double sum = 0;
        for (Probability p : probs) {
            sum += p.getValue();
        }
        if (sum != 1) {
            throw new IllegalArgumentException("Full probability is not equal to 1!");
        }
    }

    public T lookNext() {
        double rand = Math.random();
        int index = (int) Math.round(rand * 100);
        return pool.get(index == 0 ? 0 : index-1);
    }
}
