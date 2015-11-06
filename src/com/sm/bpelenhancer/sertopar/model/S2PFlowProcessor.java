package com.sm.bpelenhancer.sertopar.model;

import com.sm.bpelmodeller.ActivityProcessor;
import com.sm.bpelmodeller.ActivityRunner;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TActivity;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TFlow;

import java.util.List;

/**
 * User: mikola
 * Date: 03.11.15
 * Time: 20:06
 */
public class S2PFlowProcessor implements ActivityProcessor<TFlow, DependencyGraphNode> {
    public S2PFlowProcessor() {
        super();
    }

    @Override
    public DependencyGraphNode processActivity(TFlow a, ActivityRunner<DependencyGraphNode> activityRunner) {
        DependencyGraphFlowComplexNode complexNode = new DependencyGraphFlowComplexNode(a);
        List<Object> activities = a.getActivity();
        for (Object flow : activities) {
            DependencyGraphNode kidNode = activityRunner.goAhead((TActivity) flow);
            complexNode.addContainedElement(kidNode);
        }
        return complexNode;
    }
}
