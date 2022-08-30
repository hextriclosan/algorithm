package io.github.hextriclosan.algorithm.helpers;

import java.util.Objects;

public class NonComparableObject<T> {
    public NonComparableObject(T value) {
        this.value = value;
    }

    private final T value;

    public T getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NonComparableObject that = (NonComparableObject) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
