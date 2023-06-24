package io.github.hextriclosan.algorithm.helpers;

import java.util.Comparator;

public class CustomComparator<T> implements Comparator<T> {

    private final int id;

    public CustomComparator(int id) {
        this.id = id;
    }

    @Override
    public int compare(T o1, T o2) {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomComparator<?> cmp = (CustomComparator<?>) o;
        return id == cmp.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
