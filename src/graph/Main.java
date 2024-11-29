package graph;

import java.util.*;

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
                        3- Shortest path finding with an adjacency list
                        4- Shortest path finding with an adjacency matrix
                        Your choice:\s""");
                taskChoice = sc.nextInt();
            } while (taskChoice < 1);
            System.out.println();

            switch (taskChoice) {
                case 2:
                    adjacencyMatrixTraversal();
                    break;
                case 3:
                    adjacencyListDijkstraShortestPath();
                    break;
                case 4:
                    adjacencyMatrixDijkstraShortestPath();
                    break;
                default:
                    adjacencyListTraversal();
                    break;
            }

            System.out.println();
            System.out.print("Do you want to quit? (Y/n): ");
            quitInput = sc.nextLine();
        } while (quitInput.equals("n"));
        System.out.println();
    }

    public static void adjacencyListTraversal() {
        AdjacencyListGraph<String, String> graph = new AdjacencyListGraph<>(List.of(
                new AdjacencyListGraph.VertexEntry<>("A", "A"),
                new AdjacencyListGraph.VertexEntry<>("B", "B"),
                new AdjacencyListGraph.VertexEntry<>("C", "C"),
                new AdjacencyListGraph.VertexEntry<>("D", "D"),
                new AdjacencyListGraph.VertexEntry<>("E", "E"),
                new AdjacencyListGraph.VertexEntry<>("F", "F"),
                new AdjacencyListGraph.VertexEntry<>("G", "G")
        ))
                .addEdgesFromVertex("A", List.of(
                        new AdjacencyListGraph.BuilderEdge<>("C"),
                        new AdjacencyListGraph.BuilderEdge<>("D"),
                        new AdjacencyListGraph.BuilderEdge<>("E")
                ))
                .addEdgesFromVertex("B", List.of(
                        new AdjacencyListGraph.BuilderEdge<>("C"),
                        new AdjacencyListGraph.BuilderEdge<>("F")
                ))
                .addEdgesFromVertex("C", List.of(
                        new AdjacencyListGraph.BuilderEdge<>("A"),
                        new AdjacencyListGraph.BuilderEdge<>("B"),
                        new AdjacencyListGraph.BuilderEdge<>("E"),
                        new AdjacencyListGraph.BuilderEdge<>("F"),
                        new AdjacencyListGraph.BuilderEdge<>("G")
                ))
                .addEdgesFromVertex("D", List.of(
                        new AdjacencyListGraph.BuilderEdge<>("A")
                ))
                .addEdgesFromVertex("E", List.of(
                        new AdjacencyListGraph.BuilderEdge<>("A"),
                        new AdjacencyListGraph.BuilderEdge<>("C")
                ))
                .addEdgesFromVertex("F", List.of(
                        new AdjacencyListGraph.BuilderEdge<>("B"),
                        new AdjacencyListGraph.BuilderEdge<>("C")
                ))
                .addEdgesFromVertex("G", List.of(
                        new AdjacencyListGraph.BuilderEdge<>("C")
                ));

        String restartInput;
        do {
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

            LinkedList<AdjacencyListGraph.VertexEntry<String, String>> entries = switch (implChoice) {
                case 1 -> {
                    System.out.printf("Recursive DFS traversal from source %s:%n", source);
                    yield graph.recursiveDFS(source);
                }
                case 2 -> {
                    System.out.printf("Iterative DFS traversal from source %s:%n", source);
                    yield graph.iterativeDFS(source);
                }
                case 3 -> {
                    System.out.printf("BFS traversal from source %s:%n", source);
                    yield graph.BFS(source);
                }
                default -> new LinkedList<>();
            };

            for (AdjacencyListGraph.VertexEntry<String, String> entry : entries) {
                System.out.print(entry.key + ", ");
            }
            System.out.println("\n");

            do {
                System.out.print("Do you want to restart? (Y/n): ");
                restartInput = sc.nextLine();
            } while (!restartInput.equals("Y") && !restartInput.equals("n"));
            System.out.println();
        } while (restartInput.equals("Y"));
        System.out.println();
    }

    public static void adjacencyMatrixTraversal() {
        AdjacencyMatrixGraph<Integer, Integer> graph = new AdjacencyMatrixGraph<>(List.of(
                new AdjacencyMatrixGraph.VertexEntry<>(0, 0),
                new AdjacencyMatrixGraph.VertexEntry<>(1, 1),
                new AdjacencyMatrixGraph.VertexEntry<>(2, 2),
                new AdjacencyMatrixGraph.VertexEntry<>(3, 3),
                new AdjacencyMatrixGraph.VertexEntry<>(4, 4),
                new AdjacencyMatrixGraph.VertexEntry<>(5, 5)
        ))
                .addEdgesFromVertex(0, List.of(
                        new AdjacencyMatrixGraph.BuilderEdge<>(1),
                        new AdjacencyMatrixGraph.BuilderEdge<>(5)
                ))
                .addEdgesFromVertex(1, List.of(
                        new AdjacencyMatrixGraph.BuilderEdge<>(2),
                        new AdjacencyMatrixGraph.BuilderEdge<>(4)
                ))
                .addEdgesFromVertex(2, List.of(
                        new AdjacencyMatrixGraph.BuilderEdge<>(3)
                ))
                .addEdgesFromVertex(3, List.of(
                        new AdjacencyMatrixGraph.BuilderEdge<>(0),
                        new AdjacencyMatrixGraph.BuilderEdge<>(1)
                ))
                .addEdgesFromVertex(4, List.of(
                        new AdjacencyMatrixGraph.BuilderEdge<>(3)
                ))
                .addEdgesFromVertex(5, List.of(
                        new AdjacencyMatrixGraph.BuilderEdge<>(4)
                ));

        graph.setWeightSpace(5);
        graph.setWeightPrecision(0);

        sc.nextLine();
        String restartInput;
        do {
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

            int source;
            do {
                System.out.print("Set the source node: ");
                source = sc.nextInt();
            } while (source < 0 || source > 5);
            sc.nextLine();

            LinkedList<AdjacencyMatrixGraph.VertexEntry<Integer, Integer>> entries = switch (implChoice) {
                case 1 -> {
                    System.out.printf("Recursive DFS traversal from source %d:%n", source);
                    yield graph.recursiveDFS(source);
                }
                case 2 -> {
                    System.out.printf("Iterative DFS traversal from source %d:%n", source);
                    yield graph.iterativeDFS(source);
                }
                case 3 -> {
                    System.out.printf("BFS traversal from source %d:%n", source);
                    yield graph.BFS(source);
                }
                default -> new LinkedList<>();
            };

            for (AdjacencyMatrixGraph.VertexEntry<Integer, Integer> entry : entries) {
                System.out.printf("%d, ", entry.key);
            }
            System.out.println();

            System.out.println();
            System.out.print("Do you want to restart? (Y/n): ");
            restartInput = sc.nextLine();
        } while (restartInput.equals("Y"));
    }

    /**
     * Shortest path finding in an adjacency list graph using Moore Dijkstra's algorithm.
     * The graph that we used can be found in this article:
     * <a href="https://www.geeksforgeeks.org/dijkstras-shortest-path-algorithm-greedy-algo-7/">...</a>.
     */
    public static void adjacencyListDijkstraShortestPath() {
        String restartInput;
        do {
            AdjacencyListGraph<Integer, Integer> graph = new AdjacencyListGraph<>(List.of(
                    new AdjacencyListGraph.VertexEntry<>(0, 0),
                    new AdjacencyListGraph.VertexEntry<>(1, 1),
                    new AdjacencyListGraph.VertexEntry<>(2, 2),
                    new AdjacencyListGraph.VertexEntry<>(3, 3),
                    new AdjacencyListGraph.VertexEntry<>(4, 4),
                    new AdjacencyListGraph.VertexEntry<>(5, 5),
                    new AdjacencyListGraph.VertexEntry<>(6, 6),
                    new AdjacencyListGraph.VertexEntry<>(7, 7),
                    new AdjacencyListGraph.VertexEntry<>(8, 8)
            ))
                    .addEdgesFromVertex(0, List.of(
                            new AdjacencyListGraph.BuilderEdge<>(1, 4),
                            new AdjacencyListGraph.BuilderEdge<>(7, 8)
                    ))
                    .addEdgesFromVertex(1, List.of(
                            new AdjacencyListGraph.BuilderEdge<>(0, 4),
                            new AdjacencyListGraph.BuilderEdge<>(2, 8),
                            new AdjacencyListGraph.BuilderEdge<>(7, 11)
                    ))
                    .addEdgesFromVertex(2, List.of(
                            new AdjacencyListGraph.BuilderEdge<>(1, 8),
                            new AdjacencyListGraph.BuilderEdge<>(3, 7),
                            new AdjacencyListGraph.BuilderEdge<>(5, 4),
                            new AdjacencyListGraph.BuilderEdge<>(8, 2)
                    ))
                    .addEdgesFromVertex(3, List.of(
                            new AdjacencyListGraph.BuilderEdge<>(4, 9),
                            new AdjacencyListGraph.BuilderEdge<>(5, 14)
                    ))
                    .addEdgesFromVertex(4, List.of(
                            new AdjacencyListGraph.BuilderEdge<>(3, 9),
                            new AdjacencyListGraph.BuilderEdge<>(5, 10)
                    ))
                    .addEdgesFromVertex(5, List.of(
                            new AdjacencyListGraph.BuilderEdge<>(2, 4),
                            new AdjacencyListGraph.BuilderEdge<>(3, 14),
                            new AdjacencyListGraph.BuilderEdge<>(4, 10),
                            new AdjacencyListGraph.BuilderEdge<>(6, 2)
                    ))
                    .addEdgesFromVertex(6, List.of(
                            new AdjacencyListGraph.BuilderEdge<>(5, 2),
                            new AdjacencyListGraph.BuilderEdge<>(7, 1),
                            new AdjacencyListGraph.BuilderEdge<>(8, 6)
                    ))
                    .addEdgesFromVertex(7, List.of(
                            new AdjacencyListGraph.BuilderEdge<>(0, 8),
                            new AdjacencyListGraph.BuilderEdge<>(1, 11),
                            new AdjacencyListGraph.BuilderEdge<>(6, 1),
                            new AdjacencyListGraph.BuilderEdge<>(8, 7)
                    ))
                    .addEdgesFromVertex(8, List.of(
                            new AdjacencyListGraph.BuilderEdge<>(2, 2),
                            new AdjacencyListGraph.BuilderEdge<>(6, 6),
                            new AdjacencyListGraph.BuilderEdge<>(7, 7)
                    ));

            System.out.println("Graph:");
            System.out.println(graph);

            System.out.println();
            int source;
            do {
                System.out.print("Set the source node: ");
                source = sc.nextInt();
            } while (source < 0 || source > 8);
            sc.nextLine();
            System.out.println();

            TreeMap<Integer, AdjacencyListGraph.ShortestPathToVertex<Integer, Integer>> paths = graph.findDijkstraShortestPaths(source);

            System.out.printf("Shortest paths from source %d to all edges:%n", source);
            for (Map.Entry<Integer, AdjacencyListGraph.ShortestPathToVertex<Integer, Integer>> entry : paths.entrySet()) {
                Integer key = entry.getKey();
                AdjacencyListGraph.ShortestPathToVertex<Integer, Integer> path = entry.getValue();
                System.out.printf("%d (%.0f):", key, path.compoundWeight());
                int i = 0;
                for (AdjacencyListGraph.VertexEntry<Integer, Integer> vertexEntry : path.entries()) {
                    if (i > 0) System.out.print(" ->");
                    System.out.printf(" %d", vertexEntry.key);
                    i++;
                }
                System.out.println();
            }

            System.out.println();
            System.out.print("Do you want to restart? (Y/n): ");
            restartInput = sc.nextLine();
        } while (restartInput.equals("Y"));
    }

    /**
     * Shortest path finding in an adjacency matrix graph using Moore Dijkstra's algorithm.
     */
    public static void adjacencyMatrixDijkstraShortestPath() {
        String restartInput;
        do {
            AdjacencyMatrixGraph<Integer, Integer> graph = new AdjacencyMatrixGraph<>(List.of(
                    new AdjacencyMatrixGraph.VertexEntry<>(1, 1),
                    new AdjacencyMatrixGraph.VertexEntry<>(2, 2),
                    new AdjacencyMatrixGraph.VertexEntry<>(3, 3),
                    new AdjacencyMatrixGraph.VertexEntry<>(4, 4),
                    new AdjacencyMatrixGraph.VertexEntry<>(5, 5),
                    new AdjacencyMatrixGraph.VertexEntry<>(6, 6),
                    new AdjacencyMatrixGraph.VertexEntry<>(7, 7),
                    new AdjacencyMatrixGraph.VertexEntry<>(8, 8)
            ))
                    .addEdgesFromVertex(1, List.of(
                            new AdjacencyMatrixGraph.BuilderEdge<>(2, 3),
                            new AdjacencyMatrixGraph.BuilderEdge<>(3, 3)
                    ))
                    .addEdgesFromVertex(2, List.of(
                            new AdjacencyMatrixGraph.BuilderEdge<>(4, 2),
                            new AdjacencyMatrixGraph.BuilderEdge<>(5, 1)
                    ))
                    .addEdgesFromVertex(3, List.of(
                            new AdjacencyMatrixGraph.BuilderEdge<>(1, 2),
                            new AdjacencyMatrixGraph.BuilderEdge<>(2, 2),
                            new AdjacencyMatrixGraph.BuilderEdge<>(4, 2)
                    ))
                    .addEdgesFromVertex(4, List.of(
                            new AdjacencyMatrixGraph.BuilderEdge<>(5, 1),
                            new AdjacencyMatrixGraph.BuilderEdge<>(6, 2),
                            new AdjacencyMatrixGraph.BuilderEdge<>(7, 1)
                    ))
                    .addEdgesFromVertex(5, List.of(
                            new AdjacencyMatrixGraph.BuilderEdge<>(6, 3),
                            new AdjacencyMatrixGraph.BuilderEdge<>(7, 2)
                    ))
                    .addEdgesFromVertex(6, List.of(
                            new AdjacencyMatrixGraph.BuilderEdge<>(7, 2)
                    ))
                    .addEdgesFromVertex(7, List.of(
                            new AdjacencyMatrixGraph.BuilderEdge<>(8, 1)
                    ))
                    .addEdgesFromVertex(8, List.of(
                            new AdjacencyMatrixGraph.BuilderEdge<>(3, 4)
                    ));

            System.out.println("Graph:");
            graph.setWeightSpace(5);
            System.out.println(graph);
            System.out.println();

            int source;
            do {
                System.out.print("Set the source vertex: ");
                source = sc.nextInt();
            } while (source < 1 || source > 8);
            sc.nextLine();
            System.out.println();

            System.out.printf("Shortest paths from source %d to all vertices:%n", source);
            TreeMap<Integer, AdjacencyMatrixGraph.ShortestPathToVertex<Integer, Integer>> paths = graph.findDijkstraShortestPaths(source);
            for (Map.Entry<Integer, AdjacencyMatrixGraph.ShortestPathToVertex<Integer, Integer>> entry : paths.entrySet()) {
                Integer key = entry.getKey();
                AdjacencyMatrixGraph.ShortestPathToVertex<Integer, Integer> path = entry.getValue();
                System.out.printf("%d (%.0f): ", key, path.compoundWeight());
                int i = 0;
                for (AdjacencyMatrixGraph.VertexEntry<Integer, Integer> vertexEntry : path.entries()) {
                    if (i > 0) System.out.print(" -> ");
                    System.out.print(vertexEntry.key);
                    i++;
                }
                System.out.println();
            }
            System.out.println();

            do {
                System.out.print("Do you want to restart? (Y/n): ");
                restartInput = sc.nextLine();
            } while (!restartInput.equals("Y") && !restartInput.equals("n"));
            System.out.println();
        } while (restartInput.equals("Y"));
    }
}
