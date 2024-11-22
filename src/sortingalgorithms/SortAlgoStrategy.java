package sortingalgorithms;

/**
 * Contract for a sorting algorithm strategy
 *
 * @param <T> The type of the array items
 */
public interface SortAlgoStrategy<T extends Comparable<T>> {
    /**
     * The method that executes the sorting
     *
     * @param array            The array to sort
     * @param orderingStrategy The ordering strategy
     * @return The sorted array
     */
    T[] sort(T[] array, SortOrderingStrategy<T> orderingStrategy);
}
