package com.sm.bpelenhancer;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * User: mikola
 * Date: 26.09.15
 * Time: 21:08
 */
public enum Naming {
    flow,
    assign,
    sequence,
    invoke,
    process,
    receive, reply;

    private static Map<Naming, AtomicInteger> counts = new HashMap<>();

    public String nextName() {
        if (!counts.containsKey(this)) {
            counts.put(this, new AtomicInteger(0));
        }

        return this.toString()+"_"+(counts.get(this)).incrementAndGet();
    }
}
