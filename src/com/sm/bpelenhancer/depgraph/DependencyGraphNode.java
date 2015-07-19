package com.sm.bpelenhancer.depgraph;

import org.oasis_open.docs.wsbpel._2_0.process.executable.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * User: mikola
 * Date: 18.07.15
 * Time: 16:28
 */
public class DependencyGraphNode<T extends TActivity> {
    private final T n;
    private final List<DependencyGraphNode> kids = new ArrayList<>();
    private final List<String> writeVars = new ArrayList<>();
    private final List<String> readVars = new ArrayList<>();

    public DependencyGraphNode(T nodeValue) {
        this.n = nodeValue;
    }

    public T getNodeValue() {
        return n;
    }

    public List<String> getReadVars() throws Exception {
        if (n instanceof TReceive) {
            return Collections.emptyList();
        } else if (n instanceof TAssign) {
            TAssign a = (TAssign) n;
            List<String> readVars = new ArrayList<>();
            List<TExtensibleElements> copyElems = a.getCopyOrExtensionAssignOperation();
            for (TExtensibleElements extEl : copyElems) {
                if (extEl instanceof TCopy) {
                    TCopy copy = (TCopy) extEl;
                    if (copy.getFrom().getContent() != null) {
                        readVars.add((String) copy.getFrom().getContent().get(0));
                    }
                }

            }
            return readVars;
        }
        return Collections.emptyList();
    }

    public List<String> getWriteVars() throws Exception {
        return writeVars;
    }

    public List<DependencyGraphNode> getKids() {
        return kids;
    }

    public void addWriteVar(String varName) {
        writeVars.add(varName);
    }

    public void addReadVar(String varName) {
        readVars.add(varName);
    }
}
