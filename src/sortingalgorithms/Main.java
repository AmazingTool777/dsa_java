package sortingalgorithms;

import utils.orderingstrategy.MaxOrdering;
import utils.orderingstrategy.MinOrdering;
import utils.orderingstrategy.SortOrderingStrategy;

import java.util.Scanner;

public class Main {
    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        String quitInput;
        do {
            String datatype;
            do {
                System.out.print("""
                        What data type should be the array items?
                        a- Number
                        b- String
                        Your choice:\s""");
                datatype = sc.nextLine();
            } while (!datatype.equals("a") && !datatype.equals("b"));
            System.out.println();

            System.out.print("Define the length of the array: ");
            int length = sc.nextInt();
            System.out.println();
            if (datatype.equals("a")) numbersSortingTask(length);
            else {
                sc.nextLine();
                stringsSortingTask(length);
            }

            System.out.println();
            do {
                System.out.print("Do you want to quit? (Y/n): ");
                quitInput = sc.nextLine();
            } while (!quitInput.equals("Y") & !quitInput.equals("n"));
            System.out.println();
        } while (quitInput.equals("n"));
    }

    public static void numbersSortingTask(int length) {
        int precision;
        System.out.print("Precision of the floating point: ");
        precision = sc.nextInt();
        System.out.println();

        Double[] array = new Double[length], sortedArray;

        System.out.printf("Define the %d array items:%n", length);
        for (int i = 0; i < length; i++) {
            System.out.printf("%d: ", i);
            array[i] = sc.nextDouble();
        }
        System.out.println();

        int sortAlgoChoice;
        do {
            System.out.print("""
                    Choose the sorting algorithms:
                    1- Bubble sort
                    2- Selection sort
                    3- Insertion sort
                    4- Merge sort
                    5- Quick sort
                    6- Heap sort
                    Your choice:\s""");
            sortAlgoChoice = sc.nextInt();
        } while (sortAlgoChoice < 0 || sortAlgoChoice > 6);
        System.out.println();
        sc.nextLine();

        String orderingChoice;
        do {
            System.out.print("""
                    Choose the ordering:
                    a- Ascending (Min)
                    b- Descending (Max)
                    Your choice:\s""");
            orderingChoice = sc.nextLine();
        } while (!orderingChoice.equals("a") && !orderingChoice.equals("b"));
        System.out.println();

        System.out.println("The original array:");
        for (int i = 0; i < length; i++) {
            String format = "%." + precision + "f, ";
            System.out.printf(format, array[i]);
        }
        System.out.println();

        SortOrderingStrategy<Double> ordering = orderingChoice.equals("a")
                ? new MinOrdering<>()
                : new MaxOrdering<>();
        SortAlgoStrategy<Double> sortStrategy = switch (sortAlgoChoice) {
            case 2 -> new SelectionSort<>();
            case 3 -> new InsertionSort<>();
            case 4 -> new MergeSort<>();
            case 5 -> new QuickSort<>();
            case 6 -> new HeapSort<>();
            default -> new BubbleSort<>();
        };
        sortedArray = sortStrategy.sort(array, ordering);

        System.out.println("The sorted array:");
        for (double item : sortedArray) {
            String format = "%." + precision + "f, ";
            System.out.printf(format, item);
        }
        System.out.println();
    }

    public static void stringsSortingTask(int length) {
        String[] array = new String[length], sortedArray;

        System.out.printf("Define the %d array items:%n", length);
        for (int i = 0; i < length; i++) {
            System.out.printf("%d: ", i);
            array[i] = sc.nextLine();
        }
        System.out.println();

        int sortAlgoChoice;
        do {
            System.out.print("""
                    Choose the sorting algorithms:
                    1- Bubble sort
                    2- Selection sort
                    3- Insertion sort
                    4- Merge sort
                    5- Quick sort
                    6- Heap sort
                    Your choice:\s""");
            sortAlgoChoice = sc.nextInt();
        } while (sortAlgoChoice < 0 || sortAlgoChoice > 6);
        System.out.println();
        sc.nextLine();

        String orderingChoice;
        do {
            System.out.print("""
                    Choose the ordering:
                    a- Ascending (Min)
                    b- Descending (Max)
                    Your choice:\s""");
            orderingChoice = sc.nextLine();
        } while (!orderingChoice.equals("a") && !orderingChoice.equals("b"));
        System.out.println();

        System.out.println("The original array:");
        for (int i = 0; i < length; i++) {
            System.out.printf("%s, ", array[i]);
        }
        System.out.println();

        SortOrderingStrategy<String> ordering = orderingChoice.equals("a")
                ? new MinOrdering<>()
                : new MaxOrdering<>();
        SortAlgoStrategy<String> sortStrategy = switch (sortAlgoChoice) {
            case 2 -> new SelectionSort<>();
            case 3 -> new InsertionSort<>();
            case 4 -> new MergeSort<>();
            case 5 -> new QuickSort<>();
            case 6 -> new HeapSort<>();
            default -> new BubbleSort<>();
        };
        sortedArray = sortStrategy.sort(array, ordering);

        System.out.println("The sorted array:");
        for (String item : sortedArray) {
            System.out.printf("%s, ", item);
        }
        System.out.println();
    }
}
