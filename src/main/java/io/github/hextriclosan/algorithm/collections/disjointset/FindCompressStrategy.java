package io.github.hextriclosan.algorithm.collections.disjointset;

import java.util.Map;
import java.util.function.BiFunction;

/**
 * A strategy interface for the find and path compression operation in the {@link io.github.hextriclosan.algorithm.collections.DisjointSet}.
 * This interface extends the {@link BiFunction} interface, taking a map of element-to-parent relationships and
 * an element, and returning the representative (or root) of the subset containing the given element.
 *
 * @param <E> the type of elements in the Disjoint Set
 */
public interface FindCompressStrategy<E> extends BiFunction<Map<E, E>, E, E> {
}
