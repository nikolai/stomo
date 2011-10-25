package com.sm;

/**
 * User: smirnov-n
 * Date: 25.10.11
 * Time: 20:42
 */
public class SequenceDistributionFunctionBuilder {
    private final DistributionFunction<Integer> total;

    public SequenceDistributionFunctionBuilder(CompatibleDistributionFunctions<Integer> cdf) {
        total = mul(cdf.getDistributionFunctions()[0], cdf.getDistributionFunctions(), 1);
    }

    private DistributionFunction<Integer> mul(DistributionFunction<Integer> first, DistributionFunction<Integer>[] dfs, int i) {
        if (i >= dfs.length) {
            return first;
        }
        DistributionFunction<Integer> another = dfs[i];
        DistributionTable<Integer> result = new DistributionTable<Integer>();
        for (DiscreteValue<Integer> dvFirst : first.getDistributionTable().sortedValues()) {
            for (DiscreteValue<Integer> dvAnother : another.getDistributionTable().sortedValues()) {
                int val = dvFirst.getValue() + dvAnother.getValue();
                double p = first.getDistributionTable().getProbability(dvFirst).getValue() *
                           another.getDistributionTable().getProbability(dvAnother).getValue();
                putSafe(result, new DiscreteValue(val), new Probability(p));
            }
        }
        return mul(DistributionFunction.createByTable(result), dfs, ++i);
    }

    private static void putSafe(DistributionTable dt, DiscreteValue dv, Probability p) {
        Probability oldP = dt.getProbability(dv);
        if (oldP != null) {
            p = new Probability(p.getValue() + oldP.getValue());
        }
        dt.put(dv, p);
    }

    public DistributionFunction<Integer> build() {
        return total;
    }


}
