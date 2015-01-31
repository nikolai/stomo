package com.sm.bpelenhancer.visitor;

import org.oasis_open.docs.wsbpel._2_0.process.executable.TActivity;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TFlow;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TProcess;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TSequence;

import java.util.List;

/**
 * User: mikola
 * Date: 25.01.15
 * Time: 21:09
 */
public class ProcessRider implements ProcessVisitor {
    private final ProcessVisitor sputnik;

    public ProcessRider(ProcessVisitor sputnik) {
        this.sputnik = sputnik;
    }

    private TActivity getChildren(TProcess elem) {
        TActivity o;
        if ((o = elem.getAssign()) != null) {
            return o;
        }
        if ((o = elem.getFlow()) != null) {
            return o;
        }
        if ((o = elem.getForEach()) != null) {
            return o;
        }
        if ((o = elem.getIf()) != null) {
            return o;
        }
        throw new IllegalStateException();
    }

    public void visit(TProcess o) {
        sputnik.visit(o);
        visit(getChildren(o));
    }

    private void visit(TActivity o) {
        throw new UnsupportedOperationException();
    }

    public void visit(TSequence o) {
        List<Object> kids = o.getActivity();
    }

    public void visit(TFlow o) {

    }
}
