package com.sm.bpelmodeller.smprocessors;

import com.sm.Probability;
import com.sm.bpelmodeller.ActivityProcessor;
import com.sm.bpelmodeller.ActivityRunner;
import com.sm.bpelmodeller.ChildActivitySelector;
import com.sm.bpelmodeller.config.ConfigHelper;
import com.sm.model.Action;
import com.sm.model.Alternative;
import com.sm.model.ModelFactory;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TIf;

/**
 * User: mikola
 * Date: 09.03.15
 * Time: 17:34
 */
public class IfProcessor implements ActivityProcessor<TIf, Action> {

    private final ModelFactory mf;
    private final ConfigHelper configHelper;

    public IfProcessor(ModelFactory mf, ConfigHelper configHelper) {

        this.mf = mf;
        this.configHelper = configHelper;
    }

    public Action processActivity(TIf a, ActivityRunner<Action> activityRunner) {
        Alternative alternative = mf.createAlternative();

        // find dt and probability of branch by name
        Probability probability = configHelper.findBranchProbability(a);
        // process if true
        ChildActivitySelector.SelectedChild ifTrueBranch = ChildActivitySelector.getOne().selectChild(a);
        alternative.addStoAction(activityRunner.goAhead(ifTrueBranch.getActivity()), probability);

        // process else
        ChildActivitySelector.SelectedChild elseBranch = ChildActivitySelector.getOne().selectChild(a.getElse());
        alternative.addStoAction(activityRunner.goAhead(elseBranch.getActivity()), probability.invert());
        return alternative;
    }
}
