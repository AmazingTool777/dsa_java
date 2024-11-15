package queue;

public class LinkedListQueue<T> {
    private static class Node<T> {
        T value;
        Node<T> next;

        Node(T value) {
            this.value = value;
        }
    }

    Node<T> head;

    public void enqueue(T value) {
        Node<T> node = new Node<>(value);

        if (head == null) {
            head = node;
            return;
        }

        Node<T> current = head;
        while (current.next != null) {
            current = current.next;
        }
        current.next = node;
    }

    public T peek() {
        return head.value;
    }

    public T dequeue() {
        T value = head.value;
        head = head.next;
        return value;
    }

    public boolean isEmpty() {
        return head == null;
    }
}
