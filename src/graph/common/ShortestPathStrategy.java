package graph.common;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Abstract strategy for shortest paths' finder.
 *
 * @param <TKey>    Data type of vertex keys
 * @param <TVertex> Data type of vertex data
 */
public abstract class ShortestPathStrategy<TKey, TVertex> {
    /**
     * Record for storing the shortest path progress data to a vertex.
     * Implements the `Comparable<T>` interface because it needs to be compared when it is stored in a priority queue.
     *
     * @param entryIndex     Index of the vertex entry
     * @param compoundWeight Compound weight of the shortest path to the vertex
     */
    public record PathToVertexProgress(int entryIndex,
                                       double compoundWeight) implements Comparable<PathToVertexProgress> {
        @Override
        public int compareTo(PathToVertexProgress o) {
            return Double.compare(this.compoundWeight, o.compoundWeight());
        }
    }

    /**
     * Calculates the compound weights of the shortest paths to all vertices from a source.
     *
     * @param graph  The graph to operate upon
     * @param source The index of the source vertex
     * @return Array of the compound weights of the shortest paths to the vertices.
     */
    protected abstract double[] calculateShortestPathsCompoundWeights(Graph<TKey, TVertex> graph, int source);

    /**
     * Backtracks the sequence of vertices that led to the shortest path from a source to a destination.
     *
     * @param graph                        The graph to operate upon
     * @param source                       The index of the source vertex entry
     * @param destination                  The index of the destination vertex entry
     * @param shortestPathsCompoundWeights The array of compounds weights of the shortest paths
     * @return List of the sequence vertex entries that make the shortest path starting from the source to the destination
     */
    private LinkedList<VertexEntry<TKey, TVertex>> backtrackShortestPathVertices(
            Graph<TKey, TVertex> graph,
            int source,
            int destination,
            double[] shortestPathsCompoundWeights) {
        LinkedList<VertexEntry<TKey, TVertex>> vertices = new LinkedList<>();

        double compoundWeight = shortestPathsCompoundWeights[destination];

        // There is no sequence of vertices to build
        // the shortest path when the destination vertex is either unreachable or is trapped inside a negative cycle
        if (compoundWeight == Double.POSITIVE_INFINITY || compoundWeight == Double.NEGATIVE_INFINITY) {
            return vertices;
        }

        int current = destination;
        while (current != source) {
            vertices.addFirst(graph.entries.get(current));

            double minCompoundWeightToCurrent = Double.POSITIVE_INFINITY;
            int shortestPathPredecessor = -1;
            LinkedList<Integer> predecessors = graph.getPredecessorsOfVertexAsIndices(current);
            for (int predecessor : predecessors) {
                double compoundWeightFromPredecessor = shortestPathsCompoundWeights[predecessor]
                        + graph.getEdgeWeight(predecessor, current);
                if (compoundWeightFromPredecessor < minCompoundWeightToCurrent) {
                    minCompoundWeightToCurrent = compoundWeightFromPredecessor;
                    shortestPathPredecessor = predecessor;
                }
            }
            current = shortestPathPredecessor;
        }

        vertices.addFirst(graph.getEntries().get(source));

        return vertices;
    }

    /**
     * Public method for finding the shortest paths from a source vertex.
     * It combines the results from the compounds weights and the backtracking method to build the shortest paths results.
     *
     * @param graph  The graph to operate upon.
     * @param source The index of the source vertex.
     * @return Map of the shortest paths results by destination vertex key.
     */
    public HashMap<TKey, ShortestPathResult<TKey, TVertex>> findShortestPaths(Graph<TKey, TVertex> graph, int source) {
        double[] shortestPathsCompoundWeights = calculateShortestPathsCompoundWeights(graph, source);

        HashMap<TKey, ShortestPathResult<TKey, TVertex>> shortestPathResultsByDestination = new HashMap<>();
        for (int destination = 0; destination < graph.getOrder(); destination++) {
            LinkedList<VertexEntry<TKey, TVertex>> shortestPathVertices = backtrackShortestPathVertices(
                    graph,
                    source,
                    destination,
                    shortestPathsCompoundWeights
            );
            TKey destinationKey = graph.getEntries().get(destination).key();
            ShortestPathResult<TKey, TVertex> shortestPathResult = new ShortestPathResult<>(
                    destinationKey,
                    shortestPathsCompoundWeights[destination],
                    shortestPathVertices
            );
            shortestPathResultsByDestination.put(destinationKey, shortestPathResult);
        }

        return shortestPathResultsByDestination;
    }
}
