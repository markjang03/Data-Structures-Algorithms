import java.util.Comparator;
import java.util.Random;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.List;
/**
 * Your implementation of various sorting algorithms.
 *
 * @author Mark Jang
 * @version 1.0
 * @userid yjang95 
 * @GTID 903730588
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class Sorting {

    /**
     * Implement insertion sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("input array or/and comparator is null.");
        }
        for (int i = 1; i < arr.length; i++) {
            int j = i;
            T temp = arr[j];
            while (j > 0) {
                if (comparator.compare(temp, arr[j - 1]) < 0) {
                    arr[j] = arr[j - 1];
                } else {
                    break;
                }
                j--;
            }
            arr[j] = temp;
        }
    }

    // adaptive means that there will only be N comparisons for a sorted array.

    /**
     * Implement cocktail sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * NOTE: See pdf for last swapped optimization for cocktail sort. You
     * MUST implement cocktail sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("input array is null; cannot sort.");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("comparator is null; cannot sort.");
        }
        boolean swapped = true;
        int startp = 0;
        int endp = arr.length - 1;
        int lastSwapped = 0;
        while (swapped && startp < endp) {
            swapped = false;
            for (int i = startp; i < endp; i++) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    swap(arr, i, i + 1);
                    swapped = true;
                    lastSwapped = i;
                }
            }
            endp = lastSwapped;
            if (swapped) {
                swapped = false;
                for (int j = endp; j > startp; j--) {
                    if (comparator.compare(arr[j], arr[j - 1]) < 0) {
                        swap(arr, j, j - 1);
                        swapped = true;
                        lastSwapped = j;
                    }
                }
                startp = lastSwapped;
            }
        }
    }
    /**
     * Swap two items in the same array
     *
     * @param arr the array that contains two items
     * @param a first item that being swapped
     * @param b second item that being swapped
     * @param <T> The data type of the elements in the array.
     */
    private static <T> void swap(T[] arr, int a, int b) {
        T temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("input array or/and comparator is null.");
        }
        if (arr.length > 1) {
            int mid = arr.length / 2;
            T[] left = (T[]) new Object[mid];
            T[] right = (T[]) new Object[arr.length - mid];
            for (int i = 0; i < left.length; i++) {
                left[i] = arr[i];
            }
            for (int i = 0; i < right.length; i++) {
                right[i] = arr[i + left.length];
            }
            mergeSort(left, comparator);
            mergeSort(right, comparator);
            merge(arr, left, right, comparator);
        }
    }
    /**
     * Merges left and right array
     *
     * @param arr the array that being sorted
     * @param comparator comparator to compare values
     * @param leftA left array
     * @param rightA right array
     * @param <T> data type
     */
    private static <T> void merge(T[] arr, T[] leftA, T[] rightA,
                                  Comparator<T> comparator) {
        int i = 0;
        int j = 0;
        for (int k = 0; k < arr.length; k++) {
            if (j >= rightA.length || (i < leftA.length
                    && comparator.compare(leftA[i], rightA[j]) <= 0)) {
                arr[k] = leftA[i++];
            } else {
                arr[k] = rightA[j++];
            }
        }
    }


    /**
     * Implement quick sort.
     *
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     *
     * int pivotIndex = rand.nextInt(b - a) + a;
     *
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     *
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not receive
     * credit if you do not use the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @throws java.lang.IllegalArgumentException if the array or comparator or
     *                                            rand is null
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator, Random rand) {
        if (arr == null || comparator == null || rand == null) {
            throw new IllegalArgumentException("input array or/and comparator is null.");
        }
        quicksHelper(arr, 0, arr.length - 1, rand, comparator);
    }
    /**
     * Helper method for quick sort
     *
     * @param arr the array being sorted
     * @param first start index
     * @param last last index
     * @param rand the Random object used to select pivots
     * @param comparator the Comparator used to compare the data in arr
     * @param <T> data type to sort
     */
    private static <T> void quicksHelper(T[] arr, int first, int last,
                                     Random rand, Comparator<T> comparator) {
        if (last > first) {
            int pivotI = rand.nextInt(last - first) + first;
            T pivot = arr[pivotI];
            swap(arr, pivotI, last);
            int index = first;
            for (int i = first; i < last; i++) {
                if (comparator.compare(arr[i], pivot) <= 0) {
                    swap(arr, i, index);
                    index++;
                }
            }
            swap(arr, last, index);
            quicksHelper(arr, first, index - 1, rand, comparator);
            quicksHelper(arr, index + 1, last, rand, comparator);
        }
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(kn)
     *
     * And a best case running time of:
     * O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need. The number of iterations
     * can be determined using the number with the largest magnitude.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("input array is null");
        }

        LinkedList<Integer>[] buckets = (LinkedList<Integer>[]) new LinkedList[19];
        for (int i = 0; i < 19; i++) {
            buckets[i] = new LinkedList<>();
        }
        int mod = 10;
        int div = 1;

        boolean check = true;
        while (check) {
            check = false;
            for (int num : arr) {
                int bucket = num / div;
                if (bucket / 10 != 0) {
                    check = true;
                }
                if (buckets[bucket % mod + 9] == null) {
                    buckets[bucket % mod + 9] = new LinkedList<>();
                }
                buckets[bucket % mod + 9].add(num);
            }
            int index = 0;
            for (LinkedList<Integer> bucket : buckets) {
                if (bucket != null) {
                    for (int num : bucket) {
                        arr[index++] = num;
                    }
                    bucket.clear();
                }
            }
            div *= 10;
        }
    }

    /**
     * Implement heap sort.
     *
     * It should be:
     * out-of-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Use java.util.PriorityQueue as the heap. Note that in this
     * PriorityQueue implementation, elements are removed from smallest
     * element to largest element.
     *
     * Initialize the PriorityQueue using its build heap constructor (look at
     * the different constructors of java.util.PriorityQueue).
     *
     * Return an int array with a capacity equal to the size of the list. The
     * returned array should have the elements in the list in sorted order.
     *
     * @param data the data to sort
     * @return the array with length equal to the size of the input list that
     * holds the elements from the list is sorted order
     * @throws java.lang.IllegalArgumentException if the data is null
     */
    public static int[] heapSort(List<Integer> data) {
        if (data == null) {
            throw new IllegalArgumentException("Input data cannot be null.");
        }
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int i : data) {
            pq.add(i);
        }
        int index = 0;
        int[] arr = new int[data.size()];
        while (!pq.isEmpty()) {
            arr[index] = pq.poll();
            index++;
        }
        return arr;
    }
}
