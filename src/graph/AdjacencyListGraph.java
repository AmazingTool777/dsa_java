package graph;

import queue.LinkedListQueue;
import stack.LinkedListStack;

import java.util.*;

/**
 * Graph implementation using an adjacency list.
 *
 * @param <TKey>    The type of the key that identifies a vertex.
 * @param <TVertex> The type of the vertices.
 */
public class AdjacencyListGraph<TKey, TVertex> {
    /**
     * Stores a vertex
     *
     * @param <TKey>
     * @param <TVertex>
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
     * Array list the vertex entries
     */
    private final ArrayList<VertexEntry<TKey, TVertex>> entries;

    /**
     * The count of distinct edges
     */
    private final int size;

    /**
     * Key-Value pairs of vertex keys and the indices of the corresponding vertices inside the entries
     */
    private final TreeMap<TKey, Integer> keysIndices;

    /**
     * Refers to an edge inside the adjacency list
     */
    private static class ListEdge {
        /**
         * The index of the destination vertex inside the vertex entries
         */
        public int index;

        /**
         * The weight of the edge
         */
        public double weight;

        public ListEdge(int index, double weight) {
            this.index = index;
            this.weight = weight;
        }
    }

    /**
     * The actual adjacency list.
     * The indices of each list are the same as the indices of the source vertices in the entries.
     */
    private final ArrayList<LinkedList<ListEdge>> lists;

    public AdjacencyListGraph(List<VertexEntry<TKey, TVertex>> entries) {
        this.entries = new ArrayList<>(entries);
        size = this.entries.size();
        keysIndices = new TreeMap<>();
        lists = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            keysIndices.put(this.entries.get(i).key, i);
            lists.add(new LinkedList<>());
        }
    }

    /**
     * Refers to an edge that is specified when building the graph
     *
     * @param key    The key of the destination vertex
     * @param weight The weight of the edge
     * @param <TKey> The type of the key
     */
    public record BuilderEdge<TKey>(TKey key, double weight) {
        public BuilderEdge(TKey key) {
            this(key, 0);
        }
    }

    /**
     * Adds adjacent edges from a source vertex in the list
     *
     * @param source The key of the source vertex
     * @param edges  The list of adjacent edges
     * @return The reference to the graph instance
     */
    public AdjacencyListGraph<TKey, TVertex> addEdgesFromVertex(TKey source, List<BuilderEdge<TKey>> edges) {
        int sourceIndex = keysIndices.get(source), destIndex;
        for (BuilderEdge<TKey> edge : edges) {
            destIndex = keysIndices.get(edge.key);
            lists.get(sourceIndex).add(new ListEdge(destIndex, edge.weight));
        }
        return this;
    }

    /**
     * The actual recursive implementation of the DFS traversal
     *
     * @param source         The index of the source vertex in the entries
     * @param visited        Set that stores the indices of the visited vertices
     * @param visitedEntries List of the entries of the visited vertices
     * @return The visited vertex entries
     */
    private LinkedList<VertexEntry<TKey, TVertex>> recursiveDFS(
            int source,
            TreeSet<Integer> visited,
            LinkedList<VertexEntry<TKey, TVertex>> visitedEntries
    ) {
        visited.add(source);
        visitedEntries.add(entries.get(source));

        for (ListEdge edge : lists.get(source)) {
            if (visited.contains(edge.index)) continue;
            recursiveDFS(edge.index, visited, visitedEntries);
        }

        return visitedEntries;
    }

    /**
     * The public method that initiates the recursive DFS traversal
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
        int source = keysIndices.get(sourceKey), current, adj;

        LinkedList<VertexEntry<TKey, TVertex>> visitedVertices = new LinkedList<>();
        TreeSet<Integer> visited = new TreeSet<>();
        LinkedListStack<Integer> toTraverse = new LinkedListStack<>();

        toTraverse.push(source);

        while (!toTraverse.isEmpty()) {
            current = toTraverse.pop();

            // We skip further traversals from the current vertex if it has already been visited
            if (visited.contains(current)) continue;

            visitedVertices.add(entries.get(current));
            visited.add(current);

            // In order to get the same order as the recursive implementation,
            // we iterate backward over the adjacent vertices
            // because of the stack that stores the next vertices to traverse
            LinkedList<ListEdge> adjEdges = lists.get(current);
            ListIterator<ListEdge> adjEdgesIterator = adjEdges.listIterator(adjEdges.size());
            while (adjEdgesIterator.hasPrevious()) {
                adj = adjEdgesIterator.previous().index;
                if (visited.contains(adj)) continue;
                toTraverse.push(adj);
            }
        }

        return visitedVertices;
    }

    /**
     * BFS traversal of the graph
     *
     * @param sourceKey The key of the source vertex
     * @return The list of the visited vertex entries
     */
    public LinkedList<VertexEntry<TKey, TVertex>> BFS(TKey sourceKey) {
        int source = keysIndices.get(sourceKey), current, adj;

        LinkedList<VertexEntry<TKey, TVertex>> visitedEntries = new LinkedList<>();
        TreeSet<Integer> visited = new TreeSet<>();
        LinkedListQueue<Integer> toTraverse = new LinkedListQueue<>();

        visitedEntries.add(entries.get(source));
        visited.add(source);
        toTraverse.enqueue(source);

        while (!toTraverse.isEmpty()) {
            current = toTraverse.dequeue();

            for (ListEdge edge : lists.get(current)) {
                adj = edge.index;
                if (visited.contains(adj)) continue;
                visitedEntries.add(entries.get(adj));
                visited.add(adj);
                toTraverse.enqueue(adj);
            }
        }

        return visitedEntries;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < size; i++) {
            if (i > 0) builder.append("\n");
            builder.append(entries.get(i).key).append(" : ");
            int edgesCount = lists.get(i).size(), j = 0;
            for (ListEdge edge : lists.get(i)) {
                builder.append(entries.get(edge.index).key);
                if (edge.weight != 0) {
                    int weightPrecision = 2;
                    String format = " %." + weightPrecision + "f";
                    builder.append(" (").append(String.format(format, edge.weight)).append(")");
                }
                if (j < edgesCount - 1) builder.append(" -> ");
                j++;
            }
        }

        return builder.toString();
    }
}
