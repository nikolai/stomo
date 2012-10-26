package com.sng.bpel.main;

/**
 * User: mikola
 * Date: 26.10.12
 * Time: 17:12
 */
public class StoModellerParams {
    private final String bpelFile;

    public StoModellerParams(String bpelFile) {
        this.bpelFile = bpelFile;
    }

    public String getBpelFile() {
        return bpelFile;
    }
}
