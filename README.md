
## List algorithms

### Iterators

#### Next Permutation Iterator
Inspired by `next_permutation`/`prev_permutation` algorithms from C++ standard
library. The iterator creates permutations of an input collection, using the lexicographical order.

Sample code
```java
var iterable = () -> new NextPermutationIterator<>(Arrays.asList('A', 'B', 'B'));
StreamSupport.stream(iterable.spliterator(), false).forEach(System.out::println);
```
prints out
```shell
[A, B, B]
[B, A, B]
[B, B, A]
```
