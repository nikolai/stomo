package com.sm;

/**
 * User: smirnov-n
 * Date: 19.09.11
 * Time: 16:02
 */
public class AnalyticalDF {

    private AnalyticalDF(){}
    public static AnalyticalDF get() {
        return new AnalyticalDF();
    }

    /**
     * Analytical distribution function of several compatibles (AND-parallelism)
     * */
    public DistributionFunction createAND(CompatibleDistributionFunctions compatibleDistributionFunctions) {
        // calc distribution
        DistributionTable distributionTable = new DistributionTable();

        double previousEvaluationOfDF = 0;
        for (DiscreteValue discreteVal : compatibleDistributionFunctions.getDiscreteValueSet()) {
            double multiplication = 1;
            for (DistributionFunction df : compatibleDistributionFunctions.getDistributionFunctions()) {
                multiplication *= df.eval(discreteVal);
            }

            Probability p = new Probability(multiplication - previousEvaluationOfDF);
            distributionTable.put(discreteVal, p);

            previousEvaluationOfDF = multiplication;
        }

        return DistributionFunction.createByTable(distributionTable);
    }

    /**
     * Analytical distribution function of several compatibles (OR-parallelism)
     * */
    public DistributionFunction createOR(CompatibleDistributionFunctions compatibleDistributionFunctions) {
        // calc distribution
        DistributionTable distributionTable = new DistributionTable();
        DiscreteValue[] discreteValues = compatibleDistributionFunctions.getDiscreteValueSet().toArray(new DiscreteValue[0]);

        for (int i=0; i < discreteValues.length; i++){
            double mul1 = 1;
            double mul2 = 1;
            for (int j=0; j < compatibleDistributionFunctions.getSize(); j++) {
                mul1 *= 1-compatibleDistributionFunctions.getDistributionFunctions()[j].eval(i == 0 ? new LowestDiscreteValue() : discreteValues[i-1]);
                mul2 *= 1-compatibleDistributionFunctions.getDistributionFunctions()[j].eval(discreteValues[i]);
            }

            Probability p = new Probability(mul1 - mul2);
            distributionTable.put(discreteValues[i], p);
        }
        return DistributionFunction.createByTable(distributionTable);
    }


    /**
     * Analytical distribution function of several compatibles (MN-parallelism)
     * */
    public DistributionFunction createMN(CompatibleDistributionFunctions cdf, int M) {
        int N = cdf.getSize();
        assert M<N && M >1;
        return createMNRecursive(cdf, N, M, N);
    }

    private DistributionFunction createMNRecursive(CompatibleDistributionFunctions cdf, int M, int IND, int N) {
        if (M == 1) {
            return createOR(cdf);
        } else if (M == N) {
            return createAND(cdf);
        }
        DistributionFunction dfChild1 = createMNRecursive(cdf, M-1, IND-1, N-1);
        DistributionFunction dfChild2 = createMNRecursive(cdf, M, IND-1, N-1);


        DiscreteValue[] discreteValues = cdf.getDiscreteValueSet().toArray(new DiscreteValue[0]);
        DistributionFunctionByValueBuilder dfBuilder = new DistributionFunctionByValueBuilder();

        for (int i=0; i < discreteValues.length; i++){
            DiscreteValue dv = discreteValues[i];

            double Find = cdf.getDistributionFunctions()[IND].eval(dv);
            double gVal = Find * dfChild1.eval(dv) + (1-Find) * dfChild2.eval(dv);
            dfBuilder.add(dv, gVal);
        }

        return dfBuilder.build();
    }

}
