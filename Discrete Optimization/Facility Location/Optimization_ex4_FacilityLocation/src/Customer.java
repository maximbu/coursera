
public class Customer {
	private float demand;
	private Point location;
	private int assignedFacility;
	private double distanceToFacility;
	private int id;
	
	public Customer(int id ,float need , double x ,double y)
	{
		this.id = id;
		demand = need;
		location = new Point(x, y);
		assignedFacility = -1;
		distanceToFacility = -1;
	}
	
	
	public float getDemand() {
		return demand;
	}

	public Point getLocation() {
		return location;
	}

	public int getAssignedFacility() {
		return assignedFacility;
	}
	
	public void setAssignedFacility(int assignedFacility , Point facilityLocation) {
		this.assignedFacility = assignedFacility;
		if(facilityLocation != null)
			distanceToFacility = Math.sqrt(findSqrDistance(facilityLocation));
		else
			distanceToFacility = -1;
	}
	
	public double getDistanceToFacility() {
		return distanceToFacility;
	}
	
	public int getId() {
		return id;
	}
	
	public double findSqrDistance(Point p) {
		return (location.x - p.x) * (location.x - p.x)
			 + (location.y - p.y) * (location.y - p.y);
	}
}
