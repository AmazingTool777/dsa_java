package graph.common;

import java.util.LinkedList;

/**
 * Graph traversal strategy.
 *
 * @param <TKey>    Data type of vertex keys
 * @param <TVertex> Data type of vertex data
 */
public interface GraphTraversalStrategy<TKey, TVertex> {
    /**
     * The traversal method on a graph from a given source
     *
     * @param graph  The graph to be traversed
     * @param source The index of the source vertex entry
     * @return List of traversed vertex entries ordered by the order of traversal
     */
    LinkedList<VertexEntry<TKey, TVertex>> traverse(Graph<TKey, TVertex> graph, int source);
}
