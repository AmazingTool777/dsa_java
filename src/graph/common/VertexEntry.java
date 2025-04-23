package graph.common;

/**
 * Individual vertex that is inserted to the graph's vertices entries.
 *
 * @param key       The key that identifies the vertex
 * @param vertex    The actual vertex data
 * @param <TKey>    Data type of the key
 * @param <TVertex> Data type of the vertex
 */
public record VertexEntry<TKey, TVertex>(TKey key, TVertex vertex) {
}
