package com.sm;

/**
 * User: mikola
 * Date: 24.02.14
 * Time: 17:53
 */
public class DFCreatorFactory {
    private volatile boolean isAnalytical = true;
    private static final DFCreatorFactory instance = new DFCreatorFactory();
    private DFCreatorFactory(){}

    public static DFCreatorFactory getInstance() {
        return instance;
    }

    public void switchToAnalytical() {
        isAnalytical = true;
    }

    public void switchToMonteCarlo() {
        isAnalytical = false;
    }

    public DFCreator getCreator (){
        return isAnalytical ? AnalyticalDF.get() : ModellingDF.get();
    }

}
