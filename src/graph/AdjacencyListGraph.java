package graph;

import queue.LinkedListQueue;
import stack.LinkedListStack;

import java.util.*;

/**
 * Graph implementation using an adjacency list.
 *
 * @param <TKey>  The type of the key that identifies a node.
 * @param <TNode> The type of the nodes.
 */
public class AdjacencyListGraph<TKey, TNode> {
    /**
     * Item in the linked list of adjacent nodes of a source node
     *
     * @param key     The key that identifies the adjacent node
     * @param node    The actual node value
     * @param weight  The weight of the edge (Optional)
     * @param <TKey>  The type of the key
     * @param <TNode> The type of the node
     */
    public record AdjacencyListItem<TKey, TNode>(TKey key, TNode node, double weight) {
        public AdjacencyListItem(TKey key, TNode node) {
            this(key, node, 0);
        }
    }

    /**
     * The actual adjacency list represented as key-value pairs of node keys and linked lists of adjacent nodes.
     */
    private final TreeMap<TKey, LinkedList<AdjacencyListItem<TKey, TNode>>> lists;

    public AdjacencyListGraph(TreeMap<TKey, LinkedList<AdjacencyListItem<TKey, TNode>>> lists) {
        this.lists = lists;
    }

    /**
     * Contract for a DFS implementation strategy
     */
    public interface DFSStrategy<TKey, TNode> {
        LinkedList<TKey> run(
                TreeMap<TKey, LinkedList<AdjacencyListItem<TKey, TNode>>> lists,
                TKey source,
                Set<TKey> visited,
                LinkedList<TKey> nodes
        );
    }

    /**
     * Recursive implementation of the DFS traversal
     */
    public static class RecursiveDFS<TKey, TNode> implements DFSStrategy<TKey, TNode> {
        @Override
        public LinkedList<TKey> run(
                TreeMap<TKey, LinkedList<AdjacencyListItem<TKey, TNode>>> lists,
                TKey source,
                Set<TKey> visited,
                LinkedList<TKey> nodes
        ) {
            visited.add(source);
            nodes.add(source);

            LinkedList<AdjacencyListItem<TKey, TNode>> adjNodes = lists.get(source);
            if (adjNodes != null) {
                for (AdjacencyListItem<TKey, TNode> item : adjNodes) {
                    if (visited.contains(item.key)) continue;
                    run(lists, item.key, visited, nodes);
                }
            }

            return nodes;
        }
    }

    /**
     * Iterative DFS traversal
     */
    public static class IterativeDFS<TKey, TNode> implements DFSStrategy<TKey, TNode> {
        @Override
        public LinkedList<TKey> run(
                TreeMap<TKey, LinkedList<AdjacencyListItem<TKey, TNode>>> lists,
                TKey source,
                Set<TKey> visited,
                LinkedList<TKey> nodes
        ) {
            LinkedListStack<TKey> toTraverse = new LinkedListStack<>();

            toTraverse.push(source);

            while (!toTraverse.isEmpty()) {
                TKey current = toTraverse.pop();

                if (!visited.contains(current)) {
                    visited.add(current);
                    nodes.add(current);
                }

                LinkedList<AdjacencyListItem<TKey, TNode>> adjNodes = lists.get(current);
                if (adjNodes != null) {
                    ListIterator<AdjacencyListItem<TKey, TNode>> itemsIterator = adjNodes.listIterator(adjNodes.size());
                    while (itemsIterator.hasPrevious()) {
                        AdjacencyListItem<TKey, TNode> item = itemsIterator.previous();
                        if (visited.contains(item.key)) continue;
                        toTraverse.push(item.key);
                    }
                }
            }

            return nodes;
        }
    }

    public LinkedList<TKey> DFS(TKey source, DFSStrategy<TKey, TNode> dfsStrategy) {
        return dfsStrategy.run(lists, source, new TreeSet<>(), new LinkedList<>());
    }

    public LinkedList<TKey> BFS(TKey source) {
        LinkedList<TKey> nodes = new LinkedList<>();
        Set<TKey> visited = new TreeSet<>();
        LinkedListQueue<TKey> toTraverse = new LinkedListQueue<>();

        visited.add(source);
        nodes.add(source);
        toTraverse.enqueue(source);

        while (!toTraverse.isEmpty()) {
            TKey current = toTraverse.dequeue();
            LinkedList<AdjacencyListItem<TKey, TNode>> adjNodes = lists.get(current);
            if (adjNodes != null) {
                for (AdjacencyListItem<TKey, TNode> item : adjNodes) {
                    if (visited.contains(item.key)) continue;
                    visited.add(item.key);
                    nodes.add(item.key);
                    toTraverse.enqueue(item.key);
                }
            }
        }

        return nodes;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        int n = lists.size() - 1, i = 0;
        for (Map.Entry<TKey, LinkedList<AdjacencyListItem<TKey, TNode>>> entry : lists.entrySet()) {
            output.append(entry.getKey()).append(" : ");
            int count = entry.getValue().size() - 1, j = 0;
            for (AdjacencyListItem<TKey, TNode> item : entry.getValue()) {
                output.append(item.key);
                if (item.weight != 0) output.append("(%.2f)".formatted(item.weight));
                output.append(j < count ? " -> " : i < n ? "\n" : "");
                j++;
            }
            i++;
        }
        return output.toString();
    }
}
