package com.sm.bpelenhancer.sertopar;

import com.sm.bpelenhancer.BPELEnhancer;
import com.sm.bpelenhancer.ChangeLog;
import com.sm.bpelenhancer.ProcessModifier;
import com.sm.bpelenhancer.bpelmodifier.BPELModifyHelper;
import com.sm.bpelenhancer.depgraph.ActivityDependencyGraph;
import com.sm.bpelenhancer.depgraph.DependencyGraphNode;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TActivity;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TProcess;

import java.util.ArrayList;
import java.util.List;

/**
 * User: mikola
 * Date: 26.09.15
 * Time: 17:02
 */
public class S2PModifier implements ProcessModifier {
    private ChangeLog changeLog;
    private BPELEnhancer enhancer;
    private BPELModifyHelper bpelModifyHelper;

    @Override
    public void modify(TProcess bp, ChangeLog changeLog, ActivityDependencyGraph graph, BPELEnhancer enhancer) {
        this.changeLog = changeLog;
        this.enhancer = enhancer;
        bpelModifyHelper = BPELModifyHelper.getOne(bp);
        tryFlow(bp, graph.getRoot());
    }

    private <T extends TActivity> void tryFlow(TProcess p, DependencyGraphNode<T> dependencyGraphNode) {
        if (dependencyGraphNode != null) {
            List<DependencyGraphNode<T>> currentKids = dependencyGraphNode.getKids();
            if (currentKids.size() > 1) {
                changeLog.addChangesComment(enhancer, "replace sequence with flow");
                bpelModifyHelper.cutAndPasteInFlow(getFlowsContent(dependencyGraphNode), dependencyGraphNode.getNodeValue());
            } else if (!dependencyGraphNode.getKids().isEmpty()) {
                tryFlow(p, dependencyGraphNode.getKids().get(0));
            }
        }
    }

    public <T extends TActivity> List<List<T>> getFlowsContent(DependencyGraphNode<T> dependencyGraphNode) {
        List<List<T>> flows = new ArrayList<>(dependencyGraphNode.getKids().size());
        for (DependencyGraphNode<T> currentKid : dependencyGraphNode.getKids()) {
            List<T> flowContent = new ArrayList<>();
            collectFlowContent(flowContent, currentKid);
            flows.add(flowContent);
        }
        return flows;
    }

    private <T extends TActivity> void collectFlowContent(List<T> flowContent, DependencyGraphNode<T> currentKid) {
        // TODO support several kids
        if (currentKid.getParents().size() == 1) {
            flowContent.add(currentKid.getNodeValue());
            List<DependencyGraphNode<T>> kids = currentKid.getKids();
            if (kids.size() > 1) {
                throw new UnsupportedOperationException("flow in flow not supported yet");
            } else if (kids.size() == 1) {
                collectFlowContent(flowContent, kids.get(0));
            }
        }
    }
}
