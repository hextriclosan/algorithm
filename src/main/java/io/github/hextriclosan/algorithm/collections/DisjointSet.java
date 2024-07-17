package io.github.hextriclosan.algorithm.collections;

import io.github.hextriclosan.algorithm.collections.disjointset.FindCompressStrategy;
import io.github.hextriclosan.algorithm.collections.disjointset.FullCompression;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * A data structure that maintains a collection of disjoint (non-overlapping) sets.
 * It supports efficient union and find operations, as well as strategies for path compression and ranking.
 *
 * @param <E> the type of elements stored in the Disjoint Set
 */
public class DisjointSet<E> {
    private final Map<E, E> parentByElement;
    private final Map<E, Integer> rankByElement;
    private final FindCompressStrategy<E> findCompressStrategy;

    /**
     * Constructs a DisjointSet with default initializations using {@link FullCompression} strategy.
     */
    public DisjointSet() {
        this(new HashMap<>(), new HashMap<>(), new FullCompression<>());
    }

    /**
     * Constructs a DisjointSet with a specified path compression strategy.
     *
     * @param findCompressStrategy the strategy used for path compression and find operations
     */
    public DisjointSet(FindCompressStrategy<E> findCompressStrategy) {
        this(new HashMap<>(), new HashMap<>(), findCompressStrategy);
    }

    /**
     * Constructs a DisjointSet with custom initial parent and rank mappings, along with a specified path compression strategy.
     * This constructor allows the user to recreate a DisjointSet with a particular state, including predefined parent-to-element
     * relationships, rank values, and a customized path compression strategy.
     *
     * @param parentByElement the initial mapping of elements to their parent elements
     * @param rankByElement the initial mapping of elements to their ranks (or sizes)
     * @param findCompressStrategy the strategy used for path compression and find operations
     * @throws NullPointerException if any of the parameters are null
     */
    public DisjointSet(Map<E, E> parentByElement, Map<E, Integer> rankByElement, FindCompressStrategy<E> findCompressStrategy) {
        Objects.requireNonNull(parentByElement, "parentByElement");
        Objects.requireNonNull(rankByElement, "rankByElement");
        Objects.requireNonNull(findCompressStrategy, "findCompressStrategy");
        this.parentByElement = parentByElement;
        this.rankByElement = rankByElement;
        this.findCompressStrategy = findCompressStrategy;
    }

    /**
     * Creates a new set with the specified element in the Disjoint Set structure.
     * If the element is already present, no action is taken.
     *
     * @param element the element to initialize as a new set
     * @throws NullPointerException if the specified element is null
     */
    public void makeSet(E element) {
        Objects.requireNonNull(element, "element");
        if (!parentByElement.containsKey(element)) {
            parentByElement.put(element, element);
            rankByElement.put(element, 0);
        }
    }

    /**
     * Creates new sets for each element in the specified collection in the Disjoint Set structure.
     * If an element is already present, no action is taken for that element.
     *
     * @param elements the collection of elements to initialize as new sets
     * @throws NullPointerException if the specified collection is null or contains null elements
     */
    public void makeSets(Collection<E> elements) {
        Objects.requireNonNull(elements, "elements");
        for (E element : elements) {
            makeSet(element);
        }
    }

    /**
     * Unites the sets that contain the specified elements into a single set.
     *
     * @param first the first element
     * @param second the second element
     * @throws NullPointerException if either of the elements is null
     */
    public void union(E first, E second) {
        Objects.requireNonNull(first, "first");
        Objects.requireNonNull(second, "second");
        E firstRoot = find(first);
        E secondRoot = find(second);

        if (firstRoot == secondRoot) {
            return;
        }

        int firstRootRank = rankByElement.get(firstRoot);
        int secondRootRank = rankByElement.get(secondRoot);

        if (firstRootRank > secondRootRank) {
            parentByElement.put(secondRoot, firstRoot);
        } else {
            parentByElement.put(firstRoot, secondRoot);
            if (firstRootRank == secondRootRank) {
                rankByElement.put(secondRoot, secondRootRank + 1);
            }
        }
    }

    /**
     * Finds the representative (root) of the set containing the specified element,
     * using the configured find and path compression strategy.
     *
     * @param element the element to find
     * @return the representative (root) of the set containing the element
     * @throws NullPointerException if the element is null
     */
    public E find(E element) {
        Objects.requireNonNull(element, "element");
        return findCompressStrategy.apply(parentByElement, element);
    }

}
