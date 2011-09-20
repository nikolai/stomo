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
//        CompatiblePDF cpdf = new CompatiblePDF(compatibleDistributionFunctions);
//        ProbabilityDensityFunction.get(compatibleDistributionFunctions.getDistributionFunctions())

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


//        double previousEvaluationOfDF = 0;
//        for (DiscreteValue discreteVal : compatibleDistributionFunctions.getDiscreteValueSet()) {
//            double multiplication = 1;
//            for (DistributionFunction df : compatibleDistributionFunctions.getDistributionFunctions()) {
//                multiplication *= (1 - df.eval(discreteVal));
//            }
//
//            Probability p = new Probability(multiplication - previousEvaluationOfDF);
//            distributionTable.put(discreteVal, p);
//
//            previousEvaluationOfDF = multiplication;
//        }

        return DistributionFunction.createByTable(distributionTable);
    }

}
