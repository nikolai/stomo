package com.sm.bpelenhancer.sertopar;

import com.sm.bmc.BMCProcess;
import com.sm.bmc.BMCSequence;
import com.sm.bmc.BPELModelCreator;
import com.sm.bpelenhancer.ChangeLog;
import org.junit.Assert;
import org.junit.Test;
import org.oasis_open.docs.wsbpel._2_0.process.executable.*;

import java.util.List;

/**
 * User: mikola
 * Date: 05.11.15
 * Time: 14:32
 */
public class S2PEnhancerTest {

    @Test
    public void test_enhance_single_seq() throws Exception {
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

        S2PEnhancer enhancer = new S2PEnhancer();
        ChangeLog log = new ChangeLog();
        enhancer.enhance(process.getDelegate(), log);

        Assert.assertTrue("no modifications found in log", !log.isEmpty());
        try {
            List<Object>  mainSeq = process.getDelegate().getSequence().getActivity();
            Assert.assertEquals("wrong main sequence size" , 5, mainSeq.size());
            TReceive receive = (TReceive) mainSeq.get(0);
            TAssign assign1 = (TAssign) mainSeq.get(1);
            TFlow addedFlow = (TFlow) mainSeq.get(2);
            TInvoke invoke1 = (TInvoke) addedFlow.getActivity().get(0);
            TInvoke invoke2 = (TInvoke) addedFlow.getActivity().get(1);
            TAssign assign2 = (TAssign) mainSeq.get(3);
            TReply reply = (TReply) mainSeq.get(4);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            Assert.fail("invalid enhanced BPEL structure");
        }
    }

//    @Test
    public void test_enhance_single_seq_n_group_seq_in_flow() throws Exception {
        Assert.fail("not implemented");
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

        S2PEnhancer enhancer = new S2PEnhancer();
        ChangeLog log = new ChangeLog();
        enhancer.enhance(process.getDelegate(), log);

        Assert.assertTrue("no modifications found in log", !log.isEmpty());
        try {
            List<Object>  mainSeq = process.getDelegate().getSequence().getActivity();
            Assert.assertEquals("wrong main sequence size" , 5, mainSeq.size());
            TReceive receive = (TReceive) mainSeq.get(0);
            TAssign assign1 = (TAssign) mainSeq.get(1);
            TFlow addedFlow = (TFlow) mainSeq.get(2);
            TInvoke invoke1 = (TInvoke) addedFlow.getActivity().get(0);
            TInvoke invoke2 = (TInvoke) addedFlow.getActivity().get(1);
            TAssign assign2 = (TAssign) mainSeq.get(3);
            TReply reply = (TReply) mainSeq.get(4);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            Assert.fail("invalid enhanced BPEL structure");
        }
    }
}