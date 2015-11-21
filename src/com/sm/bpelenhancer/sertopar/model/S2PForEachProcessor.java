package com.sm.bpelenhancer.sertopar.model;

import com.sm.bpelmodeller.ActivityProcessor;
import com.sm.bpelmodeller.ActivityRunner;
import com.sm.bpelmodeller.ChildActivitySelector;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TActivity;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TForEach;

/**
 * User: mikola
 * Date: 21.11.15
 * Time: 12:18
 */
public class S2PForEachProcessor implements ActivityProcessor<TForEach,DependencyGraphNode> {
    @Override
    public DependencyGraphNode processActivity(TForEach a, ActivityRunner<DependencyGraphNode> activityRunner) {
        DependencyGraphComplexNode<TForEach> complexNode = new DependencyGraphComplexNode<>(a);
        if (a.getStartCounterValue() != null) {
            complexNode.addReadVar(S2PAssignProcessor.extractVarName(a.getStartCounterValue().getContent()));
        }
        if (a.getFinalCounterValue() != null) {
            complexNode.addReadVar(S2PAssignProcessor.extractVarName(a.getFinalCounterValue().getContent()));
        }

        TActivity kid = ChildActivitySelector.getOne().selectChildSafe(a).getActivity();

        if (kid!= null) {
            DependencyGraphNode kidNode = activityRunner.goAhead(kid);
            complexNode.addContainedElement(kidNode);
        }

        return complexNode;

    }
}
