package priorityqueue;

public class Main {
    public static void main(String[] args) {
        // array();
        // linkedList();
        binaryHeapArray();
    }

    public static void array() {
        ArrayPriorityQueue pq = new ArrayPriorityQueue();

        pq.enqueue(2, 1);
        pq.enqueue(3, 2);
        pq.enqueue(1, 2);
        pq.enqueue(4, 4);
        pq.enqueue(5, 1);

        System.out.println("The highest priority value is: %.1f".formatted(pq.peek()));

        while (!pq.isEmpty()) {
            System.out.println(pq.dequeue());
        }
    }

    public static void linkedList() {
        LinkedListPriorityQueue pq = new LinkedListPriorityQueue();

        pq.enqueue(2, 1);
        pq.enqueue(3, 2);
        pq.enqueue(1, 2);
        pq.enqueue(4, 4);
        pq.enqueue(5, 1);

        System.out.println("The highest priority value is: %.1f".formatted(pq.peek()));

        while (!pq.isEmpty()) {
            System.out.println(pq.dequeue());
        }
    }

    public static void binaryHeapArray() {
        BinaryHeapArrayPriorityQueue pq = new BinaryHeapArrayPriorityQueue();

        pq.enqueue(2);
        pq.enqueue(3);
        pq.enqueue(1);
        pq.enqueue(4);
        pq.enqueue(5);

        System.out.println("The highest priority value is: %.1f".formatted(pq.peek()));

        while (!pq.isEmpty()) {
            System.out.println(pq.dequeue());
        }
    }
}
