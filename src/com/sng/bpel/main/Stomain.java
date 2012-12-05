package com.sng.bpel.main;

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
        m.run(params);
    }
}
