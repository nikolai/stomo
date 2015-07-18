package com.sng.bpel.main.param;

/**
 * User: mikola
 * Date: 07.05.15
 * Time: 18:01
 */
public abstract class AbstractValuedOption<T> extends Option {
    private final String valueShortDesc;
    private T value;

    public AbstractValuedOption(String name, String valueShortDesc) {
        super(name);
        this.valueShortDesc = valueShortDesc;
    }

    void setRawValue(String valueString) {
        this.value = parseValue(valueString);
    }

    public abstract T parseValue(String valueString);

    public T getValue() {
        return value;
    }

    public String getValueShortDesc() {
        return valueShortDesc;
    }
}
