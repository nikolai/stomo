package com.sng.bpel.main.param;

/**
 * User: mikola
 * Date: 18.07.15
 * Time: 18:32
 */
public class StringOption extends AbstractValuedOption<String> {
    public StringOption(String name, String valueShortDesc) {
        super(name, valueShortDesc);
    }

    @Override
    public String parseValue(String valueString) {
        return valueString;
    }
}
