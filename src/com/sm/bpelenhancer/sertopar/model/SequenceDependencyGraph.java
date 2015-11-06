package com.sm.bpelenhancer.sertopar.model;

import org.oasis_open.docs.wsbpel._2_0.process.executable.TActivity;

import java.util.*;

/**
 * User: mikola
 * Date: 18.07.15
 * Time: 16:28
 */
public class SequenceDependencyGraph {
    private DependencyGraphNode root;
    private DependencyGraphNode lastAdded;

    public DependencyGraphNode getRoot() {
        return root;
    }

    public <T extends TActivity> void addNode(DependencyGraphNode<T> addingNode) {
        if (root == null) {
            root = addingNode;
            lastAdded = addingNode;
            return;
        }

        List<DependencyGraphNode<T>> parents = findParents(addingNode);
        for (DependencyGraphNode parent : parents) {
            parent.addKid(addingNode);
            addingNode.addParent(parent);
        }
        lastAdded = addingNode;
    }

    private <T extends TActivity> List<DependencyGraphNode<T>> findParents(DependencyGraphNode<T> addingNode) {
        List<DependencyGraphNode<T>> parents = new LinkedList<>();
        if (root == addingNode) {
            return parents;
        }
        addParents(addingNode, root, parents);

//        // todo: не учитывается глубина, вернее учитывается только 2 - до "родителей родителя".
//        if (addingNode.isDependingOn(lastAdded)) {
//            for (DependencyGraphNode<T> p : lastAdded.getParents()) {
//                if (addingNode.isDependingOn(p)) {
//                    parents.add(p);
//                }
//                for (DependencyGraphNode<T> k : p.getKids()) {
//                    if (addingNode.isDependingOn(k) && !parents.contains(k)) {
//                        parents.add(k);
//                    }
//                }
//            }
//            if (parents.isEmpty()) {
//                parents.add(lastAdded);
//            }
//        } else {
//            for (DependencyGraphNode<T> p : lastAdded.getParents()) {
//                if (addingNode.isDependingOnParent(p)) {
//                    parents.add(p);
//                }
//            }
//        }

        return parents;
    }

    private <T extends TActivity> void addParents(DependencyGraphNode<T> addingNode, DependencyGraphNode<T> isParentNode,
                                             List<DependencyGraphNode<T>> parents) {
        if (addingNode.isDependingOn(isParentNode)) {
            parents.add(isParentNode);
        }
        for (DependencyGraphNode<T> kid : isParentNode.getKids()) {
            addParents(addingNode, kid, parents);
        }
    }



}
