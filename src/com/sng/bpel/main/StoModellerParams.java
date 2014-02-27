package com.sng.bpel.main;

/**
 * User: mikola
 * Date: 26.10.12
 * Time: 17:12
 */
public class StoModellerParams {
    private String bpelFile;
    private String configFile;
    private Integer riskTime;
    private boolean switchToMonteCarlo;

    public StoModellerParams() {
    }

    public void setBpelFile(String bpelFile) {
        this.bpelFile = bpelFile;
    }

    public String getBpelFile() {
        return bpelFile;
    }

    public void setConfigFile(String configFile) {
        this.configFile = configFile;
    }

    public String getConfigFile() {
        return configFile;
    }

    public void setRiskTime(int riskTime) {
        this.riskTime = riskTime;
    }

    public Integer getRiskTime() {
        return riskTime;
    }

    public void setUseMonteCarlo() {
        this.switchToMonteCarlo = true;
    }

    public boolean isUseMonteCarlo() {
        return switchToMonteCarlo;
    }
}
