package fr.istic.vv;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.NoSuchElementException;

class BinaryHeap<T> {
    private final ArrayList<T> heap;
    private final Comparator<T> comparator;

    public BinaryHeap(Comparator<T> comparator) {
        this.heap = new ArrayList<>();
        this.comparator = comparator;
    }

    public T pop() {
        if (heap.isEmpty()) {
            throw new NoSuchElementException("Le tas est vide");
        }

        T min = heap.get(0);
        
        int lastIdx = heap.size() - 1;
        heap.set(0, heap.get(lastIdx));
        heap.remove(lastIdx);

        if (!heap.isEmpty()) {
            heapifyDown(0);
        }

        return min;
    }

    public T peek() {
        if (heap.isEmpty()) {
            throw new NoSuchElementException("Le tas est vide");
        }
        return heap.get(0);
    }

    public void push(T element) {
        heap.add(element);
        heapifyUp(heap.size() - 1);
    }

    public int count() {
        return heap.size();
    }

    private void heapifyUp(int index) {
        while (index > 0) {
            int parentIdx = (index - 1) / 2;
            
            // Si le parent est plus grand que l'élément courant, on échange
            if (comparator.compare(heap.get(parentIdx), heap.get(index)) > 0) {
                swap(parentIdx, index);
                index = parentIdx;
            } else {
                break;
            }
        }
    }

    private void heapifyDown(int index) {
        int size = heap.size();
        
        while (true) {
            int smallest = index;
            int leftChild = 2 * index + 1;
            int rightChild = 2 * index + 2;

            // Compaison avec left child
            if (leftChild < size && 
                comparator.compare(heap.get(leftChild), heap.get(smallest)) < 0) {
                smallest = leftChild;
            }

            // Comparaison avec right child
            if (rightChild < size && 
                comparator.compare(heap.get(rightChild), heap.get(smallest)) < 0) {
                smallest = rightChild;
            }

            // Si on n'a pas trouvé d'enfant plus petit, on arrête
            if (smallest == index) {
                break;
            }

            swap(index, smallest);
            index = smallest;
        }
    }

    private void swap(int i, int j) {
        T temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }
}