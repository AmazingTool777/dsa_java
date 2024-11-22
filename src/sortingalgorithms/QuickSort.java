package sortingalgorithms;

public class QuickSort<T extends Comparable<T>> implements SortAlgoStrategy<T> {
    /**
     * The recursive implementation of the quick sort algorithm.
     *
     * @param array            The original array
     * @param startIndex       The start index used by the algorithm
     * @param endIndex         The end index used by the algorithm
     * @param orderingStrategy The ordering strategy
     * @return The sorted array for the items between the start index and the end index
     */
    private T[] quickSort(T[] array, int startIndex, int endIndex, SortOrderingStrategy<T> orderingStrategy) {
        int length = endIndex - startIndex + 1, j = startIndex, i = j - 1;

        // We do nothing when the count of items is either 0 or 1
        if (length <= 1) return array;

        // We choose the last element of the array as the pivot for the sake of convention.
        // N.B: Any other item at a given index could be picked as the pivot.
        T pivot = array[endIndex], temp;
        // Iterating over the elements prior to the pivot in the array
        for (; j < endIndex; j++) {
            // If the `j` iterator encounters an item that should precede the pivot,
            // we shift the iterator `i` to the right by the swapping the items at those 2 indices.
            if (orderingStrategy.shouldPrecede(array[j], pivot)) {
                i++;
                if (i < j) {
                    temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                }
            }
        }
        // At this point, `i` should be the correct index of the pivot
        // and all elements prior to that index should precede the pivot
        i++;
        // We put the pivot to its correct index `ì`
        // by swapping the items at the `i` index and the last item of the array.
        temp = array[i];
        array[i] = array[endIndex];
        array[endIndex] = temp;

        // Recursively repeating the same shifting process compared with a pivot
        // for the items prior and after the current pivot
        quickSort(array, startIndex, i - 1, orderingStrategy);
        quickSort(array, i + 1, endIndex, orderingStrategy);

        return array;
    }

    /**
     * The public method that calls the recursive implementation of the quick sort.
     *
     * @param array            The array to sort
     * @param orderingStrategy The ordering strategy
     * @return The sorted array
     */
    @Override
    public T[] sort(T[] array, SortOrderingStrategy<T> orderingStrategy) {
        return quickSort(array, 0, array.length - 1, orderingStrategy);
    }
}
