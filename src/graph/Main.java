package graph;

import graph.common.*;

import java.util.*;

public class Main {
    /**
     * Reusable input scanner
     */
    private static final Scanner sc = new Scanner(System.in);

    /**
     * Abstract class for running the operations to perform on a graph.
     *
     * @param <TKey>    Data type of the vertex keys
     * @param <TVertex> Data type of the vertex data
     */
    private static abstract class GraphProgramRunner<TKey, TVertex> {
        /**
         * The graph instance to base our operations upon
         */
        private final Graph<TKey, TVertex> graph;

        public GraphProgramRunner(Graph<TKey, TVertex> graph) {
            this.graph = graph;
        }

        /**
         * Prompts the user for a vertex key
         *
         * @return The vertex key
         */
        protected abstract TKey promptVertexKey();

        /**
         * Method for running the common prompt at the end of a task
         *
         * @return End of task input from the user
         */
        private int endOfTaskPrompt() {
            int choice;
            System.out.println("""
                    What's next?
                    1. Restart
                    2. Go back
                    3. Quit""");
            do {
                System.out.print("Your choice: ");
                choice = sc.nextInt();
            } while (choice < 1 || choice > 3);
            if (choice == 3) {
                System.exit(0);
            }
            sc.nextLine();
            System.out.println();
            return choice;
        }

        /**
         * Runs the program for graph traversal
         */
        private void runGraphTraversal() {
            int endOfTaskInput;

            do {
                int traversalChoice;

                do {
                    System.out.println("""
                            Graph traversal method:
                            1. Depth-First search (DFS)
                            2. Breadth-First search (BFS)""");
                    System.out.print("Your choice: ");
                    traversalChoice = sc.nextInt();
                } while (traversalChoice < 1 || traversalChoice > 2);
                System.out.println();

                GraphTraversalStrategy<TKey, TVertex> graphTraversalStrategy;

                if (traversalChoice == 1) {
                    int dfsChoice;

                    do {
                        System.out.println("""
                                DFS implementation:
                                1. Recursive
                                2. Iterative""");
                        System.out.print("Your choice: ");
                        dfsChoice = sc.nextInt();
                    } while (dfsChoice < 1 || dfsChoice > 2);
                    System.out.println();

                    boolean isIterative = dfsChoice == 2;
                    graphTraversalStrategy = new DepthFirstSearchStrategy<>(isIterative);
                } else {
                    graphTraversalStrategy = new BreadthFirstSearchStrategy<>();
                }

                System.out.println("Enter the key of the source vertex:");
                TKey sourceKey = promptVertexKey();
                LinkedList<VertexEntry<TKey, TVertex>> visitedEntries = graph.traverse(sourceKey, graphTraversalStrategy);
                System.out.println();

                System.out.println("The traversed vertices by order:");
                int i = 0;
                for (VertexEntry<TKey, TVertex> entry : visitedEntries) {
                    if (i > 0) {
                        System.out.print(" -> ");
                    }
                    System.out.print(entry.key());
                    i++;
                }
                System.out.println("\n");

                endOfTaskInput = endOfTaskPrompt();
            } while (endOfTaskInput == 1);
        }

        /**
         * Main graph program
         */
        public void run() {
            System.out.println("Graph:");
            System.out.println(graph);
            System.out.println();
            runGraphTraversal();
        }
    }

    /**
     * Concrete class for running a graph program having vertex keys as integer.
     *
     * @param <TVertex> Data type of vertex data
     */
    private static class IntegerKeyGraphProgramRunner<TVertex> extends GraphProgramRunner<Integer, TVertex> {
        public IntegerKeyGraphProgramRunner(Graph<Integer, TVertex> graph) {
            super(graph);
        }

        @Override
        protected Integer promptVertexKey() {
            return sc.nextInt();
        }
    }

    /**
     * Concrete class for running a graph program having vertex keys as string.
     *
     * @param <TVertex> Data type of vertex data
     */
    private static class StringKeyGraphProgramRunner<TVertex> extends GraphProgramRunner<String, TVertex> {
        public StringKeyGraphProgramRunner(Graph<String, TVertex> graph) {
            super(graph);
        }

        @Override
        protected String promptVertexKey() {
            sc.nextLine();
            return sc.nextLine();
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
                System.out.print("Your choice: ");
                graphInput = sc.nextInt();

                String graphImplementationInput;
                do {
                    System.out.println();
                    System.out.println("How would you like to implement the graph?");
                    System.out.println("a. Adjacency list");
                    System.out.println("b. Adjacency matrix");
                    System.out.print("Your choice: ");
                    graphImplementationInput = sc.next();
                } while (!graphImplementationInput.equals("a") && !graphImplementationInput.equals("b"));
                sc.nextLine();
                System.out.println();

                switch (graphInput) {
                    case 1:
                    case 2: {
                        GraphArguments<Integer, Integer> graphArguments = graphInput == 1 ? graph1ArgumentsFactory.create() : graph2ArgumentsFactory.create();
                        Graph<Integer, Integer> graph = graphImplementationInput.equals("a") ? new AdjacencyListGraph<>(graphArguments.entries(), graphArguments.edgesBuilder()) : new AdjacencyMatrixGraph<>(graphArguments.entries(), graphArguments.edgesBuilder());
                        GraphProgramRunner<Integer, Integer> graph1or2Runner = new IntegerKeyGraphProgramRunner<>(graph);
                        graph1or2Runner.run();
                        break;
                    }

                    case 3:
                    case 4: {
                        GraphArguments<String, String> graphArguments = graphInput == 3 ? graph3ArgumentsFactory.create() : graph4ArgumentsFactory.create();
                        Graph<String, String> graph = graphImplementationInput.equals("a") ? new AdjacencyListGraph<>(graphArguments.entries(), graphArguments.edgesBuilder()) : new AdjacencyMatrixGraph<>(graphArguments.entries(), graphArguments.edgesBuilder());
                        GraphProgramRunner<String, String> graph3or4Runner = new StringKeyGraphProgramRunner<>(graph);
                        graph3or4Runner.run();
                        break;
                    }
                }
            } while (graphInput < 1 || graphInput > 4);

            String wannaQuitInput;
            do {
                System.out.print("Want to quit? (y/n): ");
                wannaQuitInput = sc.nextLine();
            } while (!wannaQuitInput.equals("y") && !wannaQuitInput.equals("n"));
            wannaQuit = wannaQuitInput.equals("y");

            System.out.println();
        }
    }
}
