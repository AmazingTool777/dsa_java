package graph;

import graph.common.Graph;
import graph.common.ShortestPathStrategy;
import priorityqueue.BinaryHeapListPriorityQueue;
import utils.orderingstrategy.MinOrdering;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Moore Dijkstra implementation of the shortest paths finder strategy.
 *
 * @param <TKey>    Data type of vertex key
 * @param <TVertex> Data type of vertex data
 */
public class MooreDijkstraStrategy<TKey, TVertex> extends ShortestPathStrategy<TKey, TVertex> {
    @Override
    protected double[] calculateShortestPathsCompoundWeights(Graph<TKey, TVertex> graph, int source) {
        // Array of the shortest path progress to each vertex from the source.
        // Each index maps to each index of the vertex entries.
        PathToVertexProgress[] pathsToVerticesProgress = new PathToVertexProgress[graph.getOrder()];

        // Initialization of the paths progress
        for (int i = 0; i < graph.getOrder(); i++) {
            double initialCompoundWeight = i == source ? 0 : Double.POSITIVE_INFINITY;
            pathsToVerticesProgress[i] = new PathToVertexProgress(i, initialCompoundWeight);
        }

        // Initialization of a priority queue of the paths progress.
        // The path progress with the minimum compound weight is explored next.
        BinaryHeapListPriorityQueue<PathToVertexProgress> toExplore = new BinaryHeapListPriorityQueue<>(
                new ArrayList<>(List.of(pathsToVerticesProgress)),
                new MinOrdering<>()
        );

        // Booleans telling which vertices have already been visited or not.
        // Each index maps to each index of the vertex entries.
        boolean[] visited = new boolean[graph.getOrder()];

        while (!toExplore.isEmpty()) {
            PathToVertexProgress currentPath = toExplore.dequeue();

            // Mark the vertex of the current path progress as visited
            visited[currentPath.entryIndex()] = true;

            LinkedList<Integer> successors = graph.getSuccessorsOfVertexAsIndices(currentPath.entryIndex());
            for (int successor : successors) {
                if (visited[successor]) {
                    continue;
                }
                double compoundWeightFromCurrentPathToSuccessor = currentPath.compoundWeight()
                        + graph.getEdgeWeight(currentPath.entryIndex(), successor);
                // Relaxation of the successor's progress' compound weight
                if (compoundWeightFromCurrentPathToSuccessor < pathsToVerticesProgress[successor].compoundWeight()) {
                    // Update of the priority of the successor's progress
                    toExplore.changePriority(pathsToVerticesProgress[successor], (path) -> {
                        PathToVertexProgress updatedPath = new PathToVertexProgress(
                                path.entryIndex(),
                                compoundWeightFromCurrentPathToSuccessor
                        );
                        pathsToVerticesProgress[path.entryIndex()] = updatedPath;
                        return updatedPath;
                    });
                }
            }
        }

        // Mapping the shortest path's progress to the expected return type of the method `double[]`.
        // Array of the compound weights of the shortest paths to all vertices.
        // Each index maps to each index of the vertex entries.
        double[] shortestPathsCompoundWeights = new double[graph.getOrder()];
        for (int i = 0; i < graph.getOrder(); i++) {
            shortestPathsCompoundWeights[i] = pathsToVerticesProgress[i].compoundWeight();
        }

        return shortestPathsCompoundWeights;
    }
}
