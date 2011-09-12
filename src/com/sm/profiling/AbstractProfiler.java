package com.sm.profiling;

/**
 * User: smirnov-n
 * Date: 12.09.11
 * Time: 19:51
 */
public abstract class AbstractProfiler {
    private final String name;

    protected AbstractProfiler(String name) {
        this.name = name;
    }

    public void finishPrint() {
        System.out.println(name + ":\t" + getTotalTime() + " ms");
    }

    public abstract long getTotalTime();
    public abstract void start();
    public abstract void stop();

    protected static long currentTime() {
        return System.currentTimeMillis();
    }
}
