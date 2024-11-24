package sortingalgorithms;

import priorityqueue.BinaryHeapListPriorityQueue;
import utils.orderingstrategy.SortOrderingStrategy;

import java.util.ArrayList;
import java.util.List;

public class HeapSort<T extends Comparable<T>> implements SortAlgoStrategy<T> {
    @Override
    public T[] sort(T[] array, SortOrderingStrategy<T> orderingStrategy) {
        BinaryHeapListPriorityQueue<T> heap = new BinaryHeapListPriorityQueue<>(
                new ArrayList<>(List.of(array)),
                orderingStrategy
        );

        int i = 0;
        while (!heap.isEmpty()) {
            array[i] = heap.dequeue();
            i++;
        }

        return array;
    }
}
