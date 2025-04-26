package graph;

import graph.common.Graph;
import graph.common.GraphTraversalStrategy;
import graph.common.VertexEntry;
import queue.LinkedListQueue;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Breadth-First Search a.k.a. BFS implementation of a graph traversal strategy.
 *
 * @param <TKey>    Data type of vertex keys
 * @param <TVertex> Data type of vertex data
 */
public class BreadthFirstSearchStrategy<TKey, TVertex> implements GraphTraversalStrategy<TKey, TVertex> {
    @Override
    public LinkedList<VertexEntry<TKey, TVertex>> traverse(Graph<TKey, TVertex> graph, int source) {
        ArrayList<VertexEntry<TKey, TVertex>> entries = graph.getEntries();

        // Linked list of traversed entries by order of traversal
        LinkedList<VertexEntry<TKey, TVertex>> visitedEntries = new LinkedList<>();
        // Array of booleans that tell which vertex entries have already been traversed or not.
        // The indices map to the indices of the vertex entries.
        boolean[] visitedEntriesIndices = new boolean[graph.getOrder()];

        // Setting the source as already traversed
        visitedEntries.add(entries.get(source));
        visitedEntriesIndices[source] = true;

        // Queue of the vertices' indices to traverse
        LinkedListQueue<Integer> toTraverse = new LinkedListQueue<>();
        // Initializing the queue with the source index
        toTraverse.enqueue(source);

        while (!toTraverse.isEmpty()) {
            // Extracting the next vertex to traverse from the queue
            int current = toTraverse.dequeue();

            LinkedList<Integer> successors = graph.getSuccessorsOfVertexAsIndices(current);
            for (Integer successor : successors) {
                if (visitedEntriesIndices[successor]) {
                    continue;
                }
                // Marking the successor as visited
                visitedEntriesIndices[successor] = true;
                visitedEntries.add(entries.get(successor));
                // Putting the successor to queue for traversal
                toTraverse.enqueue(successor);
            }
        }

        return visitedEntries;
    }
}
