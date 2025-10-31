package io.github.hextriclosan.algorithm.collections.disjointset;

import java.util.Map;

/**
 * An implementation of the {@link FindCompressStrategy} interface that performs path halving compression.
 * This strategy optimizes the find operation by halving the path length during path compression.
 *
 * @param <E> the type of elements in the Disjoint Set
 */
public class PathHalvingCompression<E> implements FindCompressStrategy<E> {
    /**
     * Creates a new PathHalvingCompression strategy.
     */
    public PathHalvingCompression() {
    }

    /**
     * Applies the path halving compression strategy to find the root of the subset containing the specified element.
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

        E parent = parentByElement.get(element);
        E grandparent = parentByElement.get(parent);
        while (parent != grandparent) {
            parentByElement.put(element, grandparent);
            element = grandparent;
            parent = parentByElement.get(element);
            grandparent = parentByElement.get(parent);
        }

        return parent;
    }
}
