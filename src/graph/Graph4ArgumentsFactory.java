package graph;

import graph.common.EdgesBuilder;
import graph.common.GraphArguments;
import graph.common.GraphArgumentsFactory;
import graph.common.VertexEntry;

import java.util.ArrayList;

/**
 * Factory for the Graph 4's arguments.
 * Graph 4 path: `src/graph/assets/graph3.jpg`
 */
public class Graph4ArgumentsFactory implements GraphArgumentsFactory<String, String> {
    @Override
    public GraphArguments<String, String> create() {
        ArrayList<VertexEntry<String, String>> entries = new ArrayList<>();
        entries.add(new VertexEntry<>("A", "A"));
        entries.add(new VertexEntry<>("B", "B"));
        entries.add(new VertexEntry<>("C", "C"));
        entries.add(new VertexEntry<>("D", "D"));
        entries.add(new VertexEntry<>("E", "E"));

        EdgesBuilder<String> edgesBuilder = new EdgesBuilder<>();
        edgesBuilder.addEdgeFromSource("A", "B", -1)
                .addEdgeFromSource("A", "C", 2)
                .addEdgeFromSource("B", "C", 3)
                .addEdgeFromSource("B", "D", 2)
                .addEdgeFromSource("B", "E", 2)
                .addEdgeFromSource("D", "B", 1)
                .addEdgeFromSource("D", "C", 5)
                .addEdgeFromSource("E", "D", -3);

        return new GraphArguments<>(entries, edgesBuilder);
    }
}
