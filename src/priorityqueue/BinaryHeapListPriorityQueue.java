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
        int i = items.size(), pi;

        items.add(item);

        while (i > 0) {
            pi = (i - 1) / 2;
            if (items.get(i).compareTo(items.get(pi)) >= 0) {
                swap(i, pi);
                i = pi;
                continue;
            }
            break;
        }
    }

    public T peek() {
        return items.get(0);
    }

    public T dequeue() {
        T topItem = peek();
        int i = 0;

        items.set(0, items.getLast());
        while (true) {
            int li = 2 * i + 1;
            if (li >= items.size()) break;
            if (items.get(i).compareTo(items.get(li)) < 0) {
                swap(i, li);
                i = li;
                continue;
            }
            int ri = 2 * i + 2;
            if (ri >= items.size()) break;
            if (items.get(i).compareTo(items.get(ri)) < 0) {
                swap(i, ri);
                i = ri;
                continue;
            }
            break;
        }

        items.removeLast();

        return topItem;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}
