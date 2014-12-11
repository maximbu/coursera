public class Facility {
	private float setupCost;
	private float totalCapacity;
	private float usedCapacity;
	private Point location;
	private boolean isEnbled;
	private int customersUse;
	private int id;

	public Facility(int id, float setup, float cap, double x, double y) {
		this.id = id;
		setupCost = setup;
		totalCapacity = cap;
		setEnbled(false);
		location = new Point(x, y);
		customersUse = 0;
	}

	public float getSetupCost() {
		return setupCost;
	}

	public float getCapacity() {
		return totalCapacity;
	}

	public float getRemainingCapacity() {
		return totalCapacity - usedCapacity;
	}

	public void useCapacity(float capacity) {
		usedCapacity += capacity;
		customersUse++;
	}

	public void freeCapacity(float capacity) {
		usedCapacity -= capacity;
		customersUse--;
	}

	public Point getLocation() {
		return location;
	}

	public boolean isEnbled() {
		return isEnbled;
	}

	public void setEnbled(boolean isEnbled) {
		this.isEnbled = isEnbled;
		if(!isEnbled)
		{
			usedCapacity = 0;
			customersUse = 0;
		}
	}

	public boolean isUsed() {
		return customersUse > 0;
	}

	public int getId() {
		return id;
	}

}
