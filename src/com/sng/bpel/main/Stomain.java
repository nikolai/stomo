package com.sng.bpel.main;

import com.sng.bpel.main.param.*;

import java.util.Arrays;

/**
 * User: mikola
 * Date: 26.10.12
 * Time: 17:10
 */
public class Stomain {
    private static Option MONTECARLO = new Option("-montecarlo");
    private static Option RISK = new IntegerOption("-risk", "ms");
    private static StringOption RUN_COUNT = new StringOption("-runcount", "N");
    private static Option VERBOSE = new Option("-v");
    private static StringParameter BPEL_FILE = MandatoryParameter.createStringParameter("bpelFile");
    private static StringParameter CONF_FILE = MandatoryParameter.createStringParameter("configFile");

    public static void main(String[] args) throws StoModellerException, AppArgumentReaderException {
        StoModeller m = new StoModeller();
        final StoModellerParams params = new StoModellerParams();

        AppArgumentReader argumentReader = new AppArgumentReader("java -jar stomo.jar",
                args, Arrays.asList(BPEL_FILE, CONF_FILE), MONTECARLO, RISK, RUN_COUNT, VERBOSE);

        if (!argumentReader.parse()) {
            System.exit(0);
        }

        params.setBpelFile(BPEL_FILE.getValue());
        params.setConfigFile(CONF_FILE.getValue());

        m.run(params);
//        LogService.get().stop();
    }

}
