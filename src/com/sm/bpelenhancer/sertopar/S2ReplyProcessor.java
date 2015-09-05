package com.sm.bpelenhancer.sertopar;

import com.sm.bpelenhancer.depgraph.ActivityDependencyGraph;
import com.sm.bpelenhancer.depgraph.DependencyGraphNode;
import com.sm.bpelmodeller.ActivityProcessor;
import com.sm.bpelmodeller.ActivityRunner;
import com.sm.model.Action;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TReply;

/**
 * User: mikola
 * Date: 20.07.15
 * Time: 0:20
 */
public class S2ReplyProcessor extends S2PAbstractProcessor implements ActivityProcessor<TReply> {
    protected S2ReplyProcessor(ActivityDependencyGraph dependencyGraph) {
        super(dependencyGraph);
    }

    @Override
    public Action processActivity(TReply a, ActivityRunner activityRunner) {
        DependencyGraphNode<TReply> graphNode = new DependencyGraphNode<>(a);
        graphNode.addReadVar(a.getVariable());
        dependencyGraph.addNode(graphNode);
        return null;
    }
}
