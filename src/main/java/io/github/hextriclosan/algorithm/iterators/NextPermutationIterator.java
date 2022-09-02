package io.github.hextriclosan.algorithm.iterators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * This iterator creates permutations of an input collection, using the
 * lexicographical order.
 * <p>
 * The iterator might return less than n! permutations of the input collection,
 * because duplicated combinations are sorted out.
 * The {@code remove()} operation is not supported, and will throw an
 * {@code UnsupportedOperationException}.
 * <p>
 * NOTE: in case an empty collection is provided, the iterator will
 * return exactly one empty list as result, as 0! = 1.
 *
 * @param <E> the type of the objects being permuted
 */
public class NextPermutationIterator<E> implements Iterator<List<E>> {

    /**
     * The comparator used to define order of generation,
     * or null if it uses the natural ordering.
     */
    private final Comparator<? super E> comparator;

    /**
     * Next permutation to return. When a permutation is requested
     * this instance is provided and the next one is computed.
     */
    private List<E> nextPermutation;

    /**
     * Standard constructor for this class.
     *
     * @param collection the collection to generate permutations for
     * @throws NullPointerException if collection is null
     */
    public NextPermutationIterator(final Collection<? extends E> collection) {
        this(collection, null);
    }

    /**
     * Constructor with custom comparator
     *
     * @param collection the collection to generate permutations for
     * @param comparator the comparator used to define order of generation.
     *                   If null, the natural ordering of the elements will be used.
     * @throws NullPointerException if collection is null
     */
    public NextPermutationIterator(final Collection<? extends E> collection, final Comparator<? super E> comparator) {
        Objects.requireNonNull(collection, "collection");
        nextPermutation = new ArrayList<>(collection);
        this.comparator = comparator;
    }

    /**
     * Indicates if there are more permutation available.
     *
     * @return true if there are more permutations, otherwise false
     */
    @Override
    public boolean hasNext() {
        return nextPermutation != null;
    }

    /**
     * Returns the next permutation of the input collection.
     *
     * @return a list of the permutator's elements representing a permutation
     * @throws NoSuchElementException if there are no more permutations
     */
    @Override
    public List<E> next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        final int size = nextPermutation.size();
        List<E> nextP = null;

        int i = size - 2;
        for (; i >= 0 && compare(nextPermutation.get(i), nextPermutation.get(i + 1)) >= 0; --i) {
        }

        if (i >= 0) {
            int j = size - 1;
            for (; j >= i && compare(nextPermutation.get(i), nextPermutation.get(j)) >= 0; --j) {
            }

            nextP = new ArrayList<>(nextPermutation);
            Collections.swap(nextP, i, j);
            final List<E> subList = nextP.subList(i + 1, nextP.size());
            Collections.reverse(subList);
        }

        final List<E> result = nextPermutation;
        nextPermutation = nextP;
        return result;
    }

    /**
     * Operation is not supported, and will throw an {@code UnsupportedOperationException}.
     */
    @Override
    public void remove() {
        throw new UnsupportedOperationException("remove() is not supported");
    }

    @SuppressWarnings("unchecked")
    private int compare(E e1, E e2) {
        return comparator == null
                ? ((Comparable<? super E>) e1).compareTo(e2)
                : comparator.compare(e1, e2);
    }
}
