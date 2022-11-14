package io.github.hextriclosan.algorithm.iterators;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SamplingIteratorTest {
    private static final int SEED = 0;
    private static final List<Character> LIST = Arrays.asList('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H');

    private Random random;

    @BeforeEach
    void startUp() {
        random = new Random(SEED);
    }

    @Test
    void shouldReturnAllSamples() {
        Iterator<List<Character>> samplingIterator = new SamplingIterator<>(LIST, 3, random);

        assertTrue(samplingIterator.hasNext());
        assertEquals(Arrays.asList('A', 'D', 'H'), samplingIterator.next());

        assertTrue(samplingIterator.hasNext());
        assertEquals(Arrays.asList('B', 'E', 'G'), samplingIterator.next());

        assertTrue(samplingIterator.hasNext());
        assertEquals(Arrays.asList('C', 'F'), samplingIterator.next());

        assertFalse(samplingIterator.hasNext());
    }

    @Test
    void shouldReturnAllSamplesMinimalSize() {
        Iterator<List<Character>> samplingIterator = new SamplingIterator<>(LIST, 1, random);

        assertTrue(samplingIterator.hasNext());
        assertEquals(Collections.singletonList('A'), samplingIterator.next());

        assertTrue(samplingIterator.hasNext());
        assertEquals(Collections.singletonList('H'), samplingIterator.next());

        assertTrue(samplingIterator.hasNext());
        assertEquals(Collections.singletonList('D'), samplingIterator.next());

        assertTrue(samplingIterator.hasNext());
        assertEquals(Collections.singletonList('E'), samplingIterator.next());

        assertTrue(samplingIterator.hasNext());
        assertEquals(Collections.singletonList('G'), samplingIterator.next());

        assertTrue(samplingIterator.hasNext());
        assertEquals(Collections.singletonList('B'), samplingIterator.next());

        assertTrue(samplingIterator.hasNext());
        assertEquals(Collections.singletonList('C'), samplingIterator.next());

        assertTrue(samplingIterator.hasNext());
        assertEquals(Collections.singletonList('F'), samplingIterator.next());

        assertFalse(samplingIterator.hasNext());
    }

    @Test
    void shouldNotModifyUnderlyingCollection() {
        List<Character> toSample = new ArrayList<>(LIST);

        Iterator<List<Character>> samplingIterator = new SamplingIterator<>(toSample, 3, random);

        samplingIterator.forEachRemaining(sample -> assertFalse(sample.isEmpty()));

        assertEquals(LIST, toSample);
    }

    @Test
    void shouldReturnAllCollectionWhenSampleSizeIsGreaterThanCollectionSize() {
        Iterator<List<Character>> samplingIterator = new SamplingIterator<>(LIST, 10000, random);

        assertTrue(samplingIterator.hasNext());
        assertEquals(LIST, samplingIterator.next());

        assertFalse(samplingIterator.hasNext());
    }

    @Test
    void shouldReturnNoResultForEmptyList() {
        Iterator<List<Character>> samplingIterator = new SamplingIterator<>(Collections.emptyList(), 10000, random);

        assertFalse(samplingIterator.hasNext());
    }

    @Test
    void shouldThrowNoSuchElementExceptionWhenNoElementsToReturn() {
        Iterator<List<Character>> samplingIterator = new SamplingIterator<>(LIST, 10000, random);

        assertTrue(samplingIterator.hasNext());
        assertEquals(LIST, samplingIterator.next());

        assertFalse(samplingIterator.hasNext());
        assertThrows(NoSuchElementException.class, samplingIterator::next);
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenSampleSizeIsNotPositive() {
        assertThrows(IllegalArgumentException.class, () -> new SamplingIterator<>(LIST, 0, random));
    }

    @Test
    void shouldWorkWithLargeCollection() {
        int collectionSize = 100000;
        int sampleSize = 1000;
        List<Integer> underlyingCollection = IntStream.range(0, collectionSize).boxed().collect(toList());
        Iterator<List<Integer>> iterator = new SamplingIterator<>(underlyingCollection, sampleSize, random);

        AtomicInteger expectedSamplesCount = new AtomicInteger();
        Set<Integer> index = new HashSet<>();
        iterator.forEachRemaining(lst -> {
            index.addAll(lst);
            expectedSamplesCount.incrementAndGet();
        });

        assertEquals(100, expectedSamplesCount.get());
        assertEquals(collectionSize, index.size());
    }
}
