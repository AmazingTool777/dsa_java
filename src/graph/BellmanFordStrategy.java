package graph;

import graph.common.EdgesIterator;
import graph.common.EdgesIteratorItem;
import graph.common.Graph;
import graph.common.ShortestPathStrategy;

/**
 * Bellman-Ford implementation of the shortest paths finder strategy.
 *
 * @param <TKey>    Data type of vertex keys
 * @param <TVertex> Data type of vertex data
 */
public class BellmanFordStrategy<TKey, TVertex> extends ShortestPathStrategy<TKey, TVertex> {
    @Override
    protected double[] calculateShortestPathsCompoundWeights(Graph<TKey, TVertex> graph, int source) {
        int order = graph.getOrder();
        // Array of compound weights to the vertices. Each index maps to the indices of the vertex entries.
        double[] shortestPathsCompoundWeights = new double[order];

        // Initialization of the shortest paths compound weights
        for (int i = 0; i < order; i++) {
            double initialCompoundWeight = i == source ? 0 : Double.POSITIVE_INFINITY;
            shortestPathsCompoundWeights[i] = initialCompoundWeight;
        }

        // Relaxation of the shortest paths compound weights `order - 1` times
        for (int i = 0; i < order - 1; i++) {
            // Relaxation of the shortest paths compound weights by iterating over each edge
            EdgesIterator edgesIterator = graph.createEdgesIterator();
            while (edgesIterator.hasNext()) {
                EdgesIteratorItem edge = edgesIterator.next();
                double compoundWeightToDestination = shortestPathsCompoundWeights[edge.source()] + edge.weight();
                if (compoundWeightToDestination < shortestPathsCompoundWeights[edge.destination()]) {
                    shortestPathsCompoundWeights[edge.destination()] = compoundWeightToDestination;
                }
            }
        }

        // Last relaxation of the shortest paths compound weights to check if any potential negative cycle exists.
        EdgesIterator edgesIterator = graph.createEdgesIterator();
        while (edgesIterator.hasNext()) {
            EdgesIteratorItem edge = edgesIterator.next();
            double compoundWeightToDestination = shortestPathsCompoundWeights[edge.source()] + edge.weight();
            if (compoundWeightToDestination < shortestPathsCompoundWeights[edge.destination()]) {
                // Negative cycle exists. Set the compound weight to the destination to negative infinity.
                shortestPathsCompoundWeights[edge.destination()] = Double.NEGATIVE_INFINITY;
            }
        }

        return shortestPathsCompoundWeights;
    }
}
