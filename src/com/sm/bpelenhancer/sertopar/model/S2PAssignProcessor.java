package com.sm.bpelenhancer.sertopar.model;

import com.sm.bpelmodeller.ActivityProcessor;
import com.sm.bpelmodeller.ActivityRunner;
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
public class S2PAssignProcessor extends S2PAbstractProcessor implements ActivityProcessor<TAssign, DependencyGraphNode> {
    public S2PAssignProcessor() {
        super();
    }

    @Override
    public DependencyGraphNode processActivity(TAssign a, ActivityRunner activityRunner) {
        DependencyGraphNode<TAssign> graphNode = new DependencyGraphNode<>(a);
        List<TExtensibleElements> copyElems = a.getCopyOrExtensionAssignOperation();
        for (TExtensibleElements extEl : copyElems) {
            if (extEl instanceof TCopy) {
                TCopy copy = (TCopy) extEl;
                if (copy.getFrom().getContent() != null && copy.getFrom().getContent().size() > 0) {
                    graphNode.addReadVar(extractVarName(copy.getFrom().getContent()));
                } else {
                    graphNode.addReadVar(copy.getFrom().getVariable());
                }
                if (copy.getTo().getContent() != null && copy.getTo().getContent().size() > 0) {
                    graphNode.addWriteVar(extractVarName(copy.getTo().getContent()));
                } else {
                    graphNode.addWriteVar(copy.getTo().getVariable());
                }
            }
        }
        return graphNode;
    }

    public static String[] extractVarName(List<Object> contents) {
        if (contents != null && contents.size() > 0) {
            if (contents.size() > 1) {
                throw new IllegalStateException("Contents size > 1");
            }
            List<String> vars = new ArrayList<>();
            String[] splittedVars = ((String)contents.get(0)).split("\\$");
            for (int i = 1; i < splittedVars.length; i++) {
                vars.add(splittedVars[i].split("\\.")[0]);
            }
            return vars.toArray(new String[vars.size()]);
        }
        return new String[0];
    }
}
