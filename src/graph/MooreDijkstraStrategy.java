package graph;

import graph.common.Graph;
import graph.common.ShortestPathStrategy;
import priorityqueue.BinaryHeapListPriorityQueue;
import utils.orderingstrategy.MinOrdering;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MooreDijkstraStrategy<TKey, TVertex> extends ShortestPathStrategy<TKey, TVertex> {
    @Override
    protected double[] calculateShortestPathsCompoundWeights(Graph<TKey, TVertex> graph, int source) {
        PathToVertexProgress[] pathsToVerticesProgress = new PathToVertexProgress[graph.getOrder()];

        for (int i = 0; i < graph.getOrder(); i++) {
            double initialCompoundWeight = i == source ? 0 : Double.POSITIVE_INFINITY;
            pathsToVerticesProgress[i] = new PathToVertexProgress(i, initialCompoundWeight);
        }

        BinaryHeapListPriorityQueue<PathToVertexProgress> toExplore = new BinaryHeapListPriorityQueue<>(
                new ArrayList<>(List.of(pathsToVerticesProgress)),
                new MinOrdering<>()
        );

        boolean[] visited = new boolean[graph.getOrder()];

        while (!toExplore.isEmpty()) {
            PathToVertexProgress currentPath = toExplore.dequeue();

            visited[currentPath.entryIndex()] = true;

            LinkedList<Integer> successors = graph.getSuccessorsOfVertexAsIndices(currentPath.entryIndex());
            for (int successor : successors) {
                if (visited[successor]) {
                    continue;
                }
                double compoundWeightFromCurrentPathToSuccessor = currentPath.compoundWeight()
                        + graph.getEdgeWeight(currentPath.entryIndex(), successor);
                if (compoundWeightFromCurrentPathToSuccessor < pathsToVerticesProgress[successor].compoundWeight()) {
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

        double[] shortestPathsCompoundWeights = new double[graph.getOrder()];
        for (int i = 0; i < graph.getOrder(); i++) {
            shortestPathsCompoundWeights[i] = pathsToVerticesProgress[i].compoundWeight();
        }

        return shortestPathsCompoundWeights;
    }
}
