import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.Scanner;

public class MessageLoopEditor {

	// messages is a class data member; this allows all helper methods in this
	// class to be able to access it directly.
	private static ArrayListLoop<String> messages = new ArrayListLoop<String>();
	// NOTE: change the right-hand side of the assignment to:
	// new LinkedLoop<String>();
	// after you have created and tested your LinkedLoop class

	public static void main(String[] args) throws FileNotFoundException {
		Scanner in = new Scanner(System.in); // set up to read from console
		boolean useFile = false;

		// if there is a command line argument, it is the name of the file from
		// which to read input
		if (args.length == 1) {
			File file = new File(args[0]);
			if (!file.exists() || !file.canRead()) {
				System.err.println("problem with input file!");
				System.exit(1);
			}

			in = new Scanner(file);
			useFile = true;
		}

		if (args.length > 1) { // error if there is > 1 command line argument
			System.out.println("invalid command-line arguments");
			System.exit(0);
		}

		boolean again = true;

		while (again) {
			System.out.print("enter command (? for help): ");
			String input = in.nextLine();
			if (useFile) {
				System.out.println(input); // echo input if using file
			}

			// only do something if the user enters at least one character
			if (input.length() > 0) {
				char choice = input.charAt(0); // strip off option character
				String remainder = ""; // used to hold the remainder of input
				if (input.length() > 1) {
					// trim off any leading or trailing spaces
					remainder = input.substring(1).trim();
				}

				switch (choice) {
				case '?':
					System.out.println("a (add after)  b (add before)  d (display)");
					System.out.println("f (find)       r (remove)      u (update)");
					System.out.println("> (next)       < (previous)    j (jump)");
					System.out.println("s (save)       l (load)        q (quit)");
					break;

				case 'a':
					if (remainder.length() == 0) {
						System.out.println("invalid command");
					} else {
						if (!messages.isEmpty()) {
							messages.forward();
						}

						messages.insert(remainder);
						displayCurrContext();
					}
					break;

				case 'b':
					if (remainder.length() == 0) {
						System.out.println("invalid command");
					} else {
						messages.insert(remainder);
						displayCurrContext();
					}
					break;

				case 'd':
					display();
					break;

				case 'f':
					if (remainder.length() == 0) {
						System.out.println("invalid command");
					} else {
						// done != true
						String curr;
						if (messages.isEmpty()) {
							System.out.println("no messages");
						} else {
							Iterator<String> itr = messages.iterator();
							boolean doContain = false;
							boolean findOnce = false;
							// find once, if found stop searching
							while (itr.hasNext() && findOnce != true) {
								curr = itr.next();
								messages.forward();
								if (curr.contains(remainder)) {
									messages.backward();
									displayCurrContext();
									doContain = true;
									findOnce = true;
									messages.forward();
								}
							}
							if (!doContain) {
								messages.backward();
								System.out.println("not found");
							}
						}
					}
					break;

				case 'r':
					if (messages.isEmpty()) {
						System.out.println("no messages");
					} else {
						messages.removeCurrent();
						if (messages.isEmpty()) {
							System.out.println("no messages");
						} else {
							displayCurrContext();
						}
					}
					break;

				case 'u':
					if (remainder.length() == 0) {
						System.out.println("invalid command");
					} else {
						if (messages.isEmpty()) {
							System.out.println("no messages");
						} else {
							messages.removeCurrent();
							messages.insert(remainder);
							displayCurrContext();
						}
					}
					break;

				case '>':
					if (messages.isEmpty()) {
						System.out.println("no messages");
					} else {
						messages.forward();
						displayCurrContext();
					}
					break;

				case '<':
					if (messages.isEmpty()) {
						System.out.println("no messages");
					} else {
						messages.backward();
						displayCurrContext();
					}
					break;

				case 'j':
					if (remainder.length() == 0) {
						System.out.println("invalid command");
					} else {
						try {
							// valueCheck checks if remainder is an integer
							// this is to see if messages is empty and user is
							// trying
							// to operate correctly the j function even though
							// there
							// is nothing in messages.
							int valueCheck = Integer.parseInt(remainder);
							if (messages.isEmpty() && (valueCheck > 0 || valueCheck <= 0)) {
								System.out.println("no messages");
							} else {
								int value = Integer.parseInt(remainder);
								if (value >= 0) {
									for (int i = 0; i < value; i++) {
										messages.forward();
									}
								} else {
									for (int i = 0; i > value; i--) {
										messages.backward();
									}
								}
								displayCurrContext();
							}
						} catch (NumberFormatException e) {
							System.out.println("invalid command");
						}
					}
					break;

				case 's':
					if (remainder.length() > 0) {
						save(remainder);
					} else {
						System.out.println("invalid command");
					}
					break;

				case 'l':
					if (remainder.length() > 0) {
						load(remainder);
					} else {
						System.out.println("invalid command");
					}
					break;

				case 'q':
					System.out.println("quit");
					again = false;
					break;

				default:
					System.out.println("invalid command");
				}
			}
		}
	}

	/**
	 * If the message loop is empty, display "no messages to save". Otherwise,
	 * save all the messages to a file named filename, one message per line
	 * starting with the current message (and proceeding forward through the
	 * loop). If filename already exists, display "warning: file already exists,
	 * will be overwritten" before saving the messages. If filename cannot be
	 * written to, display "unable to save".
	 * 
	 * @param filename
	 *            the name of the file to which to save the messages
	 */
	private static void save(String filename) {
		if (messages.size() == 0) {
			System.out.println("no messages to save");
			return;
		}

		File file = new File(filename);

		// warn about overwriting existing file
		if (file.exists()) {
			System.out.print("warning: file already exists, ");
			System.out.println("will be overwritten");
		}

		// give message if not able to write to file
		if (file.exists() && !file.canWrite()) {
			System.out.println("unable to save");
			return;
		}

		// print each message to the file
		try {
			PrintStream outFile = new PrintStream(file);
			for (String msg : messages)
				outFile.println(msg);
			outFile.close();
		} catch (FileNotFoundException e) {
			System.out.println("unable to save");
		}
	}

	/**
	 * If a file named filename does not exists or cannot be read from, display
	 * "unable to load". Otherwise, load the messages from filename in the order
	 * they are given before the current message. The current message is is not
	 * changed.
	 * 
	 * It is assumed that the input file contains one message per line, that
	 * there are no blank lines, and that the file is not empty (i.e., it has at
	 * least one line).
	 * 
	 * @param filename
	 *            the name of the file from which to load the messages
	 */
	private static void load(String filename) {
		File file = new File(filename);

		// check for existence and readability
		if (!file.exists() || !file.canRead()) {
			System.out.println("unable to load");
			return;
		}

		try {
			Scanner inFile = new Scanner(file);
			while (inFile.hasNext()) {
				// trim off any leading or trailing spaces before adding
				messages.insert(inFile.nextLine().trim());

				// since insert sets the current to the item just added,
				// move forward so the next item will get added after the one
				// that just got added
				messages.forward();
			}
			inFile.close();
		} catch (FileNotFoundException e) {
			System.out.println("unable to load");
		}
	}

	/**
	 * If the message loop is empty, display "no message". Otherwise, display
	 * all of the messages in the loop, starting with the current message, one
	 * message per line (going forward through the entire loop).
	 */
	private static void display() {
		if (messages.size() == 0)
			System.out.println("no messages");

		else {
			Iterator<String> iter = messages.iterator();
			while (iter.hasNext()) {
				System.out.println(iter.next());
			}
		}
	}

	private static void displayCurrContext() {
		if (messages.size() >= 3) {
			// prev
			messages.backward();
			System.out.println("     " + messages.getCurrent() + "     ");
			// original curr
			messages.forward();
			System.out.println("===[ " + messages.getCurrent() + " ]===");
			// next
			messages.forward();
			System.out.println("     " + messages.getCurrent() + "     ");
			messages.backward();
		} else if (messages.size() == 2) {
			System.out.println("===[ " + messages.getCurrent() + " ]===");
			messages.forward();
			System.out.println("     " + messages.getCurrent() + "     ");
			messages.backward();
		} else {
			System.out.println("===[ " + messages.getCurrent() + " ]===");
		}
	}
}