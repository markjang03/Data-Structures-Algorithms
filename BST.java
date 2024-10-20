import java.util.List;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Collection;
import java.util.Queue;
import java.util.LinkedList;
/**
 * Your implementation of a BST.
 *
 * @author Mark jang
 * @version 1.0
 * @userid yjang95
 * @GTID 903730588
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data is null
     */
    public BST(Collection<T> data) {
        if (data == null || data.contains(null)) {
            throw new IllegalArgumentException("Cannot insert null data into data structure.");
        }
        for (T item : data) {
            add(item);
        }
    }

    /**
     * Adds the data to the tree.
     *
     * This must be done recursively.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into data structure.");
        }
        root = addRecursive(root, data);
    }
    /**
     * Recursively search a place that can be added a new node that has the input data.
     *
     * If the current node is null, it creates a new node with the specified data and returns it.
     * If the current node is not null, it traverses the subtree and recursively adds the new data.
     *
     * @param current Node of the current subtree.
     * @param data    The data that will be added.
     * @return The node of the modified subtree after adding the data.
     */
    private BSTNode<T> addRecursive(BSTNode<T> current, T data) {
        if (current == null) {
            size++;
            return new BSTNode<>(data);
        } else {
            if (data.compareTo(current.getData()) < 0) {
                current.setLeft(addRecursive(current.getLeft(), data));
            } else if (data.compareTo(current.getData()) > 0) {
                current.setRight(addRecursive(current.getRight(), data));
            }
        }
        return current;
    }
    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into data structure.");
        }
        T removed = get(data);
        root = removeRecursive(root, data);
        return removed;

    }
    /**
     * Recursively search for the data that needs to be deleted
     * and removes a node containing the data.
     *
     * @param current The node of the current subtree being processed.
     * @param data    The data that will be removed from the tree.
     * @return The node of the modified subtree after.
     * @throws java.util.NoSuchElementException if the data is not found in the tree.
     */
    private BSTNode<T> removeRecursive(BSTNode<T> current, T data) {
        if (current == null) {
            throw new NoSuchElementException("Data not found in the tree.");
        }

        if (data.compareTo(current.getData()) < 0) {
            current.setLeft(removeRecursive(current.getLeft(), data));
        } else if (data.compareTo(current.getData()) > 0) {
            current.setRight(removeRecursive(current.getRight(), data));
        } else {
            if (current.getLeft() == null) {
                size--;
                return current.getRight();
            } else if (current.getRight() == null) {
                size--;
                return current.getLeft();
            }
            current.setData(minimum(current.getRight()));
            current.setRight(removeRecursive(current.getRight(), current.getData()));
        }

        return current;
    }
    /**
     * Finds and returns the minimum value in a subtree at the specified node.
     *
     * @param node The node of the subtree in which to find the minimum value.
     * @return The minimum value in the subtree.
     */

    private T minimum(BSTNode<T> node) {
        while (node.getLeft() != null) {
            node = node.getLeft();
        }
        return node.getData();
    }

    /**
     * Returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into data structure.");
        }
        return getRecursive(root, data);

    }
    /**
     * Recursively searches the data matching the value in the subtree
     * and returns the value once it found the data.
     *
     * @param current The root node of the current subtree being searched.
     * @param data    The data to search for in the tree.
     * @return The data in the tree that matches the specified value.
     * @throws java.util.NoSuchElementException if the specified data is not found in the tree.
     */
    private T getRecursive(BSTNode<T> current, T data) {
        if (current == null) {
            throw new NoSuchElementException("Data not found in the tree.");
        }
        if (data.compareTo(current.getData()) == 0) {
            return current.getData();
        } else if (data.compareTo(current.getData()) < 0) {
            return getRecursive(current.getLeft(), data);
        } else {
            return getRecursive(current.getRight(), data);
        }
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * This must be done recursively.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into data structure.");
        }
        return containsRecursive(root, data);
    }
    /**
     * Recursively searches for the presence of data matching the data
     * within the subtree rooted at the current node.
     *
     * This method performs a binary search in the subtree rooted at the current node
     * to determine whether the specified data exists within the subtree.
     *
     * @param current The root node of the current subtree being searched.
     * @param data The data to search for within the subtree.
     * @return true if the data is found within the subtree, false otherwise.
     */
    private boolean containsRecursive(BSTNode<T> current, T data) {
        if (current == null) {
            return false;
        }

        int comparisonResult = data.compareTo(current.getData());

        if (comparisonResult == 0) {
            return true;
        } else if (comparisonResult < 0) {

            return containsRecursive(current.getLeft(), data);
        } else {
            return containsRecursive(current.getRight(), data);
        }
    }


    /**
     * Generate a pre-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        List<T> result = new ArrayList<>();
        preorderRecursive(root, result);
        return result;
    }
    /**
     * Recursively performs a preorder traversal of the subtree and,
     * add the data to the result list.
     *
     * @param current The root node of the current subtree.
     * @param result  The list that has the order of data elements,
     * when the pre-ordeer traversal was performed.
     */
    private void preorderRecursive(BSTNode<T> current, List<T> result) {
        if (current == null) {
            return;
        }
        result.add(current.getData());
        preorderRecursive(current.getLeft(), result);
        preorderRecursive(current.getRight(), result);
    }

    /**
     * Generate an in-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        List<T> result = new ArrayList<>();
        inorderRecursive(root, result);
        return result;
    }
    /**
     * Recursively performs a inorder traversal of the subtree and,
     * add the data to the result list.
     *
     * @param current The root node of the current subtree.
     * @param result  The list that has the order of data elements,
     * when the in-ordeer traversal was performed.
     */
    private void inorderRecursive(BSTNode<T> current, List<T> result) {
        if (current == null) {
            return;
        }

        inorderRecursive(current.getLeft(), result);
        result.add(current.getData());
        inorderRecursive(current.getRight(), result);
    }
    /**
     * Generate a post-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        List<T> result = new ArrayList<>();
        postorderRecursive(root, result);
        return result;
    }
    /**
     * Recursively performs a postorder traversal of the subtree and,
     * add the data to the result list.
     *
     * @param current The root node of the current subtree.
     * @param result  The list that has the order of data elements,
     * when the post-ordeer traversal was performed.
     */
    private void postorderRecursive(BSTNode<T> current, List<T> result) {
        if (current == null) {
            return;
        }

        postorderRecursive(current.getLeft(), result);
        postorderRecursive(current.getRight(), result);
        result.add(current.getData());
    }


    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        List<T> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        Queue<BSTNode<T>> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            BSTNode<T> current = queue.poll();
            result.add(current.getData());

            if (current.getLeft() != null) {
                queue.offer(current.getLeft());
            }
            if (current.getRight() != null) {
                queue.offer(current.getRight());
            }
        }

        return result;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * This must be done recursively.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return heightRecursive(root);
    }
    /**
     * Recursively calculates the height of the subtree rooted at the input.
     *
     * @param current The root node of the current subtree for which height is calculated.
     * @return The height of the subtree, but if the tree is empty returns -1.
     */
    private int heightRecursive(BSTNode<T> current) {
        if (current == null) {
            return -1;
        }
        int leftHeight = heightRecursive(current.getLeft());
        int rightHeight = heightRecursive(current.getRight());
        return Math.max(leftHeight, rightHeight) + 1;
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Finds the path between two elements in the tree, specifically the path
     * from data1 to data2, inclusive of both.
     *
     * This must be done recursively.
     *
     * A good way to start is by finding the deepest common ancestor (DCA) of both data
     * and add it to the list. You will most likely have to split off and
     * traverse the tree for each piece of data adding to the list in such a
     * way that it will return the path in the correct order without requiring any
     * list manipulation later. One way to accomplish this (after adding the DCA
     * to the list) is to then traverse to data1 while adding its ancestors
     * to the front of the list. Finally, traverse to data2 while adding its
     * ancestors to the back of the list.
     *
     * Please note that there is no relationship between the data parameters
     * in that they may not belong to the same branch.
     *
     * You may only use 1 list instance to complete this method. Think about
     * what type of list to use considering the Big-O efficiency of the list
     * operations.
     *
     * This method only needs to traverse to the deepest common ancestor once.
     * From that node, go to each data in one traversal each. Failure to do
     * so will result in a penalty.
     *
     * If both data1 and data2 are equal and in the tree, the list should be
     * of size 1 and contain the element from the tree equal to data1 and data2.
     *
     * Ex:
     * Given the following BST composed of Integers
     *              50
     *          /        \
     *        25         75
     *      /    \
     *     12    37
     *    /  \    \
     *   11   15   40
     *  /
     * 10
     * findPathBetween(10, 40) should return the list [10, 11, 12, 25, 37, 40]
     * findPathBetween(50, 37) should return the list [50, 25, 37]
     * findPathBetween(75, 75) should return the list [75]
     *
     * Must be O(log n) for a balanced tree and O(n) for worst case.
     *
     * @param data1 the data to start the path from
     * @param data2 the data to end the path on
     * @return the unique path between the two elements
     * @throws java.lang.IllegalArgumentException if either data1 or data2 is
     *                                            null
     * @throws java.util.NoSuchElementException   if data1 or data2 is not in
     *                                            the tree
     */
    public List<T> findPathBetween(T data1, T data2) {
        if (data1 == null || data2 == null) {
            throw new IllegalArgumentException("Cannot find null data in tree");
        }

        if (!contains(data1) || !contains(data2)) {
            throw new NoSuchElementException("Data is not found in tree, check again");
        }

        List<T> list = new ArrayList<>();

        BSTNode<T> commonAncestor = findCommonAncestor(root, data1, data2);

        findPathfromNode(data1, commonAncestor, list);

        int reverseSize = list.size();

        for (int i = 0; i < reverseSize / 2; i++) {
            T temp = list.get(i);
            list.set(i, list.get(reverseSize - 1 - i));
            list.set(reverseSize - 1 - i, temp);
        }
        list.remove(reverseSize - 1);
        findPathfromNode(data2, commonAncestor, list);
        return list;
    }
    /**
     * Finds the common ancestor of two nodes that has the input values
     * in a binary search tree.
     *
     * @param node  The root node of the BST to search in.
     * @param data1 The data of the first node.
     * @param data2 The data of the second node.
     * @return The common ancestor node of the two nodes with data values data1 and data2,
     *         or null if one or both of the nodes are not found in the BST.
     */
    private BSTNode<T> findCommonAncestor(BSTNode<T> node, T data1, T data2) {
        if (node == null) {
            return null;
        }

        if (data1.compareTo(node.getData()) < 0 && data2.compareTo(node.getData()) < 0) {
            return findCommonAncestor(node.getLeft(), data1, data2);
        } else if (data1.compareTo(node.getData()) > 0 && data2.compareTo(node.getData()) > 0) {
            return findCommonAncestor(node.getRight(), data1, data2);
        }

        return node;
    }
    /**
     * Finds the path from the input node with the data value to a target node
     * with the same data value in a binary search tree and stores the path in a list.
     *
     * @param data  The data value of the target node to find the path to.
     * @param node  The current node being examined in the BST.
     * @param path  A list that will have stored the path from the current node to the target node.
     */
    private void findPathfromNode(T data, BSTNode<T> node, List<T> path) {
        if (node == null) {
            return;
        }
        path.add(node.getData());

        int compareResult = data.compareTo(node.getData());
        if (compareResult < 0) {
            findPathfromNode(data, node.getLeft(), path);
        } else if (compareResult > 0) {
            findPathfromNode(data, node.getRight(), path);
        }
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}

