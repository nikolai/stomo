package com.sm.util;

import com.sm.CompatibleExperiments;
import com.sm.DiscreteRandomValueGenerator;
import com.sm.Experiment;

/**
 * User: mikola
 * Date: 13.09.12
 * Time: 19:45
 */
public class RunExperimentService {

    public static final int STD_RUN_COUNT = 100000;

    private RunExperimentService(){}
    public static RunExperimentService get() {
        return new RunExperimentService();
    }

    public CompatibleExperiments run(DiscreteRandomValueGenerator... generator) {
        assert generator != null && generator.length >= 2;

        Experiment[] experiments = new Experiment[generator.length];

        for (int i=0; i < generator.length; i++) {
            experiments[i] = new Experiment(generator[i]);
        }

        CompatibleExperiments ce = new CompatibleExperiments(experiments);
        return ce.run(STD_RUN_COUNT);
    }
}
