import java.util.NoSuchElementException;

/**
 * Your implementation of a non-circular DoublyLinkedList with a tail pointer.
 *
 * @author SHIHAB MOINUDDIN
 * @version 1.0
 * @userid smoinuddin6 (i.e. gburdell3)
 * @GTID 903506408 (i.e. 900000000)
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class DoublyLinkedList<T> {

    // Do not add new instance variables or modify existing ones.
    private DoublyLinkedListNode<T> head;
    private DoublyLinkedListNode<T> tail;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the element to the specified index. Don't forget to consider whether
     * traversing the list from the head or tail is more efficient!
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bounds!");
        }
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null!");
        }
        if (index == 0) {
            DoublyLinkedListNode<T> newHead = new DoublyLinkedListNode<T>(data, null, head);
            if (!(this.isEmpty())) {
                head.setPrevious(newHead);
            } else {
                tail = newHead;
            }
            head = newHead;
            size++;
        } else if (index == size) {
            DoublyLinkedListNode<T> newTail = new DoublyLinkedListNode<T>(data, tail, null);
            if (!(this.isEmpty())) {
                tail.setNext(newTail);
            } else {
                head = newTail;
            }
            tail = newTail;
            size++;
        } else {
            if (index <= ((size - 1) / 2)) {
                DoublyLinkedListNode<T> curr = head;
                for (int i = 0; i < index; i++) {
                    curr = curr.getNext();
                }
                DoublyLinkedListNode<T> addThis = new DoublyLinkedListNode<T>(data, curr.getPrevious(), curr);
                (curr.getPrevious()).setNext(addThis);
                (addThis.getNext()).setPrevious(addThis);
                size++;
            } else {
                DoublyLinkedListNode<T> curr = tail;
                for (int i = size - 1; i > index; i--) {
                    curr = curr.getPrevious();
                }
                DoublyLinkedListNode<T> addThis = new DoublyLinkedListNode<T>(data, curr.getPrevious(), curr);
                curr.getPrevious().setNext(addThis);
                curr.setPrevious(addThis);
                size++;
            }

        }
    }

    /**
     * Adds the element to the front of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        addAtIndex(0, data);
    }

    /**
     * Adds the element to the back of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        addAtIndex(size, data);
    }

    /**
     * Removes and returns the element at the specified index. Don't forget to
     * consider whether traversing the list from the head or tail is more
     * efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds!");
        }
        if (index == 0) {
            T data = head.getData();
            head = head.getNext();
            head.setPrevious(null);
            size--;
            return data;
        } else if (index == size - 1) {
            T data = tail.getData();
            tail = tail.getPrevious();
            tail.setNext(null);
            size--;
            return data;
        } else {
            if (index <= ((size - 1) / 2)) {
                DoublyLinkedListNode<T> curr = head;
                for (int i = 0; i < index; i++) {
                    curr = curr.getNext();
                }
                T data = curr.getData();
                DoublyLinkedListNode<T> prev = curr.getPrevious();
                DoublyLinkedListNode<T> next = curr.getNext();
                prev.setNext(next);
                next.setPrevious(prev);
                size--;
                return data;
            } else {
                DoublyLinkedListNode<T> curr = tail;
                for (int i = size - 1; i > index; i--) {
                    curr = curr.getPrevious();
                }
                T data = curr.getData();
                DoublyLinkedListNode<T> prev = curr.getPrevious();
                DoublyLinkedListNode<T> next = curr.getNext();
                prev.setNext(next);
                next.setPrevious(prev);
                size--;
                return data;
            }
        }
    }

    /**
     * Removes and returns the first element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        return removeAtIndex(0);
    }

    /**
     * Removes and returns the last element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        return removeAtIndex(size - 1);
    }

    /**
     * Returns the element at the specified index. Don't forget to consider
     * whether traversing the list from the head or tail is more efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds!");
        }
        if (index == 0) {
            return head.getData();
        } else if (index == size - 1) {
            return tail.getData();
        } else {
            DoublyLinkedListNode<T> curr = head;
            for (int i = 0; i < index; i++) {
                curr = curr.getNext();
            }
            return (T) curr.getData();
        }
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the list.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        head = null;
        tail = null;
        size = 0; 
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(1) if data is in the tail and O(n) for all other cases.
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
         if (data == null) {
             throw new IllegalArgumentException("Data cannot be null!");
         }
         DoublyLinkedListNode<T> curr = tail;
        T returnThis = null;
         for (int i = size - 1; i > 0; i--) {
             if ((curr.getData()).equals(data)) {
                 returnThis = curr.getData();
                 removeAtIndex(i);
                 break;
             }
             curr = curr.getPrevious();
         }
         if (returnThis == null) {
            throw new IllegalArgumentException("Data was not found!");
         }
         return returnThis;
    }

    /**
     * Returns an array representation of the linked list. If the list is
     * size 0, return an empty array.
     *
     * Must be O(n) for all cases.
     *
     * @return an array of length size holding all of the objects in the
     * list in the same order
     */
    public Object[] toArray() {
        Object[] array = (T[]) new Object[size];
        if (!isEmpty()) {
            DoublyLinkedListNode<T> curr = head;
            for (int i = 0; i < size; i++) {
                array[i] = curr.getData();
                curr = curr.getNext();
            }
            return array;
        } else {
            return array;
        }
    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public DoublyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the tail of the list
     */
    public DoublyLinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }

    /**
     * Returns the size of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }
}
