package com.sm.profiling;

/**
 * User: smirnov-n
 * Date: 12.09.11
 * Time: 19:46
 */
public class AccumulatingProfiler extends AbstractProfiler{
    private long total;
    private long currentStart;

    private AccumulatingProfiler(String name) {
        super(name);
    }

    @Override
    public long getTotalTime() {
        return total;
    }

    public static AccumulatingProfiler init(String name) {
        return new AccumulatingProfiler(name);
    }

    public void start() {
        currentStart = currentTime();
    }

    @Override
    public void stop() {
        total += currentTime() - currentStart;
    }
}
