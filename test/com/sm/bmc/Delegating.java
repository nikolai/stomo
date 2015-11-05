package com.sm.bmc;

/**
 * User: mikola
 * Date: 05.11.15
 * Time: 15:31
 */
public interface Delegating<T> {
    T getDelegate();
}
