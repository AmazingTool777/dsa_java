package queue;

import java.util.ArrayList;

public class ArrayListQueue<T> {
    private final ArrayList<T> items = new ArrayList<>();

    public void enqueue(T item) {
        items.addFirst(item);
    }

    public T peek() {
        return items.getLast();
    }

    public T dequeue() {
        return items.removeLast();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}
