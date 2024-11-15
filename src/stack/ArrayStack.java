package stack;

import java.util.ArrayList;

public class ArrayStack<T> {
    private final ArrayList<T> items = new ArrayList<>();

    public ArrayStack() {
    }

    public void push(T item) {
        items.add(item);
    }

    public T pop() {
        return items.removeLast();
    }

    public T peek() {
        return items.getLast();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}
