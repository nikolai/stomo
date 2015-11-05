package com.sm.bpelenhancer.sertopar;

import com.sm.bmc.BMCProcess;
import com.sm.bmc.BMCSequence;
import com.sm.bmc.BPELModelCreator;
import com.sm.bpelenhancer.ChangeLog;
import org.junit.Assert;
import org.junit.Test;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TFlow;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TInvoke;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TReply;

/**
 * User: mikola
 * Date: 05.11.15
 * Time: 14:32
 */
public class Ser2ParEnhancerTest {

    @Test
    public void testEnhance() throws Exception {
        BPELModelCreator bp = new BPELModelCreator();

        String  bpVarIn = "bpVarIn",
                bpVarOut = "bpVarOut",
                inv1VarIn = "inv1VarIn",
                inv1VarOut = "inv1VarOut",
                inv2VarIn = "inv2VarIn",
                inv2VarOut = "inv2VarOut";

        BMCProcess process = bp.createTProcess();

        BMCSequence sequence = bp.createTSequence();
        process.setSequence(sequence);

        sequence.add(bp.createTReceive().setWriteVars(bpVarIn))
                .add(bp.createTAssign().addCopy(bpVarIn, inv1VarIn).addCopy(bpVarIn, inv2VarIn))
                .add(bp.createTInvoke().setReadVars(inv1VarIn).setWriteVars(inv1VarOut))
                .add(bp.createTInvoke().setReadVars(inv2VarIn).setWriteVars(inv2VarOut))
                .add(bp.createTAssign().addCopy(inv1VarOut, bpVarOut).addCopy(inv2VarOut, bpVarOut))
                .add(bp.createTReply().setReadVars(bpVarOut));

        Ser2ParEnhancer enhancer = new Ser2ParEnhancer();
        ChangeLog log = new ChangeLog();
        enhancer.enhance(process.getDelegate(), log);

        Assert.assertTrue("no modifications found in log", !log.isEmpty());
        try {
            TFlow addedFlow = (TFlow) process.getDelegate().getSequence().getActivity().get(2);
            TInvoke invoke1 = (TInvoke) addedFlow.getActivity().get(0);
            TInvoke invoke2 = (TInvoke) addedFlow.getActivity().get(1);
            TReply reply = (TReply) process.getDelegate().getSequence().getActivity().get(4);
        } catch (Exception e) {
            Assert.fail("invalid enhanced BPEL structure");
        }
    }
}