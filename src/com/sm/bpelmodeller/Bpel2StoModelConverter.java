package com.sm.bpelmodeller;

import com.sm.bpelmodeller.config.ConfigHelper;
import com.sm.bpelmodeller.config.xsd.StoModelConfig;
import com.sm.bpelmodeller.smprocessors.DefaultProcessor;
import com.sm.bpelmodeller.smprocessors.FlowProcessor;
import com.sm.bpelmodeller.smprocessors.IfProcessor;
import com.sm.bpelmodeller.smprocessors.SequenceProcessor;
import com.sm.logging.LogService;
import com.sm.model.Action;
import com.sm.model.ModelFactory;
import com.sm.model.StoModel;
import com.sm.model.impl.DefaultModelFactory;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TFlow;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TIf;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TProcess;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TSequence;

/**
 * User: mikola
 * Date: 05.11.12
 * Time: 19:29
 */
public class Bpel2StoModelConverter {

    private final ModelFactory mf = DefaultModelFactory.getDefault();
    private final ActivityRunner<Action> activityRunner;

    private Bpel2StoModelConverter(StoModelConfig config){
        ConfigHelper configHelper = ConfigHelper.getOne(config);
        activityRunner = ActivityRunner.getOne(new DefaultProcessor(mf, configHelper));
        activityRunner.registerActivityProcessor(TSequence.class, new SequenceProcessor(mf));
        activityRunner.registerActivityProcessor(TIf.class, new IfProcessor(mf, configHelper));
        activityRunner.registerActivityProcessor(TFlow.class, new FlowProcessor(mf, configHelper));
    }
    public static Bpel2StoModelConverter getOne(StoModelConfig config){
        return new Bpel2StoModelConverter(config);
    }

    public StoModel convert(TProcess process) {
        LogService.get().config("Converting BPEL model to StoModel...");
        StoModel stoModel = mf.createStoModel();
        ChildActivitySelector.SelectedChild selected = ChildActivitySelector.getOne().selectChild(process);
        Action rootAction = activityRunner.goAhead(selected.getActivity());
        stoModel.addStoAction(rootAction);
        return stoModel;
    }

}
