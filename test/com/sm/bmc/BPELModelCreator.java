package com.sm.bmc;

import org.oasis_open.docs.wsbpel._2_0.process.executable.ObjectFactory;

/**
 * User: mikola
 * Date: 05.11.15
 * Time: 14:41
 */
public class BPELModelCreator {
    private final ObjectFactory of;
    private BMCProcess process;

    public BPELModelCreator() {
        of = new ObjectFactory();
    }

    public BMCProcess createTProcess() {
        process = new BMCProcess(of.createTProcess());
        return process;
    }

    public BMCSequence createTSequence() {
        return new BMCSequence(of.createTSequence());
    }

    public BMCReceive createTReceive() {
        return new BMCReceive(of.createTReceive());
    }

    public BMCTAssign createTAssign() {
        return new BMCTAssign(of.createTAssign(), of);
    }

    public BMCInvoke createTInvoke() {
        return new BMCInvoke(of.createTInvoke());
    }

    public BMCReply createTReply() {
        return new BMCReply(of.createTReply());
    }
}
