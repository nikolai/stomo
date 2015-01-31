package com.sm.model.impl;

import com.sm.DFCreatorFactory;
import com.sm.DistributionFunction;
import com.sm.model.Sequence;

/**
 * User: mikola
 * Date: 26.10.12
 * Time: 17:47
 */
public class SequenceImpl extends AbstractActionContainer implements Sequence {
    SequenceImpl(){}

    public DistributionFunction getDistributionFunction() {
        return DFCreatorFactory.getInstance().getCreator().createSequenceProcessing(getCompatibleDistributionFunctions());
    }
}
