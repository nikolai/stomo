package com.sm.bpelenhancer.visitor;

import org.oasis_open.docs.wsbpel._2_0.process.executable.TFlow;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TProcess;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TSequence;

/**
 * User: mikola
 * Date: 25.01.15
 * Time: 20:46
 */
public interface ProcessVisitor {
    public void visit(TProcess o);
    public void visit(TSequence o);
    public void visit(TFlow o);
}
