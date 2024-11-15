package queue;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String quitInput;
        int implChoice;

        do {
            System.out.println("""
                    Choose the Queue implementation:
                    1- Array
                    2- Linked List""");
            do {
                System.out.print("Your choice: ");
                implChoice = sc.nextInt();
            } while (implChoice < 1 || implChoice > 2);

            System.out.println();
            if (implChoice == 1) {
                arrayList();
            } else {
                linkedList();
            }

            System.out.println();
            do {
                System.out.print("Do you want to quit? (Y/n): ");
                quitInput = sc.nextLine();
            } while (!quitInput.equals("Y") && !quitInput.equals("n"));
            System.out.println();
        } while (quitInput.equals("n"));
    }

    public static void arrayList() {
        ArrayListQueue<String> queue = new ArrayListQueue<>();
        String[] items = {"Space", "Craft", "Scholar", "Poland", "Ship", "Earth", "Hike", "Relation"};

        for (String item : items) {
            queue.enqueue(item);
            System.out.printf("Enqueued - Value: %s\n", item);
        }

        System.out.printf("Peeked - Value: %s\n", queue.peek());

        while (!queue.isEmpty()) {
            System.out.printf("Dequeued - Value: %s\n", queue.dequeue());
        }
    }

    public static void linkedList() {
        LinkedListQueue<String> queue = new LinkedListQueue<>();
        String[] items = {"Space", "Craft", "Scholar", "Poland", "Ship", "Earth", "Hike", "Relation"};

        for (String item : items) {
            queue.enqueue(item);
            System.out.printf("Enqueued - Value: %s\n", item);
        }

        System.out.printf("Peeked - Value: %s\n", queue.peek());

        while (!queue.isEmpty()) {
            System.out.printf("Dequeued - Value: %s\n", queue.dequeue());
        }
    }
}
