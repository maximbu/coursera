
public class Model {

	private int numOfVehiles;
	private int numOfCustomers;
	private Vehicle[] vehiles; 
	private Customer[] customers;
	private String fileName ;
	
	public Model(int N , int V , int C, String file)
	{
		numOfCustomers = N;
		numOfVehiles = V;
		vehiles = new Vehicle[numOfVehiles];
		for(int i=0;i<numOfVehiles;i++)
		{
			addVehicle(new Vehicle(i, C));
		}
		customers = new Customer[numOfCustomers];
		fileName = file;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public int getNumOfVehicles() {
		return numOfVehiles;
	}

	public int getNumOfCustomers() {
		return numOfCustomers;
	}

	public Vehicle[] getVehicles() {
		return vehiles;
	}
	
	public void addVehicle(Vehicle vehile) {
		vehiles[vehile.getId()] = vehile;
	}
	
	public Vehicle getVehicle(int id) {
		return vehiles[id];
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
	
	public Customer getBase() {
		return customers[0];
	}
	
	
}
