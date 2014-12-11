
public class Customer implements Comparable<Customer>{
	private float demand;
	private Point location;
	private int assignedVehicle;
	private int id;
	
	public Customer(int id ,float need , double x ,double y)
	{
		this.id = id;
		demand = need;
		location = new Point(x, y);
		assignedVehicle = -1;
	}
	
	
	public float getDemand() {
		return demand;
	}

	public Point getLocation() {
		return location;
	}

	public int getAssignedVehicle() {
		return assignedVehicle;
	}
	
	public void setAssignedVehicle(int assignedVehicle) {
		this.assignedVehicle = assignedVehicle;
	}
	
	
	public int getId() {
		return id;
	}
	
	public double findSqrDistance(Point p) {
		return (location.x - p.x) * (location.x - p.x)
			 + (location.y - p.y) * (location.y - p.y);
	}


	@Override
	public int compareTo(Customer that) {
		return (int) (that.demand - demand);
	}
	
}
