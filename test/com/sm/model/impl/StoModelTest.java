package com.sm.model.impl;

import com.sm.DistributionTable;
import com.sm.Probability;
import com.sm.bpelmodeller.config.BpelModellingConfigFactory;
import com.sm.bpelmodeller.config.xsd.*;
import com.sm.model.*;
import com.sng.bpel.main.StoModeller;
import com.sng.bpel.main.StoModellerException;
import com.sng.bpel.main.StoModellerParams;
import org.junit.Test;

import static com.sm.util.AssertUtil.*;
import static com.sm.util.AssertUtil.assertEquals;

/**
 * User: mikola
 * Date: 26.10.12
 * Time: 18:02
 */
public class StoModelTest {
    final ModelFactory mf = DefaultModelFactory.getDefault();
    final DistributionTable dtAlmost3 = new DistributionTable(new int[]{1, 2, 3}, new double[]{0.01, 0.01, 0.98});
    final DistributionTable dtAlmost2 = new DistributionTable(new int[]{1, 2, 3}, new double[]{0.01, 0.98, 0.01});
    final DistributionTable dtAlmost1 = new DistributionTable(new int[]{1, 2, 3}, new double[]{0.98, 0.01, 0.01});
    final DistributionTable dtAlmost4 = new DistributionTable(new int[]{4, 2, 3}, new double[]{0.98, 0.01, 0.01});

    @Test
    public void test_config() throws Exception {
        ObjectFactory of = new ObjectFactory();
        StoModelConfig conf = of.createStoModelConfig();


        TAtomicActivityConfig ac1 = of.createTAtomicActivityConfig();
        ac1.setActivityName("testInvoke1");
        TTimeDistribution td1 = of.createTTimeDistribution();
        td1.setProbability(0.1);
        td1.setTime(1);
        TTimeDistribution td2 = of.createTTimeDistribution();
        td2.setProbability(0.5);
        td2.setTime(2);
        TTimeDistribution td3 = of.createTTimeDistribution();
        td1.setProbability(0.4);
        td1.setTime(3);
        ac1.getTimeDistribution().add(td1);
        ac1.getTimeDistribution().add(td2);
        ac1.getTimeDistribution().add(td3);
        conf.getActivityConfig().add(ac1);


        TIfBranchConfig ifBranchConfigType = of.createTIfBranchConfig();
        ifBranchConfigType.setActivityName("If23");
        ifBranchConfigType.setProbabilityOfBranch(0.33);

        conf.getIfActivityConfig().add(ifBranchConfigType);

        BpelModellingConfigFactory.getOne().saveConfig("testBpelModelConfig.xml", conf);
    }

    @Test
    public void test_sequence_of_2(){
        StoModel model = mf.createStoModel();

        DistributionTable dt1 = new DistributionTable(new int[]{1, 2, 3}, new double[]{0.1, 0.3, 0.6});
        model.addStoAction(mf.createAction(dt1));
        model.addStoAction(mf.createAction(dt1));

        StoModellingResult result = model.analyticalRun();

        assertEquals(5.0, result.getExpectedValue().getValue(), ANALYTICAL_ERROR);
        assertEquals(1.65, result.getVariance().getValue(), ANALYTICAL_ERROR);
    }

    @Test
    public void test_sequence_of_3(){
        StoModel model = mf.createStoModel();

        model.addStoAction(mf.createAction(dtAlmost3));
        model.addStoAction(mf.createAction(dtAlmost3));
        model.addStoAction(mf.createAction(dtAlmost3));

        StoModellingResult result = model.analyticalRun();

        assertEquals(8.91, result.getExpectedValue().getValue(), ANALYTICAL_ERROR);
        assertEquals(0.18, result.getVariance().getValue(), ANALYTICAL_ERROR);
    }

    @Test
    public void test_seq_of_seq(){
        StoModel model = mf.createStoModel();

        Sequence seq1 = mf.createSequence();
        seq1.addStoAction(mf.createAction(dtAlmost3));
        seq1.addStoAction(mf.createAction(dtAlmost3));

        model.addStoAction(seq1).addStoAction(mf.createAction(dtAlmost3));

        StoModellingResult result = model.analyticalRun();

        assertEquals(8.91, result.getExpectedValue().getValue(), ANALYTICAL_ERROR);
        assertEquals(0.18, result.getVariance().getValue(), ANALYTICAL_ERROR);
    }

    @Test
    public void test_alternative(){
        StoModel model = mf.createStoModel();

        Sequence seq1 = mf.createSequence();
        seq1.addStoAction(mf.createAction(dtAlmost3));
        seq1.addStoAction(mf.createAction(dtAlmost3));

        Alternative alter = mf.createAlternative();
        alter.addStoAction(seq1, new Probability(0.1));
        alter.addStoAction(mf.createAction(dtAlmost2), new Probability(0.9));
            model.addStoAction(alter);

        StoModellingResult result = model.analyticalRun();

        assertEquals(2.39, result.getExpectedValue().getValue(), ANALYTICAL_ERROR);
        assertEquals(21.96, result.getVariance().getValue(), ANALYTICAL_ERROR);
    }

    @Test
    public void test_parallel(){
        StoModel model = mf.createStoModel();

        Parallel par = mf.createParallel();
        par.addStoAction(mf.createAction(dtAlmost4));
        par.addStoAction(mf.createAction(dtAlmost3));

        Alternative alter = mf.createAlternative();
        alter.addStoAction(mf.createAction(dtAlmost3), new Probability(0.01));
        alter.addStoAction(mf.createAction(dtAlmost2), new Probability(0.99));

        par.addStoAction(alter);
        model.addStoAction(par);

        // check N of N
        StoModellingResult result = model.analyticalRun();
        assertEquals(3.98, result.getExpectedValue().getValue(), ANALYTICAL_ERROR);
        assertEquals(0.02, result.getVariance().getValue(), ANALYTICAL_ERROR);

        // check 1 of N
        par.setCountOfWaitedResults(1);
        result = model.analyticalRun();
        assertEquals(2.0, result.getExpectedValue().getValue(), ANALYTICAL_ERROR);
        assertEquals(5.02, result.getVariance().getValue(), ANALYTICAL_ERROR);

        // check 2 of N
        par.setCountOfWaitedResults(2);
        result = model.analyticalRun();
        assertEquals(2.97, result.getExpectedValue().getValue(), ANALYTICAL_ERROR);
        assertEquals(1.09, result.getVariance().getValue(), ANALYTICAL_ERROR);
    }


    @Test
    public void test_alternative_bpel_like(){
        StoModel model = mf.createStoModel();

        Sequence mainSeq = mf.createSequence();
        model.addStoAction(mainSeq);

        Action invoke0 = mf.createAction(dtAlmost1);

        mainSeq.addStoAction(invoke0);

        Alternative if1 = mf.createAlternative();
        Action invoke1 = mf.createAction(dtAlmost1);
        Action invoke2 = mf.createAction(dtAlmost1);
        Action invokeM = mf.createAction(dtAlmost1);
        Action invokeN = mf.createAction(dtAlmost1);

        if1.addStoAction(invoke1, new Probability(1.0/4));
        if1.addStoAction(invoke2, new Probability(1.0/4));
        if1.addStoAction(invokeM, new Probability(1.0/4));
        if1.addStoAction(invokeN, new Probability(1.0/4));
        if1.validate();

        mainSeq.addStoAction(if1);

        Action invokeNp1 = mf.createAction(dtAlmost1);
        mainSeq.addStoAction(invokeNp1);

        StoModellingResult result = model.analyticalRun();
        assertEquals(3.09, result.getExpectedValue().getValue(), ANALYTICAL_ERROR);
        assertEquals(87.25, result.getVariance().getValue(), ANALYTICAL_ERROR);
    }


    @Test
    public void test_alternative_bpel_real() throws Exception {
        StoModeller m = new StoModeller();
        StoModellerParams params = new StoModellerParams();
        params.setBpelFile("../Samples/src/Proc1ofN.bpel");
        params.setConfigFile("./stoMC-AlterBpel.xml");
        StoModellingResult result = m.run(params);

        assertEquals(3.09, result.getExpectedValue().getValue(), ANALYTICAL_ERROR);
        assertEquals(87.25, result.getVariance().getValue(), ANALYTICAL_ERROR);
    }

    @Test
    public void test_N_of_N_bpel_like(){
        StoModellingResult result = test_M_of_N_like_template(4);
        assertEquals(3.18, result.getExpectedValue().getValue(), ANALYTICAL_ERROR);
        assertEquals(83.72, result.getVariance().getValue(), ANALYTICAL_ERROR);
    }

    @Test
    public void test_1_of_N_bpel_like(){
        StoModellingResult result = test_M_of_N_like_template(1);
        assertEquals(3.06, result.getExpectedValue().getValue(), ANALYTICAL_ERROR);
        assertEquals(88.49, result.getVariance().getValue(), ANALYTICAL_ERROR);
    }

    @Test
    public void test_2_of_N_bpel_like(){
        StoModellingResult result = test_M_of_N_like_template(2);
        assertEquals(3.06, result.getExpectedValue().getValue(), ANALYTICAL_ERROR);
        assertEquals(88.48, result.getVariance().getValue(), ANALYTICAL_ERROR);
    }

    @Test
    public void test_3_of_N_bpel_like(){
        StoModellingResult result = test_M_of_N_like_template(3);
        assertEquals(3.06, result.getExpectedValue().getValue(), ANALYTICAL_ERROR);
        assertEquals(88.36, result.getVariance().getValue(), ANALYTICAL_ERROR);
    }

    private StoModellingResult test_M_of_N_like_template(int M) {
        StoModel model = mf.createStoModel();

        Sequence mainSeq = mf.createSequence();
        model.addStoAction(mainSeq);

        Action invoke0 = mf.createAction(dtAlmost1);

        mainSeq.addStoAction(invoke0);

        Parallel parallel = mf.createParallel();
        Action invoke1 = mf.createAction(dtAlmost1);
        Action invoke2 = mf.createAction(dtAlmost1);
        Action invokeM = mf.createAction(dtAlmost1);
        Action invokeN = mf.createAction(dtAlmost1);

        parallel.addStoAction(invoke1);
        parallel.addStoAction(invoke2);
        parallel.addStoAction(invokeM);
        parallel.addStoAction(invokeN);

        parallel.setCountOfWaitedResults(M);

        mainSeq.addStoAction(parallel);

        Action invokeNp1 = mf.createAction(dtAlmost1);
        mainSeq.addStoAction(invokeNp1);

        return model.analyticalRun();
    }


    private StoModellingResult test_M_of_N_bpel_real_template(String confFile) throws StoModellerException {
        StoModeller m = new StoModeller();
        StoModellerParams params = new StoModellerParams();
        params.setBpelFile("../Samples/src/ProcMofN.bpel");
        params.setConfigFile(confFile);
        return m.run(params);
    }

    @Test
    public void test_N_of_N_bpel_real() throws Exception {
        StoModellingResult result = test_M_of_N_bpel_real_template("./stoMC-NofNBpel.xml");
        assertEquals(3.18, result.getExpectedValue().getValue(), ANALYTICAL_ERROR);
        assertEquals(83.72, result.getVariance().getValue(), ANALYTICAL_ERROR);
    }

    @Test
    public void test_1_of_N_bpel_real() throws Exception {
        StoModellingResult result = test_M_of_N_bpel_real_template("./stoMC-1ofNBpel.xml");
        assertEquals(3.06, result.getExpectedValue().getValue(), ANALYTICAL_ERROR);
        assertEquals(88.49, result.getVariance().getValue(), ANALYTICAL_ERROR);
    }

    @Test
    public void test_2_of_N_bpel_real() throws Exception {
        StoModellingResult result = test_M_of_N_bpel_real_template("./stoMC-2ofNBpel.xml");
        assertEquals(3.06, result.getExpectedValue().getValue(), ANALYTICAL_ERROR);
        assertEquals(88.48, result.getVariance().getValue(), ANALYTICAL_ERROR);
    }
    @Test
    public void test_3_of_N_bpel_real() throws Exception {
        StoModellingResult result = test_M_of_N_bpel_real_template("./stoMC-3ofNBpel.xml");
        assertEquals(3.06, result.getExpectedValue().getValue(), ANALYTICAL_ERROR);
        assertEquals(88.36, result.getVariance().getValue(), ANALYTICAL_ERROR);
    }
}
