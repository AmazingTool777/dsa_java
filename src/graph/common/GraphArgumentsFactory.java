package graph.common;

/**
 * Factory of a graph's arguments.
 *
 * @param <TKey>    Data type of vertex key
 * @param <TVertex> Data type of the actual vertex data
 */
public interface GraphArgumentsFactory<TKey, TVertex> {
    /**
     * The factory method.
     *
     * @return The built graph arguments
     */
    GraphArguments<TKey, TVertex> create();
}
