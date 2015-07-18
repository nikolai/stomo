package com.sng.bpel.main.param;

/**
 * User: mikola
 * Date: 18.07.15
 * Time: 18:47
 */
public class IntegerParameter extends MandatoryParameter<Integer> {
    public IntegerParameter(String shortDesc) {
        super(shortDesc);
    }

    @Override
    protected Integer parseValue(String argumentString) {
        return Integer.parseInt(argumentString);
    }
}
