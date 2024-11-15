package priorityqueue;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String quitInput;
        int implChoice;

        do {
            System.out.println("""
                    Choose the Priority Queue implementation:
                    1- Array
                    2- Linked List
                    3- Array-based Binary Heap
                    4- List-based Generic Binary Heap""");
            do {
                System.out.print("Your choice: ");
                implChoice = sc.nextInt();
            } while (implChoice < 1 || implChoice > 4);

            System.out.println();
            switch (implChoice) {
                case 1:
                    array();
                    break;
                case 2:
                    linkedList();
                    break;
                case 3:
                    binaryHeapArray();
                    break;
                case 4:
                    binaryHeapList();
                    break;
                default:
                    break;
            }

            System.out.println();
            do {
                System.out.print("Do you want to quit? (Y/n): ");
                quitInput = sc.nextLine();
            } while (!quitInput.equals("Y") && !quitInput.equals("n"));
            System.out.println();
        } while (quitInput.equals("n"));
    }

    public static void array() {
        ArrayPriorityQueue pq = new ArrayPriorityQueue();
        ArrayPriorityQueue.Item[] items = {
                new ArrayPriorityQueue.Item(2, 1),
                new ArrayPriorityQueue.Item(3, 2),
                new ArrayPriorityQueue.Item(1, 2),
                new ArrayPriorityQueue.Item(4, 4),
                new ArrayPriorityQueue.Item(5, 1),
        };

        for (ArrayPriorityQueue.Item item : items) {
            pq.enqueue(item.value, item.priority);
            System.out.printf("Enqueued - value: %.0f - priority: %d%n", item.value, item.priority);
        }

        System.out.printf("Peeked - value: %.0f - priority: %d%n", pq.peek().value, pq.peek().priority);

        while (!pq.isEmpty()) {
            ArrayPriorityQueue.Item item = pq.dequeue();
            System.out.printf("Dequeued - value: %.0f - priority: %d%n", item.value, item.priority);
        }
    }

    public static void linkedList() {
        LinkedListPriorityQueue pq = new LinkedListPriorityQueue();
        LinkedListPriorityQueue.Node[] nodes = {
                new LinkedListPriorityQueue.Node(2, 1),
                new LinkedListPriorityQueue.Node(3, 2),
                new LinkedListPriorityQueue.Node(1, 2),
                new LinkedListPriorityQueue.Node(4, 4),
                new LinkedListPriorityQueue.Node(5, 1),
        };

        for (LinkedListPriorityQueue.Node node : nodes) {
            pq.enqueue(node);
            System.out.printf("Enqueued - value: %.0f - priority: %d%n", node.value, node.priority);
        }

        System.out.printf("Peeked - value: %.0f - priority: %d%n", pq.peek().value, pq.peek().priority);

        while (!pq.isEmpty()) {
            LinkedListPriorityQueue.Node node = pq.dequeue();
            System.out.printf("Dequeued - value: %.0f - priority: %d%n", node.value, node.priority);
        }
    }

    public static void binaryHeapArray() {
        BinaryHeapArrayPriorityQueue pq = new BinaryHeapArrayPriorityQueue();
        double[] items = {1, 2, 3, 4, 5, 5, 6};

        for (double item : items) {
            pq.enqueue(item);
            System.out.printf("Enqueued - value: %.0f%n", item);
        }

        System.out.printf("Peeked - value: %.0f%n", pq.peek());

        int updatePriorityIndex = 3, newPriority = 7;
        pq.changePriority(updatePriorityIndex, newPriority);
        System.out.printf("Priority updated - index: %d - new value: %d%n", updatePriorityIndex, newPriority);
        System.out.printf("Peeked - value: %.1f%n", pq.peek());

        int removeIndex = 3;
        double removed = pq.remove(removeIndex);
        System.out.printf("Removed - index: %d - value: %.0f%n", removeIndex, removed);

        while (!pq.isEmpty()) {
            double dequeued = pq.dequeue();
            System.out.printf("Dequeued - value: %.0f%n", dequeued);
        }
    }

    public static void binaryHeapList() {
        BinaryHeapListPriorityQueue<Job> pq = new BinaryHeapListPriorityQueue<>();
        Job[] jobs = {
                new Job(1),
                new Job(2),
                new Job(3),
                new Job(4),
                new Job(5),
                new Job(5),
                new Job(6),
        };

        for (Job job : jobs) {
            pq.enqueue(job);
            System.out.printf("Enqueued - Value: %d%n", job.priority);
        }

        System.out.printf("Peeked - value: %d%n", pq.peek().priority);

        while (!pq.isEmpty()) {
            Job dequeued = pq.dequeue();
            System.out.printf("Dequeued - value: %d%n", dequeued.priority);
        }
    }

    public record Job(int priority) implements Comparable<Job> {

        @Override
        public int compareTo(Job job) {
            return priority - job.priority;
        }
    }
}