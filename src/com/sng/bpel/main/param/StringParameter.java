package com.sng.bpel.main.param;

/**
 * User: mikola
 * Date: 18.07.15
 * Time: 18:46
 */
public class StringParameter extends MandatoryParameter<String> {
    public StringParameter(String shortDesc) {
        super(shortDesc);
    }

    @Override
    protected String parseValue(String argumentString) {
        return argumentString;
    }
}
