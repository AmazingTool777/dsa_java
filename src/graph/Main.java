package graph;

import graph.common.Graph;
import graph.common.GraphArguments;
import graph.common.GraphArgumentsFactory;

import java.util.*;

public class Main {
    private static final Scanner sc = new Scanner(System.in);

    /**
     * Record for running the operations to perform on a graph.
     *
     * @param graph     The graph instance
     * @param <TKey>    Data type of the vertex keys
     * @param <TVertex> Data type of the vertex data
     */
    private record GraphProgramRunner<TKey, TVertex>(Graph<TKey, TVertex> graph) {
        public void run() {
            System.out.println("Graph:");
            System.out.println(graph);
        }
    }

    /**
     * Factories instances for our 4 samples of graphs.
     */
    private static final Graph1ArgumentsFactory graph1ArgumentsFactory = new Graph1ArgumentsFactory();
    private static final Graph2ArgumentsFactory graph2ArgumentsFactory = new Graph2ArgumentsFactory();
    private static final Graph3ArgumentsFactory graph3ArgumentsFactory = new Graph3ArgumentsFactory();
    private static final Graph4ArgumentsFactory graph4ArgumentsFactory = new Graph4ArgumentsFactory();

    public static void main(String[] args) {
        boolean wannaQuit = false;

        while (!wannaQuit) {
            int graphInput;
            do {
                System.out.println("Which graph would you want to work on?");
                System.out.println("1. Graph 1");
                System.out.println("2. Graph 2");
                System.out.println("3. Graph 3");
                System.out.println("4. Graph 4");
                System.out.println("Your choice: ");
                graphInput = sc.nextInt();

                String graphImplementationInput;
                do {
                    System.out.println();
                    System.out.println("How would you like to implement the graph?");
                    System.out.println("a. Adjacency list");
                    System.out.println("b. Adjacency matrix");
                    System.out.println("Your choice: ");
                    graphImplementationInput = sc.next();
                } while (!graphImplementationInput.equals("a") && !graphImplementationInput.equals("b"));
                sc.nextLine();
                System.out.println();

                switch (graphInput) {
                    case 1:
                    case 2: {
                        GraphArguments<Integer, Integer> graphArguments = graphInput == 1 ? graph1ArgumentsFactory.create() : graph2ArgumentsFactory.create();
                        Graph<Integer, Integer> graph = graphImplementationInput.equals("a") ? new AdjacencyListGraph<>(graphArguments.entries(), graphArguments.edgesBuilder()) : new AdjacencyMatrixGraph<>(graphArguments.entries(), graphArguments.edgesBuilder());
                        GraphProgramRunner<Integer, Integer> graph1or2Runner = new GraphProgramRunner<>(graph);
                        graph1or2Runner.run();
                        break;
                    }

                    case 3:
                    case 4: {
                        GraphArguments<String, String> graphArguments = graphInput == 3 ? graph3ArgumentsFactory.create() : graph4ArgumentsFactory.create();
                        Graph<String, String> graph = graphImplementationInput.equals("a") ? new AdjacencyListGraph<>(graphArguments.entries(), graphArguments.edgesBuilder()) : new AdjacencyMatrixGraph<>(graphArguments.entries(), graphArguments.edgesBuilder());
                        GraphProgramRunner<String, String> graph3or4Runner = new GraphProgramRunner<>(graph);
                        graph3or4Runner.run();
                        break;
                    }
                }
            } while (graphInput < 1 || graphInput > 4);

            System.out.println();
            String wannaQuitInput;
            do {
                System.out.println("Want to quit? (y/n): ");
                wannaQuitInput = sc.next();
            } while (!wannaQuitInput.equals("y") && !wannaQuitInput.equals("n"));
            wannaQuit = wannaQuitInput.equals("y");

            System.out.println();
        }
    }
}
