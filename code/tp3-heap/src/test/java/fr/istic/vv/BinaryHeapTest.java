package fr.istic.vv;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Comparator;
import java.util.NoSuchElementException;

class BinaryHeapTest {
    private BinaryHeap<Integer> heap;

    @BeforeEach
    void setUp() {
        heap = new BinaryHeap<>(Integer::compareTo);
    }

    // Tests pour count()
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

    @Test
    void push_shouldMaintainHeapProperty_whenPushingSmaller() {
        heap.push(3);
        heap.push(1);
        assertEquals(1, heap.peek());
    }

    @Test
    void push_shouldMaintainHeapProperty_whenPushingLarger() {
        heap.push(1);
        heap.push(3);
        assertEquals(1, heap.peek());
    }

    @Test
    void push_shouldMaintainHeapProperty_whenPushingIntermediate() {
        heap.push(1);
        heap.push(5);
        heap.push(3);
        assertEquals(1, heap.peek());
    }

    @Test
    void push_shouldAcceptNull_whenImplementationAllows() {
        heap = new BinaryHeap<>(Comparator.nullsFirst(Integer::compareTo));
        heap.push(null);
        assertNull(heap.peek());
    }

    // Tests pour pop()
    @Test
    void pop_shouldThrowException_whenHeapIsEmpty() {
        assertThrows(NoSuchElementException.class, () -> heap.pop());
    }

    @Test
    void pop_shouldReturnAndRemoveElement_whenHeapHasOneElement() {
        heap.push(1);
        assertEquals(1, heap.pop());
        assertEquals(0, heap.count());
    }

    @Test
    void pop_shouldMaintainHeapProperty_whenHeapNeedsHeapifyDown() {
        heap.push(1);
        heap.push(3);
        heap.push(2);
        assertEquals(1, heap.pop());
        assertEquals(2, heap.peek());
    }

    @Test
    void pop_shouldReturnElementsInOrder_whenMultipleElementsExist() {
        heap.push(3);
        heap.push(1);
        heap.push(4);
        heap.push(2);
        assertEquals(1, heap.pop());
        assertEquals(2, heap.pop());
        assertEquals(3, heap.pop());
        assertEquals(4, heap.pop());
    }

    // Tests spécifiques pour les comparaisons strictes
    @Test
    void strictComparison_heapifyUp() {
        // On crée un comparateur qui compte les égalités
        class StrictComparator implements Comparator<Integer> {
            @Override
            public int compare(Integer a, Integer b) {
                // Forcer une comparaison stricte
                return (a == b) ? 0 : (a < b) ? -1 : 1;
            }
        }
        
        BinaryHeap<Integer> strictHeap = new BinaryHeap<>(new StrictComparator());
        strictHeap.push(5);
        strictHeap.push(3);
        strictHeap.push(7);
        
        assertEquals(3, strictHeap.peek());
    }

    @Test
    void strictComparison_heapifyDown() {
        class StrictComparator implements Comparator<Integer> {
            @Override
            public int compare(Integer a, Integer b) {
                // Forcer des comparaisons strictes
                if (a == null || b == null) {
                    if (a == b) return 0;
                    return (a == null) ? -1 : 1;
                }
                return (a == b) ? 0 : (a < b) ? -1 : 1;
            }
        }
        
        BinaryHeap<Integer> strictHeap = new BinaryHeap<>(new StrictComparator());
        strictHeap.push(4);
        strictHeap.push(2);
        strictHeap.push(6);
        strictHeap.push(1);
        strictHeap.push(3);
        
        assertEquals(1, strictHeap.pop());
        assertEquals(2, strictHeap.pop());
    }

    @Test
    void strictComparison_pushOrderedElements() {
        // Test avec un comparateur qui fait des comparaisons strictes
        Comparator<Integer> strictComparator = (a, b) -> {
            if (a.equals(b)) return 0;
            return a < b ? -1 : 1;
        };
        
        BinaryHeap<Integer> strictHeap = new BinaryHeap<>(strictComparator);
        for (int i = 1; i <= 5; i++) {
            strictHeap.push(i);
        }
        
        assertEquals(1, strictHeap.pop());
        assertEquals(2, strictHeap.pop());
    }

    @Test
    void heapifyUp_shouldNotAccessNegativeIndex() {
        // Crée un comparateur qui force l'échange à chaque fois
        Comparator<Integer> alwaysSwapComparator = (a, b) -> 1;  // toujours retourner > 0
        
        BinaryHeap<Integer> testHeap = new BinaryHeap<>(alwaysSwapComparator);
        testHeap.push(1);
        testHeap.push(2); 
        
        assertEquals(2, testHeap.peek());
    }
}