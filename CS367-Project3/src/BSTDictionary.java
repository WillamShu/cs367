import java.util.Iterator;

public class BSTDictionary<K extends Comparable<K>> implements DictionaryADT<K> {
	private BSTnode<K> root; // the root node
	private int numItems; // the number of items in the dictionary

	// TO DO:
	//
	// Add a no-argument constructor
	public BSTDictionary() {
		root = null;
		numItems = 0;
	}
	//
	// Add your code to implement the Dictionary ADT operations using a binary
	// search tree.
	// You may use any code given in the on-line reading on BSTs.

	/**
	 * Inserts the given key into the Dictionary if the key is not already in
	 * the Dictionary. If the key is already in the Dictionary, a
	 * DuplicatException is thrown.
	 * 
	 * @param key
	 *            the key to insert into the Dictionary
	 * @throws DuplicateException
	 *             if the key is already in the Dictionary
	 * @throws IllegalArgumentException
	 *             if the key is null
	 */
	public void insert(K key) throws DuplicateException {
		root = insert(root, key);
	}

	private BSTnode<K> insert(BSTnode<K> n, K key) throws DuplicateException {
		// base case
		// If root is null, then add the key as the root and increase numItems
		if (n == null) {
			numItems++;
			return new BSTnode<K>(key, null, null);
		}

		// If the key user input is the key of the root, then throw a
		// DuplicateException
		if (n.getKey().equals(key)) {
			throw new DuplicateException();
		}

		// Recursion
		// if the key user input is smaller than the key of the root
		if (key.compareTo(n.getKey()) < 0) {
			// add key to the left subtree
			n.setLeft(insert(n.getLeft(), key));
			return n;
		}

		// if the key user input is bigger than the key of the root
		else {
			// add key to the right subtree
			n.setRight(insert(n.getRight(), key));
			return n;
		}
	}

	/**
	 * Deletes the given key from the Dictionary. If the key is in the
	 * Dictionary, the key is deleted and true is returned. If the key is not in
	 * the Dictionary, the Dictionary is unchanged and false is returned.
	 * 
	 * @param key
	 *            the key to delete from the Dictionary
	 * @return true if the deletion is successful (i.e., the key was in the
	 *         Dictionary and has been removed), false otherwise (i.e., the key
	 *         was not in the Dictionary)
	 */
	public boolean delete(K key) {
		// If the key is in the tree, then delete it
		if (lookup(key) != null) {
			root = delete(root, key);
			numItems--;
			return true;
		} else {
			return false;
		}
	}

	private BSTnode<K> delete(BSTnode<K> n, K key) {
		// Base case
		// If the root is null, return null
		if (n == null) {
			return null;
		}

		// Recursion
		if (key.equals(n.getKey())) {
			// n is the node to be remove.
			if (n.getLeft() == null && n.getRight() == null) {
				// If there is no children, return null
				return null;
			}

			// If only left child is null, return right child
			if (n.getLeft() == null) {
				return n.getRight();
			}

			// If only right child is null, return left child
			if (n.getRight() == null) {
				return n.getLeft();
			}

			// if we get here, then n has 2 children
			K smallVal = smallest(n.getRight());
			n.setKey(smallVal);
			n.setRight(delete(n.getRight(), smallVal));
			return n;
		}

		// If the key is smaller than the current root, go left
		else if (key.compareTo(n.getKey()) < 0) {
			n.setLeft(delete(n.getLeft(), key));
			return n;
		}

		// If the key is bigger than the current root, go right
		else {
			n.setRight(delete(n.getRight(), key));
			return n;
		}
	}

	private K smallest(BSTnode<K> root) {
		// If root is null, then return null
		if (root == null) {
			return null;
		}
		// If left child is null, means root is the smallest, then return root
		if (root.getLeft() == null) {
			return root.getKey();
		}
		// Otherwise, keep using recursion to find the smallest
		else {
			return smallest(root.getLeft());
		}
	}

	/**
	 * Searches for the given key in the Dictionary and returns the key stored
	 * in the Dictionary. If the key is not in the Dictionary, null is returned.
	 * 
	 * @param key
	 *            the key to search for
	 * @return the key from the Dictionary, if the key is in the Dictionary;
	 *         null if the key is not in the Dictionary
	 */
	public K lookup(K key) {
		return lookup(root, key);
	}

	private K lookup(BSTnode<K> n, K key) {
		// Base cases
		// If the key is not found, return null
		if (n == null) {
			return null;
		}

		// If the key of this node is the key that user input, return the key
		if (n.getKey().equals(key)) {
			return n.getKey();
		}

		// Recursion
		// if the key that user input is smaller than the key of this node
		if (key.compareTo(n.getKey()) < 0) {
			// Pass the parameter of the node to the left subtree
			return lookup(n.getLeft(), key);
		}

		// if the key that user input is bigger than the key of this node
		else {
			// Pass the parameter of the node to the right subtree
			return lookup(n.getRight(), key);
		}

	}

	/**
	 * Returns true if and only if the Dictionary is empty.
	 * 
	 * @return true if the Dictionary is empty, false otherwise
	 */
	public boolean isEmpty() {
		return numItems == 0;
	}

	/**
	 * Returns the number of keys in the Dictionary.
	 * 
	 * @return the number of keys in the Dictionary
	 */
	public int size() {
		return numItems;
	}

	/**
	 * Returns the total path length. The total path length is the sum of the
	 * lengths of the paths to each (key, value) pair.
	 * 
	 * @return the total path length
	 */
	public int totalPathLength() {
		return 0; // replace this stub with your code
	}

	/**
	 * Returns an iterator over the Dictionary that iterates over the keys in
	 * the Dictionary in order from smallest to largest.
	 * 
	 * @return an iterator over the keys in the Dictionary in order
	 */
	public Iterator<K> iterator() {
		return new BSTDictionaryIterator<K>(root);
	}
}
