package com.sm.bpelmodeller.config;

import java.io.File;

/**
 * User: mikola
 * Date: 05.11.12
 * Time: 20:58
 */
public class BpelModellingConfigfACTORY {
    private BpelModellingConfigfACTORY(String configFile) {
        if (configFile != null) {
            confFile = new File(configFile);

        }
    }

    BpelModellingConfigfACTORY getOne(String configFile) {
        return new BpelModellingConfigfACTORY(configFile)
    }
}
