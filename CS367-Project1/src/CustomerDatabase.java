import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CustomerDatabase {
	// Constructs an empty customer database.

	List<Customer> customerDatabase;

	public CustomerDatabase() {
		customerDatabase = new ArrayList<Customer>();
	}

	// Add a customer with the given username c to the end of the database.
	// If a customer with username c is already in the database, just return.
	void addCustomer(String c) {
		if (!containsCustomer(c))
			customerDatabase.add(new Customer(c));
	}

	// Add the given product p to the wish list for customer c in the database.
	// If customer c is not in the database throw a
	// java.lang.IllegalArgumentException.
	// If p is already in the wish list for customer c, just return.
	void addProduct(String c, String p) throws IllegalArgumentException {
		Iterator<Customer> itr = customerDatabase.iterator();
		boolean doContain = false;
		while (itr.hasNext()) {
			Customer curr = itr.next();
			if (curr.getUsername().equals(c))
				curr.getWishlist().add(p);
			doContain = true;
		}
		if (!doContain)
			throw new IllegalArgumentException();
	}

	// Return true iff customer c is in the database.
	boolean containsCustomer(String c) {
		boolean doContain = false;
		Iterator<Customer> itr = customerDatabase.iterator();
		if (customerDatabase.size() > 0) {
			while (itr.hasNext()) {
				if (itr.next().getUsername().equals(c)) {
					doContain = true;
				}
			}
		}
		return doContain;
	}

	// Return true iff product p appears in at least one customer's wish list in
	// the database.
	boolean containsProduct(String p) {
		Iterator<Customer> itr = customerDatabase.iterator();
		if (customerDatabase.size() > 0) {
			while (itr.hasNext()) {
				Iterator<String> itr2 = itr.next().getWishlist().iterator();
				if (itr2.next().equals(p)) {
					return true;
				}
			}
		}
		return false;
	}

	// Returns true iff product p is in the wish list for customer c.
	// If customer c is not in the database, return false.
	boolean hasProduct(String c, String p) {
		if (!containsCustomer(c)) {
			return false;
		}
		Iterator<Customer> itr = customerDatabase.iterator();
		while (itr.hasNext()) {
			Customer curr = itr.next();
			if (curr.getUsername().equals(c)) {
				Iterator<String> itr2 = curr.getWishlist().iterator();
				while (itr2.hasNext()) {
					String curr2 = itr2.next();
					if (curr2.equals(p)) {
						return true;
					}
				}
			}

		}
		return false;

	}

	// Return the list of customers who have product p in their wish list.
	// If product p is not in the database, return null.
	List<Customer> getCustomers(String p) {
		List<Customer> haveProduct = new ArrayList<Customer>();
		Iterator<Customer> itr = customerDatabase.iterator();
		while (itr.hasNext()) {
			Customer curr = itr.next();
			if (hasProduct(curr.getUsername(), p)) {
				haveProduct.add(curr);
			}
		}
		return haveProduct;
	}

	// Return the wish list for the customer c.
	// If a customer c is not in the database, return null.
	List<String> getProducts(String c) {

		Iterator<Customer> itr = customerDatabase.iterator();
		while (itr.hasNext()) {
			Customer curr = itr.next();
			if (curr.getUsername().equals(c)) {
				return curr.getWishlist();
			}
		}
		return null;
	}

	// Return an Iterator over the Customer objects in the database.
	// The customers should be returned in the order they were added to the
	// database
	// (resulting from the order in which they are in the text file).
	Iterator<Customer> iterator() {
		Iterator<Customer> itr = customerDatabase.iterator();
		return itr;
	}

	// Remove customer c from the database.
	// If customer c is not in the database, return false; otherwise
	// (i.e., the removal is successful) return true.
	boolean removeCustomer(String c) {
		Iterator<Customer> itr = customerDatabase.iterator();
		while (itr.hasNext()) {
			Customer curr = itr.next();
			if (curr.getUsername().equals(c)) {
				itr.remove();
				return true;
			}
		}
		return false;
	}

	// Remove product p from the database, i.e., remove product p from every
	// wish list in which it appears.
	// If product p is not in the database, return false; otherwise
	// (i.e., the removal is successful) return true.
	boolean removeProduct(String p) {
		Iterator<Customer> itr = customerDatabase.iterator();
		boolean doContain = false;
		while (itr.hasNext()) {
			Customer curr = itr.next();
			Iterator<String> itr2 = curr.getWishlist().iterator();
			while (itr2.hasNext()) {
				if (itr2.next().equals(p)) {
					itr2.remove();
					doContain=true;
				}
			}
		}
		return doContain;
	}

	// Return the number of customers in this database.
	int size() {
		return customerDatabase.size();

	}
}
