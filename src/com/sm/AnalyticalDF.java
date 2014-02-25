package com.sm;

import java.util.Set;

/**
 * User: smirnov-n
 * Date: 19.09.11
 * Time: 16:02
 */
public class AnalyticalDF implements DFCreator {

    private AnalyticalDF(){}
    public static AnalyticalDF get() {
        return new AnalyticalDF();
    }

    /**
     * Analytical distribution function of several compatibles (AND-parallelism)
     * */
    public DistributionFunction createAND(CompatibleDistributionFunctions<Integer> compatibleDistributionFunctions) {
        return createAND(compatibleDistributionFunctions, compatibleDistributionFunctions.getSize());
    }

    private DistributionFunction createAND(CompatibleDistributionFunctions<Integer> cdf, int dfProcessedCount) {
        assert dfProcessedCount <= cdf.getSize();
        // calc distribution
        DistributionTable distributionTable = new DistributionTable();

        double previousEvaluationOfDF = 0;
        for (DiscreteValue discreteVal : cdf.getDiscreteValueSet()) {
            double multiplication = 1;
            for (int i=0; i < dfProcessedCount; i++) {
                multiplication *= cdf.getDistributionFunctions()[i].eval(discreteVal);
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
    public DistributionFunction createOR(CompatibleDistributionFunctions<Integer> compatibleDistributionFunctions) {
        return createOR(compatibleDistributionFunctions, compatibleDistributionFunctions.getSize());
    }
    private DistributionFunction createOR(CompatibleDistributionFunctions<Integer> cdf, int dfProcessedCount) {
        assert dfProcessedCount <= cdf.getSize();

        // calc distribution
        DistributionTable distributionTable = new DistributionTable();
        DiscreteValue[] discreteValues = cdf.getDiscreteValueSet().toArray(new DiscreteValue[0]);

        for (int i=0; i < discreteValues.length; i++){
            double mul1 = 1;
            double mul2 = 1;
            for (int j=0; j < dfProcessedCount; j++) {
                mul1 *= 1-cdf.getDistributionFunctions()[j].eval(i == 0 ? new LowestDiscreteValue() : discreteValues[i-1]);
                mul2 *= 1-cdf.getDistributionFunctions()[j].eval(discreteValues[i]);
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
        assert M<=N && M>=1 : "Analytical MN function is not supported for M>N && M<1. M=" + M;
        return createMNRecursive(cdf, N, M, N);
    }

    private DistributionFunction createMNRecursive(CompatibleDistributionFunctions<Integer> cdf, int N, int M, int IND) {
        if (M == N) {
            return createAND(cdf, N);
        } else if (M == 1) {
            return createOR(cdf, N);
        }
        DistributionFunction dfChild1 = createMNRecursive(cdf, N-1, M-1, IND-1);
        DistributionFunction dfChild2 = createMNRecursive(cdf, N-1, M, IND-1);


        DiscreteValue[] discreteValues = cdf.getDiscreteValueSet().toArray(new DiscreteValue[0]);
        DistributionFunctionByValueBuilder dfBuilder = new DistributionFunctionByValueBuilder();

        for (int i=0; i < discreteValues.length; i++){
            DiscreteValue dv = discreteValues[i];

            double Find = cdf.getDistributionFunctions()[IND-1].eval(dv);
            double gVal = Find * dfChild1.eval(dv) + (1-Find) * dfChild2.eval(dv);
            dfBuilder.add(dv, gVal);
        }

        return dfBuilder.build();
    }

    public DistributionFunction createAlternative(CompatibleDistributionFunctions cdf, Probability[] probs){
        assert cdf.getSize() == probs.length;
        Set<DiscreteValue> values = cdf.getDiscreteValueSet();
        DistributionFunctionByValueBuilder dfBuilder = new DistributionFunctionByValueBuilder();
        for (DiscreteValue dv : values) {
            double dvAlternativeVal = 0;
            for (int i = 0; i < probs.length; i++) {
                dvAlternativeVal += cdf.getDistributionFunctions()[i].eval(dv) * probs[i].getValue();
            }
            dfBuilder.add(dv, dvAlternativeVal);
        }

        return dfBuilder.build();
    }

//    public DistributionFunction createSequenceProcessing(CompatibleDistributionFunctions cdf) {
//        DistributionTable dt1 = cdf.getDistributionFunctions()[0].getDistributionTable();
//        DistributionTable dt2 = cdf.getDistributionFunctions()[1].getDistributionTable();
//
//        int t01 = ((Integer)dt1.getDiscreteValueInRow(0).getValue()) + ((Integer)dt2.getDiscreteValueInRow(0).getValue());
//        int t02 = ((Integer)dt1.getDiscreteValueInRow(0).getValue()) + ((Integer)dt2.getDiscreteValueInRow(1).getValue());
//        int t11 = ((Integer)dt1.getDiscreteValueInRow(1).getValue()) + ((Integer)dt2.getDiscreteValueInRow(0).getValue());
//        int t12 = ((Integer)dt1.getDiscreteValueInRow(1).getValue()) + ((Integer)dt2.getDiscreteValueInRow(1).getValue());
//
//        double p01 = dt1.getProbabilityInRow(0).getValue() * dt2.getProbabilityInRow(0).getValue();
//        double p02 = dt1.getProbabilityInRow(0).getValue() * dt2.getProbabilityInRow(1).getValue();
//        double p11 = dt1.getProbabilityInRow(1).getValue() * dt2.getProbabilityInRow(0).getValue();
//        double p12 = dt1.getProbabilityInRow(1).getValue() * dt2.getProbabilityInRow(1).getValue();
//
//        DistributionTable dt = new DistributionTable();
//        puttt(dt, new DiscreteValue(t01), new Probability(p01));
//        puttt(dt, new DiscreteValue(t02), new Probability(p02));
//        puttt(dt, new DiscreteValue(t11), new Probability(p11));
//        puttt(dt, new DiscreteValue(t12), new Probability(p12));
//
//        return DistributionFunction.createByTable(dt);

//        ProbabilityDensityFunctionBuilder pdfBuilder = new ProbabilityDensityFunctionBuilder();
//        for (int dfIndex = 0; dfIndex < cdf.getDistributionFunctions().length; dfIndex++) {
//
//            for (DiscreteValue dv : df.getDistributionTable().sortedValues()) {
//
//            }
//        }
//        for (DiscreteValue dv : cdf.getDiscreteValueSet()) {
//            double multiplication = 1;
//            for (DistributionFunction df : cdf.getDistributionFunctions()) {
//                ProbabilityDensityFunction pdf = ProbabilityDensityFunction.get(df);
//                multiplication += ((Integer)dv.getValue()) * pdf.eval(dv);
//            }
//            pdfBuilder.add(dv, multiplication);
//        }
//        return DistributionFunction.createByTable(pdfBuilder.build().getDistributionTable());
//    }

    public DistributionFunction createSequenceProcessing(CompatibleDistributionFunctions<Integer> cdf) {
//        assert cdf.getSize() > 1;
//        DistributionTable<Integer> dtResult = new DistributionTable<Integer>();
//        DistributionFunction<Integer> currentDF = cdf.getDistributionFunctions()[0];
//        for (int i = 1; i < cdf.getSize(); i++) {
//            DistributionFunction<Integer> nextDF = cdf.getDistributionFunctions()[i];
//            for (int j = 0; j < currentDF.getDistributionTable().size(); j++) {
//                int sum = 0;
//                double p = 1;
//                for (int k=0; k < nextDF.getDistributionTable().size(); k++) {
//                     sum += currentDF.getDistributionTable().getDiscreteValueInRow(j).getValue()
//                            + nextDF.getDistributionTable().getDiscreteValueInRow(k).getValue();
//                     p *= currentDF.getDistributionTable().getProbabilityInRow(j).getValue()
//                          * nextDF.getDistributionTable().getProbabilityInRow(k).getValue();
//                }
//                puttt(dtResult, new DiscreteValue(sum), new Probability(p));
//            }
//            currentDF = nextDF;
//        }
//        return DistributionFunction.createByTable(dtResult);
        return new SequenceDistributionFunctionBuilder(cdf).build();
    }
//    private static void puttt(DistributionTable dt, DiscreteValue dv, Probability p) {
//        Probability oldP = dt.getProbability(dv);
//        if (oldP != null) {
//            p = new Probability(p.getValue() + oldP.getValue());
//        }
//        dt.put(dv, p);
//    }
}
