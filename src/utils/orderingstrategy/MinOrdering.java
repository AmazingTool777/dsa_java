package utils.orderingstrategy;

public class MinOrdering<T extends Comparable<T>> implements SortOrderingStrategy<T> {
    @Override
    public boolean shouldPrecede(T current, T other) {
        return current.compareTo(other) < 0;
    }
}
