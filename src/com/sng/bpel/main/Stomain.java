package com.sng.bpel.main;

import com.sm.DFCreatorFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * User: mikola
 * Date: 26.10.12
 * Time: 17:10
 */
public class Stomain {
    public static void main(String[] args) throws StoModellerException {
        StoModeller m = new StoModeller();
        StoModellerParams params = new StoModellerParams();
        params.setBpelFile(args[0]);
        params.setConfigFile(args[1]);
        List argsList = Arrays.asList(args);

        if (argsList.contains("-montecarlo")) {
            DFCreatorFactory.getInstance().switchToMonteCarlo();
        } else {
            DFCreatorFactory.getInstance().switchToAnalytical();
        }

        m.run(params);
    }
}
