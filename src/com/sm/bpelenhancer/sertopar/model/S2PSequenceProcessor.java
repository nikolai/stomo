package com.sm.bpelenhancer.sertopar.model;

import com.sm.bpelmodeller.ActivityProcessor;
import com.sm.bpelmodeller.ActivityRunner;
import com.sm.model.Action;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TActivity;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TSequence;

import java.util.List;

/**
 * User: mikola
 * Date: 20.07.15
 * Time: 0:14
 */
public class S2PSequenceProcessor extends S2PAbstractProcessor implements ActivityProcessor<TSequence, Action> {
    public S2PSequenceProcessor(SequenceDependencyGraph dependencyGraph) {
        super(dependencyGraph);
    }

    @Override
    public Action processActivity(TSequence sequence, ActivityRunner activityRunner) {
        List<Object> activities = sequence.getActivity();
        for (Object a : activities) {
            activityRunner.goAhead((TActivity) a);
        }
        return null;
    }
}
