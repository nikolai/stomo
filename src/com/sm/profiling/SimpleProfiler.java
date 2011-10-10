package com.sm.profiling;

/**
 * User: smirnov-n
 * Date: 12.09.11
 * Time: 19:15
 */
public class SimpleProfiler extends AbstractProfiler {
    private final long startTime;
    private long estimated;

    private SimpleProfiler(String name, long startTime) {
        super(name);
        this.startTime = startTime;
    }

    public static SimpleProfiler start(String name) {
        return new SimpleProfiler(name, currentTime());
    }

    @Override
    public long getTotalTime() {
        return estimated;
    }

    @Override
    public void finishPrint() {
        stop();
        super.finishPrint();
    }

    @Override
    public void start() {
        // nothing to do
    }

    @Override
    public void stop() {
        estimated = currentTime() - startTime;
    }
}
