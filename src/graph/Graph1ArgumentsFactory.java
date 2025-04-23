package graph;

import graph.common.EdgesBuilder;
import graph.common.GraphArguments;
import graph.common.GraphArgumentsFactory;
import graph.common.VertexEntry;

import java.util.ArrayList;

/**
 * Factory for the Graph 1's arguments.
 * Graph 1 path: `src/graph/assets/graph1.jpg`
 */
public class Graph1ArgumentsFactory implements GraphArgumentsFactory<Integer, Integer> {
    @Override
    public GraphArguments<Integer, Integer> create() {
        ArrayList<VertexEntry<Integer, Integer>> entries = new ArrayList<>();
        entries.add(new VertexEntry<>(0, 0));
        entries.add(new VertexEntry<>(1, 1));
        entries.add(new VertexEntry<>(2, 2));
        entries.add(new VertexEntry<>(3, 3));
        entries.add(new VertexEntry<>(4, 4));
        entries.add(new VertexEntry<>(5, 5));
        entries.add(new VertexEntry<>(6, 6));
        entries.add(new VertexEntry<>(7, 7));
        entries.add(new VertexEntry<>(8, 8));

        EdgesBuilder<Integer> edgesBuilder = new EdgesBuilder<>();
        edgesBuilder.addEdgeFromSource(0, 1, 4)
                .addEdgeFromSource(1, 0, 4)
                .addEdgeFromSource(0, 7, 8)
                .addEdgeFromSource(7, 0, 8)
                .addEdgeFromSource(1, 2, 8)
                .addEdgeFromSource(2, 1, 8)
                .addEdgeFromSource(1, 7, 11)
                .addEdgeFromSource(7, 1, 11)
                .addEdgeFromSource(2, 3, 7)
                .addEdgeFromSource(3, 2, 7)
                .addEdgeFromSource(2, 5, 4)
                .addEdgeFromSource(5, 2, 4)
                .addEdgeFromSource(2, 8, 2)
                .addEdgeFromSource(8, 2, 2)
                .addEdgeFromSource(3, 4, 9)
                .addEdgeFromSource(4, 3, 9)
                .addEdgeFromSource(3, 5, 14)
                .addEdgeFromSource(5, 3, 14)
                .addEdgeFromSource(4, 5, 10)
                .addEdgeFromSource(5, 4, 10)
                .addEdgeFromSource(5, 6, 2)
                .addEdgeFromSource(6, 5, 2)
                .addEdgeFromSource(6, 7, 1)
                .addEdgeFromSource(7, 6, 1)
                .addEdgeFromSource(6, 8, 6)
                .addEdgeFromSource(8, 6, 6)
                .addEdgeFromSource(7, 8, 7)
                .addEdgeFromSource(8, 7, 7);

        return new GraphArguments<>(entries, edgesBuilder);
    }
}
