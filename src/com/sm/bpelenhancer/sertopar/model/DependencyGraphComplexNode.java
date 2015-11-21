package com.sm.bpelenhancer.sertopar.model;

import org.oasis_open.docs.wsbpel._2_0.process.executable.TActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * User: mikola
 * Date: 06.11.15
 * Time: 15:26
 */
public class DependencyGraphComplexNode<T extends TActivity> extends DependencyGraphNode<T> {
    private final List<DependencyGraphNode> containedElements = new ArrayList<>();

    public DependencyGraphComplexNode(T activity) {
        super(activity);
    }

    @Override
    public List<String> getReadVars() {
        List<String> allReadVars = new ArrayList<>();
        for (DependencyGraphNode<T> containedElement : containedElements) {
            for (String rv : containedElement.getAllReadVars()) {
                if (!allReadVars.contains(rv)) {
                    allReadVars.add(rv);
                }
            }
        }
        return allReadVars;
    }

    @Override
    public List<String> getWriteVars() {
        List<String> allWriteVars = new ArrayList<>();
        for (DependencyGraphNode<T> containedElement : containedElements) {
            for (String rv : containedElement.getAllWriteVars()) {
                if (!allWriteVars.contains(rv)) {
                    allWriteVars.add(rv);
                }
            }
        }
        return allWriteVars;
    }

    @Override
    protected List<String> getAllReadVars() {
        return this.getReadVars();
    }

    @Override
    protected List<String> getAllWriteVars() {
        return this.getWriteVars();
    }

    public List<DependencyGraphNode> getContainedElements() {
        return containedElements;
    }

    public void addContainedElement(DependencyGraphNode containedElement) {
        containedElements.add(containedElement);
    }
}
