package com.sng.bpel.main;

import com.sm.logging.LogService;

import java.util.logging.Level;

/**
 * User: mikola
 * Date: 26.10.12
 * Time: 17:10
 */
public class Stomain {
    private static enum ParamDic {
        MONTECARLO("-montecarlo"),
        RISK("-risk"),
        VERBOSE("-v");
        public final String key;

        ParamDic(String key) {
            this.key = key;
        }

        public static boolean contains(String key) {
            for (ParamDic v : values()) {
                if (v.key.equalsIgnoreCase(key)) return true;
            }
            return false;
        }
    }
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
                String p = args[i];

                if (!ParamDic.contains(p)){
                    error("unknown parameter " + p);
                }

                if (p.equals(ParamDic.VERBOSE.key)) {
                    LogService.get().setCurLogLevel(Level.FINE);
                }
                if (p.equals(ParamDic.MONTECARLO.key)) {
                    params.setUseMonteCarlo();
                    continue;
                }
                if (p.equals(ParamDic.RISK.key)) {
                    try {
                        if (i < args.length-1) {
                            try {
                                i++;
                                params.setRiskTime(Integer.parseInt(args[i]));
                            } catch (Exception e) {
                                throw new IllegalArgumentException("invalid time value for -risk: " + e.getMessage());
                            }
                        } else throw new IllegalArgumentException("missing time value for -risk");
                    } catch (Exception e) {
                        error(e.getMessage());
                    }
                }
            }
        }

        long start = System.currentTimeMillis();
        m.run(params);
        LogService.get().log("Modelling time: " + (System.currentTimeMillis()-start));
        LogService.get().stop();
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
