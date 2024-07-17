
# Java Algorithms

## Acquiring Algorithms
You can download source and binaries from the [release page](https://github.com/hextriclosan/algorithm/releases/latest).
Alternatively you can pull it from the central Maven repositories:

#### Maven
```xml
<dependency>
    <groupId>io.github.hextriclosan</groupId>
    <artifactId>algorithm</artifactId>
    <version>0.0.4</version>
</dependency>
```

#### Gradle
```groovy
implementation 'io.github.hextriclosan:algorithm:0.0.4'
```

## Basic usage

### Collections

##### Disjoint Set
Disjoint Set a.k.a. Union-Find data structure implementation
```java
DisjointSet<String> disjointSet = new DisjointSet<>();
disjointSet.makeSets(List.of("New York", "Los Angeles", "Chicago", "Houston"));

record Edge(String firstCity, String secondCity, int distance) {
}

List<Edge> edges = List.of(
        new Edge("New York", "Los Angeles", 2445),
        new Edge("New York", "Chicago", 790),
        new Edge("New York", "Houston", 1628),
        new Edge("Los Angeles", "Chicago", 2015),
        new Edge("Los Angeles", "Houston", 1547),
        new Edge("Chicago", "Houston", 1092)
);

edges.stream()
    .sorted(Comparator.comparingInt(Edge::distance))
    .filter(edge -> disjointSet.find(edge.firstCity()) != disjointSet.find(edge.secondCity()))
    .forEach(edge -> {
        disjointSet.union(edge.firstCity(), edge.secondCity());
        System.out.println(edge);
    });
// prints out
// Edge[firstCity=New York, secondCity=Chicago, distance=790]
// Edge[firstCity=Chicago, secondCity=Houston, distance=1092]
// Edge[firstCity=Los Angeles, secondCity=Houston, distance=1547]
```

### Comparators

##### Lexicographical Comparator
Compares Iterables lexicographically
```java
Comparator<Iterable<Character>> comparator = new LexicographicalComparator<>();
comparator.compare(List.of('A', 'B', 'C'), List.of('C', 'B', 'A')); // -1
```

### Functors

##### Is Sorted Predicate
Evaluates if Iterable is sorted.
```java
Predicate<Iterable<Character>> predicate = new IsSortedPredicate<>();
predicate.test(List.of('A', 'B', 'C')); // true
```

##### Is Permutation Predicate
Evaluates if one list is permutation of another.
```java
BiPredicate<List<Character>, List<Character>> predicate = new IsPermutationPredicate<>();
predicate.test(List.of('A', 'B', 'C'), List.of('C', 'B', 'A')); // true
```

### Iterators

##### Next Permutation Iterator
Inspired by `next_permutation`/`prev_permutation` algorithms from C++ standard
library. The iterator creates permutations of an input collection, using the lexicographical order.
```java
Iterator<List<Character>> iterator = new NextPermutationIterator<>(List.of('A', 'B', 'B'));
iterator.forEachRemaining(System.out::println);
// prints out
// [A, B, B]
// [B, A, B]
// [B, B, A]
```

##### Sampling Iterator
This iterator creates random samples of a given size from the input `List`. 
The algorithm preserves original order of elements.
```java
Iterator<List<Character>> iterator = new SamplingIterator<>(List.of('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'), 3);
iterator.forEachRemaining(System.out::println);
// one of possible outputs
// [A, D, H]
// [B, E, G]
// [C, F]
```
