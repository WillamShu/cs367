import java.util.NoSuchElementException;

public class ArrayHeap<E extends Prioritizable> implements PriorityQueueADT<E> {

	// default number of items the heap can hold before expanding
	private static final int INIT_SIZE = 100;
	private E[] heapArray;
	private int currentSize = 0;
	// TO DO:
	//
	// Add a no-argument constructor that constructs a heap whose underlying
	// array has enough space to store INIT_SIZE items before needing to
	// expand.
	//
	// Add a 1-argument constructor that takes an integer parameter and
	// constructs a heap whose underlying array has enough space to store the
	// number of items given in the parameter before needing to expand. If
	// the parameter value is less 0, an IllegalArgumentException is thrown.
	//
	// Add your code to implement the PriorityQueue ADT operations using a
	// heap whose underlying data structure is an array.

	public ArrayHeap() {
		heapArray = (E[]) (new Prioritizable[INIT_SIZE]);
	}

	public ArrayHeap(int n) throws IllegalArgumentException {
		if (n < 0) {
			throw new IllegalArgumentException();
		}
		heapArray = (E[]) new Prioritizable[n];

	}

	public boolean isEmpty() {
		if (currentSize != 0) {
			return false;
		}
		return true;
	}

	public void insert(E item) {
		// If we are at the max capacity of the array, grow the array
		if (heapArray.length == currentSize + 1) {
			// Create a new array twice the size
			E[] newData = (E[]) new Prioritizable[heapArray.length * 2];
			// Copy the old array to the new bigger array
			System.arraycopy(heapArray, 0, newData, 0, heapArray.length);
			// Set the new array as the data reference
			heapArray = newData;
		}
		// Put the item in the next spot in the array/heap
		heapArray[++currentSize] = item;

		// Heapify by swaping the value up
		int child = currentSize;
		while (heapArray[child / 2] != null && heapArray[child / 2].getPriority() < heapArray[child].getPriority()) {
			// Swap the value up because the parent is less
			E temp = heapArray[child / 2];
			heapArray[child / 2] = heapArray[child];
			heapArray[child] = temp;

			// Do we need to swap again?
			child = child / 2;
		}
	}

	private void moveUp(int newPosition) {
		E temp = heapArray[newPosition];
		int parent = (newPosition - 1) / 2;
		while (newPosition > 0 && (heapArray[parent]).getPriority() < temp.getPriority())
			;
		{
			heapArray[newPosition] = heapArray[parent];
			newPosition = parent;
			parent = (newPosition - 1) / 2;
		}
		heapArray[newPosition] = temp;
	}

	public E removeMax() {
		// Make sure the heap has values
		if (currentSize == 0) {
			throw new NoSuchElementException();
		}

		// Save the root as the value to return
		E returnedMax = heapArray[1];

		// Set the last child as the root
		heapArray[1] = heapArray[currentSize];
		// Set the old last child as null
		heapArray[currentSize--] = null;

		// Heapify by swapping down
		int parent = 1;
		while (parent * 2 < heapArray.length && heapArray[parent * 2] != null && heapArray[parent * 2 + 1] != null
				&& (heapArray[parent * 2].getPriority() > heapArray[parent].getPriority()
						|| heapArray[parent * 2 + 1].getPriority() > heapArray[parent].getPriority())) {
			// Swap the parent with the child if the children are bigger
			E temp = heapArray[parent];
			// If both children are bigger, pick the biggest and swap
			if (heapArray[parent * 2].getPriority() > heapArray[parent].getPriority()
					&& heapArray[parent * 2 + 1].getPriority() > heapArray[parent].getPriority()) {
				if (heapArray[parent * 2].getPriority() > heapArray[parent * 2 + 1].getPriority()) {
					// The left is bigger, swap with the parent
					heapArray[parent] = heapArray[parent * 2];
					heapArray[parent * 2] = temp;
					parent *= 2;
				} else {
					// The right is bigger, swap with the parent
					heapArray[parent] = heapArray[parent * 2 + 1];
					heapArray[parent * 2 + 1] = temp;
					parent = parent * 2 + 1;
				}

			} else if (heapArray[parent * 2].getPriority() > heapArray[parent].getPriority()) {
				// Only the left child is bigger swap with the parent
				heapArray[parent] = heapArray[parent * 2];
				heapArray[parent * 2] = temp;
				parent *= 2;
			} else {
				// Only the right child is bigger, swap with the parent
				heapArray[parent] = heapArray[parent * 2 + 1];
				heapArray[parent * 2 + 1] = temp;
				parent = parent * 2 + 1;
			}
		}

		// Return the original root
		return returnedMax;
	}

	public E getMax() {
		// Make sure the heap has values else return the root
		if (currentSize == 0) {
			throw new NoSuchElementException();
		}
		return heapArray[1];
	}

	public int size() {
		return currentSize; // replace this stub with your code
	}

}