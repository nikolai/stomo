package com.sm.bpelenhancer.sertopar;

import com.sm.bpelenhancer.depgraph.ActivityDependencyGraph;
import com.sm.bpelenhancer.depgraph.DependencyGraphNode;
import com.sm.bpelmodeller.ActivityProcessor;
import com.sm.bpelmodeller.ActivityRunner;
import com.sm.model.Action;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TInvoke;

/**
 * User: mikola
 * Date: 20.07.15
 * Time: 0:20
 */
public class S2PInvokeProcessor extends S2PAbstractProcessor implements ActivityProcessor<TInvoke> {
    protected S2PInvokeProcessor(ActivityDependencyGraph dependencyGraph) {
        super(dependencyGraph);
    }

    @Override
    public Action processActivity(TInvoke a, ActivityRunner activityRunner) {
        DependencyGraphNode<TInvoke> graphNode = new DependencyGraphNode<>(a);
        graphNode.addReadVar(a.getInputVariable());
        graphNode.addWriteVar(a.getOutputVariable());
        dependencyGraph.addNode(graphNode);
        return null;
    }
}
