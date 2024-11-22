package sortingalgorithms;

import java.util.ArrayList;
import java.util.Arrays;

public class MergeSort<T extends Comparable<T>> implements SortAlgoStrategy<T> {
    /**
     * Merges 2 array lists according to the order defined by the ordering strategy.
     *
     * @param arr1             The first array list
     * @param arr2             The second array list
     * @param orderingStrategy The ordering strategy
     * @return The merged array list
     */
    private ArrayList<T> merge(
            ArrayList<T> arr1,
            ArrayList<T> arr2,
            SortOrderingStrategy<T> orderingStrategy
    ) {
        int arr1Length = arr1.size(),
                arr2Length = arr2.size();
        int i = 0, j = 0;
        ArrayList<T> merged = new ArrayList<>();

        // Iterating over 2 array lists
        // and appending the item that takes the most precedence to the merged array list.
        // The iterations stop once one of the two array list is done iterating over all its items.
        while (i < arr1Length && j < arr2Length) {
            if (orderingStrategy.shouldPrecede(arr1.get(i), arr2.get(j))) {
                merged.add(arr1.get(i));
                i++;
            } else {
                merged.add(arr2.get(j));
                j++;
            }
        }

        // Appending the remaining items from either of the two array lists
        // which was not done iterating over all of its items yet.
        while (i < arr1Length) {
            merged.add(arr1.get(i));
            i++;
        }
        while (j < arr2Length) {
            merged.add(arr2.get(j));
            j++;
        }

        return merged;
    }

    /**
     * The recursive implementation of the merge sort.
     *
     * @param array            The given array list
     * @param orderingStrategy The ordering strategy
     * @return The sorted array list
     */
    private ArrayList<T> mergeSort(ArrayList<T> array, SortOrderingStrategy<T> orderingStrategy) {
        int length = array.size(), mid = length / 2;

        if (length == 1) {
            return array;
        }

        ArrayList<T> leftArray = new ArrayList<>(array.subList(0, mid));
        ArrayList<T> rightArray = new ArrayList<>(array.subList(mid, length));

        ArrayList<T> leftSorted = mergeSort(leftArray, orderingStrategy);
        ArrayList<T> rightSorted = mergeSort(rightArray, orderingStrategy);

        return merge(leftSorted, rightSorted, orderingStrategy);
    }

    /**
     * The public method that calls the recursive merge sort.
     *
     * @param array            The array to sort
     * @param orderingStrategy The ordering strategy
     * @return The sorted array.
     */
    @Override
    public T[] sort(T[] array, SortOrderingStrategy<T> orderingStrategy) {
        // Converting the array to an array list that is compatible with the `mergeSort()` method
        ArrayList<T> arrayList = new ArrayList<>(Arrays.stream(array).toList());
        // Converting the sorted array list back to an array
        return mergeSort(arrayList, orderingStrategy).toArray(array);
    }
}
