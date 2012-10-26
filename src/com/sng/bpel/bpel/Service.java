package com.sng.bpel.bpel;

/**
 * User: smirnov-n
 * Date: 10.10.11
 * Time: 17:23
 */
public interface Service<T> {
    public T invoke(String arg);
}
