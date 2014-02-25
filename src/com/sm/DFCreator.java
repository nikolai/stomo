package com.sm;

/**
 * User: mikola
 * Date: 24.02.14
 * Time: 16:32
 */
public interface DFCreator {
    DistributionFunction createAND(CompatibleDistributionFunctions<Integer> compatibleDistributionFunctions);

    DistributionFunction createOR(CompatibleDistributionFunctions<Integer> compatibleDistributionFunctions);

    DistributionFunction createMN(CompatibleDistributionFunctions cdf, int M);

    DistributionFunction createAlternative(CompatibleDistributionFunctions cdf, Probability[] probs);

    DistributionFunction createSequenceProcessing(CompatibleDistributionFunctions<Integer> cdf);
}
