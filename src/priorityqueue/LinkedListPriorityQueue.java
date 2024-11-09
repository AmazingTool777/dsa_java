package priorityqueue;

/**
 * Priority queue implemented using a Linked List.
 * Items that have higher values have higher priorities.
 * The head always holds the node with the highest priority.
 */
public class LinkedListPriorityQueue {
    public static class Node {
        double value;
        int priority;
        Node next;

        public Node(double value, int priority) {
            this.value = value;
            this.priority = priority;
        }
    }

    private Node head;

    public boolean isEmpty() {
        return head == null;
    }

    public void enqueue(double value, int priority) {
        Node node = new Node(value, priority);

        // If initially empty, only the head is pushed to the list
        if (head == null) {
            head = node;
        }

        // If the new node has a higher priority than the head,
        // the new node becomes the head
        if (head.priority <= priority && (head.priority < priority || head.value < value)) {
            node.next = head;
            head = node;
        }

        // Looking for the next node than has a lower priority than the new node
        // then inserting the new node right before that node
        Node current = head;
        while (current.next != null
                && current.next.priority >= priority
                && (current.next.priority > priority || current.next.value > value)) {
            current = current.next;
        }
        node.next = current.next;
        current.next = node;
    }

    public double dequeue() {
        double value = head.value;
        head = head.next;
        return value;
    }

    public double peek() {
        return head.value;
    }
}
