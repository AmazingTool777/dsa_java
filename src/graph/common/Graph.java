package graph.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Base graph for any specific graph to inherit from.
 *
 * @param <TKey>    Data type of vertex key.
 * @param <TVertex> Data type a vertex data.
 */
public abstract class Graph<TKey, TVertex> {
    /**
     * The vertices entries of the graph.
     */
    protected ArrayList<VertexEntry<TKey, TVertex>> entries;

    /**
     * Getter of the vertices entries of the graph.
     *
     * @return The vertices entries of the graph.
     */
    public ArrayList<VertexEntry<TKey, TVertex>> getEntries() {
        return entries;
    }

    /**
     * The count of vertices in the graph.
     */
    protected int order;

    /**
     * Map of the vertex entries' keys to their indices in the list of the vertices entries `entries`.
     */
    protected HashMap<TKey, Integer> entriesKeyToIndexMap;

    public Graph(ArrayList<VertexEntry<TKey, TVertex>> entries) {
        this.entries = entries;
        order = entries.size();
        // Mapping of the entries keys to the entries indices
        entriesKeyToIndexMap = new HashMap<>();
        for (int i = 0; i < order; i++) {
            entriesKeyToIndexMap.put(entries.get(i).key(), i);
        }
    }

    /**
     * Gets the successors of a source vertex as a list of the indices of the vertices of entries of the successors.
     *
     * @param source The index of the source vertex.
     * @return List of the indices of the vertices entries of the successors.
     */
    public abstract LinkedList<Integer> getSuccessorsOfVertexAsIndices(int source);

    /**
     * Gets the predecessors of a destination vertex as a list of the indices of the vertices of entries of the predecessors.
     *
     * @param destination The index of the destination vertex.
     * @return List of the indices of the vertices entries of the predecessors.
     */
    public abstract LinkedList<Integer> getPredecessorsOfVertexAsIndices(int destination);

    /**
     * Gets the weight of an edge starting from a source vertex to destination vertex.
     *
     * @param source      The index of the source vertex
     * @param destination The index of the destination vertex.
     * @return The weight of the edge.
     */
    public abstract double getEdgeWeight(int source, int destination);
}
