package com.sng.bpel.main;

import com.sng.bpel.util.BpelFactory;

import java.io.File;

/**
 * User: mikola
 * Date: 26.10.12
 * Time: 17:11
 */
public class StoModeller {

    public void run(StoModellerParams params) throws StoModellerException {
        try {
            String bpelFilePath = params.getBpelFile();
            File bpelFile = new File(bpelFilePath);

            Object process = BpelFactory.getDefault().createProcess(bpelFile);
        } catch (Exception e) {
            throw new StoModellerException(e);
        }
    }
}
