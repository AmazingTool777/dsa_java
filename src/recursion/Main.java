package recursion;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String quitInput;
        int choice;

        do {
            System.out.println("""
                    Choose the example to run:
                    1- Fibonacci sequence
                    2- Towers of Hanoï""");
            do {
                System.out.print("Your choice: ");
                choice = sc.nextInt();
            } while (choice < 1 || choice > 2);

            System.out.println();
            if (choice == 1) {
                FibonacciSequence.run();
            }

            System.out.println();
            do {
                System.out.print("Do you want to quit? (Y/n): ");
                quitInput = sc.nextLine();
            } while (!quitInput.equals("Y") && !quitInput.equals("n"));
            System.out.println();
        } while (quitInput.equals("n"));
    }
}
