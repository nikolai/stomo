package com.sm.bpelenhancer.sertopar;

import com.sm.bpelenhancer.depgraph.ActivityDependencyGraph;
import com.sm.bpelenhancer.depgraph.DependencyGraphNode;
import com.sm.bpelmodeller.ActivityProcessor;
import com.sm.bpelmodeller.ActivityRunner;
import com.sm.model.Action;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TAssign;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TCopy;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TExtensibleElements;

import java.util.List;

/**
 * User: mikola
 * Date: 20.07.15
 * Time: 0:20
 */
public class S2PAssignProcessor extends S2PAbstractProcessor implements ActivityProcessor<TAssign> {
    protected S2PAssignProcessor(ActivityDependencyGraph dependencyGraph) {
        super(dependencyGraph);
    }

    @Override
    public Action processActivity(TAssign a, ActivityRunner processorFactory) {
        DependencyGraphNode<TAssign> graphNode = new DependencyGraphNode<>(a);
        List<TExtensibleElements> copyElems = a.getCopyOrExtensionAssignOperation();
        for (TExtensibleElements extEl : copyElems) {
            if (extEl instanceof TCopy) {
                TCopy copy = (TCopy) extEl;
                if (copy.getFrom().getContent() != null) {
                    graphNode.addReadVar((String) copy.getFrom().getContent().get(0));
                }
                if (copy.getTo().getContent() != null) {
                    graphNode.addWriteVar((String) copy.getTo().getContent().get(0));
                }
            }
        }
        dependencyGraph.addNode(graphNode);
        return null;
    }
}
