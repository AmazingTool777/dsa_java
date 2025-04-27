package graph;

import graph.common.EdgeToVertex;
import graph.common.EdgesBuilder;
import graph.common.Graph;
import graph.common.VertexEntry;

import java.util.*;

/**
 * Adjacency matrix implementation of a graph
 *
 * @param <TKey>    Data type of vertex key
 * @param <TVertex> Data type of vertex data
 */
public class AdjacencyMatrixGraph<TKey, TVertex> extends Graph<TKey, TVertex> {
    /**
     * The adjacency matrix of the graph.
     * The indices of the matrix map to the indices of the vertices entries.
     */
    private final double[][] matrix;

    public AdjacencyMatrixGraph(ArrayList<VertexEntry<TKey, TVertex>> entries, EdgesBuilder<TKey> edgesBuilder) {
        super(entries);

        // Building the adjacency matrix
        matrix = new double[order][order];
        for (Map.Entry<TKey, LinkedList<EdgeToVertex<TKey>>> edgesFromSourceEntry : edgesBuilder.getEdgesBySource().entrySet()) {
            TKey sourceKey = edgesFromSourceEntry.getKey();
            int source = entriesKeyToIndexMap.get(sourceKey);
            LinkedList<EdgeToVertex<TKey>> edgesFromSource = edgesFromSourceEntry.getValue();
            for (EdgeToVertex<TKey> edge : edgesFromSource) {
                int destination = entriesKeyToIndexMap.get(edge.destinationVertex());
                matrix[source][destination] = edge.weight();
            }
        }
    }

    @Override
    public LinkedList<Integer> getSuccessorsOfVertexAsIndices(int source) {
        LinkedList<Integer> successors = new LinkedList<>();
        for (int i = 0; i < order; i++) {
            if (matrix[source][i] != 0) {
                successors.add(i);
            }
        }
        return successors;
    }

    @Override
    public LinkedList<Integer> getPredecessorsOfVertexAsIndices(int destination) {
        LinkedList<Integer> predecessors = new LinkedList<>();
        for (int i = 0; i < order; i++) {
            if (matrix[i][destination] != 0) {
                predecessors.add(i);
            }
        }
        return predecessors;
    }

    @Override
    public double getEdgeWeight(int source, int destination) {
        return matrix[source][destination];
    }

    /**
     * The space occupied by a weight inside the `toString()` method
     */
    private int weightSpace = 10;

    /**
     * Setter of `weightSpace`
     *
     * @param weightSpace The weight space
     * @return Reference to the graph
     */
    public AdjacencyMatrixGraph<TKey, TVertex> setWeightSpace(int weightSpace) {
        this.weightSpace = weightSpace;
        return this;
    }

    /**
     * The precision of the floating point of the weights inside the `toString()` method
     */
    private int weightPrecision = 0;

    /**
     * Setter of `weightPrecision`
     *
     * @param weightPrecision The weight precision
     * @return Reference to the graph
     */
    public AdjacencyMatrixGraph<TKey, TVertex> setWeightPrecision(int weightPrecision) {
        this.weightPrecision = weightPrecision;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("    ");
        // Column indices header
        for (int i = 0; i < order; i++) {
            builder.append(String.format(" %" + weightSpace + "d", i));
        }
        builder.append("\n");
        // Columns header divider
        int colHeaderDividerLength = 4 + (weightSpace + 1) * order;
        builder.append("-".repeat(Math.max(0, colHeaderDividerLength)));
        builder.append("\n");
        // Rows
        for (int i = 0; i < order; i++) {
            if (i > 0) builder.append("\n");
            builder.append(String.format("%2d |", i)); // Row index
            for (int j = 0; j < order; j++) {
                builder.append(String.format(" %" + weightSpace + "." + weightPrecision + "f", matrix[i][j]));
            }
        }

        return builder.toString();
    }
}
