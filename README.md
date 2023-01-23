
# Java Algorithms

## Acquiring Algorithms
You can download source and binaries from our [release page](https://github.com/hextriclosan/algorithm/releases/latest).
Alternatively you can pull it from the central Maven repositories:

#### Maven
```xml
<dependency>
    <groupId>io.github.hextriclosan</groupId>
    <artifactId>algorithm</artifactId>
    <version>0.0.3</version>
</dependency>
```

#### Gradle
```groovy
implementation 'io.github.hextriclosan:algorithm:0.0.3'
```

## Basic usage

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
var iterator = new NextPermutationIterator<>(List.of('A', 'B', 'B'));
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
var iterator = new SamplingIterator<>(List.of('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'), 3);
iterator.forEachRemaining(System.out::println);
// one of possible outputs
// [A, D, H]
// [B, E, G]
// [C, F]
```
