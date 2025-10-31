package io.github.hextriclosan.algorithm.collections.disjointset;

import java.util.Map;

/**
 * An implementation of the {@link FindCompressStrategy} interface that performs full path compression.
 * This strategy ensures that all nodes along the path from the given element to the root are directly
 * connected to the root, optimizing future find operations.
 *
 * @param <E> the type of elements in the Disjoint Set
 */
public class FullCompression<E> implements FindCompressStrategy<E> {
    /**
     * Creates a new FullCompression strategy.
     */
    public FullCompression() {
    }

    /**
     * Applies the full path compression strategy to find the root of the subset containing the specified element.
     * If the element is not found in the map, an {@link IllegalArgumentException} is thrown.
     *
     * @param parentByElement the map representing the parent relationship of elements in the disjoint set
     * @param element         the element for which to find the root
     * @return the root of the subset containing the specified element
     * @throws IllegalArgumentException if the element is not found in the disjoint set
     */
    @Override
    public E apply(Map<E, E> parentByElement, E element) {
        if (!parentByElement.containsKey(element)) {
            throw new IllegalArgumentException("Element not found in the disjoint set");
        }

        E old = element;
        E ancestor = parentByElement.get(element);
        while (ancestor != element) {
            element = ancestor;
            ancestor = parentByElement.get(element);
        }

        element = parentByElement.get(old);
        while (ancestor != element) {
            parentByElement.put(old, ancestor);
            old = element;
            element = parentByElement.get(old);
        }

        return ancestor;
    }
}
