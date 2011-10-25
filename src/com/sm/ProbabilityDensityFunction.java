package com.sm;

/**
 * User: smirnov-n
 * Date: 19.09.11
 * Time: 16:55
 */
public class ProbabilityDensityFunction {
    private final DistributionTable dt;

    private ProbabilityDensityFunction(DistributionTable dt) {
        this.dt = dt;
    }

    public static ProbabilityDensityFunction get(DistributionTable dt) {
        return new ProbabilityDensityFunction(dt);
    }
    public static ProbabilityDensityFunction get(DistributionFunction df) {
        return get(df.getDistributionTable());
    }

    public double eval(DiscreteValue dv) {
        Probability p = dt.getProbability(dv);
        if (p == null) {
//            throw new IllegalArgumentException("Probability Density Function is not defined in " + dv);
            return 0;
        }
        return p.getValue();
    }

    public DistributionTable getDistributionTable() {
        return dt;
    }
}
