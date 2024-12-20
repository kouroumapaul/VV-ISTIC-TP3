# Implementing and testing a binary heap

A [*binary heap*](https://en.wikipedia.org/wiki/Binary_heap) is a data structure that contains comparable objects and it is able to efficiently return the lowest element.
This data structure relies on a binary tree to keep the insertion and deletion operations efficient. It is the base of the [*Heapsort* algorithm](https://en.wikipedia.org/wiki/Heapsort).

Implement a `BinaryHeap` class with the following interface:

```java
class BinaryHeap<T> {

    public BinaryHeap(Comparator<T> comparator) { ... }

    public T pop() { ... }

    public T peek() { ... }

    public void push(T element) { ... }

    public int count() { ... }

}
```

A `BinaryHeap` instance is created using a `Comparator` object that represents the ordering criterion between the objects in the heap.
`pop` returns and removes the minimum object in the heap. If the heap is empty it throws a `NotSuchElementException`.
`peek` similar to `pop`, returns the minimum object but it does not remove it from the `BinaryHeap`.
`push` adds an element to the `BinaryHeap`.
`count` returns the number of elements in the `BinaryHeap`.

Design and implement a test suite for this `BinaryHeap` class.
Feel free to add any extra method you may need.

Use the following steps to design the test suite:

1. With the help of *Input Space Partitioning* design a set of initial test inputs for each method. Write below the characteristics and blocks you identified for each method. Specify which characteristics are common to more than one method.
2. Evaluate the statement coverage of the test cases designed in the previous step. If needed, add new test cases to increase the coverage. Describe below what you did in this step.
3. If you have in your code any predicate that uses more than two boolean operators check if the test cases written to far satisfy *Base Choice Coverage*. If needed add new test cases. Describe below how you evaluated the logic coverage and the new test cases you added.
4. Use PIT to evaluate the test suite you have so far. Describe below the mutation score and the live mutants. Add new test cases or refactor the existing ones to achieve a high mutation score.

Use the project in [tp3-heap](../code/tp3-heap) to complete this exercise.

## Answer

# 1 Input Space Partitioning

Caractéristiques communes à toutes les méthodes :
1. État du tas
   - Tas vide
   - Tas avec un seul élément
   - Tas avec plusieurs éléments (>1 éléments)

Analyse par méthode :

1. push(T element)
- Valeur de l'élément par rapport aux éléments existants (quand le tas n'est pas vide)
  * Plus petit que tous les éléments existants
  * Plus grand que tous les éléments existants
  * Valeur intermédiaire entre les éléments existants
- Nullité de l'élément
  * Élément null
  * Élément non-null

2. pop()
Caractéristiques :
- Structure après le pop
  * Nécessite heapifyDown (quand la racine supprimée avait des enfants)
  * Pas besoin de heapifyDown (quand on supprime le dernier élément)

3. peek()
- Pas de caractéristiques spécifiques supplémentaires car il lit uniquement la racine

4. count()
Caractéristiques :
- Pas de caractéristiques spécifiques supplémentaires car retourne simplement la taille


- Voici quelques tests (La suite dans le fichier BinaryHeapTest.java)

```java
@Test
    void count_shouldReturnZero_whenHeapIsEmpty() {
        assertEquals(0, heap.count());
    }

    @Test
    void count_shouldReturnOne_whenHeapHasOneElement() {
        heap.push(1);
        assertEquals(1, heap.count());
    }

    @Test
    void count_shouldReturnCorrectSize_whenHeapHasMultipleElements() {
        heap.push(1);
        heap.push(2);
        heap.push(3);
        assertEquals(3, heap.count());
    }

    // Tests pour peek()
    @Test
    void peek_shouldThrowException_whenHeapIsEmpty() {
        assertThrows(NoSuchElementException.class, () -> heap.peek());
    }

    @Test
    void peek_shouldReturnElement_whenHeapHasOneElement() {
        heap.push(1);
        assertEquals(1, heap.peek());
    }

    @Test
    void peek_shouldReturnSmallestElement_whenHeapHasMultipleElements() {
        heap.push(3);
        heap.push(1);
        heap.push(2);
        assertEquals(1, heap.peek());
    }

    // Tests pour push()
    @Test
    void push_shouldAddElement_whenHeapIsEmpty() {
        heap.push(1);
        assertEquals(1, heap.peek());
    }
```


# 2 Coverage

Suite à la conception des tests basés sur l'ISP, Voici la couverture de code obtenue :

![Resultat du coverage](../images/coverage%20heap.PNG)

Nous avons une couverture de 100%. A première vue, pas besoin de rajouter de tests supplementaires. L'utilisation des mutations plus tard nous dira s'il y'a des cas qu'on peut encore couvrir avec des tests


# 3


Dans la méthode `heapifyDown`,
```java
if (leftChild < size && comparator.compare(heap.get(leftChild), heap.get(smallest)) < 0)
```
et
```java
if (rightChild < size && comparator.compare(heap.get(rightChild), heap.get(smallest)) < 0)
```

Ces prédicats sont similaires et contiennent chacun deux opérateurs (&&). Comme ils n'ont que deux opérateurs booléens, ils ne nécessitent pas d'analyse Base Choice Coverage.



# PIT

Resultat de PIT
![Resultat de PIT](../images/pit%20heap.PNG)

D'après le resultat de PIT, on a 4 mutants qui restent en vie, on peut rajouter des tests supplementaires pour essayer de tuer ces mutants

![Mutant survivant](../images/mutant%20heap.PNG)

L'analyse des mutants survivants du tas binaire (BinaryHeap), nous avons identifié quatre mutants qui ont survécu aux tests :

Pour le mutant index > 0 → index >= 0 :

Nous avons réussi à tuer ce mutant avec un test qui force des échanges jusqu'à la racine
Le mutant aurait causé une IndexOutOfBoundsException en tentant d'accéder à l'index -1

![Mutant mis à jour](../images/mutant%202%20heap.PNG)



