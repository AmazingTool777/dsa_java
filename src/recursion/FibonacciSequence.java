package recursion;

import java.util.Scanner;

public class FibonacciSequence {
    public static int getTerm(int n) {
        if (n < 2) {
            return n;
        }
        return getTerm(n - 1) + getTerm(n - 2);
    }

    public static void run() {
        Scanner sc = new Scanner(System.in);
        int n;

        do {
            System.out.print("Get the n-th term of the Fibonacci sequence starting from n = 2.\nEnter n: ");
            n = sc.nextInt();
        } while (n < 2);

        System.out.printf("The %d-th term of the Fibonacci sequence is: %d", n, getTerm(n));
    }
}
