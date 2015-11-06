package com.sm.bpelenhancer.sertopar.model;

import org.oasis_open.docs.wsbpel._2_0.process.executable.TActivity;

import java.util.*;

/**
 * User: mikola
 * Date: 18.07.15
 * Time: 16:28
 */
public class SequenceDependencyGraph {

    //TODO: refactor - move enhancer to another module, also CMDParam parser
    private DependencyGraphNode root;

    public DependencyGraphNode getRoot() {
        return root;
    }

    public <T extends TActivity> void addNode(DependencyGraphNode<T> addingNode) {
        if (root == null) {
            root = addingNode;
            return;
        }

        List<DependencyGraphNode<T>> parents = findParents(addingNode);
        for (DependencyGraphNode<T> parent : parents) {
            parent.addKid(addingNode);
            addingNode.addParent(parent);
        }
    }

    private <T extends TActivity> List<DependencyGraphNode<T>> findParents(DependencyGraphNode<T> addingNode) {
        List<DependencyGraphNode<T>> parents = new LinkedList<>();
        addParents(addingNode, root, parents);
        return parents;
    }

    private <T extends TActivity> void addParents(DependencyGraphNode<T> addingNode, DependencyGraphNode<T> isParentNode,
                                             List<DependencyGraphNode<T>> parents) {
        if (addingNode == isParentNode) return;

        if (addingNode.isDependingOn(isParentNode)) {
            parents.add(isParentNode);
        }
        for (DependencyGraphNode<T> kid : isParentNode.getKids()) {
            addParents(addingNode, kid, parents);
        }
    }
}
