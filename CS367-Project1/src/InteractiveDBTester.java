import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class InteractiveDBTester {
	static List<String> list;
	static List<Customer> customerDatabase;
	static List<String> uniqueProduct;

public static void main(String[] args) throws FileNotFoundException {

		// *** Add code for steps 1 - 3 of the main method ***
		if (args.length==1) {
		//Put in desired file to load.
		File tempFile = new File(args[0]);
		//checks for if input file exists and can be read.
		boolean exists = tempFile.exists();
		boolean readable = tempFile.canRead();
		if ((exists && readable) == false) {
			System.out.println("Error: Cannot access input file");
		}
		Scanner s = new Scanner(new File(args[0]));
		list = new ArrayList<String>();
		// customerNameList = new ArrayList<String>();
		// wishList = new ArrayList<List<String>>();
		String[] parts;

		// add all the things into one list
		while (s.hasNextLine()) {
			list.add(s.nextLine());
		}

		Iterator<String> itr = list.iterator();
		CustomerDatabase customerDatabase = new CustomerDatabase();
		uniqueProduct = new ArrayList<String>();
		while (itr.hasNext()) {
			parts = list.remove(0).split(",");
			customerDatabase.addCustomer(parts[0]);
			for (int i = 1; i < parts.length; i++) {
				customerDatabase.addProduct(parts[0], parts[i]);
				uniqueProduct.add(parts[i]);

			}
		}

		s.close();

		
		Scanner stdin = new Scanner(System.in); // for reading console input
		printOptions();
		boolean done = false;
		while (!done) {
			System.out.print("Enter option ( dfhisqr ): ");
			String input = stdin.nextLine();
			input = input.toLowerCase(); // convert input to lower case

			// only do something if the user enters at least one character

				char choice = input.charAt(0); // strip off option character
				String remainder = ""; // used to hold the remainder of input
				if (input.length() > 1) {
					// trim off any leading or trailing spaces
					remainder = input.substring(1).trim();
				}

				switch (choice) {

				case 'd':
					// *** Add code to implement this option ***
					if (!customerDatabase.containsProduct(remainder)) {
						System.out.println("product not found");
					} else {
						customerDatabase.removeProduct(remainder);
						System.out.println("product discontinued");
					}
					break;

				case 'f':
					// *** Add code to implement this option ***

					if (!customerDatabase.containsCustomer(remainder)) {
						System.out.println("customer not found");
					} else {
						Iterator<Customer> itr1 = customerDatabase.iterator();
						while (itr1.hasNext()) {
							Customer curr = itr1.next();
							if (curr.getUsername().equals(remainder)) {
								System.out.print(curr.getUsername() + ":");
								Iterator<String> itr2 = curr.getWishlist().iterator();
								while (itr2.hasNext()) {
									System.out.print(itr2.next());
									if (itr2.hasNext()) {
										System.out.print(",");
									}

								}
							}
						}
						System.out.println();
					}
					break;

				case 'h':
					printOptions();
					break;

				case 'i':
					// *** Add code to implement this option ***
					// part 1 of command i.
					List<String> uniqueItems = new ArrayList<String>();
					Iterator<Customer> itr1 = customerDatabase.iterator();
					boolean doContain = false;
					while (itr1.hasNext()) {
						Customer curr1 = itr1.next();
						Iterator<String> itr3 = curr1.getWishlist().iterator();
						while (itr3.hasNext()) {
							String curr3 = itr3.next();
							Iterator<String> itr4 = uniqueItems.iterator();
							while (itr4.hasNext()) {
								String curr2 = itr4.next();
								if (curr2.equals(curr3)) {
									doContain = true;
								}
							}
							if (doContain != true)
								uniqueItems.add(curr3);
							doContain = false;
						}
					}
					System.out.println(
							"Customers: " + customerDatabase.size() + ", " + "Products: " + uniqueItems.size());

					// part 2
					int wishListSize;
					int sum = 0;
					int average;
					List<Integer> numOfProducts = new ArrayList<Integer>();
					Iterator<Customer> itr4 = customerDatabase.iterator();
					while (itr4.hasNext()) {
						Customer curr2 = itr4.next();
						List<String> itr5 = curr2.getWishlist();
						wishListSize = itr5.size();
						numOfProducts.add(wishListSize);
					}
					// average
					Iterator<Integer> itr6 = numOfProducts.iterator();
					while (itr6.hasNext()) {
						Integer currSum = itr6.next();
						sum = sum + currSum;
					}
					average = (int) Math.round(((double) sum / customerDatabase.size()));
					int max = 0;
					int min = 0;
					// max
					int currNumIndex;
					int currNumIndex2;
					Iterator<Integer> itr20 = numOfProducts.iterator();
					while (itr20.hasNext()) {
						Integer currNum = itr20.next();
						max = currNum;
						currNumIndex = numOfProducts.indexOf(currNum);
						Iterator<Integer> itr21 = numOfProducts.iterator();
						while (itr21.hasNext()) {
							Integer currNum2 = itr21.next();
							currNumIndex2 = numOfProducts.indexOf(currNum2);
							if (max <= currNum2) {
								max = numOfProducts.get(currNumIndex2);
							}
						}
					}
					// min
					int currNumIndex3;
					int currNumIndex4;
					Iterator<Integer> itr22 = numOfProducts.iterator();
					while (itr22.hasNext()) {
						Integer currNum3 = itr22.next();
						min = currNum3;
						currNumIndex3 = numOfProducts.indexOf(currNum3);
						Iterator<Integer> itr23 = numOfProducts.iterator();
						while (itr23.hasNext()) {
							Integer currNum4 = itr23.next();
							currNumIndex4 = numOfProducts.indexOf(currNum4);
							if (min >= currNum4) {
								min = numOfProducts.get(currNumIndex4);
							}
						}
					}

					System.out.println("# of products/customer: " + "most " + max + ", " + "least " + min + ", "
							+ "average " + average);

					// part 3 of command i
					Iterator<String> itr5 = uniqueItems.iterator();
					List<Integer> numItemsList = new ArrayList<Integer>();
					;
					while (itr5.hasNext()) {
						String curr5 = itr5.next();
						Integer numItems = customerDatabase.getCustomers(curr5).size();
						numItemsList.add(numItems);
					}


					// average2
					int average2 = sum / uniqueItems.size();

					int max1 = 0;
					int min1 = 0;
					// max1
					int currNumIndex5;
					int currNumIndex6;
					Iterator<Integer> itr24 = numItemsList.iterator();
					while (itr24.hasNext()) {
						Integer currNum = itr24.next();
						max1 = currNum;
						currNumIndex5 = numItemsList.indexOf(currNum);
						Iterator<Integer> itr25 = numItemsList.iterator();
						while (itr25.hasNext()) {
							Integer currNum2 = itr25.next();
							currNumIndex6 = numItemsList.indexOf(currNum2);
							if (max1 <= currNum2) {
								max1 = numItemsList.get(currNumIndex6);
							}
						}
					}
					// min1
					int currNumIndex7;
					int currNumIndex8;
					Iterator<Integer> itr26 = numItemsList.iterator();
					while (itr26.hasNext()) {
						Integer currNum3 = itr26.next();
						min1 = currNum3;
						currNumIndex7 = numItemsList.indexOf(currNum3);
						Iterator<Integer> itr27 = numItemsList.iterator();
						while (itr27.hasNext()) {
							Integer currNum4 = itr27.next();
							currNumIndex8 = numItemsList.indexOf(currNum4);
							if (min1 >= currNum4) {
								min1 = numItemsList.get(currNumIndex8);
							}
						}
					}

					System.out.println(
							"# of customers/product: most " + max1 + ", least " + min1 + ", average " + average2);

					// part 4 of command i
					List<String> popularItemsList = new ArrayList<String>();
					while (numItemsList.indexOf(max1) >= 0) {
						String name = uniqueItems.remove(numItemsList.indexOf(max1));
						numItemsList.remove(numItemsList.indexOf(max1));
						popularItemsList.add(name);
					}

					Iterator<String> itr11 = popularItemsList.iterator();
					String curr = itr11.next();
					System.out.print("Most popular product: ");
					System.out.print(curr);
					while (itr11.hasNext()) {
						System.out.print("," + itr11.next());
					}
					System.out.println(" [" + max1 + "]");


					break;

				case 's':
					// *** Add code to implement this option ***
					if (!customerDatabase.containsProduct(remainder)) {
						System.out.println("product not found");
					} else {
						List<Customer> customerList = new ArrayList<Customer>();
						customerList = customerDatabase.getCustomers(remainder);
						Iterator<Customer> itr2 = customerList.iterator();
						int count2 = 0;

						while (itr2.hasNext()) {
							Customer curr1 = itr2.next();
							if (count2 < 1) {
								System.out.print(remainder + ":");
								count2++;
							}
							System.out.print(curr1.getUsername());

							if (itr2.hasNext()) {
								System.out.print(",");
							}
						}
						System.out.println();
					}
					break;

				case 'q':
					done = true;
					System.out.println("quit");
					break;

				case 'r':
					// *** Add code to implement this option ***
					if (!customerDatabase.containsCustomer(remainder)) {
						System.out.println("customer not found");
					} else {
						customerDatabase.removeCustomer(remainder);
						System.out.println("customer removed");
					}
					break;

				default: // ignore any unknown commands
					break;
				}

			} 
		stdin.close();
		
		}
		else {
			System.out.println("Please provide input file as command-line argument");

		}


	}

	/**
	 * Prints the list of command options along with a short description of one.
	 * This method should not be modified.
	 */
	private static void printOptions() {
		System.out.println("d <product> - discontinue the given <product>");
		System.out.println("f <customer> - find the given <customer>");
		System.out.println("h - display this help menu");
		System.out.println("i - display information about this customer database");
		System.out.println("s <product> - search for the given <product>");
		System.out.println("q - quit");
		System.out.println("r <customer> - remove the given <customer>");
	}
}