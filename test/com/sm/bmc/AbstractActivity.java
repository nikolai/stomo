package com.sm.bmc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * User: mikola
 * Date: 05.11.15
 * Time: 15:32
 */
public abstract class AbstractActivity<T> implements Delegating<T>{
    private final T delegate;
    private List<String> readVars = new ArrayList<>();
    private List<String> writeVars = new ArrayList<>();

    protected AbstractActivity(T delegate) {
        this.delegate = delegate;
    }

    public T getDelegate() {
        return delegate;
    }

    public List<String> getReadVars(){
        return readVars;
    }

    public List<String> getWriteVars(){
        return writeVars;
    }

    public AbstractActivity<T> setReadVars(String ... vars){
        readVars.clear();
        readVars.addAll(Arrays.asList(vars));
        return this;
    }

    public AbstractActivity<T> setWriteVars(String... vars){
        writeVars.clear();
        writeVars.addAll(Arrays.asList(vars));
        return this;
    }
}
