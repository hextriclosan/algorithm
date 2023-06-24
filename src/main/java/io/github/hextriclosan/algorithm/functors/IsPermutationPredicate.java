package io.github.hextriclosan.algorithm.functors;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.function.BiPredicate;

/**
 * Predicate implementation that checks if there exists a permutation of the first list elements that makes
 * that range equal to the range
 *
 * The worst-case complexity is O(N^2)
 *
 * @param <E> the type of the objects being checked
 */
public class IsPermutationPredicate<E> implements BiPredicate<List<E>, List<E>>, Serializable {

    /**
     * Serialization version.
     */
    private static final long serialVersionUID = -6071571875873472433L;

    /**
     * The predicate used to check equality of checked lists elements
     */
    private final BiPredicate<? super E, ? super E> predicate;

    /**
     * Standard constructor for this class.
     *
     * Natural elements equality is used for comparing.
     */
    public IsPermutationPredicate() {
        this(null);
    }

    /**
     * Constructor with custom predicate
     *
     * @param predicate the custom predicate used to check equality,
     *        or null if it uses the natural equality.
     */
    public IsPermutationPredicate(BiPredicate<? super E, ? super E> predicate) {
        //this.predicate = predicate != null ? predicate : Objects::equals;
        this.predicate = predicate;
    }

    /**
     * Checks if one list is permutation of another
     *
     * @param first the first list
     * @param second the second list
     *
     * @return true if one list is permutation of another, false otherwise
     */
    @Override
    public boolean test(List<E> first, List<E> second) {
        if (first.size() != second.size()) {
            return false;
        }

        int firstIndex = 0;
        int secondIndex = 0;
        //  shorten sequences as much as possible by lopping of any equal prefix
        for (; firstIndex < first.size(); ++firstIndex, ++secondIndex) {
            if (!testElements(first.get(firstIndex), second.get(secondIndex))) {
                break;
            }
        }

        for (int i = firstIndex; i < first.size(); ++i) {
            // Have we already counted the number of i in [ firstIndex, first.size() )?
            int match = firstIndex;
            for (; match != i; ++match) {
                if (testElements(first.get(match), first.get(i))) {
                    break;
                }
            }

            if (match == i) {

                // Count number of i in [ secondIndex, second.size() )
                int secondCounter = 0;
                for (int j = secondIndex; j < second.size(); ++j) {
                    if (testElements(first.get(i), second.get(j))) {
                        ++secondCounter;
                    }
                }
                if (secondCounter == 0) {
                    return false;
                }

                // Count number of i in [ i, first.size() ) (we can start with 1)
                int firstCounter = 1;
                for (int j = i + 1; j < first.size(); ++j) {
                    if (testElements(first.get(i), first.get(j))) {
                        ++firstCounter;
                    }
                }
                if (firstCounter != secondCounter) {
                    return false;
                }

            }

        }

        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        IsPermutationPredicate<?> that = (IsPermutationPredicate<?>) o;
        return Objects.equals(predicate, that.predicate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(predicate);
    }

    private boolean testElements(E e1, E e2) {
        return predicate == null
                ? Objects.equals(e1, e2)
                : predicate.test(e1, e2);
    }

}
