
# Java Algorithms

## Acquiring Algorithms
You can download source and binaries from our [release page](https://github.com/hextriclosan/algorithm/releases/latest).
Alternatively you can pull it from the central Maven repositories:

#### Maven
```xml
<dependency>
    <groupId>io.github.hextriclosan</groupId>
    <artifactId>algorithm</artifactId>
    <version>0.0.2</version>
</dependency>
```

#### Gradle
```groovy
implementation 'io.github.hextriclosan:algorithm:0.0.2'
```

## Basic usage

### Comparators

##### Lexicographical Comparator
Compares Iterables lexicographically
```java
Comparator<Iterable<Character>> comparator = new LexicographicalComparator<>();
comparator.compare(Arrays.asList('A', 'B', 'C'), Arrays.asList('C', 'B', 'A')); // -1
```

### Functors

##### Is Sorted Predicate
Evaluates if Iterable is sorted.
```java
Predicate<Iterable<Character>> predicate = new IsSortedPredicate<>();
predicate.test(Arrays.asList('A', 'B', 'C')); // true
```


### Iterators

##### Next Permutation Iterator
Inspired by `next_permutation`/`prev_permutation` algorithms from C++ standard
library. The iterator creates permutations of an input collection, using the lexicographical order.
```java
var iterable = () -> new NextPermutationIterator<>(Arrays.asList('A', 'B', 'B'));
StreamSupport.stream(iterable.spliterator(), false).forEach(System.out::println);
// prints out
// [A, B, B]
// [B, A, B]
// [B, B, A]
```
