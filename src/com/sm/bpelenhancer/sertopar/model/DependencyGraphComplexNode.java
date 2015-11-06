package com.sm.bpelenhancer.sertopar.model;

import org.oasis_open.docs.wsbpel._2_0.process.executable.TActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * User: mikola
 * Date: 06.11.15
 * Time: 15:26
 */
public abstract class DependencyGraphComplexNode<T extends TActivity> extends DependencyGraphNode<T> {
    private List<String> allReadVars;
    private List<String> allWriteVars;

    private final List<DependencyGraphNode<T>> containedElements = new ArrayList<>();

    public DependencyGraphComplexNode(T activity) {
        super(activity);
    }

    @Override
    public List<String> getReadVars() {
        if (allReadVars == null) {
            allReadVars = new ArrayList<>();
            for (DependencyGraphNode<T> containedElement : containedElements) {
                for (String rv : containedElement.getAllReadVars()) {
                    if (!allReadVars.contains(rv)) {
                        allReadVars.add(rv);
                    }
                }
            }
        }
        return allReadVars;
    }

    @Override
    public List<String> getWriteVars() {
        if (allWriteVars == null) {
            allWriteVars = new ArrayList<>();
            for (DependencyGraphNode<T> containedElement : containedElements) {
                for (String rv : containedElement.getAllWriteVars()) {
                    if (!allWriteVars.contains(rv)) {
                        allWriteVars.add(rv);
                    }
                }
            }
        }
        return allWriteVars;
    }

    public List<DependencyGraphNode<T>> getContainedElements() {
        return containedElements;
    }

    public void addContainedElement(DependencyGraphNode containedElement) {
        containedElements.add(containedElement);
    }
}
