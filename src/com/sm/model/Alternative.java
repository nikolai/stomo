package com.sm.model;

import com.sm.Probability;

/**
 * User: mikola
 * Date: 26.10.12
 * Time: 19:40
 */
public interface Alternative extends ActionContainer {
    public ActionContainer addStoAction(Action action, Probability probability);

    void validate();
}
