package graph;

import graph.common.EdgesBuilder;
import graph.common.GraphArguments;
import graph.common.GraphArgumentsFactory;
import graph.common.VertexEntry;

import java.util.ArrayList;

/**
 * Factory for the Graph 3's arguments.
 * Graph 3 path: `src/graph/assets/graph3.jpg`
 */
public class Graph3ArgumentsFactory implements GraphArgumentsFactory<String, String> {
    @Override
    public GraphArguments<String, String> create() {
        ArrayList<VertexEntry<String, String>> entries = new ArrayList<>();
        entries.add(new VertexEntry<>("A", "A"));
        entries.add(new VertexEntry<>("B", "B"));
        entries.add(new VertexEntry<>("C", "C"));
        entries.add(new VertexEntry<>("D", "D"));
        entries.add(new VertexEntry<>("E", "E"));

        EdgesBuilder<String> edgesBuilder = new EdgesBuilder<>();
        edgesBuilder.addEdgeFromSource("A", "B", 4)
                .addEdgeFromSource("A", "C", 2)
                .addEdgeFromSource("B", "C", 3)
                .addEdgeFromSource("B", "D", 2)
                .addEdgeFromSource("C", "B", 1)
                .addEdgeFromSource("C", "D", 4)
                .addEdgeFromSource("C", "E", 5)
                .addEdgeFromSource("E", "D", -5);

        return new GraphArguments<>(entries, edgesBuilder);
    }
}