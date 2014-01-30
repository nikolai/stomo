package com.sm.bpelmodeller.config;

import com.sm.bpelmodeller.config.xsd.ObjectFactory;
import com.sm.bpelmodeller.config.xsd.StoModelConfig;
import com.sm.util.XmlUtil;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * User: mikola
 * Date: 05.11.12
 * Time: 20:58
 */
public class BpelModellingConfigFactory {
    private BpelModellingConfigFactory() {
    }

    public static BpelModellingConfigFactory getOne() {
        return new BpelModellingConfigFactory();
    }


    public StoModelConfig readConfig(String configFile) throws Exception {
        return XmlUtil.unmarshall(configFile, StoModelConfig.class, ObjectFactory.class);
    }

    public void saveConfig(String configFile, StoModelConfig config) throws Exception {
        XmlUtil.marshall(configFile, new ObjectFactory().createBpelStoModelConfiguration(config), ObjectFactory.class);
    }
}
