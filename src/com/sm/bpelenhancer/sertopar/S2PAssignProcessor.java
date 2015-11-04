package com.sm.bpelenhancer.sertopar;

import com.sm.bpelenhancer.depgraph.ActivityDependencyGraph;
import com.sm.bpelenhancer.depgraph.DependencyGraphNode;
import com.sm.bpelmodeller.ActivityProcessor;
import com.sm.bpelmodeller.ActivityRunner;
import com.sm.model.Action;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TAssign;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TCopy;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TExtensibleElements;

import java.util.ArrayList;
import java.util.List;

/**
 * User: mikola
 * Date: 20.07.15
 * Time: 0:20
 */
public class S2PAssignProcessor extends S2PAbstractProcessor implements ActivityProcessor<TAssign, Action> {
    protected S2PAssignProcessor(ActivityDependencyGraph dependencyGraph) {
        super(dependencyGraph);
    }

    @Override
    public Action processActivity(TAssign a, ActivityRunner activityRunner) {
        DependencyGraphNode<TAssign> graphNode = new DependencyGraphNode<>(a);
        List<TExtensibleElements> copyElems = a.getCopyOrExtensionAssignOperation();
        for (TExtensibleElements extEl : copyElems) {
            if (extEl instanceof TCopy) {
                TCopy copy = (TCopy) extEl;
                if (copy.getFrom().getContent() != null) {
                    graphNode.addReadVar(extractVarName((String) copy.getFrom().getContent().get(0)));
                }
                if (copy.getTo().getContent() != null) {
                    graphNode.addWriteVar(extractVarName((String) copy.getTo().getContent().get(0)));
                }
            }
        }
        dependencyGraph.addNode(graphNode);
        return null;
    }

    private String[] extractVarName(String expression) {
        List<String> vars = new ArrayList<>();
        String[] splittedVars = expression.split("\\$");
        for (int i = 1; i < splittedVars.length; i++) {
            vars.add(splittedVars[i].split("\\.")[0]);
        }
        return vars.toArray(new String[vars.size()]);
    }
}
