package com.sm.bpelmodeller.config;

import com.sm.DistributionTable;
import com.sm.Probability;
import com.sm.bpelmodeller.config.xsd.*;
import com.sm.logging.LogService;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TActivity;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TFlow;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TIf;

/**
 * User: mikola
 * Date: 19.11.12
 * Time: 0:10
 */
public class ConfigHelper {
    private final StoModelConfig conf;

    private ConfigHelper(StoModelConfig conf) {
        this.conf = conf;
    }

    public static ConfigHelper getOne(StoModelConfig conf) {
        return new ConfigHelper(conf);
    }

    public DistributionTable<Integer> findDT(TActivity a) {
        for (TAtomicActivityConfig ac : conf.getActivityConfig()) {
            if (ac.getActivityName().equals(a.getName())) {
                int size = ac.getTimeDistribution().size();
                int[] times = new int[size];
                double[] probs = new double[size];

                int idx = 0;
                for (TTimeDistribution td : ac.getTimeDistribution()) {
                    times[idx] = td.getTime();
                    probs[idx++] = td.getProbability();
                }
                LogService.get().config("Config was found for " + a.getName());
                return new DistributionTable<Integer>(times, probs);
            }
        }
        //throw new IllegalArgumentException("Cannot find DT for activity '" + a.getName() + "'");
        return new DistributionTable<Integer>(new int[]{0}, new double[]{1});
    }

    public Probability findBranchProbability(TIf ifActivity) {
        for (TIfBranchConfig ifBranch : conf.getIfActivityConfig()) {
            if (ifBranch.getActivityName().equals(ifActivity.getName())) {
                LogService.get().config("Config was found for " + ifActivity.getName());
                return new Probability(ifBranch.getProbabilityOfBranch());
            }
        }
        return new Probability(1);
    }

    public Long findCountOfWaitedResults(TFlow a) {
        for (TFlowActivityConfig flowConfig : conf.getFlowActivityConfig()) {
            if (flowConfig.getActivityName().equals(a.getName())){
                LogService.get().config("Config was found for " + a.getName());
                return flowConfig.getCountOfWaitedResults();
            }
        }
        return null;
    }
}
