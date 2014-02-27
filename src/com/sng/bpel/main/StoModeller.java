package com.sng.bpel.main;

import com.sm.DFCreatorFactory;
import com.sm.DistributionFunction;
import com.sm.DistributionTable;
import com.sm.Probability;
import com.sm.bpelmodeller.BpelModeller;
import com.sm.bpelmodeller.config.BpelModellingConfigFactory;
import com.sm.bpelmodeller.config.xsd.StoModelConfig;
import com.sm.logging.LogService;
import com.sm.model.StoModellingResult;
import com.sm.model.impl.StoModellingResultImpl;
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

            if (params.isUseMonteCarlo()) {
                DFCreatorFactory.getInstance().switchToMonteCarlo();
            }

            BpelModeller modeller = BpelModeller.getDefault(config);

            if (params.getRunCount() > 1) {
                runManyTimes(params, modeller, process);
                return null;
            }

            long start = System.currentTimeMillis();
            StoModellingResult result = modeller.analyse(process);
            LogService.get().log("Modelling time: " + (System.currentTimeMillis()-start));

            log.info("Modelling result: " + result);
            if (params.isUseMonteCarlo()) {
                double mean = result.getExpectedValue().getValue();
                log.info("95% confidence interval: ["+
                        Probability.round(mean - result.getCIDelta()) + ", " +
                        Probability.round(mean + result.getCIDelta()) + "]");
            }

            if (params.getRiskTime() != null) {
                log.info("Risk of excess " + params.getRiskTime() + ": " + result.evalRisk(params.getRiskTime()));
            }
            return result;
        } catch (Exception e) {
            throw new StoModellerException(e);
        }
    }

    private void runManyTimes(StoModellerParams params, BpelModeller modeller, TProcess process) {
        double meanTime = 0;
        for (int i=0; i < params.getRunCount(); i++) {
            long start = System.currentTimeMillis();
            modeller.analyse(process);
            meanTime += System.currentTimeMillis()-start;
        }
        LogService.get().warning("Average modelling time of "+params.getRunCount()+" executions: "
                + (meanTime/params.getRunCount()));
    }


}
