package sortingalgorithms;

public class InsertionSort<T extends Comparable<T>> implements SortAlgoStrategy<T> {
    @Override
    public T[] sort(T[] array, SortOrderingStrategy<T> orderingStrategy) {
        int length = array.length;
        T current;

        for (int i = 1; i < length; i++) {
            current = array[i];

            // As long as the current item should precede any element amongst the sorted items,
            // we shift the items of the sorted items until the current items find its correct location.
            int j = i;
            while (j > 0 && orderingStrategy.shouldPrecede(current, array[j - 1])) {
                array[j] = array[j - 1];
                j--;
            }
            if (j < i) {
                array[j] = current;
            }
        }

        return array;
    }
}
