package utils.orderingstrategy;

/**
 * Contract for a sorting algorithm's ordering strategy.
 * An ordering strategy defines whether an array item should precede another item
 * based on nature of the ordering ie ascending or descending
 *
 * @param <T> The type of array item
 */
public interface SortOrderingStrategy<T extends Comparable<T>> {
    /**
     * The method that checks whether a current array item should precede another item
     *
     * @param current The current array item
     * @param other   The other array item
     * @return The boolean result
     */
    boolean shouldPrecede(T current, T other);
}
