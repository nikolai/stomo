package com.sm.bpelenhancer.depgraph;

import org.oasis_open.docs.wsbpel._2_0.process.executable.TActivity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static com.sm.util.CollectionUtil.containsAny;

/**
 * User: mikola
 * Date: 18.07.15
 * Time: 16:28
 */
public class DependencyGraphNode<T extends TActivity> {
    private final T activity;
    private final List<DependencyGraphNode<T>> kids = new ArrayList<>();
    private final List<String> writeVars = new ArrayList<>();
    private final List<String> readVars = new ArrayList<>();
    private final List<DependencyGraphNode<T>> parents = new ArrayList<>();

    public DependencyGraphNode(T activity) {
        this.activity = activity;
    }

    public T getNodeValue() {
        return activity;
    }

    public List<String> getReadVars() {
        return getReadVars(false);
    }

    public List<String> getAllReadVars() {
        return getReadVars(true);
    }

    public List<String> getAllWriteVars() {
        return getWriteVars(true);
    }

    public List<String> getReadVars(boolean includeKids) {
        List<String> result = new LinkedList<>(readVars);
        if (includeKids) {
            for (DependencyGraphNode kid : getKids()) {
                result.addAll(kid.getReadVars());
            }
        }
        return result;
    }
    public List<String> getWriteVars() {
        return getWriteVars(false);
    }

    public List<String> getWriteVars(boolean includeKids) {
        List<String> result = new LinkedList<>(writeVars);
        if (includeKids) {
            for (DependencyGraphNode kid : getKids()) {
                result.addAll(kid.getWriteVars());
            }
        }
        return result;
    }

    public List<DependencyGraphNode<T>> getKids() {
        return kids;
    }

    public void addWriteVar(String... varName) {
        for (String name : varName) {
            if (!writeVars.contains(name)) {
                writeVars.add(name);
            }
        }
    }

    public void addReadVar(String... varName) {
        for (String name : varName) {
            if (!readVars.contains(name)) {
                readVars.add(name);
            }
        }
    }

    public boolean isDependingOn(DependencyGraphNode existedNode) {
        return isDepending(existedNode.getReadVars(), existedNode.getWriteVars());
    }

    public boolean isDependingOnParent(DependencyGraphNode<T> parent) {
        return isDepending(parent.getAllReadVars(), parent.getAllWriteVars());
    }

    private boolean isDepending(List<String> existingRead,
                                List<String> existingWrite) {
        if (containsAny(existingWrite, getReadVars())) {
            return true; // RAW
        } else if (containsAny(existingRead, getWriteVars())) {
            return true; // WAR
        } else if (containsAny(existingWrite, getWriteVars())) {
            return true; // WAW
        }
        return false;
    }

    public void addKid(DependencyGraphNode<T> addingNode) {
        getKids().add(addingNode);
    }

    public void addParent(DependencyGraphNode parent) {
        parents.add(parent);
    }

    public List<DependencyGraphNode<T>> getParents() {
        return parents;
    }

    @Override
    public String toString() {
        return getNodeValue().getName();
    }
}