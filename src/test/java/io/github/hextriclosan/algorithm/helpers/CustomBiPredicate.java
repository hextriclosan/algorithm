package io.github.hextriclosan.algorithm.helpers;

import java.util.Objects;
import java.util.function.BiPredicate;

public class CustomBiPredicate<T, U> implements BiPredicate<T, U> {

    private final int id;

    public CustomBiPredicate(int id) {
        this.id = id;
    }

    @Override
    public boolean test(T t, U u) {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomBiPredicate that = (CustomBiPredicate) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
