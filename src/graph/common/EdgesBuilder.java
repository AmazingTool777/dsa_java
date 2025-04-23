package graph.common;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Builder of a graph's edges
 *
 * @param <TKey> Data type of vertex key.
 */
public class EdgesBuilder<TKey> {
    /**
     * Map of edges by source.
     * A key of the map is a source vertex's key.
     * The value of the map is a linked list of the edges that originate from the source vertex represented by its key.
     */
    private final HashMap<TKey, LinkedList<EdgeToVertex<TKey>>> edgesBySource;

    /**
     * Getter of the map of edges by source `edgesBySource`.
     *
     * @return the map of edges by source.
     */
    public HashMap<TKey, LinkedList<EdgeToVertex<TKey>>> getEdgesBySource() {
        return edgesBySource;
    }

    public EdgesBuilder() {
        edgesBySource = new HashMap<>();
    }

    /**
     * Adds an edge from a source vertex to a destination vertex without setting the weight.
     *
     * @param source      The key of the source vertex.
     * @param destination The key of the destination vertex.
     * @return Reference to the builder.
     */
    public EdgesBuilder<TKey> addEdgeFromSource(TKey source, TKey destination) {
        LinkedList<EdgeToVertex<TKey>> edgesFromSource = edgesBySource.computeIfAbsent(source, k -> new LinkedList<>());
        edgesFromSource.add(new EdgeToVertex<>(destination));
        return this;
    }

    /**
     * Adds an edge from a source vertex to a destination vertex without setting the weight.
     *
     * @param source      The key of the source vertex.
     * @param destination The key of the destination vertex.
     * @param weight      The weight of the edge.
     * @return Reference to the builder.
     */
    public EdgesBuilder<TKey> addEdgeFromSource(TKey source, TKey destination, double weight) {
        LinkedList<EdgeToVertex<TKey>> edgesFromSource = edgesBySource.computeIfAbsent(source, k -> new LinkedList<>());
        edgesFromSource.add(new EdgeToVertex<>(destination, weight));
        return this;
    }
}
