package sortingalgorithms;

import utils.orderingstrategy.SortOrderingStrategy;

public class SelectionSort<T extends Comparable<T>> implements SortAlgoStrategy<T> {
    @Override
    public T[] sort(T[] array, SortOrderingStrategy<T> orderingStrategy) {
        int length = array.length, lastIndex = length - 1, refIndex;
        T ref;

        for (int i = 0; i < lastIndex; i++) {
            ref = array[i];
            refIndex = i;

            for (int j = i + 1; j < length; j++) {
                if (orderingStrategy.shouldPrecede(array[j], ref)) {
                    ref = array[j];
                    refIndex = j;
                }
            }

            if (i != refIndex) {
                array[refIndex] = array[i];
                array[i] = ref;
            }
        }

        return array;
    }
}
