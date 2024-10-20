import java.util.ArrayList;
import java.util.NoSuchElementException;
/**
 * Your implementation of a MinHeap.
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
public class MinHeap<T extends Comparable<? super T>> {

    /**
     * The initial capacity of the MinHeap when created with the default
     * constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    // Do not add new instance variables or modify existing ones.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new MinHeap.
     *
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     * To initialize the backing array, create a Comparable array and then cast
     * it to a T array.
     */
    public MinHeap() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;

    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     *
     * You must use the BuildHeap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     * As a reminder, this is the algorithm that involves building the heap
     * from the bottom up by repeated use of downHeap operations.
     *
     * Before doing the algorithm, first copy over the data from the
     * ArrayList to the backingArray (leaving index 0 of the backingArray
     * empty). The data in the backingArray should be in the same order as it
     * appears in the passed in ArrayList before you start the BuildHeap
     * algorithm.
     *
     * The backingArray should have capacity 2n + 1 where n is the
     * size of the passed in ArrayList (not INITIAL_CAPACITY). Index 0 should
     * remain empty, indices 1 to n should contain the data in proper order, and
     * the rest of the indices should be empty.
     *
     * @param data a list of data to initialize the heap with
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public MinHeap(ArrayList<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot build heap with a null data");
        }
        backingArray = (T[]) (new Comparable[2 * data.size() + 1]);

        for (T item : data) {
            if (item == null) {
                throw new IllegalArgumentException("a heap cannot contain null data.");
            }
            backingArray[size + 1] = item;
            size++;
        }
        for (int i = size / 2; i > 0; i--) {
            T item = backingArray[i];
            heapifyDown(item, i, i * 2, i * 2 + 1);
        }

    }

    /**
     * Adds an item to the heap. If the backing array is full (except for
     * index 0) and you're trying to add a new item, then double its capacity.
     * The order property of the heap must be maintained after adding. You can
     * assume that no duplicate data will be passed in.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null data.");
        }
        if (size == backingArray.length - 1) {
            resize();
        }
        if (size == 0) {
            backingArray[1] = data;
            size++;
        } else {
            backingArray[size + 1] = data;
            int parentI = (size + 1) / 2;
            int itemI = size + 1;
            heapifyUp(data, parentI, itemI);
            size++;
        }
    }
    /**
     * resizes backing array to twice the current length
     */
    private void resize() {
        int blength = backingArray.length;
        T[] newArray = (T[]) (new Comparable[blength * 2]);
        for (int i = 0; i < blength; i++) {
            newArray[i] = backingArray[i];
        }
        backingArray = newArray;
    }

    /**
     * heapify up operation; recursive helper method to add.
     * @param data item to add
     * @param parentIndex index of parent item's parent
     * @param itemI item's index
     */
    private void heapifyUp(T data, int parentIndex, int itemI) {
        if (parentIndex == 0) {
            return;
        }
        if (backingArray[parentIndex].compareTo(data) > 0) {
            swap(data, parentIndex, itemI);
            heapifyUp(data, parentIndex / 2, itemI / 2);
        } else if (backingArray[parentIndex].compareTo(data) < 0) {
            return;
        }
    }
    /**
     * private helper method that swap indices
     * @param item element to be swapped
     * @param ai index of first element
     * @param bi index of second
     */
    private void swap(T item, int ai, int bi) {
        T temp = backingArray[ai];
        backingArray[ai] = item;
        backingArray[bi] = temp;
    }


    /**
     * Removes and returns the min item of the heap. As usual for array-backed
     * structures, be sure to null out spots as you remove. Do not decrease the
     * capacity of the backing array.
     * The order property of the heap must be maintained after removing.
     *
     * @return the data that was removed
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T remove() {
        if (size == 0) {
            throw new NoSuchElementException("heap is empty.");
        }
        T removed = backingArray[1];
        backingArray[1] = backingArray[size];
        heapifyDown(backingArray[1], 1, 2, 3);
        backingArray[size] = null;
        size--;
        return removed;

    }
    /**
     * heapify down operation; recursive helper method to remove
     * @param item item to move
     * @param itemI item's index
     * @param ri item's right childs index
     * @param li item's left's index
     */
    private void heapifyDown(T item, int itemI, int li, int ri) {
        int blen = backingArray.length;
        if (itemI == size || li >= blen || ri >= blen) {
            return;
        }
        if (backingArray[li] == null && backingArray[ri] == null) {
            return;
        }
        if (backingArray[ri] == null) {
            if (backingArray[itemI].compareTo(backingArray[li]) > 0) {
                swap(item, li, itemI);
            }
            return;
        } else if (backingArray[li] == null) {
            if (backingArray[itemI].compareTo(backingArray[ri]) > 0) {
                swap(item, ri, itemI);
            }
            return;
        }
        if (backingArray[itemI].compareTo(backingArray[li]) < 0
            && backingArray[itemI].compareTo(backingArray[ri]) < 0) {
            return;
        } else {
            if (backingArray[ri].compareTo(backingArray[li]) > 0) {
                swap(item, li, itemI);
                itemI = li;
            } else {
                swap(item, ri, itemI);
                itemI = ri;
            }
            heapifyDown(item, itemI, itemI * 2, itemI * 2 + 1);
        }
    }

    /**
     * Returns the minimum element in the heap.
     *
     * @return the minimum element
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T getMin() {
        if (size == 0) {
            return null;
        }
        return backingArray[1];
    }

    /**
     * Returns whether or not the heap is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the heap.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     */
    public void clear() {
        size = 0;
        backingArray = (T[]) (new Comparable[INITIAL_CAPACITY]);

    }

    /**
     * Returns the backing array of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
