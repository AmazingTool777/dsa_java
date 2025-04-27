package graph.common;

import java.util.LinkedList;

/**
 * Record for storing the shortest path result to a destination vertex.
 *
 * @param destinationKey The key of the destination vertex
 * @param compoundWeight The compound weight of the shortest path
 * @param entries        List of the sequence of vertices that constitute the shortest path from the origin vertex
 * @param <TKey>         Data type of the vertex key
 * @param <TVertex>      Data type of the vertex data
 */
public record ShortestPathResult<TKey, TVertex>(TKey destinationKey,
                                                double compoundWeight,
                                                LinkedList<VertexEntry<TKey, TVertex>> entries) {
}
