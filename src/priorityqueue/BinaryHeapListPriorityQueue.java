package priorityqueue;

import utils.orderingstrategy.SortOrderingStrategy;

import java.util.ArrayList;
import java.util.List;

public class BinaryHeapListPriorityQueue<T extends Comparable<T>> {
    private List<T> items = new ArrayList<>();

    /**
     * The ordering strategy to use for ordering the items
     */
    private SortOrderingStrategy<T> orderingStrategy;

    public void setOrderingStrategy(SortOrderingStrategy<T> orderingStrategy) {
        this.orderingStrategy = orderingStrategy;
    }

    public BinaryHeapListPriorityQueue() {
    }

    public BinaryHeapListPriorityQueue(ArrayList<T> items, SortOrderingStrategy<T> orderingStrategy) {
        this.items = items;
        this.orderingStrategy = orderingStrategy;
        // Heapify the items
        int mid = getParentIndex(items.size() - 1);
        for (int i = mid; i >= 0; i--) {
            siftDown(i);
        }
    }

    private void swap(int i1, int i2) {
        T temp = items.get(i1);
        items.set(i1, items.get(i2));
        items.set(i2, temp);
    }

    private int getParentIndex(int index) {
        return (index - 1) / 2;
    }

    private void siftUp(int index) {
        int i = index, pi = getParentIndex(index);

        while (pi >= 0 && orderingStrategy.shouldPrecede(items.get(i), items.get(pi))) {
            swap(i, pi);
            i = pi;
            pi = getParentIndex(i);
        }
    }

    public void enqueue(T item) {
        items.add(item);
        siftUp(items.size() - 1);
    }

    public T peek() {
        return items.get(0);
    }

    private int getLeftChildIndex(int index) {
        return 2 * index + 1;
    }

    private int getRightChildIndex(int index) {
        return 2 * index + 2;
    }

    private void siftDown(int index) {
        int i = index, li = getLeftChildIndex(i), ri = getRightChildIndex(i), childIndex, lastIndex = items.size() - 1;

        while ((li <= lastIndex && orderingStrategy.shouldPrecede(items.get(li), items.get(i)))
                || (ri <= lastIndex && orderingStrategy.shouldPrecede(items.get(ri), items.get(i)))) {
            childIndex = li == lastIndex
                    || !orderingStrategy.shouldPrecede(items.get(ri), items.get(i))
                    || orderingStrategy.shouldPrecede(items.get(li), items.get(ri))
                    ? li : ri;
            swap(i, childIndex);
            i = childIndex;
            li = getLeftChildIndex(i);
            ri = getRightChildIndex(i);
        }
    }

    public T dequeue() {
        T topItem = peek();
        items.set(0, items.getLast());
        siftDown(0);
        items.removeLast();
        return topItem;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void changePriority(T item, boolean priorityIncreased) {
        int index = items.indexOf(item);
        if (priorityIncreased) siftUp(index);
        else siftDown(index);
    }
}
