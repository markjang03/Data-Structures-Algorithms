import java.util.Collection;
import java.util.NoSuchElementException;
/**
 * Your implementation of an AVL.
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
public class AVL<T extends Comparable<? super T>> {

    // Do not add new instance variables or modify existing ones.
    private AVLNode<T> root;
    private int size;

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize the AVL with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("input data cannot be null");
        }
        size = 0;
        for (T item : data) {
            add(item);
        }
    }

    /**
     * Adds the element to the tree.
     *
     * Start by adding it as a leaf like in a regular BST and then rotate the
     * tree as necessary.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after adding the element, making sure to rebalance if
     * necessary.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("input data cannot be null");
        }
        root = addHelper(data, root);
    }

    /**
     * Helper method for add(T data)
     *
     * @param data the data to be added
     * @param node the root of a tree
     * @return node that is balanced
     */
    private AVLNode<T> addHelper(T data, AVLNode<T> node) {
        if (node == null) {
            size++;
            return new AVLNode<>(data);
        }
        if (data.compareTo(node.getData()) < 0) {
            node.setLeft(addHelper(data, node.getLeft()));
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(addHelper(data, node.getRight()));
        }
        newSet(node);
        if (Math.abs(node.getBalanceFactor()) > 1) {
            node = balance(node);
        }
        return node;
    }
    /**
     * Recalculates the height and balance factor of the given AVLNode and updates its properties.
     *
     * @param node the AVLNode
     */
    private void newSet(AVLNode<T> node) {
        int left = nodeHeight(node.getLeft());
        int right = nodeHeight(node.getRight());
        node.setHeight(1 + Math.max(left, right));
        node.setBalanceFactor(left - right);
    }

    /**
     * Balances the node by rotations
     *
     * @param node the node of a tree that will get balanced
     * @return node that is balanced
     */
    private AVLNode<T> balance(AVLNode<T> node) {
        AVLNode<T> finalNode = node;
        if (node.getBalanceFactor() == 0) {
            return finalNode;
        } else if (node.getBalanceFactor() < 0) {
            if (node.getRight().getBalanceFactor() > 0) {
                //right-left rotation
                node.setRight(rotateRight(node.getRight()));
                finalNode = rotateLeft(node);
            } else {
                finalNode = rotateLeft(node);
            }
        } else if (node.getBalanceFactor() > 0) {
            if (node.getLeft().getBalanceFactor() < 0) {
                //left-right rotation
                node.setLeft(rotateLeft(node.getLeft()));
                finalNode = rotateRight(node);
            } else {
                finalNode = rotateRight(node);
            }
        }
        return finalNode;
    }

    /**
     * operate right rotation
     * @param node the node that getting roated
     * @return rotated node
     */
    private AVLNode<T> rotateRight(AVLNode<T> node) {
        AVLNode<T> leftNode = node.getLeft();
        node.setLeft(leftNode.getRight());
        leftNode.setRight(node);
        newSet(node);
        newSet(leftNode);
        return leftNode;
    }

    /**
     * operate left rotation
     * @param node the node that getting roated
     * @return rotated node
     */
    private AVLNode<T> rotateLeft(AVLNode<T> node) {
        AVLNode<T> rightNode = node.getRight();
        node.setRight(rightNode.getLeft());
        rightNode.setLeft(node);
        newSet(node);
        newSet(rightNode);
        return rightNode;
    }


    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the predecessor to
     * replace the data, NOT successor. As a reminder, rotations can occur
     * after removing the predecessor node.
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after removing the element, making sure to rebalance if
     * necessary.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not found
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Input data cannot be null");
        }
        AVLNode<T> removed = new AVLNode<>(null);
        root = removeHelper(data, root, removed);
        if (removed.getData() == null) {
            throw new NoSuchElementException("Data  was not found, please check the number again");
        }
        size--;
        return removed.getData();
    }
    /**
     * recursively traverses AVL to find the node has the data
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the predecessor to
     * replace the data, NOT successor. As a reminder, rotations can occur
     * after removing the predecessor node.
     *
     * @param data data that will be removed
     * @param node node that gets compared to data
     * @param removed reference to dummy node that has removed data
     * @return node's data
     * @throws java.util.NoSuchElementException if the node is null
     */
    private AVLNode<T> removeHelper(T data, AVLNode<T> node, AVLNode<T> removed) {
        if (node == null) {
            throw new NoSuchElementException("data was not found, please check the number again");
        }
        if (data.compareTo(node.getData()) < 0) {
            node.setLeft(removeHelper(data, node.getLeft(), removed));
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(removeHelper(data, node.getRight(), removed));
        } else {
            //case 1 and 2
            if (removed.getData() == null) {
                removed.setData(node.getData());
            }
            if (node.getLeft() == null) {
                return node.getRight();
            } else if (node.getRight() == null) {
                return node.getLeft();
            }
            //case 3
            node.setData(predecessor(node));
            node.setLeft(removeHelper(node.getData(), node.getLeft(), removed));
        }
        newSet(node);
        if (Math.abs(node.getBalanceFactor()) > 1) {
            node = balance(node);
        }
        return node;
    }

    /**
     * helper method for remove that finds the predecessor node
     * @param node node getting removed
     * @return node's predecessor
     */
    private T predecessor(AVLNode<T> node) {
        node = node.getLeft();
        T data = node.getData();
        while (node.getRight() != null) {
            data = node.getRight().getData();
            node = node.getRight();
        }
        return data;
    }
    /**
     * Returns the element from the tree matching the given parameter.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * @param data the data to search for in the tree
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        T result = getHelper(data, root);
        if (result == null) {
            throw new NoSuchElementException("Data is not found, check again");
        }
        return result;
    }
    /**
     * Recursively searches the AVL tree for the specified data element.
     *
     * @param data the data to search for.
     * @param node the current node being examined.
     * @return the data element with the specified value if found.
     * @throws java.util.NoSuchElementException if the specified data is not found in the tree.
     */
    private T getHelper(T data, AVLNode<T> node) {
        if (node == null) {
            throw new NoSuchElementException("Data is not found, check again");
        }
        int tmp = data.compareTo(node.getData());
        if (tmp > 0) {
            return getHelper(data, node.getRight());
        } else if (tmp < 0) {
            return getHelper(data, node.getLeft());
        } else {
            return node.getData();
        }
    }


    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to search for in the tree.
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("input data is null; it cant contain it");
        }
        return containsHelper(data, root);
    }

    /**
     * recursive helper method for contains
     * @param data data to search
     * @param node node to check
     * @return boolean of contain
     */
    private boolean containsHelper(T data, AVLNode<T> node) {
        if (node == null) {
            return false;
        } else if (node.getData().equals(data)) {
            return true;
        } else {
            if (data.compareTo(node.getData()) > 0) {
                return containsHelper(data, node.getRight());
            } else {
                return containsHelper(data, node.getLeft());
            }
        }
    }


    /**
     * Returns the height of the root of the tree.
     *
     * Should be O(1).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return nodeHeight(root);
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * The predecessor is the largest node that is smaller than the current data.
     *
     * Should be recursive.
     *
     * This method should retrieve (but not remove) the predecessor of the data
     * passed in. There are 2 cases to consider:
     * 1: The left subtree is non-empty. In this case, the predecessor is the
     * rightmost node of the left subtree.
     * 2: The left subtree is empty. In this case, the predecessor is the lowest
     * ancestor of the node containing data whose right child is also
     * an ancestor of data.
     *
     * This should NOT be used in the remove method.
     *
     * Ex:
     * Given the following AVL composed of Integers
     *     76
     *   /    \
     * 34      90
     *  \    /
     *  40  81
     * predecessor(76) should return 40
     * predecessor(81) should return 76
     *
     * @param data the data to find the predecessor of
     * @return the predecessor of data. If there is no smaller data than the
     * one given, return null.
     * @throws java.lang.IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
    */
    public T predecessor(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        AVLNode<T> predecessor = findPredecessor(root, data);
        if (predecessor != null) {
            return predecessor.getData();
        } else {
            return null;
        }
    }

    /**
     * Finds the predecessor of a given data element in the AVL tree.
     *
     * @param node the current node being examined during the search.
     * @param data the data to find.
     * @return the predecessor of the specified data; if not found, returns `null`.
     * @throws java.util.NoSuchElementException if the specified data is not found in the tree.
     */
    private AVLNode<T> findPredecessor(AVLNode<T> node, T data) {
        if (node == null) {
            throw new NoSuchElementException("Data is not found in the tree");
        }

        if (data.compareTo(node.getData()) < 0) {
            return findPredecessor(node.getLeft(), data);
        } else if (data.compareTo(node.getData()) > 0) {
            return findPredecessor(node.getRight(), data);
        } else {
            // Data found
            if (node.getLeft() != null) {
                // Case 1
                AVLNode<T> leftNode = node.getLeft();
                while (leftNode.getRight() != null) {
                    leftNode = leftNode.getRight();
                }
                return leftNode;
            } else {
                // Case 2
                AVLNode<T> lowestAncestor = null;
                AVLNode<T> curr = root;
                while (curr != null) {
                    if (data.compareTo(curr.getData()) < 0) {
                        curr = curr.getLeft();
                    } else if (data.compareTo(curr.getData()) > 0) {
                        lowestAncestor = curr;
                        curr = curr.getRight();
                    } else {
                        break;
                    }
                }
                return lowestAncestor;
            }
        }
    }


    /**
     * Returns the data in the deepest node. If there is more than one node
     * with the same deepest depth, return the rightmost (i.e. largest) node with
     * the deepest depth.
     *
     * Should be recursive.
     *
     * Must run in O(log n) for all cases.
     *
     * Example
     * Tree:
     *           2
     *        /    \
     *       0      3
     *        \
     *         1
     * Max Deepest Node:
     * 1 because it is the deepest node
     *
     * Example
     * Tree:
     *           2
     *        /    \
     *       0      4
     *        \    /
     *         1  3
     * Max Deepest Node:
     * 3 because it is the maximum deepest node (1 has the same depth but 3 > 1)
     *
     * @return the data in the maximum deepest node or null if the tree is empty
     */
    public T maxDeepestNode() {
        if (size == 0) {
            return null;
        }
        return deepestNodeHelper(root);
    }

    /**
     * recursive helper method for finding the deepest node in a subtree
     * @param node the root of the subtree
     * @return data of the deepest node
     */
    private T deepestNodeHelper(AVLNode<T> node) {
        if (node.getLeft() == null && node.getRight() == null) {
            return node.getData();
        } else {
            if (nodeHeight(node.getLeft()) > nodeHeight(node.getRight())) {
                return deepestNodeHelper(node.getLeft());
            } else {
                return deepestNodeHelper(node.getRight());
            }
        }
    }
    /**
     * helper method for height
     * @param node node to get height
     * @return node height
     */
    private int nodeHeight(AVLNode<T> node) {
        if (node == null) {
            return -1;
        }
        return node.getHeight();
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public AVLNode<T> getRoot() {
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
