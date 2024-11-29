package graph;

import priorityqueue.BinaryHeapListPriorityQueue;
import queue.LinkedListQueue;
import stack.LinkedListStack;
import utils.orderingstrategy.MinOrdering;

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
        public int vertexIndex;

        /**
         * The weight of the edge
         */
        public double weight;

        public ListEdge(int vertexIndex, double weight) {
            this.vertexIndex = vertexIndex;
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
            if (visited.contains(edge.vertexIndex)) continue;
            recursiveDFS(edge.vertexIndex, visited, visitedEntries);
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
                adj = adjEdgesIterator.previous().vertexIndex;
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
                adj = edge.vertexIndex;
                if (visited.contains(adj)) continue;
                visitedEntries.add(entries.get(adj));
                visited.add(adj);
                toTraverse.enqueue(adj);
            }
        }

        return visitedEntries;
    }

    /**
     * Gets the weight from a vertex to itself
     *
     * @param index The index of the vertex
     * @return The weight
     */
    private double getWeightToSelf(int index) {
        double weight = 0;
        for (ListEdge edge : lists.get(index)) {
            if (edge.vertexIndex == index) {
                weight = edge.weight;
                break;
            }
        }
        return weight;
    }

    /**
     * Holds the data of the shortest path to a destination vertex after the shortest path finding algorithm is done.
     *
     * @param key            The key of the destination vertex.
     * @param compoundWeight The sum of the weights of the edges along the shortest path.
     * @param entries        Linked list of the sequence of vertices that constitute the path.
     * @param <TKey>         The type of the vertex key
     * @param <TVertex>      The type of the vertex value
     */
    public record ShortestPathToVertex<TKey, TVertex>(
            TKey key,
            double compoundWeight,
            LinkedList<VertexEntry<TKey, TVertex>> entries
    ) {
    }

    /**
     * Intermediate class that we use to store the progress of the shortest path finding to a vertex.
     */
    private static class ShortestPathProgress implements Comparable<ShortestPathProgress> {
        /**
         * The current vertex's index
         */
        public int vertexIndex;

        /**
         * The index of the previous vertex that led to the current path progress
         */
        public int prevVertexIndex;

        /**
         * The sum of the weights of the edges that make up the path so far.
         */
        public double compoundWeight;

        public ShortestPathProgress(int vertexIndex, int prevVertexIndex, double compoundWeight) {
            this.vertexIndex = vertexIndex;
            this.prevVertexIndex = prevVertexIndex;
            this.compoundWeight = compoundWeight;
        }

        @Override
        public int compareTo(ShortestPathProgress other) {
            return Double.compare(this.compoundWeight, other.compoundWeight);
        }
    }

    /**
     * Backtracks the sequence of vertices that make up the shortest path from a source vertex to a destination vertex.
     *
     * @param sourceIndex The index of the source vertex
     * @param finalPath   The final path to the destination vertex
     * @param paths       Array of the shortest paths to all vertices from the source vertex
     * @return Linked list of the sequence of vertices
     */
    private LinkedList<VertexEntry<TKey, TVertex>> backtrackShortestPathEntries(
            int sourceIndex,
            ShortestPathProgress finalPath,
            ShortestPathProgress[] paths
    ) {
        LinkedList<VertexEntry<TKey, TVertex>> entries = new LinkedList<>();

        ShortestPathProgress currentPath = finalPath;
        while (currentPath.vertexIndex != sourceIndex) {
            entries.addFirst(this.entries.get(currentPath.vertexIndex));
            currentPath = paths[currentPath.prevVertexIndex];
        }

        entries.addFirst(this.entries.get(sourceIndex));

        return entries;
    }

    /**
     * Shortest pathfinder using Moore Dijkstra's algorithm.
     *
     * @param sourceKey The key of the source vertex.
     * @return Map of all the destination vertices' keys and the corresponding shortest path data.
     */
    public TreeMap<TKey, ShortestPathToVertex<TKey, TVertex>> findDijkstraShortestPaths(TKey sourceKey) {
        int size = entries.size(), source = keysIndices.get(sourceKey);
        TreeMap<TKey, ShortestPathToVertex<TKey, TVertex>> verticesPaths = new TreeMap<>();

        // Array that checks whether a vertex at a given index has already been included or not
        boolean[] included = new boolean[size];
        included[source] = true;

        // Initialization of shortest paths' progress
        ShortestPathProgress[] paths = new ShortestPathProgress[size];
        for (int i = 0; i < size; i++) {
            boolean isSource = i == source;
            double compoundWeight = isSource ? getWeightToSelf(source) : Double.POSITIVE_INFINITY;
            int prevVertexIndex = isSource ? source : -1;
            paths[i] = new ShortestPathProgress(i, prevVertexIndex, compoundWeight);
        }

        // Priority queue of the shortest paths' progress ordered by the least compound weights of the paths
        BinaryHeapListPriorityQueue<ShortestPathProgress> toExplore = new BinaryHeapListPriorityQueue<>(
                new ArrayList<>(Arrays.asList(paths)),
                new MinOrdering<>()
        );

        while (!toExplore.isEmpty()) {
            ShortestPathProgress currentPath = toExplore.dequeue();
            included[currentPath.vertexIndex] = true;

            for (ListEdge edge : lists.get(currentPath.vertexIndex)) {
                if (included[edge.vertexIndex]) continue;
                ShortestPathProgress adjPath = paths[edge.vertexIndex];
                double compoundWeightFromCurrentPath = currentPath.compoundWeight + edge.weight;
                // Updating the compound weights of the non-included adjacent path
                // if the sum of the compound weights of the current path and the weight to the adjacent path
                // is less than the adjacent path's current compound weight.
                if (adjPath.compoundWeight > compoundWeightFromCurrentPath) {
                    adjPath.compoundWeight = compoundWeightFromCurrentPath;
                    adjPath.prevVertexIndex = currentPath.vertexIndex;
                    toExplore.changePriority(adjPath, true);
                }
            }
        }

        // Mapping the shortest paths' final progress to the actual shortest path data to each destination vertex
        for (ShortestPathProgress path : paths) {
            VertexEntry<TKey, TVertex> entry = entries.get(path.vertexIndex);
            LinkedList<VertexEntry<TKey, TVertex>> entries = backtrackShortestPathEntries(source, path, paths);
            verticesPaths.put(entry.key, new ShortestPathToVertex<>(entry.key, path.compoundWeight, entries));
        }

        return verticesPaths;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < size; i++) {
            if (i > 0) builder.append("\n");
            builder.append(entries.get(i).key).append(" : ");
            int edgesCount = lists.get(i).size(), j = 0;
            for (ListEdge edge : lists.get(i)) {
                builder.append(entries.get(edge.vertexIndex).key);
                if (edge.weight != 0) {
                    int weightPrecision = 0;
                    String format = "%." + weightPrecision + "f";
                    builder.append(" (").append(String.format(format, edge.weight)).append(")");
                }
                if (j < edgesCount - 1) builder.append(" -> ");
                j++;
            }
        }

        return builder.toString();
    }
}
