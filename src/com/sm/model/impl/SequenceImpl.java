package com.sm.model.impl;

import com.sm.AnalyticalDF;
import com.sm.CompatibleDistributionFunctions;
import com.sm.DistributionFunction;
import com.sm.model.Action;
import com.sm.model.Sequence;

import java.util.LinkedList;
import java.util.List;

/**
 * User: mikola
 * Date: 26.10.12
 * Time: 17:47
 */
public class SequenceImpl extends AbstractActionContainer implements Sequence {
    SequenceImpl(){}

    public DistributionFunction getDistributionFunction() {
        return AnalyticalDF.get().createSequenceProcessing(getCompatibleDistributionFunctions());
    }
}
