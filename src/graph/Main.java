package graph;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;

public class Main {
    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        String quitInput;

        do {
            int taskChoice;
            do {
                System.out.print("""
                        Choose the task to run:
                        1- Graph traversal with an adjacency list
                        2- Graph traversal with an adjacency matrix
                        Your choice:\s""");
                taskChoice = sc.nextInt();
            } while (taskChoice < 1);
            System.out.println();

            if (taskChoice == 1) {
                adjacencyListTraversal();
            }

            System.out.println();
            System.out.print("Do you want to quit? (Y/n): ");
            quitInput = sc.nextLine();
        } while (!quitInput.equals("Y") && !quitInput.equals("n"));
    }

    public static void adjacencyListTraversal() {
        String restartInput;
        do {
            AdjacencyListGraph.AdjacencyListItem<String, String> a = new AdjacencyListGraph.AdjacencyListItem<>("A", "A"),
                    b = new AdjacencyListGraph.AdjacencyListItem<>("B", "B"),
                    c = new AdjacencyListGraph.AdjacencyListItem<>("C", "C"),
                    d = new AdjacencyListGraph.AdjacencyListItem<>("D", "D"),
                    e = new AdjacencyListGraph.AdjacencyListItem<>("E", "E"),
                    f = new AdjacencyListGraph.AdjacencyListItem<>("F", "F"),
                    g = new AdjacencyListGraph.AdjacencyListItem<>("G", "G");
            TreeMap<String, LinkedList<AdjacencyListGraph.AdjacencyListItem<String, String>>> lists = new TreeMap<>();
            lists.put("A", new LinkedList<>(List.of(c, d, e)));
            lists.put("B", new LinkedList<>(List.of(c, f)));
            lists.put("C", new LinkedList<>(List.of(a, b, e, f, g)));
            lists.put("D", new LinkedList<>(List.of(a)));
            lists.put("E", new LinkedList<>(List.of(a, c)));
            lists.put("F", new LinkedList<>(List.of(b, c)));
            lists.put("G", new LinkedList<>(List.of(c)));
            AdjacencyListGraph<String, String> graph = new AdjacencyListGraph<>(lists);
            System.out.println("Graph:");
            System.out.println(graph);
            System.out.println();

            int implChoice;
            do {
                System.out.println("""
                        Choose the graph traversal implementation:
                        1- Recursive DFS
                        2- Iterative DFS
                        3- BFS""");
                System.out.print("Your choice: ");
                implChoice = sc.nextInt();
            } while (implChoice > 3 || implChoice < 1);
            System.out.println();

            String source;
            sc.nextLine();
            do {
                System.out.print("Set the source node: ");
                source = sc.nextLine();
            } while (source.length() > 1);

            LinkedList<String> nodes = switch (implChoice) {
                case 1 -> {
                    System.out.printf("Recursive DFS traversal from source %s:%n", source);
                    yield graph.DFS(source, new AdjacencyListGraph.RecursiveDFS<>());
                }
                case 2 -> {
                    System.out.printf("Iterative DFS traversal from source %s:%n", source);
                    yield graph.DFS(source, new AdjacencyListGraph.IterativeDFS<>());
                }
                case 3 -> {
                    System.out.printf("BFS traversal from source %s:%n", source);
                    yield graph.BFS(source);
                }
                default -> new LinkedList<>();
            };
            for (String node : nodes) {
                System.out.print(node + ", ");
            }
            System.out.println();

            do {
                System.out.print("Do you want to restart? (Y/n): ");
                restartInput = sc.nextLine();
            } while (!restartInput.equals("Y") && !restartInput.equals("n"));
        } while (restartInput.equals("Y"));
    }
}
