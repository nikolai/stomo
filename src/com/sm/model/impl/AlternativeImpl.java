package com.sm.model.impl;

import com.sm.AnalyticalDF;
import com.sm.DistributionFunction;
import com.sm.Probability;
import com.sm.model.Action;
import com.sm.model.ActionContainer;
import com.sm.model.Alternative;
import com.sm.util.CoinsUtil;

import java.util.LinkedList;
import java.util.List;

/**
 * User: mikola
 * Date: 26.10.12
 * Time: 19:41
 */
public class AlternativeImpl extends AbstractActionContainer implements Alternative {
    private final List<Probability> probabilities = new LinkedList<Probability>();

    @Override
    public ActionContainer addStoAction(Action action) {
        throw new UnsupportedOperationException("Use addStoAction(Action, Probability) instead");
    }

    public ActionContainer addStoAction(Action action, Probability probability) {
        probabilities.add(probability);
        return super.addStoAction(action);
    }

    public void validate() {
        getDistributionFunction();
    }


    public DistributionFunction getDistributionFunction() {
        CoinsUtil.checkFullProbability(probabilities);
        return AnalyticalDF.get().createAlternative(
                getCompatibleDistributionFunctions(),
                probabilities.toArray(new Probability[0]));
    }
}
