package graph;

import graph.common.EdgesBuilder;
import graph.common.GraphArguments;
import graph.common.GraphArgumentsFactory;
import graph.common.VertexEntry;

import java.util.ArrayList;

/**
 * Factory for Graph 2's arguments.
 * Graph 2 path: `src/graph/assets/graph2.PNG`
 */
public class Graph2ArgumentsFactory implements GraphArgumentsFactory<Integer, Integer> {
    @Override
    public GraphArguments<Integer, Integer> create() {
        ArrayList<VertexEntry<Integer, Integer>> entries = new ArrayList<>();
        entries.add(new VertexEntry<>(1, 1));
        entries.add(new VertexEntry<>(2, 2));
        entries.add(new VertexEntry<>(3, 3));
        entries.add(new VertexEntry<>(4, 4));
        entries.add(new VertexEntry<>(5, 5));
        entries.add(new VertexEntry<>(6, 6));
        entries.add(new VertexEntry<>(7, 7));
        entries.add(new VertexEntry<>(8, 8));

        EdgesBuilder<Integer> edgesBuilder = new EdgesBuilder<>();
        edgesBuilder.addEdgeFromSource(1, 2, 3)
                .addEdgeFromSource(1, 3, 3)
                .addEdgeFromSource(2, 5, 1)
                .addEdgeFromSource(2, 4, 2)
                .addEdgeFromSource(3, 1, 2)
                .addEdgeFromSource(3, 2, 2)
                .addEdgeFromSource(3, 4, 2)
                .addEdgeFromSource(4, 5, 1)
                .addEdgeFromSource(4, 6, 2)
                .addEdgeFromSource(4, 7, 1)
                .addEdgeFromSource(5, 6, 3)
                .addEdgeFromSource(5, 7, 2)
                .addEdgeFromSource(6, 7, 2)
                .addEdgeFromSource(7, 8, 1)
                .addEdgeFromSource(8, 3, 4);

        return new GraphArguments<>(entries, edgesBuilder);
    }
}
