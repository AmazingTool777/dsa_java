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
     * The actual adjacency list
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

    public LinkedList<VertexEntry<TKey, TVertex>> recursiveDFS(TKey source) {
        return recursiveDFS(keysIndices.get(source), new TreeSet<>(), new LinkedList<>());
    }

    public LinkedList<VertexEntry<TKey, TVertex>> iterativeDFS(TKey sourceKey) {
        int source = keysIndices.get(sourceKey), current;

        LinkedList<VertexEntry<TKey, TVertex>> visitedVertices = new LinkedList<>();
        TreeSet<Integer> visited = new TreeSet<>();
        LinkedListStack<Integer> toTraverse = new LinkedListStack<>();

        toTraverse.push(source);

        while (!toTraverse.isEmpty()) {
            current = toTraverse.pop();

            if (visited.contains(current)) continue;

            visitedVertices.add(entries.get(current));
            visited.add(current);

            LinkedList<ListEdge> adjEdges = lists.get(current);
            ListIterator<ListEdge> adjEdgesIterator = adjEdges.listIterator(adjEdges.size());
            while (adjEdgesIterator.hasPrevious()) {
                ListEdge edge = adjEdgesIterator.previous();
                if (visited.contains(edge.index)) continue;
                toTraverse.push(edge.index);
            }
        }

        return visitedVertices;
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

            for (ListEdge edge : lists.get(current)) {
                if (visited.contains(edge.index)) continue;
                visitedEntries.add(entries.get(edge.index));
                visited.add(edge.index);
                toTraverse.enqueue(edge.index);
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
