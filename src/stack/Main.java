package stack;

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
                    2- Linked List""");
            do {
                System.out.print("Your choice: ");
                implChoice = sc.nextInt();
            } while (implChoice < 1 || implChoice > 2);

            System.out.println();
            if (implChoice == 1) {
                array();
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
        ArrayStack<String> stack = new ArrayStack<>();
        String[] items = {"Space", "Craft", "Scholar", "Poland", "Ship", "Earth", "Hike", "Relation"};

        for (String item : items) {
            stack.push(item);
            System.out.printf("Pushed - Value: %s\n", item);
        }

        System.out.printf("Peeked - Value: %s\n", stack.peek());

        while (!stack.isEmpty()) {
            System.out.printf("Popped - Value: %s\n", stack.pop());
        }
    }
}
