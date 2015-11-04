package com.sm.bpelenhancer.sertopar;

import com.sm.bpelenhancer.depgraph.ActivityDependencyGraph;
import com.sm.bpelenhancer.depgraph.DependencyGraphNode;
import com.sm.bpelmodeller.ActivityProcessor;
import com.sm.bpelmodeller.ActivityRunner;
import com.sm.model.Action;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TReceive;

/**
 * User: mikola
 * Date: 20.07.15
 * Time: 0:18
 */
public class S2PReceiveProcessor extends S2PAbstractProcessor implements ActivityProcessor<TReceive, Action> {
    protected S2PReceiveProcessor(ActivityDependencyGraph dependencyGraph) {
        super(dependencyGraph);
    }

    @Override
    public Action processActivity(TReceive a, ActivityRunner activityRunner) {
        DependencyGraphNode<TReceive> graphNode = new DependencyGraphNode<>(a);
        graphNode.addWriteVar(a.getVariable());
        dependencyGraph.addNode(graphNode);
        return null;
    }
}
