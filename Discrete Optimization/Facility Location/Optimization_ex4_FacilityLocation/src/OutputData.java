
public class OutputData {
	private double value;
	private int[] assignedFacilities;
    
    public OutputData(double objectiveValue , int [] customers)
    {
    	value = objectiveValue;
    	assignedFacilities = customers;
    }

	public int[] getAssignedFacilities() {
		return assignedFacilities;
	}
	
	public int getAssignedFacility(int i) {
		return assignedFacilities[i];
	}
	
	public int getNumOfAssignedFacilities() {
		return assignedFacilities.length;
	}

	public double getValue() {
		return value;
	}
	
	public OutputData clone()
	{
		return new OutputData(value, assignedFacilities.clone());
	}

}
