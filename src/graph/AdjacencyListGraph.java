package graph;

import graph.common.EdgeToVertex;
import graph.common.EdgesBuilder;
import graph.common.Graph;
import graph.common.VertexEntry;

import java.util.*;

/**
 * Graph implementation using an adjacency list.
 *
 * @param <TKey>    Data type of vertex key.
 * @param <TVertex> Data type of the actual vertex data.
 */
public class AdjacencyListGraph<TKey, TVertex> extends Graph<TKey, TVertex> {
    /**
     * Local record for storing the edge data from an arbitrary source to a destination vertex.
     * The destination vertex is a destination vertex if the edge is stored in the successors adjacency list,
     * otherwise the destination vertex is a source vertex if it is stored in the predecessors adjacency list.
     *
     * @param destination Index of the destination vertex
     * @param weight      Weight of the edge
     */
    private record ListEdgeToVertex(int destination, double weight) {
    }

    /**
     * Successors adjacency list.
     * The indices of the array map to the indices of each vertex entries as source vertices.
     * Each item in the array is a linked list of edges data that originates from the source vertex represented by the index.
     */
    private final ArrayList<LinkedList<ListEdgeToVertex>> successorsLists;

    /**
     * Predecessors adjacency list.
     * The indices of the array map to the indices of each vertex entries as destination vertices.
     * Each item in the array is a linked list of edges data that ends with the destination vertex represented by the index.
     */
    private final ArrayList<LinkedList<ListEdgeToVertex>> predecessorsLists;

    public AdjacencyListGraph(ArrayList<VertexEntry<TKey, TVertex>> entries, EdgesBuilder<TKey> edgesBuilder) {
        super(entries);

        // Building the successors adjacency list and the predecessors adjacency list
        successorsLists = new ArrayList<>(Collections.nCopies(order, null));
        predecessorsLists = new ArrayList<>(Collections.nCopies(order, null));
        for (VertexEntry<TKey, TVertex> entry : entries) {
            int source = entriesKeyToIndexMap.get(entry.key());
            LinkedList<EdgeToVertex<TKey>> edgesFromSource = edgesBuilder.getEdgesBySource().get(entry.key());
            LinkedList<ListEdgeToVertex> successorsList = new LinkedList<>(), predecessorsList;
            if (edgesFromSource != null) {
                for (EdgeToVertex<TKey> edge : edgesFromSource) {
                    int destination = entriesKeyToIndexMap.get(edge.destinationVertex());
                    successorsList.add(new ListEdgeToVertex(destination, edge.weight()));
                    predecessorsList = predecessorsLists.get(destination);
                    if (predecessorsList == null) {
                        predecessorsList = new LinkedList<>();
                        predecessorsLists.set(destination, predecessorsList);
                    }
                    predecessorsList.add(new ListEdgeToVertex(source, edge.weight()));
                }
            }
            successorsLists.set(source, successorsList);
        }
    }

    @Override
    public LinkedList<Integer> getSuccessorsOfVertexAsIndices(int source) {
        LinkedList<Integer> successors = new LinkedList<>();
        for (ListEdgeToVertex edge : successorsLists.get(source)) {
            successors.add(edge.destination());
        }
        return successors;
    }

    @Override
    public LinkedList<Integer> getPredecessorsOfVertexAsIndices(int destination) {
        LinkedList<Integer> predecessors = new LinkedList<>();
        for (ListEdgeToVertex edge : predecessorsLists.get(destination)) {
            predecessors.add(edge.destination());
        }
        return predecessors;
    }

    @Override
    public double getEdgeWeight(int source, int destination) {
        double weight = 0;
        for (ListEdgeToVertex edge : successorsLists.get(source)) {
            if (edge.destination() == destination) {
                weight = edge.weight();
                break;
            }
        }
        return weight;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < order; i++) {
            if (i > 0) builder.append("\n");
            builder.append(entries.get(i).key()).append(" : ");
            LinkedList<ListEdgeToVertex> successorList = successorsLists.get(i);
            if (successorList == null) continue;
            int edgesCount = successorList.size(), j = 0;
            for (ListEdgeToVertex edge : successorList) {
                builder.append(entries.get(edge.destination()).key());
                if (edge.weight != 0) {
                    int weightPrecision = 0;
                    String format = "%." + weightPrecision + "f";
                    builder.append(" (").append(String.format(format, edge.weight)).append(")");
                }
                if (j < edgesCount - 1) builder.append(" -> ");
                j++;
            }
        }

        return builder.toString();
    }
}
