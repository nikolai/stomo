package com.sm.bpelmodeller;

import com.sm.bpelmodeller.config.xsd.StoModelConfig;
import com.sm.logging.LogService;
import com.sm.model.*;
import com.sm.model.impl.DefaultModelFactory;
import org.oasis_open.docs.wsbpel._2_0.process.executable.*;

/**
 * User: mikola
 * Date: 05.11.12
 * Time: 19:29
 */
public class Bpel2StoModelConverter {

    private final ModelFactory mf = DefaultModelFactory.getDefault();

    private Bpel2StoModelConverter(StoModelConfig config){
        ActivityProcessorFactory.getOne(config);
    }
    public static Bpel2StoModelConverter getOne(StoModelConfig config){
        return new Bpel2StoModelConverter(config);
    }

    public StoModel convert(TProcess process){
        LogService.get().debug("Converting Bpel to StoModel...");
        StoModel stoModel = mf.createStoModel();
        ChildActivitySelector.SelectedChild selected = (ChildActivitySelector.getOne().selectChild(process));
        Action baseAction = selected.getType().process(selected.getActivity());
        stoModel.addStoAction(baseAction);
        return stoModel;
    }

}
