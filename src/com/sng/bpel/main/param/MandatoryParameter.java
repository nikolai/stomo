package com.sng.bpel.main.param;

/**
 * User: mikola
 * Date: 18.07.15
 * Time: 18:44
 */
public abstract class MandatoryParameter<T> {
    private final String shortDesc;
    private T value;

    protected MandatoryParameter(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public static IntegerParameter createIntParameter(String shortDesc) {
        return new IntegerParameter(shortDesc);
    }

    public static StringParameter createStringParameter(String shortDesc) {
        return new StringParameter(shortDesc);
    }

    void setRawValue(String argumentString) {
        value = parseValue(argumentString);
    }

    protected abstract T parseValue(String argumentString);

    public T getValue() {
        return value;
    }

    public String getShortDesc() {
        return shortDesc;
    }
}
