package recursion;

import java.util.Scanner;

public class TowersOfHanoi {
    private static final int[] towers = {1, 2, 3};

    private static int inferIntermediateTower(int from, int to) {
        for (int t : towers) {
            if (t != from && t != to) return t;
        }
        throw new RuntimeException("The intermediate tower cannot be inferred");
    }

    private static void moveDisks(int n, int from, int to) {
        if (n == 1) {
            System.out.printf("Move top disk from tower %d to tower %d%n", from, to);
            return;
        }

        int intermediate = inferIntermediateTower(from, to);
        moveDisks(n - 1, from, intermediate);
        moveDisks(1, from, to);
        moveDisks(n - 1, intermediate, to);
    }

    public static void run() {
        Scanner sc = new Scanner(System.in);
        int disksCount, from, to;

        do {
            System.out.print("Disks count: ");
            disksCount = sc.nextInt();
        } while (disksCount < 1);
        do {
            do {
                System.out.print("Start tower: ");
                from = sc.nextInt();
            } while (from < 1 || from > 3);
            do {
                System.out.print("Destination tower: ");
                to = sc.nextInt();
            } while (to < 1 || to > 3);
        } while (from == to);

        moveDisks(disksCount, from, to);
    }
}
