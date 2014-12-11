public class Vehicle {

	private float totalCapacity;
	private float usedCapacity;
	private int customersUse;
	private int id;

	public Vehicle(int id, int capacity) {
		this.id = id;
		totalCapacity = capacity;
		customersUse = 0;
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

	public boolean isUsed() {
		return customersUse > 0;
	}
	
	public int customersUse() {
		return customersUse;
	}

	public int getId() {
		return id;
	}

}
