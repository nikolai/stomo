package com.sm.model;

/**
 * User: mikola
 * Date: 26.10.12
 * Time: 17:43
 */
public interface ActionContainer extends Action {
    ActionContainer addStoAction(Action action);
}
