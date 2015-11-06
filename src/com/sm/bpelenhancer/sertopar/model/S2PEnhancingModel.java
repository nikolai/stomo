package com.sm.bpelenhancer.sertopar.model;

import org.oasis_open.docs.wsbpel._2_0.process.executable.TActivity;

import java.util.Stack;

/**
 * User: mikola
 * Date: 06.11.15
 * Time: 19:06
 */
public class S2PEnhancingModel {
    private Stack<DependencyGraphComplexNode> complexNodes = new Stack<>();

//    public <T extends TActivity> DependencyGraphComplexNode<T> getRoot() {
//        return root;
//    }

    public <T extends TActivity> void addNode(DependencyGraphNode<T> graphNode) {
        if (complexNodes.peek() == null) {
            throw new IllegalStateException("start with complex node!");
        }
//        complexNodes.peek().get
    }

    public <T extends TActivity> void addComplexNode(DependencyGraphComplexNode<T> complexNode) {
        if (!complexNodes.contains(complexNode)) {
            complexNodes.push(complexNode);
        }
    }

    public <T extends TActivity> void finishComplex(DependencyGraphComplexNode<T> complexNode) {
        if (complexNodes.contains(complexNode)) {
            complexNodes.pop();
        }
    }
}
