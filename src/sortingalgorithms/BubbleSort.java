package sortingalgorithms;

public class BubbleSort<T extends Comparable<T>> implements SortAlgoStrategy<T> {
    @Override
    public T[] sort(T[] array, SortOrderingStrategy<T> ordering) {
        int length = array.length, lastIndex = length - 1;
        T temp;

        for (int i = 0; i < lastIndex; i++) {
            for (int j = i + 1; j < length; j++) {
                if (ordering.shouldPrecede(array[j], array[i])) {
                    temp = array[j];
                    array[j] = array[i];
                    array[i] = temp;
                }
            }
        }

        return array;
    }
}
