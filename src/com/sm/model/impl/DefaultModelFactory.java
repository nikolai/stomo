package com.sm.model.impl;

import com.sm.DistributionTable;
import com.sm.model.*;

/**
 * User: mikola
 * Date: 26.10.12
 * Time: 19:24
 */
public class DefaultModelFactory implements ModelFactory {
    private DefaultModelFactory(){}

    public StoModel createStoModel() {
        return new StoModelImpl();
    }

    public Sequence createSequence() {
        return new SequenceImpl();
    }

    public Action createAction(DistributionTable distributionTable) {
        return new ActionImpl(distributionTable);
    }

    public Alternative createAlternative(){
        return new AlternativeImpl();
    }

    public Parallel createParallel() {
        return new ParallelImpl();
    }

    public static ModelFactory getDefault() {
        return new DefaultModelFactory();
    }

}
