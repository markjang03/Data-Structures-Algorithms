import java.util.NoSuchElementException;

/**
 * Your implementation of a non-circular DoublyLinkedList with a tail pointer.
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
        if (data == null) {
            throw new IllegalArgumentException("cannot insert null dat into data structure");
        }

        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("index is smaller than zero or bigger than the size");
        }

        DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<>(data);
        if (index == 0) {
            newNode.setNext(head);
            if (head != null) {
                head.setPrevious(newNode);
            }
            head = newNode;
            if (tail == null) {
                tail = newNode;
            }
        } else if (index == size) {
            newNode.setPrevious(tail);

            if (tail != null) {
                tail.setNext(newNode);
            }
            tail = newNode;
            if (head == null) {
                head = newNode;
            }
        } else {
            if (index <= ((size - 1) / 2)) {
                DoublyLinkedListNode<T> current = head;
                for (int i = 0; i < index - 1; i++) {
                    current = current.getNext();
                }
                newNode.setPrevious(current);
                newNode.setNext(current.getNext());
                current.getNext().setPrevious(newNode);
                current.setNext(newNode);
            } else {
                DoublyLinkedListNode<T> current = tail;
                for (int i = size - 1; i > index + 1; i--) {
                    current = current.getPrevious();
                }
                newNode.setNext(current);
                newNode.setPrevious(current.getPrevious());
                current.getPrevious().setNext(newNode);
                current.setPrevious(newNode);
            }


        }

        size++;
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
            throw new IndexOutOfBoundsException("index is smaller than zero or bigger than the size");
        }

        DoublyLinkedListNode<T> current = head;

        if (index == 0) {

            // Removing the front element
            head = current.getNext();

            if (head != null) {
                head.setPrevious(null);

            }
            if (tail == current) {
                tail = null;
            }
        } else if (index == size - 1) {

            current = tail;
            tail = current.getPrevious();
            if (tail != null) {
                tail.setNext(null);
            }
            if (head == current) {
                head = null;
            }
        } else {
            if (index <= ((size - 1) / 2)) {
                for (int i = 0; i < index; i++) {
                    current = current.getNext();
                }
                current.getPrevious().setNext(current.getNext());
                current.getNext().setPrevious(current.getPrevious());
            } else {
                current = tail;
                for (int i = size - 1; i > index; i--) {
                    current = current.getPrevious();
                }
                current.getPrevious().setNext(current.getNext());
                current.getNext().setPrevious(current.getPrevious());
            }
        }
        size--;
        return current.getData();
    }


    /**
     * Removes and returns the first element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("the list is empty. cant remove anything if the list is empty");
        }
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
    public T removeFromBack() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("the list is empty. cant remove anything if the list is empty");
        }
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
            throw new IndexOutOfBoundsException("index is smaller than zero or bigger than the size");
        }

        DoublyLinkedListNode<T> current = head;

        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }

        return current.getData();
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
            throw new IllegalArgumentException("cannot insert null dat into data structure");
        }

        DoublyLinkedListNode<T> current = tail;

        while (current != null && !current.getData().equals(data)) {
            current = current.getPrevious();
        }

        if (current == null) {
            throw new NoSuchElementException("the list is empty. cant remove anything if the list is empty");
        }

        if (current == head) {
            return removeFromFront();
        } else if (current == tail) {
            return removeFromBack();
        } else {
            current.getPrevious().setNext(current.getNext());
            current.getNext().setPrevious(current.getPrevious());


            size--;
            return current.getData();
        }
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
        Object[] arr = new Object[size];
        DoublyLinkedListNode<T> current = head;

        for (int i = 0; i < size; i++) {
            arr[i] = current.getData();
            current = current.getNext();
        }

        return arr;
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
