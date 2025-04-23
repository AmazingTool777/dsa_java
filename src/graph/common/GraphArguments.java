package graph.common;

import java.util.ArrayList;

/**
 * Record for storing the arguments to pass to a graph constructor.
 *
 * @param entries      The vertices entries
 * @param edgesBuilder The edges builder
 * @param <TKey>       Data type of vertex key
 * @param <TVertex>    Data type of vertex data
 */
public record GraphArguments<TKey, TVertex>(ArrayList<VertexEntry<TKey, TVertex>> entries,
                                            EdgesBuilder<TKey> edgesBuilder) {
}
