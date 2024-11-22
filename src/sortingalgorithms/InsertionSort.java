package sortingalgorithms;

public class InsertionSort<T extends Comparable<T>> implements SortAlgoStrategy<T> {
    @Override
    public T[] sort(T[] array, SortOrderingStrategy<T> orderingStrategy) {
        int length = array.length;
        T current;

        for (int i = 1; i < length; i++) {
            current = array[i];

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
