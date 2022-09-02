package io.github.hextriclosan.algorithm.functors;

import io.github.hextriclosan.algorithm.helpers.NonComparableObject;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Predicate;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


class IsSortedPredicateTest {

    private static final Predicate<Iterable<Character>> naturalOrderPredicate =
            new IsSortedPredicate<>();

    @Test
    void shouldReturnTrueForSortedIterable() {
        assertTrue(naturalOrderPredicate.test(Arrays.asList('A', 'B', 'C')));
    }

    @Test
    void shouldReturnFalseForNotSortedIterable() {
        assertFalse(naturalOrderPredicate.test(Arrays.asList('C', 'B', 'A')));
    }

    @Test
    void shouldReturnTrueWhenAllElementsOfIterableAreEqual() {
        assertTrue(naturalOrderPredicate.test(Arrays.asList('A', 'A', 'A')));
    }

    @Test
    void shouldReturnTrueForOneElementIterable() {
        assertTrue(naturalOrderPredicate.test(Arrays.asList('A')));
    }

    @Test
    void shouldReturnTrueForEmptyIterable() {
        assertTrue(naturalOrderPredicate.test(emptyList()));
    }

    @Test
    void shouldThrowWhenObjectsAreNonComparableWithoutCustomComparator() {
        Predicate<Iterable<NonComparableObject<Character>>> predicate = new IsSortedPredicate<>();
        assertThrows(ClassCastException.class, () -> predicate.test(Arrays.asList(
                new NonComparableObject<>('A'),
                new NonComparableObject<>('B'))));
    }

    @Test
    void shouldReturnCorrectResultWhenObjectsAreNonComparableWithCustomComparator() {
        Predicate<Iterable<NonComparableObject<Character>>> predicate =
                new IsSortedPredicate<>(Comparator.comparing(NonComparableObject::getValue));
        assertTrue(predicate.test(Arrays.asList(
                new NonComparableObject<>('A'),
                new NonComparableObject<>('B'))));
    }
}
