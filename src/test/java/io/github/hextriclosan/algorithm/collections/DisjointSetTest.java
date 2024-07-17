package io.github.hextriclosan.algorithm.collections;

import io.github.hextriclosan.algorithm.collections.disjointset.PathHalvingCompression;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class DisjointSetTest {

    @ParameterizedTest
    @MethodSource("disjointSetProvider")
    public void shouldCreateSetsWithSingleElement(DisjointSet<Character> disjointSet) {
        disjointSet.makeSets(Arrays.asList('A', 'B', 'C'));
        assertSame('A', disjointSet.find('A'));
        assertSame('B', disjointSet.find('B'));
        assertSame('C', disjointSet.find('C'));
    }

    @ParameterizedTest
    @MethodSource("disjointSetProvider")
    public void shouldUnionSets(DisjointSet<Character> disjointSet) {
        disjointSet.makeSets(Arrays.asList('A', 'B'));

        disjointSet.union('A', 'B');

        assertSame('B', disjointSet.find('A'));
        assertSame('B', disjointSet.find('B'));
    }


    @ParameterizedTest
    @MethodSource("disjointSetProvider")
    void shouldCreateSet2(DisjointSet<Character> disjointSet) {
        List<Character> characters = Arrays.asList('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H');
        disjointSet.makeSets(characters);

        disjointSet.union('A', 'B');
        disjointSet.union('C', 'D');
        disjointSet.union('B', 'D');

        List<Character> expected = Arrays.asList('D', 'D', 'D', 'D', 'E', 'F', 'G', 'H');
        IntStream.range(0, characters.size())
                .forEach(index -> assertSame(expected.get(index), disjointSet.find(characters.get(index))));
    }

    @ParameterizedTest
    @MethodSource("disjointSetProvider")
    void shouldCalculateMinimumSpanningTree(DisjointSet<City> disjointSet) {
        Edge[] paths = {
                edge("New York", "Los Angeles", 2800),
                edge("New York", "Chicago", 800),
                edge("New York", "Houston", 1600),
                edge("Los Angeles", "Chicago", 2000),
                edge("Los Angeles", "Houston", 1500),
                edge("Los Angeles", "Phoenix", 400),
                edge("Chicago", "Houston", 1000),
                edge("Chicago", "Phoenix", 1700),
                edge("Houston", "Phoenix", 1200),
                edge("Philadelphia", "New York", 100),
                edge("Philadelphia", "Chicago", 750)
        };
        Arrays.sort(paths, Comparator.comparingInt(o -> o.distance));

        disjointSet.makeSets(Arrays.asList(
                city("New York"),
                city("Los Angeles"),
                city("Chicago"),
                city("Houston"),
                city("Phoenix"),
                city("Philadelphia")));
        List<Edge> mst = new ArrayList<>();
        int mstCost = 0;
        for (Edge edge : paths) {
            if (disjointSet.find(edge.from) != disjointSet.find(edge.to)) {
                disjointSet.union(edge.from, edge.to);
                mst.add(edge);
                mstCost += edge.distance;
            }
        }

        assertEquals(3450, mstCost);

        Edge[] expectedMst = {
                edge("Philadelphia", "New York", 100),
                edge("Los Angeles", "Phoenix", 400),
                edge("Philadelphia", "Chicago", 750),
                edge("Chicago", "Houston", 1000),
                edge("Houston", "Phoenix", 1200)
        };
        assertArrayEquals(expectedMst, mst.toArray());
    }

    static Stream<DisjointSet<?>> disjointSetProvider() {
        return Stream.of(
                new DisjointSet<>(),
                new DisjointSet<>(new PathHalvingCompression<>())
        );
    }

    private static class Edge {
        final City from, to;
        final int distance;

        public Edge(City from, City to, int distance) {
            this.from = from;
            this.to = to;
            this.distance = distance;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Edge edge = (Edge) o;
            return distance == edge.distance && Objects.equals(from, edge.from) && Objects.equals(to, edge.to);
        }

        @Override
        public int hashCode() {
            return Objects.hash(from, to, distance);
        }
    }

    private static class City {
        final String name;

        private City(String name) {
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            City city = (City) o;
            return Objects.equals(name, city.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }

    private static City city(String name) {
        return new City(name);
    }

    private static Edge edge(String from, String to, int distance) {
        return new Edge(city(from), city(to), distance);
    }

}
