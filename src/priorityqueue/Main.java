package priorityqueue;

public class Main {
    public static void main(String[] args) {
        // array();
        linkedList();
        // binaryHeapArray();
        // binaryHeapList();
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

        pq.enqueue(1);
        pq.enqueue(2);
        pq.enqueue(3);
        pq.enqueue(4);
        pq.enqueue(5);

        System.out.println("The highest priority value is: %.1f".formatted(pq.peek()));

        while (!pq.isEmpty()) {
            System.out.println(pq.dequeue());
        }
    }

    public static void binaryHeapList() {
        BinaryHeapListPriorityQueue<Job> pq = new BinaryHeapListPriorityQueue();

        pq.enqueue(new Job(1));
        pq.enqueue(new Job(2));
        pq.enqueue(new Job(3));
        pq.enqueue(new Job(4));
        pq.enqueue(new Job(5));

        System.out.println("The highest priority value is: %d".formatted(pq.peek().getPriority()));

        while (!pq.isEmpty()) {
            System.out.println(pq.dequeue().getPriority());
        }
    }

    public static class Job implements Comparable<Job> {
        private int priority;

        public Job(int priority) {
            this.priority = priority;
        }

        @Override
        public int compareTo(Job job) {
            return priority - job.priority;
        }

        public int getPriority() {
            return priority;
        }
    }
}