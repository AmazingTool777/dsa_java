package graph.common;

/**
 * Record for storing the edge data from an arbitrary source to a destination vertex.
 *
 * @param destinationVertex The key of the destination vertex
 * @param weight            The weight of the edge
 * @param <TKey>            Data type of vertex key
 */
public record EdgeToVertex<TKey>(TKey destinationVertex, double weight) {
    EdgeToVertex(TKey destinationVertex) {
        this(destinationVertex, 0);
    }
}
