package com.sm.model.impl;

import com.sm.DistributionFunction;
import com.sm.DistributionTable;
import com.sm.ExpectedValue;
import com.sm.Variance;
import com.sm.model.StoModel;
import com.sm.model.StoModellingResult;

/**
 * User: mikola
 * Date: 26.10.12
 * Time: 17:42
 */
public class StoModelImpl extends SequenceImpl implements StoModel {
    StoModelImpl(){}
    public StoModellingResult analyticalRun() {
        final DistributionFunction df = getDistributionFunction();
        return new StoModellingResultImpl(
                df.getDistributionTable(),
                new ExpectedValue(df),
                new Variance(df)
        );
    }
}
