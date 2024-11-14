package priorityqueue;

import java.util.ArrayList;
import java.util.List;

public class BinaryHeapListPriorityQueue<T extends Comparable<T>> {
    private final List<T> items = new ArrayList<T>();

    private void swap(int i1, int i2) {
        T temp = items.get(i1);
        items.set(i1, items.get(i2));
        items.set(i2, temp);
    }

    public void enqueue(T item) {
        int i = items.size(), pi = (i - 1) / 2;

        items.add(item);

        while (pi >= 0 && items.get(i).compareTo(items.get(pi)) > 0) {
            swap(i, pi);
            i = pi;
            pi = (i - 1) / 2;
        }
    }

    public T peek() {
        return items.get(0);
    }

    public T dequeue() {
        T topItem = peek();
        int i = 0, lastIndex = items.size() - 1, li = 1, ri = 2, childIndex;

        items.set(0, items.getLast());

        while ((li <= lastIndex && items.get(i).compareTo(items.get(li)) < 0)
                || (ri <= lastIndex && items.get(i).compareTo(items.get(ri)) < 0)) {
            childIndex = li == lastIndex || items.get(i).compareTo(items.get(li)) < 0 ? li : ri;
            swap(i, childIndex);
            i = childIndex;
            li = i * 2 + 1;
            ri = i * 2 + 2;
        }

        items.removeLast();

        return topItem;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}
