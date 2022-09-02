package io.github.hextriclosan.algorithm.comparators;

import io.github.hextriclosan.algorithm.helpers.NonComparableObject;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LexicographicalComparatorTest {

    private static final Iterable<Character> EMPTY = emptyList();
    private static final Iterable<Character> AB = Arrays.asList('A', 'B');
    private static final Iterable<Character> ABC = Arrays.asList('A', 'B', 'C');
    private static final Iterable<Character> CBA = Arrays.asList('C', 'B', 'A');

    private static final Comparator<Iterable<Character>> naturalOrderComparator =
            new LexicographicalComparator<>();
    private static final Comparator<Iterable<Character>> customOrderComparator =
            new LexicographicalComparator<>(Comparator.reverseOrder());

    @Test
    void shouldReturnNegativeResultWhenFirstIterableIsLessThanSecondUsingNaturalOrdering() {
        assertTrue(naturalOrderComparator.compare(ABC, CBA) < 0);
    }

    @Test
    void shouldReturnPositiveResultWhenFirstIterableIsGreaterThanSecondUsingNaturalOrdering() {
        assertTrue(naturalOrderComparator.compare(CBA, ABC) > 0);
    }

    @Test
    void shouldReturnZeroResultWhenFirstIterableIsEqualToSecondUsingNaturalOrdering() {
        assertTrue(naturalOrderComparator.compare(ABC, ABC) == 0);
    }

    @Test
    void shouldReturnNegativeResultWhenFirstIterableIsShorterThanSecondUsingNaturalOrdering() {
        assertTrue(naturalOrderComparator.compare(AB, ABC) < 0);
    }

    @Test
    void shouldReturnPositiveResultWhenSecondIterableIsShorterThanFirstUsingNaturalOrdering() {
        assertTrue(naturalOrderComparator.compare(ABC, AB) > 0);
    }

    @Test
    void shouldReturnPositiveResultWhenSecondIterableIsEmpty() {
        assertTrue(naturalOrderComparator.compare(ABC, EMPTY) > 0);
    }

    @Test
    void shouldReturnNegativeResultWhenFirstIterableIsEmpty() {
        assertTrue(naturalOrderComparator.compare(EMPTY, ABC) < 0);
    }

    @Test
    void shouldReturnZeroResultWhenBothIterablesAreEmpty() {
        assertTrue(naturalOrderComparator.compare(EMPTY, EMPTY) == 0);
    }

    @Test
    void shouldReturnCorrectResultUsingCustomComparator() {
        assertTrue(customOrderComparator.compare(ABC, CBA) > 0);
    }

    @Test
    void shouldReturnCorrectResultUsingCustomComparatorWithNonComparableObjects() {
        Comparator<Iterable<NonComparableObject<Character>>> comparator =
                new LexicographicalComparator<>(Comparator.comparing(NonComparableObject::getValue));
        assertTrue(comparator.compare(
                Arrays.asList(new NonComparableObject<>('A')),
                Arrays.asList(new NonComparableObject<>('B'))) < 0);
    }

    @Test
    void shouldThrowWhenObjectsAreNonComparableWithoutCustomComparator() {
        Comparator<Iterable<NonComparableObject<Character>>> comparator = new LexicographicalComparator<>();
        assertThrows(ClassCastException.class, () -> comparator.compare(
                Arrays.asList(new NonComparableObject<>('A')),
                Arrays.asList(new NonComparableObject<>('B'))));
    }

    @Test
    void shouldReturnCorrectResultComparingBaseTypesWithCustomComparator() {
        Comparator<Iterable<Number>> comparator = new LexicographicalComparator<>(Comparator.comparingLong(Number::longValue));
        assertTrue(comparator.compare(Arrays.asList(1), Arrays.asList(1L)) == 0);
    }

    @Test
    void shouldThrowWhenTypesAreNotComparableWithoutCustomComparator() {
        Comparator<Iterable<Number>> comparator = new LexicographicalComparator<>();
        assertThrows(ClassCastException.class, () -> comparator.compare(Arrays.asList(1), Arrays.asList(1L)));
    }
}
