package com.sng.system.bpel;

import org.junit.Test;

/**
 * User: smirnov-n
 * Date: 10.10.11
 * Time: 18:28
 */
public class LoaderTest {

    //@Test
    public void start_stop_test() throws InterruptedException {
        Loader loader = new Loader.LoaderBuilder().setClientCount(10).setExecutionCount(100).build();
        loader.start();
        loader.stop();
    }
}
