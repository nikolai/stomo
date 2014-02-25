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

        if (needHelp(args)) {
            usage();
            System.exit(0);
        }

        if (args.length > 0) {
            params.setBpelFile(args[0]);
        } else {error("missing path to bpel file!");}
        if (args.length > 1) {
            params.setConfigFile(args[1]);
        } else {error("missing config file");}

        if (args.length > 2) {
            for (int i = 2; i < args.length; i++) {
                if ("-montecarlo".equals(args[i])) {
                    DFCreatorFactory.getInstance().switchToMonteCarlo();
                    continue;
                }
                if ("-risk".equals(args[i])) {
                    try {
                        if (i < args.length-1) {
                            params.setRiskTime(Integer.parseInt(args[i+1]));
                        } else throw new IllegalArgumentException("missing time value for -risk");
                    } catch (Exception e) {
                        error(e.getMessage());
                    }
                }
            }
        }

        long start = System.currentTimeMillis();
        m.run(params);
        System.out.println("Modelling time: " + (System.currentTimeMillis()-start));
    }

    private static boolean needHelp(String[] args) {
        for (String a : args) {
            if (a.equals("-h") || a.equals("--help")|| a.equals("-?")) {
                return true;
            }
        }
        return false;
    }

    private static void error(String s) {
        System.out.println(s);
        usage();
        System.exit(1);
    }

    public static void usage() {
        System.out.println("java -jar stomo.jar <bpel> <config> [-montecarlo] [-risk <time>]");
    }
}
