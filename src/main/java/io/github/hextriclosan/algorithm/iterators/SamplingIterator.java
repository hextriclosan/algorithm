package io.github.hextriclosan.algorithm.iterators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * This iterator creates random samples of a given size from the input {@code List}.
 * The algorithm preserves original order of elements.
 * The {@code remove()} operation is not supported, and will throw an {@code UnsupportedOperationException}.
 *
 * @param <E> the type of the objects being sampled
 */
public class SamplingIterator<E> implements Iterator<List<E>> {
    private final List<? extends E> toSample;
    private final int sampleSize;
    private final List<Integer> index;

    private List<E> nextSample;
    private int start;

    /**
     * Standard constructor for this class.
     *
     * @param toSample list to make samples from
     * @param sampleSize size of the sample
     * @param random the source of randomness
     *
     * @throws IllegalArgumentException if sampleSize is not positive.
     * @throws NullPointerException if either toSample or random is null
     */
    public SamplingIterator(List<? extends E> toSample, int sampleSize, Random random) {
        validate(toSample, sampleSize, random);
        this.toSample = toSample;
        this.sampleSize = sampleSize;
        index = IntStream.range(0, toSample.size())
                .boxed()
                .collect(Collectors.collectingAndThen(Collectors.toCollection(ArrayList::new),
                        collected -> {
                            Collections.shuffle(collected, random);
                            return collected;
                        }));

        start = 0;
        nextSample();
    }

    /**
     * Constructor for this class.
     *
     * @param toSample list to make samples from
     * @param sampleSize size of the sample
     *
     * @throws IllegalArgumentException if sampleSize is not positive.
     * @throws NullPointerException if toSample is null
     */
    public SamplingIterator(List<? extends E> toSample, int sampleSize) {
        this(toSample, sampleSize, new Random());
    }

    /**
     * Indicates if there are more samples available.
     *
     * @return true if there are more samples, otherwise false
     */
    @Override
    public boolean hasNext() {
        return nextSample != null;
    }

    /**
     * Returns the next sample of the input collection.
     *
     * @return a list of the randomly sampling elements
     * @throws NoSuchElementException if there are no more samples
     */
    @Override
    public List<E> next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        final List<E> result = nextSample;
        nextSample();

        return result;
    }

    /**
     * Operation is not supported, and will throw an {@code UnsupportedOperationException}.
     */
    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SamplingIterator<?> that = (SamplingIterator<?>) o;
        return sampleSize == that.sampleSize && toSample.equals(that.toSample);
    }

    @Override
    public int hashCode() {
        return Objects.hash(toSample, sampleSize);
    }

    private void nextSample() {
        if (start >= toSample.size()) {
            nextSample = null;
            return;
        }

        final int len = Math.min(sampleSize, toSample.size() - start);
        nextSample = index.subList(start, start + len).stream()
                .sorted()
                .map(toSample::get)
                .collect(Collectors.toCollection(ArrayList::new));

        start += len;
    }

    private void validate(Collection<? extends E> collection, int sampleSize, Random random) {
        Objects.requireNonNull(collection, "collection");
        Objects.requireNonNull(random, "random");
        if (sampleSize <= 0) {
            throw new IllegalArgumentException("sampleSize should be positive");
        }
    }
}
