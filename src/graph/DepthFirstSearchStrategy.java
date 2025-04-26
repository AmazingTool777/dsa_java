package graph;

import graph.common.Graph;
import graph.common.GraphTraversalStrategy;
import graph.common.VertexEntry;
import stack.LinkedListStack;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Depth-First Search a.k.a. DFS implementation of a graph traversal strategy.
 *
 * @param <TKey>    Data type of vertex key
 * @param <TVertex> Data type of vertex data
 */
public class DepthFirstSearchStrategy<TKey, TVertex> implements GraphTraversalStrategy<TKey, TVertex> {
    /**
     * Whether to use an iterative implementation of DFS traversal or not.
     * - True: Iterative
     * - False: Recursive
     */
    private final boolean isIterative;

    public DepthFirstSearchStrategy(boolean isIterative) {
        this.isIterative = isIterative;
    }

    /**
     * Recursive implementation of the DFS graph traversal.
     *
     * @param graph                 The graph to be traversed
     * @param source                The index of the source vertex entry
     * @param visitedEntriesIndices Array of booleans that tells whether a vertex entry has already been visited or not
     * @param visitedEntries        Linked list of the already visited vertex entries ordered by order of traversal
     * @return The linked list of all the visited entries ordered by order of traversal
     */
    private LinkedList<VertexEntry<TKey, TVertex>> recursiveDFS(
            Graph<TKey, TVertex> graph,
            int source,
            boolean[] visitedEntriesIndices,
            LinkedList<VertexEntry<TKey, TVertex>> visitedEntries) {
        ArrayList<VertexEntry<TKey, TVertex>> entries = graph.getEntries();

        // Marking the source vertex as visited
        visitedEntriesIndices[source] = true;
        visitedEntries.add(entries.get(source));

        LinkedList<Integer> successors = graph.getSuccessorsOfVertexAsIndices(source);
        for (int successor : successors) {
            if (visitedEntriesIndices[successor]) {
                continue;
            }
            // Doing DFS starting from the successor
            recursiveDFS(graph, successor, visitedEntriesIndices, visitedEntries);
        }

        return visitedEntries;
    }

    /**
     * Recursive implementation of the DFS graph traversal.
     *
     * @param graph  The graph to traverse
     * @param source The index of the source vertex
     * @return List of the visited vertices by order of traversal
     */
    private LinkedList<VertexEntry<TKey, TVertex>> iterativeDFS(Graph<TKey, TVertex> graph, int source) {
        ArrayList<VertexEntry<TKey, TVertex>> entries = graph.getEntries();

        // Linked list of the visited vertex entries ordered by order of traversal
        LinkedList<VertexEntry<TKey, TVertex>> visitedEntries = new LinkedList<>();
        // Array of boolean telling which entries at the indices have already been visited or not
        boolean[] visitedEntriesIndices = new boolean[graph.getOrder()];

        // Stack for the next vertices to traverse
        LinkedListStack<Integer> toTraverse = new LinkedListStack<>();
        // Initialization of the stack with the source
        toTraverse.push(source);

        while (!toTraverse.isEmpty()) {
            // Popping the next vertex to traverse
            int current = toTraverse.pop();

            // Skip if the current vertex has already been visited
            if (visitedEntriesIndices[current]) {
                continue;
            }

            // Mark the current vertex as visited
            visitedEntriesIndices[current] = true;
            visitedEntries.add(entries.get(current));

            LinkedList<Integer> successors = graph.getSuccessorsOfVertexAsIndices(current);
            for (int successor : successors) {
                if (visitedEntriesIndices[successor]) {
                    continue;
                }
                // Pushing the successor to the stack for traversal
                toTraverse.push(successor);
            }
        }

        return visitedEntries;
    }

    @Override
    public LinkedList<VertexEntry<TKey, TVertex>> traverse(Graph<TKey, TVertex> graph, int source) {
        if (isIterative) {
            return iterativeDFS(graph, source);
        } else {
            LinkedList<VertexEntry<TKey, TVertex>> visitedVertexEntries = new LinkedList<>();
            boolean[] visitedEntriesIndices = new boolean[graph.getOrder()];
            return recursiveDFS(graph, source, visitedEntriesIndices, visitedVertexEntries);
        }
    }
}
