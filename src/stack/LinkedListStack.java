package stack;

public class LinkedListStack<T> {
    private static class Node<T> {
        T value;
        Node<T> next;

        public Node(T value) {
            this.value = value;
        }
    }

    Node<T> head;

    public boolean isEmpty() {
        return head == null;
    }

    public void push(T value) {
        Node<T> node = new Node<>(value);
        if (head == null) {
            head = node;
            return;
        }
        node.next = head;
        head = node;
    }

    public T peek() {
        return head.value;
    }

    public T pop() {
        T value = head.value;
        head = head.next;
        return value;
    }
}
