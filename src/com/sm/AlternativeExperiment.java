package com.sm;

/**
 * User: mikola
 * Date: 24.10.12
 * Time: 17:23
 */
public class AlternativeExperiment extends Experiment {
    private Probability p;

    public AlternativeExperiment(DiscreteRandomValueGenerator generator, Probability p) {
        super(generator);
        this.p = p;
    }

    public Probability getProbability() {
        return p;
    }
}
