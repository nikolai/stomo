package com.sm;

/**
 * User: smirnov-n
 * Date: 20.09.11
 * Time: 16:21
 */
public class CompatiblePDF {

    private final ProbabilityDensityFunction[] functions;

    public CompatiblePDF(CompatibleDistributionFunctions compatibleDistributionFunctions) {
        functions = new ProbabilityDensityFunction[compatibleDistributionFunctions.getSize()];
        for (int i = 0; i < functions.length; i++) {
            functions[i] = ProbabilityDensityFunction.get(
                compatibleDistributionFunctions.getDistributionFunctions()[i].getDistributionTable());
        }
    }

}
