import java.util.*;

public class KeyWord implements Comparable<KeyWord>, Prioritizable {
	String word; // the key word
	int occurrence; // the occurence for this key word

	/**
	 * Create a new key word with word given by user and no occurrence. If the
	 * word given is null or empty, throw IllegalArgumentException.
	 * 
	 * @param word
	 * @throws IllegalArgumentException
	 */
	public KeyWord(String word) throws IllegalArgumentException {
		if (word == null || word.length() == 0)
			throw new IllegalArgumentException();
		else {
			this.word = word.toLowerCase();
			occurrence = 0;
		}
	}

	/**
	 * Compare this keyword with the keyword the user provide
	 * 
	 * 
	 * @param other
	 * @return 0 if two keywords are same, 1 if this keyword is bigger, -1 if
	 *         this keyword is smaller
	 */
	public int compareTo(KeyWord other) {
		return this.word.compareTo(other.getWord());
	}

	/**
	 * Compare two keyword if their words are equal
	 * 
	 * @param other
	 * @return true if two words are equal, otherwise false
	 * 
	 */
	public boolean equals(java.lang.Object other) {
		if (other instanceof KeyWord) {
			if (((KeyWord) other).getWord().equals(word)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Used to get the number of occurrences of this key word
	 * 
	 * @return occurrence of this key word
	 */
	public int getOccurrences() {
		return occurrence;
	}

	/**
	 * Returns the priority for this key word. The priority of a KeyWord is the
	 * number of occurrences it has.
	 * 
	 * @return the priority for this key word
	 */
	public int getPriority() {
		return occurrence;
	}

	/**
	 * Get the word for this key word
	 * 
	 * @return the word of this key word.
	 */
	public String getWord() {
		return this.word;
	}

	/**
	 * Increase the number of occurrence by one.
	 */
	public void increment() {
		occurrence++;
	}

}
