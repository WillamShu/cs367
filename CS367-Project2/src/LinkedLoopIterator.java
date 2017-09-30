import java.util.*;

public class LinkedLoopIterator<E> implements Iterator<E> {
	DblListnode<E> tail;
	DblListnode<E> currNode;

	public LinkedLoopIterator(DblListnode<E> currNodeProvided) {
		currNode = currNodeProvided;
		tail = currNodeProvided.getPrev();
	}

	@Override
	public boolean hasNext() throws NoSuchElementException{
		if (currNode == null) throw new NoSuchElementException();
		else if (currNode == tail) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public E next() throws NoSuchElementException{
		if (currNode.getNext()==null) throw new NoSuchElementException();
		else{
			E data = currNode.getData();
			currNode = currNode.getNext();
			return data;
		}
	}

	public void remove() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}
}
