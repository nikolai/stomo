package com.sm;

/**
 * User: smirnov-n
 * Date: 08.09.11
 * Time: 16:57
 */
public class DiscreteValue<T extends Comparable> implements Comparable<DiscreteValue<T>>{
    private final T value;

    public DiscreteValue(T value) {
        this.value = value;
    }

    public int compareTo(DiscreteValue<T> o) {
        return -o.value.compareTo(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DiscreteValue)) return false;

        DiscreteValue that = (DiscreteValue) o;

        if (value != null ? !value.equals(that.value) : that.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }

    public T getValue() {
        return value;
    }

}
