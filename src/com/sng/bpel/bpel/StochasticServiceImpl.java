package com.sng.bpel.bpel;

import java.util.concurrent.ConcurrentHashMap;

/**
 * User: smirnov-n
 * Date: 10.10.11
 * Time: 17:32
 */
public class StochasticServiceImpl implements Service<String> {
    private static ConcurrentHashMap<String,Service<String>> serviceRepository = new ConcurrentHashMap<String, Service<String>>();
    private final NamedValueGenerator generator;

    private StochasticServiceImpl(NamedValueGenerator generator) {
        this.generator = generator;
    }

    public static Service<String> get(NamedValueGenerator generator) {
        StochasticServiceImpl service = new StochasticServiceImpl(generator);
        Service<String> oldService = serviceRepository.putIfAbsent(generator.getName(), service);
        return oldService == null ? service : oldService;
    }

    public String invoke(String arg) {
        int processDelay = Integer.parseInt(generator.getNext().getValue() + "");
        try {
            Thread.sleep(processDelay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return arg + "-" + String.valueOf(processDelay);
    }
}
