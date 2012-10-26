package com.sng.bpel.bpengine;

import java.util.List;

/**
 * User: mikola
 * Date: 26.10.12
 * Time: 16:20
 */
public interface ElementContainer extends BpelElement {
    void addActivity(Activity a);
    List<Activity> getActivities();
}
