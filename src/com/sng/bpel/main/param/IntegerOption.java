package com.sng.bpel.main.param;

/**
 * User: mikola
 * Date: 18.07.15
 * Time: 18:31
 */
public class IntegerOption extends AbstractValuedOption<Integer> {
    public IntegerOption(String name, String valueShortDesc) {
        super(name, valueShortDesc);
    }

    @Override
    public Integer parseValue(String valueString) {
        return Integer.parseInt(valueString);
    }
}
