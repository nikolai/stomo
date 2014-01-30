package com.sm.bpelmodeller;

import com.sm.bpelmodeller.config.xsd.StoModelConfig;
import com.sm.model.StoModel;
import com.sm.model.StoModellingResult;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TProcess;

/**
 * User: mikola
 * Date: 26.10.12
 * Time: 17:32
 */
public class BpelModeller {

    private final StoModelConfig config;

    private BpelModeller(StoModelConfig config){
        this.config = config;
    }
    public static BpelModeller getDefault(StoModelConfig config) {
        return new BpelModeller(config);
    }

    public StoModellingResult analyse(TProcess process){
        StoModel stoModel = Bpel2StoModelConverter.getOne(config).convert(process);
        StoModellingResult modelRes = stoModel.analyticalRun();
        return modelRes;
    }

}
