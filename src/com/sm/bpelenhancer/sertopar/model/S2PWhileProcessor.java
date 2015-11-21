package com.sm.bpelenhancer.sertopar.model;

import com.sm.bpelmodeller.ActivityProcessor;
import com.sm.bpelmodeller.ActivityRunner;
import com.sm.bpelmodeller.ChildActivitySelector;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TActivity;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TWhile;

/**
 * User: mikola
 * Date: 21.11.15
 * Time: 12:05
 */
public class S2PWhileProcessor implements ActivityProcessor<TWhile,DependencyGraphNode> {

    @Override
    public DependencyGraphNode processActivity(TWhile a, ActivityRunner<DependencyGraphNode> activityRunner) {
        DependencyGraphComplexNode<TWhile> complexNode = new DependencyGraphComplexNode<>(a);
        complexNode.addReadVar(S2PAssignProcessor.extractVarName(a.getCondition().getContent()));

        TActivity kid = ChildActivitySelector.getOne().selectChildSafe(a).getActivity();

        if (kid!= null) {
            DependencyGraphNode kidNode = activityRunner.goAhead(kid);
            complexNode.addContainedElement(kidNode);
        }

        return complexNode;
    }
}
