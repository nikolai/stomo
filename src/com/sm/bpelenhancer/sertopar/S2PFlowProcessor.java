package com.sm.bpelenhancer.sertopar;

import com.sm.bpelenhancer.depgraph.ActivityDependencyGraph;
import com.sm.bpelenhancer.depgraph.DependencyGraphNode;
import com.sm.bpelmodeller.ActivityProcessor;
import com.sm.bpelmodeller.ActivityRunner;
import com.sm.model.Action;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TActivity;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TFlow;

import java.util.List;

/**
 * User: mikola
 * Date: 03.11.15
 * Time: 20:06
 */
public class S2PFlowProcessor extends S2PAbstractProcessor implements ActivityProcessor<TFlow, Action> {
    public S2PFlowProcessor(ActivityDependencyGraph dependencyGraph) {
        super(dependencyGraph);
    }

    @Override
    public Action processActivity(TFlow flow, ActivityRunner<Action> activityRunner) {
        List<Object> flows = flow.getActivity();
        dependencyGraph.addNode(new DependencyGraphNode<>(flow));
        for (Object a : flows) {
            activityRunner.goAhead((TActivity) a);
        }
        return null;
    }
}
