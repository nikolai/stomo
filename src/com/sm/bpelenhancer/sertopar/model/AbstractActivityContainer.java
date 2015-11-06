package com.sm.bpelenhancer.sertopar.model;

import com.sm.bpelenhancer.sertopar.VisibleVar;

import java.util.List;

/**
 * User: mikola
 * Date: 31.01.15
 * Time: 19:39
 */
public interface AbstractActivityContainer {
    public void setVisibleVars(VisibleVar... vars);
    public List<VisibleVar> getReadVars(); // filtered by VisibleVars
    public List<VisibleVar> getModifiedVars();
    public boolean isLongActivity();
}
