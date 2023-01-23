
package io.github.hextriclosan.algorithm.comparators;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.ToIntBiFunction;

/**
 * A Comparator that will compare Iterables in lexicographical order.
 *
 * @param <E> the type of the objects being compared
 */
public class LexicographicalComparator<E> implements Comparator<Iterable<E>>, Serializable {

    /**
     * Serialization version.
     */
    private static final long serialVersionUID = 5411973727779054539L;

    /**
     * The comparator to use when comparing elements of Iterable,
     * or null if it uses the natural ordering.
     **/
    private final ToIntBiFunction<? super E, ? super E> compareFunction;


    /**
     * Construct an instance that compares elements of Iterable in the natural order.
     **/
    public LexicographicalComparator() {
        this(null);
    }

    /**
     * Construct an instance that compares elements of Iterable with custom comparator.
     *
     * @param comparator the comparator used to compare elements of Iterable.
     *                   If null, the natural ordering of the elements will be used.
     */
    @SuppressWarnings("unchecked")
    public LexicographicalComparator(final Comparator<? super E> comparator) {
        compareFunction = comparator == null
                ? (e1, e2) -> ((Comparable<? super E>) e1).compareTo(e2)
                : comparator::compare;
    }

    /**
     * Perform a comparison between two Iterables.
     *
     * @param o1 the first Iterable to compare
     * @param o2 the Iterable to compare it to.
     * @return {@code -1} if {@code o1} is "lower" than (less than,
     * before, etc.) {@code o2}; {@code 1} if {@code o1} is
     * "higher" than (greater than, after, etc.) {@code o2}; or
     * {@code 0} if {@code o1} and {@code o2} are equal.
     **/
    @Override
    public int compare(final Iterable<E> o1, final Iterable<E> o2) {
        final Iterator<E> it1 = o1.iterator();
        final Iterator<E> it2 = o2.iterator();

        while (it1.hasNext() && it2.hasNext()) {
            final int compared = compareElements(it1.next(), it2.next());
            if (compared != 0) {
                return compared;
            }
        }

        return it1.hasNext() ? 1 : it2.hasNext() ? -1 : 0;
    }

    /**
     * Determines whether the specified object represents a comparator that is
     * equal to this comparator.
     *
     * @param obj the object to compare this comparator with.
     * @return {@code true} if the specified object is a LexicographicalComparator
     * with equivalent comparison behavior and with equivalent underlying object comparators.
     **/
    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(this.getClass())) {
            return false;
        }

        final LexicographicalComparator<?> other = (LexicographicalComparator<?>) obj;
        return compareFunction.equals(other.compareFunction);
    }

    /**
     * Implement a hash code for this comparator that is consistent with
     * {@link #equals(Object)}.
     *
     * @return a hash code for this comparator.
     **/
    @Override
    public int hashCode() {
        return Objects.hash(compareFunction);
    }

    private int compareElements(E e1, E e2) {
        return compareFunction.applyAsInt(e1, e2);
    }
}
