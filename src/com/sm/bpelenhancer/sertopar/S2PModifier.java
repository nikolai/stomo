package com.sm.bpelenhancer.sertopar;

import com.sm.bpelenhancer.BPELEnhancer;
import com.sm.bpelenhancer.ChangeLog;
import com.sm.bpelenhancer.ProcessModifier;
import com.sm.bpelenhancer.bpelmodifier.BPELModifyHelper;
import com.sm.bpelenhancer.sertopar.model.DependencyGraphComplexNode;
import com.sm.bpelenhancer.sertopar.model.DependencyGraphNode;
import com.sm.bpelenhancer.sertopar.model.DependencyGraphSequenceComplexNode;
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
    public void modify(TProcess bp, ChangeLog changeLog, DependencyGraphComplexNode model, BPELEnhancer enhancer) {
        this.changeLog = changeLog;
        this.enhancer = enhancer;
        bpelModifyHelper = BPELModifyHelper.getOne(bp);
        tryFlow(null, model);
    }

    private <T extends TActivity> boolean tryFlow(DependencyGraphComplexNode container, DependencyGraphNode<T> dependencyGraphNode) {
        if (dependencyGraphNode != null) {
            if (dependencyGraphNode instanceof DependencyGraphComplexNode) {
                DependencyGraphComplexNode<T> complexNode = ((DependencyGraphComplexNode<T>) dependencyGraphNode);
                if (!complexNode.getContainedElements().isEmpty()) {
                    // TODO: try to enhance all contained elements not only first (first is used for Sequence)
                    if (complexNode instanceof DependencyGraphSequenceComplexNode) {
                        trySequence2Flow((DependencyGraphSequenceComplexNode)complexNode);
                    } else {
                        for (DependencyGraphNode<T> containedNode : complexNode.getContainedElements()) {
                            tryFlow(complexNode, containedNode);
                        }
                    }
                }
            } else {
                List<DependencyGraphNode<T>> currentKids = dependencyGraphNode.getKids();
                if (currentKids.size() > 1) {
                    changeLog.addChangesComment(enhancer, "replace sequence with flow");
                    bpelModifyHelper.cutAndPasteInFlow(getFlowsContent(dependencyGraphNode), dependencyGraphNode.getNodeValue());
                    return true;
                } else if (!dependencyGraphNode.getKids().isEmpty()) {
                    tryFlow(container, dependencyGraphNode.getKids().get(0));
                }
            }
        }
        return false;
    }

    private <T extends TActivity> void trySequence2Flow(DependencyGraphSequenceComplexNode sequenceNode) {
        if (!tryFlow(sequenceNode, sequenceNode.getContainedElements().get(0))) {
            // tryFlow independent nodes (without parent)
            List<List<T>> orphanNodes = new ArrayList<>();
            int orphanNodesCount = 0;
            for (DependencyGraphNode seqElem : sequenceNode.getContainedElements()) {
                if (seqElem.getParents().isEmpty()) {
                    orphanNodesCount++;
                }
            }
            if (orphanNodesCount > 1) {
                for (DependencyGraphNode seqElem : sequenceNode.getContainedElements()) {
                    if (seqElem.getParents().isEmpty()) {
                        List<T> flowContent = new ArrayList<>();
                        collectFlowContent2(flowContent, seqElem);
                        orphanNodes.add(flowContent);
                    }
                }
                if (orphanNodes.size() > 1) {
                    changeLog.addChangesComment(enhancer, "replace sequence with flow");
                    bpelModifyHelper.cutAndPasteInFlowInSequence(orphanNodes, sequenceNode.getNodeValue());
                }
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

    private <T extends TActivity> void collectFlowContent2(List<T> flowContent, DependencyGraphNode<T> currentKid) {
        flowContent.add(currentKid.getNodeValue());
        List<DependencyGraphNode<T>> kids = currentKid.getKids();
        if (kids.size() > 1) {
            throw new UnsupportedOperationException("flow in flow not supported yet");
        } else if (kids.size() == 1) {
            collectFlowContent(flowContent, kids.get(0));
        }
    }
}
