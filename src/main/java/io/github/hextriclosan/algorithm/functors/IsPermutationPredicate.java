package io.github.hextriclosan.algorithm.functors;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.function.BiPredicate;

/**
 * Predicate implementation that checks if there exists a permutation of the first list elements that makes
 * that range equal to the range
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
        this.predicate = predicate != null ? predicate : Objects::equals;
    }

    /**
     * Checks if one list is permutation of another
     *
     * @param list1 the first list
     * @param list2 the second list
     *
     * @return true if one list is permutation of another, false otherwise
     */
    @Override
    public boolean test(List<E> list1, List<E> list2) {
        if (list1.size() != list2.size()) {
            return false;
        }

        int index1 = 0;
        int index2 = 0;
        //  shorten sequences as much as possible by lopping of any equal prefix
        for (; index1 < list1.size(); ++index1, ++index2) {
            if (!predicate.test(list1.get(index1), list2.get(index2))) {
                break;
            }
        }

        for (int i = index1; i < list1.size(); ++i) {
            // Have we already counted the number of i in [ index1, list1.size() )?
            int match = index1;
            for (; match != i; ++match) {
                if (predicate.test(list1.get(match), list1.get(i))) {
                    break;
                }
            }

            if (match == i) {

                // Count number of i in [ index2, list2.size() )
                int c2 = 0;
                for (int j = index2; j < list2.size(); ++j) {
                    if (predicate.test(list1.get(i), list2.get(j))) {
                        ++c2;
                    }
                }
                if (c2 == 0) {
                    return false;
                }

                // Count number of i in [ i, list1.size() ) (we can start with 1)
                int c1 = 1;
                for (int j = i + 1; j < list1.size(); ++j) {
                    if (predicate.test(list1.get(i), list1.get(j))) {
                        ++c1;
                    }
                }
                if (c1 != c2) {
                    return false;
                }

            }

        }

        return true;
    }

}
