package io.github.hextriclosan.algorithm.iterators;

import io.github.hextriclosan.algorithm.helpers.NonComparableObject;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NextPermutationIteratorTest {

    @Test
    void shouldReturnOneEmptyResultForEmptyList() {
        Iterator<List<Integer>> permutationIterator = new NextPermutationIterator<>(Collections.emptyList());

        assertTrue(permutationIterator.hasNext());
        assertTrue(permutationIterator.next().isEmpty());

        assertFalse(permutationIterator.hasNext());
    }

    @Test
    void shouldIterateOverAllPossiblePermutations() {
        Iterator<List<Character>> permutationIterator = new NextPermutationIterator<>(Arrays.asList('A', 'B', 'C'));

        assertTrue(permutationIterator.hasNext());
        assertEquals(Arrays.asList('A', 'B', 'C'), permutationIterator.next());

        assertTrue(permutationIterator.hasNext());
        assertEquals(Arrays.asList('A', 'C', 'B'), permutationIterator.next());

        assertTrue(permutationIterator.hasNext());
        assertEquals(Arrays.asList('B', 'A', 'C'), permutationIterator.next());

        assertTrue(permutationIterator.hasNext());
        assertEquals(Arrays.asList('B', 'C', 'A'), permutationIterator.next());

        assertTrue(permutationIterator.hasNext());
        assertEquals(Arrays.asList('C', 'A', 'B'), permutationIterator.next());

        assertTrue(permutationIterator.hasNext());
        assertEquals(Arrays.asList('C', 'B', 'A'), permutationIterator.next());

        assertFalse(permutationIterator.hasNext());
    }

    @Test
    void shouldSkipDuplicatedPermutations() {
        Iterator<List<Character>> permutationIterator = new NextPermutationIterator<>(Arrays.asList('A', 'A', 'B', 'B'));

        assertTrue(permutationIterator.hasNext());
        assertEquals(Arrays.asList('A', 'A', 'B', 'B'), permutationIterator.next());

        assertTrue(permutationIterator.hasNext());
        assertEquals(Arrays.asList('A', 'B', 'A', 'B'), permutationIterator.next());

        assertTrue(permutationIterator.hasNext());
        assertEquals(Arrays.asList('A', 'B', 'B', 'A'), permutationIterator.next());

        assertTrue(permutationIterator.hasNext());
        assertEquals(Arrays.asList('B', 'A', 'A', 'B'), permutationIterator.next());

        assertTrue(permutationIterator.hasNext());
        assertEquals(Arrays.asList('B', 'A', 'B', 'A'), permutationIterator.next());

        assertTrue(permutationIterator.hasNext());
        assertEquals(Arrays.asList('B', 'B', 'A', 'A'), permutationIterator.next());

        assertFalse(permutationIterator.hasNext());
    }

    @Test
    void shouldUseComparatorIfProvided() {
        Iterator<List<Character>> permutationIterator = new NextPermutationIterator<>(Arrays.asList('C', 'B', 'A'),
                Comparator.reverseOrder());

        assertTrue(permutationIterator.hasNext());
        assertEquals(Arrays.asList('C', 'B', 'A'), permutationIterator.next());

        assertTrue(permutationIterator.hasNext());
        assertEquals(Arrays.asList('C', 'A', 'B'), permutationIterator.next());

        assertTrue(permutationIterator.hasNext());
        assertEquals(Arrays.asList('B', 'C', 'A'), permutationIterator.next());

        assertTrue(permutationIterator.hasNext());
        assertEquals(Arrays.asList('B', 'A', 'C'), permutationIterator.next());

        assertTrue(permutationIterator.hasNext());
        assertEquals(Arrays.asList('A', 'C', 'B'), permutationIterator.next());

        assertTrue(permutationIterator.hasNext());
        assertEquals(Arrays.asList('A', 'B', 'C'), permutationIterator.next());

        assertFalse(permutationIterator.hasNext());
    }

    @Test
    void shouldUseComparatorIfProvidedForNonComparableObjects() {
        Iterator<List<NonComparableObject<Character>>> permutationIterator =
                new NextPermutationIterator<>(Arrays.asList(
                        new NonComparableObject<>('A'),
                        new NonComparableObject<>('B')), Comparator.comparing(NonComparableObject::getValue));

        assertTrue(permutationIterator.hasNext());
        assertEquals(Arrays.asList(
                new NonComparableObject<>('A'),
                new NonComparableObject<>('B')), permutationIterator.next());

        assertTrue(permutationIterator.hasNext());
        assertEquals(Arrays.asList(
                new NonComparableObject<>('B'),
                new NonComparableObject<>('A')), permutationIterator.next());

        assertFalse(permutationIterator.hasNext());
    }

    @Test
    void shouldGenerateStreamOfAllPossiblePermutations() {
        Iterable<List<Character>> iterable = () -> new NextPermutationIterator<>(Arrays.asList('A', 'B', 'C'));

        List<List<Character>> allPermutations = StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());

        assertEquals(Arrays.asList(
                Arrays.asList('A', 'B', 'C'),
                Arrays.asList('A', 'C', 'B'),
                Arrays.asList('B', 'A', 'C'),
                Arrays.asList('B', 'C', 'A'),
                Arrays.asList('C', 'A', 'B'),
                Arrays.asList('C', 'B', 'A')), allPermutations);
    }

    @Test
    void shouldThrowWhenObjectIsNonComparable() {
        Iterator<List<NonComparableObject<Character>>> permutationIterator = new NextPermutationIterator<>(
                Arrays.asList(
                        new NonComparableObject<>('A'),
                        new NonComparableObject<>('B')));

        assertTrue(permutationIterator.hasNext());
        assertThrows(ClassCastException.class, permutationIterator::next);
    }

    @Test
    void shouldThrowWhenNoMorePermutationsLeft() {
        Iterator<List<Character>> permutationIterator = new NextPermutationIterator<>(Arrays.asList('A', 'B'));

        assertTrue(permutationIterator.hasNext());
        assertEquals(Arrays.asList('A', 'B'), permutationIterator.next());

        assertTrue(permutationIterator.hasNext());
        assertEquals(Arrays.asList('B', 'A'), permutationIterator.next());

        assertFalse(permutationIterator.hasNext());
        assertThrows(NoSuchElementException.class, permutationIterator::next);
    }

    @Test
    void shouldThrowWhenRemoveIsCalled() {
        Iterator<List<Character>> permutationIterator = new NextPermutationIterator<>(Arrays.asList('A', 'B', 'C'));

        assertTrue(permutationIterator.hasNext());
        assertThrows(UnsupportedOperationException.class, permutationIterator::remove);
    }
}
