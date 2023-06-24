package io.github.hextriclosan.algorithm.functors;

import io.github.hextriclosan.algorithm.helpers.CustomBiPredicate;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.BiPredicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IsPermutationPredicateTest {
    private static final BiPredicate<List<Character>, List<Character>> naturalEqualityPredicate = new IsPermutationPredicate<>();

    @Test
    void shouldReturnTrueForEmptyLists() {
        assertTrue(naturalEqualityPredicate.test(Collections.emptyList(), Collections.emptyList()));
    }

    @Test
    void shouldReturnTrueForSameLists() {
        assertTrue(naturalEqualityPredicate.test(Arrays.asList('A', 'B', 'C'), Arrays.asList('A', 'B', 'C')));
    }

    @Test
    void shouldReturnFalseForDifferentSizeLists() {
        assertFalse(naturalEqualityPredicate.test(Arrays.asList('A', 'B'), Arrays.asList('A', 'B', 'C')));
    }


    @Test
    void shouldReturnTrueWhenOneListIsPermutationOfAnother() {
        assertTrue(naturalEqualityPredicate.test(Arrays.asList('A', 'B', 'C'), Arrays.asList('C', 'B', 'A')));
    }

    @Test
    void shouldReturnTrueWhenOneListIsPermutationOfAnotherWithDuplicates() {
        assertTrue(naturalEqualityPredicate.test(Arrays.asList('A', 'B', 'B'), Arrays.asList('B', 'A', 'B')));
    }

    @Test
    void shouldReturnTrueWhenOneListIsPermutationOfAnotherWithSamePrefix() {
        assertTrue(naturalEqualityPredicate.test(Arrays.asList('A', 'B', 'C', 'D'), Arrays.asList('A', 'B', 'D', 'C')));
    }

    @Test
    void shouldReturnFalseWhenOneListIsNotPermutationOfAnotherWithDuplicates() {
        assertFalse(naturalEqualityPredicate.test(Arrays.asList('A', 'B', 'C'), Arrays.asList('C', 'B', 'B')));
    }

    @Test
    void shouldReturnFalseWhenOneListIsNotPermutationOfAnotherWithExtraElement() {
        assertFalse(naturalEqualityPredicate.test(Arrays.asList('A', 'B', 'B'), Arrays.asList('B', 'D', 'B')));
    }

    @Test
    void shouldReturnFalseWhenOneListIsNotPermutationOfAnotherWithSamePrefix() {
        assertFalse(naturalEqualityPredicate.test(Arrays.asList('A', 'B', 'C', 'D'), Arrays.asList('A', 'B', 'D', 'D')));
    }

    @Test
    void shouldReturnTrueWhenOneListIsPermutationOfAnotherWithCustomBiPredicate() {
        BiPredicate<List<Integer>, List<Integer>> predicate = new IsPermutationPredicate<>(
                (integer1, integer2) -> integer1 % 3 == integer2 % 3);
        assertTrue(predicate.test(
                Arrays.asList(1, 2, 3, 4),      // 1, 2, 0, 1
                Arrays.asList(300, 10, 19, 17)  // 0, 1, 1, 2
        ));
    }

    @Test
    void shouldReturnFalseWhenOneListIsNotPermutationOfAnotherWithCustomBiPredicate() {
        BiPredicate<List<Integer>, List<Integer>> predicate = new IsPermutationPredicate<>(
                (integer1, integer2) -> integer1 % 3 == integer2 % 3);
        assertFalse(predicate.test(
                Arrays.asList(1, 2, 3, 4),      // 1, 2, 0, 1
                Arrays.asList(8, 10, 19, 17)    // 2, 1, 1, 2
        ));
    }

    @Test
    void shouldTreatDefaultConstructedObjectsAsEqual() {
        BiPredicate<List<Short>, List<Short>> one = new IsPermutationPredicate<>();
        BiPredicate<List<Short>, List<Short>> another = new IsPermutationPredicate<>();

        assertEquals(one, another);
    }

    @Test
    void shouldTreatObjectsWithEqualPredicatesAsEqual() {
        BiPredicate<List<Short>, List<Short>> one = new IsPermutationPredicate<>(new CustomBiPredicate<>(42));
        BiPredicate<List<Short>, List<Short>> another = new IsPermutationPredicate<>(new CustomBiPredicate<>(42));

        assertEquals(one, another);
    }

    @Test
    void shouldReturnSameHashcodeForDefaultConstructedObjects() {
        BiPredicate<List<Short>, List<Short>> one = new IsPermutationPredicate<>();
        BiPredicate<List<Short>, List<Short>> another = new IsPermutationPredicate<>();

        assertEquals(one.hashCode(), another.hashCode());
    }

    @Test
    void shouldReturnSameHashcodeForObjectsWithSameHashcodePredicates() {
        BiPredicate<List<Short>, List<Short>> one = new IsPermutationPredicate<>(new CustomBiPredicate<>(42));
        BiPredicate<List<Short>, List<Short>> another = new IsPermutationPredicate<>(new CustomBiPredicate<>(42));

        assertEquals(one.hashCode(), another.hashCode());
    }

}
