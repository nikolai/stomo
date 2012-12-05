package com.sm.bpelmodeller;

import org.oasis_open.docs.wsbpel._2_0.process.executable.TProcess;

/**
 * User: mikola
 * Date: 26.10.12
 * Time: 17:32
 */
public class BpelModeller {

    private BpelModeller(){}
    public static BpelModeller getDefault() {
        return new BpelModeller();
    }

    public ModellerResult analyse(TProcess process){
        ModellerResult result = new ModellerResult();



        return result;
    }

}
