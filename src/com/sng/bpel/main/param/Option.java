package com.sng.bpel.main.param;

/**
 * User: mikola
 * Date: 07.05.15
 * Time: 18:01
 */
public class Option {
    private final String name;
    private boolean existed;

    public Option(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isExisted() {
        return existed;
    }

    void setExisted(boolean existed) {
        this.existed = existed;
    }
}
