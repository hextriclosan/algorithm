package io.github.hextriclosan.algorithm.collections.disjointset;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FullCompressionTest {
    private Map<Integer, Integer> parentByElement;

    @BeforeEach
    public void setUp() {
        parentByElement = new HashMap<>();
    }

    @ParameterizedTest
    @MethodSource("strategyProvider")
    public void shouldThrowWhenValueToFindAnsCompressIsNotInSet(FindCompressStrategy<Integer> strategy) {
        assertThrows(IllegalArgumentException.class, () -> strategy.apply(parentByElement, 1));
    }

    @ParameterizedTest
    @MethodSource("strategyProvider")
    public void shouldReturnValueForSingleElementSet(FindCompressStrategy<Integer> strategy) {
        parentByElement.put(1, 1);
        assertEquals(1, strategy.apply(parentByElement, 1));

        Map<Integer, Integer> expectedParentByElement = new HashMap<>();
        expectedParentByElement.put(1, 1);
        assertEquals(expectedParentByElement, parentByElement);
    }

    @ParameterizedTest
    @MethodSource("strategyProvider")
    public void shouldReturnResultWhitPathCompression(FindCompressStrategy<Integer> strategy) {
        parentByElement.put(1, 1);
        parentByElement.put(2, 1);
        parentByElement.put(3, 2);

        assertEquals(1, strategy.apply(parentByElement, 3));

        Map<Integer, Integer> expectedParentByElement = new HashMap<>();
        expectedParentByElement.put(1, 1);
        expectedParentByElement.put(2, 1);
        expectedParentByElement.put(3, 1);
        assertEquals(expectedParentByElement, parentByElement);
    }

    @ParameterizedTest
    @MethodSource("strategyProvider")
    public void shouldReturnResultWithoutPathCompressionIfAlreadyCompressed(FindCompressStrategy<Integer> strategy) {
        parentByElement.put(1, 1);
        parentByElement.put(2, 1);
        parentByElement.put(3, 1);

        assertEquals(1, strategy.apply(parentByElement, 3));

        Map<Integer, Integer> expectedParentByElement = new HashMap<>();
        expectedParentByElement.put(1, 1);
        expectedParentByElement.put(2, 1);
        expectedParentByElement.put(3, 1);
        assertEquals(expectedParentByElement, parentByElement);
    }

    static Stream<FindCompressStrategy<?>> strategyProvider() {
        return Stream.of(
                new FullCompression<>(),
                new PathHalvingCompression<>()
        );
    }
}
