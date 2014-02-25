package com.sm.model.impl;

import com.sm.*;
import com.sm.model.StoModel;
import com.sm.model.StoModellingResult;

/**
 * User: mikola
 * Date: 26.10.12
 * Time: 17:42
 */
public class StoModelImpl extends SequenceImpl implements StoModel {
    StoModelImpl(){}

    public StoModellingResult runModelling() {
        final DistributionFunction df = getDistributionFunction();
        return new StoModellingResultImpl(
                df.getDistributionTable(),
                new ExpectedValue(df),
                new Variance(df)
        );
    }
}
