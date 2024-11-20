package graph;

import queue.LinkedListQueue;
import stack.LinkedListStack;

import java.util.*;

/**
 * Adjacency matrix implementation of a graph
 *
 * @param <TKey>    The type of the keys that identify vertices
 * @param <TVertex> The type of vertices
 */
public class AdjacencyMatrixGraph<TKey, TVertex> {
    /**
     * The size of the matrix
     */
    private final int size;

    /**
     * The actual adjacency matrix
     */
    private final double[][] matrix;

    /**
     * Key-Value pairs of vertices keys and vertices indices
     */
    private final TreeMap<TKey, Integer> keysIndices;

    /**
     * Class that stores a vertex along with its identifies the vertex
     *
     * @param <TKey>    The type of the vertex
     * @param <TVertex> The type of the actual vertex value
     */
    public static class VertexEntry<TKey, TVertex> {
        public TKey key;
        public TVertex vertex;

        public VertexEntry(TKey key, TVertex vertex) {
            this.key = key;
            this.vertex = vertex;
        }
    }

    /**
     * Entries of vertices used in the matrix
     */
    private final ArrayList<VertexEntry<TKey, TVertex>> entries;

    public AdjacencyMatrixGraph(List<VertexEntry<TKey, TVertex>> entries) {
        this.entries = new ArrayList<>(entries);

        size = this.entries.size();
        keysIndices = new TreeMap<>();
        for (int i = 0; i < size; i++) {
            keysIndices.put(this.entries.get(i).key, i);
        }

        this.matrix = new double[size][size];
    }

    /**
     * Record for an edge to a destination vertex during the building of the graph
     *
     * @param vertex The vertex of the destination vertex
     * @param weight The weight
     * @param <TKey> The type of the vertex
     */
    public record BuilderEdge<TKey>(TKey vertex, double weight) {
        public BuilderEdge(TKey vertex) {
            this(vertex, 1);
        }
    }

    /**
     * Adds vertices from a source vertex
     *
     * @param source The key of the source vertex
     * @param edges  The edges that originate from the source vertex
     * @return The graph instance
     */
    public AdjacencyMatrixGraph<TKey, TVertex> addEdgesFromVertex(
            TKey source,
            List<BuilderEdge<TKey>> edges
    ) {
        int i = keysIndices.get(source), j;
        for (BuilderEdge<TKey> edge : edges) {
            j = keysIndices.get(edge.vertex);
            matrix[i][j] = edge.weight;
        }
        return this;
    }

    /**
     * Gets the indices of the adjacent vertices of a source vertex
     *
     * @param source The index in the matrix of the source vertex
     * @return List of the indices of the adjacent vertices
     */
    private LinkedList<Integer> getAdjacentVerticesIndices(int source) {
        LinkedList<Integer> indices = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            if (matrix[source][i] != 0) {
                indices.add(i);
            }
        }
        return indices;
    }

    /**
     * The actual recursive implementation of the DFS traversal
     *
     * @param source         The index of the source vertex
     * @param visited        The set that stores the indices of the visited vertices
     * @param visitedEntries The list of the visited vertex entries
     * @return The list of the visited vertex entries
     */
    private LinkedList<VertexEntry<TKey, TVertex>> recursiveDFS(
            int source,
            TreeSet<Integer> visited,
            LinkedList<VertexEntry<TKey, TVertex>> visitedEntries
    ) {
        visited.add(source);
        visitedEntries.add(entries.get(source));

        for (int index : getAdjacentVerticesIndices(source)) {
            if (visited.contains(index)) continue;
            recursiveDFS(index, visited, visitedEntries);
        }

        return visitedEntries;
    }

    /**
     * Public method that initiates the recursive DFS traversal
     *
     * @param source The key of the source vertex
     * @return The list of the visited vertex entries
     */
    public LinkedList<VertexEntry<TKey, TVertex>> recursiveDFS(TKey source) {
        return recursiveDFS(keysIndices.get(source), new TreeSet<>(), new LinkedList<>());
    }

    /**
     * Iterative implementation of the DFS traversal
     *
     * @param sourceKey The key of the source vertex
     * @return The list of the visited vertex entries
     */
    public LinkedList<VertexEntry<TKey, TVertex>> iterativeDFS(TKey sourceKey) {
        int source = keysIndices.get(sourceKey), current;

        LinkedList<VertexEntry<TKey, TVertex>> visitedEntries = new LinkedList<>();
        TreeSet<Integer> visited = new TreeSet<>();
        LinkedListStack<Integer> toTraverse = new LinkedListStack<>();

        toTraverse.push(source);

        while (!toTraverse.isEmpty()) {
            current = toTraverse.pop();

            // We prevent further traversals from the current vertex if it has already been visited
            if (visited.contains(current)) continue;

            visited.add(current);
            visitedEntries.add(entries.get(current));

            // In order to get the same order as the recursive implementation,
            // we iterate backward over the adjacent vertices
            // because of the stack that stores the next vertices to traverse
            LinkedList<Integer> adjVertices = getAdjacentVerticesIndices(current);
            ListIterator<Integer> adjVerticesIterator = adjVertices.listIterator(adjVertices.size());
            while (adjVerticesIterator.hasPrevious()) {
                int adj = adjVerticesIterator.previous();
                if (visited.contains(adj)) continue;
                toTraverse.push(adj);
            }
        }

        return visitedEntries;
    }

    public LinkedList<VertexEntry<TKey, TVertex>> BFS(TKey sourceKey) {
        int source = keysIndices.get(sourceKey), current;

        LinkedList<VertexEntry<TKey, TVertex>> visitedEntries = new LinkedList<>();
        TreeSet<Integer> visited = new TreeSet<>();
        LinkedListQueue<Integer> toTraverse = new LinkedListQueue<>();

        visitedEntries.add(entries.get(source));
        visited.add(source);
        toTraverse.enqueue(source);

        while (!toTraverse.isEmpty()) {
            current = toTraverse.dequeue();

            for (int adj : getAdjacentVerticesIndices(current)) {
                if (visited.contains(adj)) continue;
                visitedEntries.add(entries.get(adj));
                visited.add(adj);
                toTraverse.enqueue(adj);
            }
        }

        return visitedEntries;
    }

    /**
     * The space occupied by a weight inside the `toString()` method
     */
    private int weightSpace = 10;

    public void setWeightSpace(int weightSpace) {
        this.weightSpace = weightSpace;
    }

    /**
     * The precision of the floating point of the weights inside the `toString()` method
     */
    private int weightPrecision = 2;

    public void setWeightPrecision(int weightPrecision) {
        this.weightPrecision = weightPrecision;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("    ");
        // Column indices header
        for (int i = 0; i < size; i++) {
            builder.append(String.format(" %" + weightSpace + "d", i));
        }
        builder.append("\n");
        // Columns header divider
        int colHeaderDividerLength = 4 + (weightSpace + 1) * size;
        builder.append("-".repeat(Math.max(0, colHeaderDividerLength)));
        builder.append("\n");
        // Rows
        for (int i = 0; i < size; i++) {
            if (i > 0) builder.append("\n");
            builder.append(String.format("%2d |", i)); // Row index
            for (int j = 0; j < size; j++) {
                builder.append(String.format(" %" + weightSpace + "." + weightPrecision + "f", matrix[i][j]));
            }
        }

        return builder.toString();
    }
}
