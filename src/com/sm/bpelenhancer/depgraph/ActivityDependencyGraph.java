package com.sm.bpelenhancer.depgraph;

import org.oasis_open.docs.wsbpel._2_0.process.executable.TActivity;

import java.util.*;

/**
 * User: mikola
 * Date: 18.07.15
 * Time: 16:28
 */
public class ActivityDependencyGraph {
    private DependencyGraphNode<? extends TActivity> rootNode;
    private Deque<DependencyGraphNode> nodes = new ArrayDeque<>();

    public ActivityDependencyGraph(DependencyGraphNode<? extends TActivity> rootNode) {
        this.rootNode = rootNode;
    }

    public ActivityDependencyGraph() {

    }

    public DependencyGraphNode<? extends TActivity> getRootNode() {
        return rootNode;
    }

    public void addNode(DependencyGraphNode<? extends  TActivity> addingNode) {
        Iterator<DependencyGraphNode> it = nodes.descendingIterator();
        while (it.hasNext()) {
            DependencyGraphNode next = it.next();
            if (addingNode.isDepending(next)) {
                next.getKids().add(addingNode);
                return;
            }
            nodes.add(addingNode);
        }
    }
}
