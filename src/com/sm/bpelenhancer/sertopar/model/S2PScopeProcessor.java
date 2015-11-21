package com.sm.bpelenhancer.sertopar.model;

import com.sm.bpelmodeller.ActivityProcessor;
import com.sm.bpelmodeller.ActivityRunner;
import com.sm.bpelmodeller.ChildActivitySelector;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TActivity;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TScope;

/**
 * User: mikola
 * Date: 06.11.15
 * Time: 20:42
 */
public class S2PScopeProcessor implements ActivityProcessor<TScope, DependencyGraphNode> {
    public S2PScopeProcessor() {
    }

    @Override
    public DependencyGraphNode processActivity(TScope a, ActivityRunner<DependencyGraphNode> activityRunner) {
        DependencyGraphScopeComplexNode complexNode = new DependencyGraphScopeComplexNode(a);
        TActivity activity = ChildActivitySelector.getOne().selectChildSafe(a).getActivity();
        complexNode.addContainedElement(activityRunner.goAhead(activity));
        return complexNode;
    }
}
