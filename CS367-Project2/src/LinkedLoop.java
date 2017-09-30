import java.util.*;

public class LinkedLoop<E> implements Loop<E> {

	private int numItems; // the number of items in this list
	private DblListnode<E> currNode;

	public LinkedLoop() {
		numItems = 0;
		currNode = new DblListnode<E>(null);
	}

	/**
	 * Returns the current item. If the Loop is empty, an
	 * <tt>EmptyLoopException</tt> is thrown.
	 * 
	 * @return the current item
	 * @throws EmptyLoopException
	 *             if the Loop is empty
	 */
	public E getCurrent() throws EmptyLoopException {
		if (numItems == 0)
			throw new EmptyLoopException("no messages");
		else {
			return currNode.getData();
		}

	}

	/**
	 * Adds the given <tt>item</tt> immediately <em>before</em> the current
	 * item. After the new item has been added, the new item becomes the current
	 * item.
	 * 
	 * @param item
	 *            the item to add
	 */
	public void insert(E item) {
		if (numItems == 0) {
			DblListnode<E> newNode = new DblListnode<E>(item);
			newNode.setNext(newNode);
			newNode.setPrev(newNode);
			currNode = newNode;	
			numItems++;
		} else {
			DblListnode<E> newNode = new DblListnode<E>(currNode.getPrev(), item, currNode);
			currNode.getPrev().setNext(newNode);
			currNode.setPrev(newNode);
			currNode = newNode;
			numItems++;
		}

	}

	/**
	 * Removes and returns the current item. The item immediately <em>after</em>
	 * the removed item then becomes the current item. If the Loop is empty
	 * initially, an <tt>EmptyLoopException</tt> is thrown.
	 * 
	 * @return the removed item
	 * @throws EmptyLoopException
	 *             if the Loop is empty
	 */
	public E removeCurrent() throws EmptyLoopException {
		E removedItem;
		if (numItems == 0)
			throw new EmptyLoopException("no messages");
		else {
			removedItem = currNode.getData();
			currNode.getPrev().setNext(currNode.getNext());
			currNode.getNext().setPrev(currNode.getPrev());
			currNode = currNode.getNext();
			numItems--;
		}
		return removedItem;
	}

	/**
	 * Advances the current item forward one item resulting in the item that is
	 * immediately <em>after</em> the current item becoming the current item. If
	 * the Loop is empty initially, an <tt>EmptyLoopException</tt> is thrown.
	 *
	 * @throws EmptyLoopException
	 *             if the Loop is empty
	 */
	public void forward() throws EmptyLoopException {
		if (numItems == 0)
			throw new EmptyLoopException("no messages");
		else {
			currNode = currNode.getNext();
		}
	}

	/**
	 * Moves the current item backward one item resulting in the item that is
	 * immediately <em>before</em> the current item becoming the current item.
	 * If the Loop is empty initially, an <tt>EmptyLoopException</tt> is thrown.
	 *
	 * @throws EmptyLoopException
	 *             if the Loop is empty
	 */
	public void backward() {
		if (numItems == 0)
			throw new EmptyLoopException("no messages");
		else {
			currNode = currNode.getPrev();
		}
	}

	/**
	 * Returns the number of items in this Loop.
	 * 
	 * @return the number of items in this Loop
	 */
	public int size() {
		return numItems;
	}

	/**
	 * Returns true if this Loop is empty and false otherwise.
	 * 
	 * @return true if this Loop is empty; false otherwise.
	 **/
	public boolean isEmpty() {
		if (numItems == 0)
			return true;
		else
			return false;
	}

	/**
	 * Returns an iterator for this Loop.
	 * 
	 * @return an iterator for this Loop
	 */
	public Iterator<E> iterator() {
		LinkedLoopIterator<E> itr = new LinkedLoopIterator<E>(currNode);
		return itr;
	}
}