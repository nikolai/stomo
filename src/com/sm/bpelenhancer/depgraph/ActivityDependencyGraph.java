package com.sm.bpelenhancer.depgraph;

import org.oasis_open.docs.wsbpel._2_0.process.executable.TActivity;

import java.util.*;

/**
 * User: mikola
 * Date: 18.07.15
 * Time: 16:28
 */
public class ActivityDependencyGraph {
    private DependencyGraphNode root;
    private DependencyGraphNode lastAdded;

    public <T extends TActivity> void addNode(DependencyGraphNode<T> addingNode) {
        if (root == null) {
            root = addingNode;
            lastAdded = addingNode;
            return;
        }

        List<DependencyGraphNode<T>> parents = findParents(lastAdded, addingNode);
        for (DependencyGraphNode parent : parents) {
            parent.addKid(addingNode);
            addingNode.addParent(parent);
        }
        lastAdded = addingNode;
    }

    private <T extends TActivity> List<DependencyGraphNode<T>> findParents(DependencyGraphNode<T> lastAdded, DependencyGraphNode<T> addingNode) {
        List<DependencyGraphNode<T>> parents = new LinkedList<>();
        if (lastAdded == null) {
            return parents;
        }

        if (addingNode.isDependingOn(lastAdded)) {
            for (DependencyGraphNode<T> p : lastAdded.getParents()) {
                if (addingNode.isDependingOn(p)) {
                    parents.add(p);
                }
                for (DependencyGraphNode<T> k : p.getKids()) {
                    if (addingNode.isDependingOn(k) && !parents.contains(k)) {
                        parents.add(k);
                    }
                }
            }
            if (parents.isEmpty()) {
                parents.add(lastAdded);
            }
        } else {
            for (DependencyGraphNode<T> p : lastAdded.getParents()) {
                if (addingNode.isDependingOnParent(p)) {
                    parents.add(p);
                }
            }
        }

        return parents;
    }

}
