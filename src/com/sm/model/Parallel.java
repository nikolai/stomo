package com.sm.model;

/**
 * User: mikola
 * Date: 26.10.12
 * Time: 20:10
 */
public interface Parallel extends ActionContainer {
    /**
     * If this method is not invoked - process should wait for all results.
     * */
    void setCountOfWaitedResults(int count);
}
