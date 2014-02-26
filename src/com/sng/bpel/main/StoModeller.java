package com.sng.bpel.main;

import com.sm.bpelmodeller.BpelModeller;
import com.sm.bpelmodeller.config.BpelModellingConfigFactory;
import com.sm.bpelmodeller.config.xsd.StoModelConfig;
import com.sm.logging.LogService;
import com.sm.model.StoModellingResult;
import com.sm.util.XmlUtil;
import org.oasis_open.docs.wsbpel._2_0.process.executable.ObjectFactory;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TProcess;

import java.io.File;
import java.util.logging.Logger;

/**
 * User: mikola
 * Date: 26.10.12
 * Time: 17:11
 */
public class StoModeller {
    private static Logger log = LogService.get();

    public StoModellingResult run(StoModellerParams params) throws StoModellerException {
        try {
            String bpelFilePath = params.getBpelFile();
            File bpelFile = new File(bpelFilePath);

            TProcess process = XmlUtil.unmarshall(bpelFile.getAbsolutePath(), TProcess.class, ObjectFactory.class);
            log.config("Process read from "+bpelFilePath+": " + process.getName());


            String confFile = params.getConfigFile();
            log.config("Read configuration from "+confFile);
            StoModelConfig config = BpelModellingConfigFactory.getOne().readConfig(confFile);


            BpelModeller modeller = BpelModeller.getDefault(config);
            StoModellingResult result = modeller.analyse(process);
            log.info("Modelling result: " + result);
            if (params.getRiskTime() != null) {
                log.info("Risk of excess " + params.getRiskTime() + ": " + result.evalRisk(params.getRiskTime()));
            }
            //LogService.get().stop();
            return result;
        } catch (Exception e) {
            throw new StoModellerException(e);
        }
    }
}
