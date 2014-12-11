
public class InputData {

	private int numOfFacilities;
	private int numOfCustomers;
	private Facility[] facilities; 
	private Customer[] customers;
	
	public InputData(int N , int M)
	{
		numOfFacilities = N;
		numOfCustomers = M;
		facilities = new Facility[numOfFacilities];
		customers = new Customer[numOfCustomers];
	}
	
	public int getNumOfFacilities() {
		return numOfFacilities;
	}

	public int getNumOfCustomers() {
		return numOfCustomers;
	}

	public Facility[] getFacilities() {
		return facilities;
	}
	
	public void addFacility(Facility facility) {
		facilities[facility.getId()] = facility;
	}
	
	public Facility getFacility(int id) {
		return facilities[id];
	}
	
	public Customer[] getCustomers() {
		return customers;
	}
	
	public void addCustomer(Customer customer) {
		customers[customer.getId()] = customer;
	}
	
	public Customer getCustomer(int id) {
		return customers[id];
	}
	
	
}
