
package io.github.hextriclosan.algorithm.functors;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Predicate;


/**
 * Predicate implementation that checks if Iterable elements are sorted.
 *
 * @param <T> the type of the objects being compared
 */
public final class IsSortedPredicate<T> implements Predicate<Iterable<T>>, Serializable {

    /**
     * Serialization version.
     */
    private static final long serialVersionUID = 4163929177396440253L;

    /**
     * The custom comparator used to check order,
     * or null if it uses the natural ordering.
     */
    private final Comparator<? super T> comparator;

    /**
     * Standard constructor for this class.
     *
     * Natural elements ordering is used for comparing.
     */
    public IsSortedPredicate() {
        this(null);
    }

    /**
     * Constructor with custom comparator
     *
     * @param comparator the custom comparator used to check order,
     *        or null if it uses the natural ordering.
     */
    public IsSortedPredicate(Comparator<? super T> comparator) {
        this.comparator = comparator;
    }

    /**
     * Evaluates if given Iterable is sorted
     *
     * @param iterable Iterable to evaluate
     * @return true if sorted, false otherwise
     *
     * @throws NullPointerException if iterable or any of it elements are null
     */
    @Override
    public boolean test(Iterable<T> iterable) {
        Objects.requireNonNull(iterable, "iterable");
        final Iterator<T> iterator = iterable.iterator();

        T prev = null;
        if (iterator.hasNext()) {
            prev = iterator.next();
        }

        while (iterator.hasNext()) {
            T curr = iterator.next();
            if (compare(prev, curr) > 0) {
                return false;
            }

            prev = curr;
        }

        return true;
    }

    @SuppressWarnings("unchecked")
    private int compare(T t1, T t2) {
        return comparator == null
                ? ((Comparable<? super T>) t1).compareTo(t2)
                : comparator.compare(t1, t2);
    }
}
