package graph.common;

import java.util.HashMap;
import java.util.LinkedList;

public abstract class ShortestPathStrategy<TKey, TVertex> {
    public record PathToVertexProgress(int entryIndex,
                                       double compoundWeight) implements Comparable<PathToVertexProgress> {
        @Override
        public int compareTo(PathToVertexProgress o) {
            return Double.compare(this.compoundWeight, o.compoundWeight());
        }
    }

    protected abstract double[] calculateShortestPathsCompoundWeights(Graph<TKey, TVertex> graph, int source);

    private LinkedList<VertexEntry<TKey, TVertex>> backtrackShortestPathVertices(
            Graph<TKey, TVertex> graph,
            int source,
            int destination,
            double[] shortestPathsCompoundWeights) {
        LinkedList<VertexEntry<TKey, TVertex>> vertices = new LinkedList<>();

        double compoundWeight = shortestPathsCompoundWeights[destination];

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
