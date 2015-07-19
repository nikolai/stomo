package com.sm.bpelenhancer.sertopar;

import com.sm.bpelenhancer.depgraph.ActivityDependencyGraph;
import com.sm.bpelenhancer.depgraph.DependencyGraphNode;
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
public class S2PSequenceProcessor extends S2PAbstractProcessor implements ActivityProcessor<TSequence> {
    protected S2PSequenceProcessor(ActivityDependencyGraph dependencyGraph) {
        super(dependencyGraph);
    }

    @Override
    public Action processActivity(TSequence sequence, ActivityRunner processorFactory) {
        List<Object> activities = sequence.getActivity();
        for (Object a : activities) {
            TActivity activity = (TActivity) a;
            DependencyGraphNode<TActivity> graphNode = new DependencyGraphNode<>(activity);
            dependencyGraph.addNode(graphNode);
            processorFactory.goAhead(activity);
        }
        return null;
    }
}
