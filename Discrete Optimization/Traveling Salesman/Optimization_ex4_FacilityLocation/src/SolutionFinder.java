public class SolutionFinder {

	private ModelBL bl;
	private SolverConfig solverConfig;

	public SolutionFinder(ModelBL inputData, SolverConfig solverConfig) {
		this.bl = inputData;
		this.solverConfig = solverConfig;
	}

	public OutputData findSolution() {

		int[] assigments = getAssignedFacilitiesCount(getClosestFacilities());
		OutputData bestOut = findOptimalAssigmentOfCustomers(assigments);

		while (solverConfig.getTenPtsValue() <= bestOut.getValue()) {
			
			System.out.println("Best value is : " + bestOut.getValue());
			System.out.println("Curent value is : " + bl.getObjectiveVal());
			
			for (int i = 0; i < bl.getNumOfFacilities(); i++) {
				boolean enabled = bl.getFacility(i).isEnbled();
				bl.setFacilityEnabled(i, !enabled);
				OutputData data = findOptimalAssigmentOfCustomers(getAssignedFacilitiesCount(bestOut.getAssignedFacilities()));
				bl.setFacilityEnabled(i, enabled);
				if (data.getValue() < bestOut.getValue()) {
					bestOut = data.clone();
				}
			}
			
			randomizeCustomerAssigments(5);
			
			bl.closeUnusedFacilities();
			
			if (bl.getObjectiveVal() < bestOut.getValue()) {
				bestOut = bl.getOutputData();
			}
		}
		return bestOut;
	}

	private void randomizeCustomerAssigments(int num) {
		for (int i = 0; i < num; i++) {
			int customerId = getRandomIndex(bl.getNumOfCustomers());
			int facilityId = getRandomIndex(bl.getNumOfFacilities());
			if(!bl.tryAssignCustomerToFacility(customerId, facilityId, false))
			{
				bl.tryAssignToClosesetPossibleFacility(customerId, true);
			}
		}
	}
	
	private static int getRandomIndex(int n) {
		return (int) (Math.random() * (n));
	}
	
	
	private int[] getClosestFacilities()
	{
		int[] facilities = new int[bl.getNumOfCustomers()];
		for (int i = 0; i < bl.getNumOfCustomers(); i++) {
			facilities[i] = bl.getClosestFacility(i, true);
		}
		
		return facilities;
	}
	
	
	private int[] getAssignedFacilitiesCount(int[] assigments)
	{
		int[] facilities = new int[bl.getNumOfFacilities()];
		for (int i = 0; i < bl.getNumOfCustomers(); i++) {
			facilities[assigments[i]]++;
		}
		
		return facilities;
	}

	private OutputData findOptimalAssigmentOfCustomers(int[] assigments) {
		double oldValue = 0;

		while (true) {
			for (int i = 0; i < bl.getNumOfCustomers(); i++) {
				tryAssignToCheapestPossibleFacility(i, true,assigments);
			}

			double value = bl.getObjectiveVal();
			if (Math.abs(value - oldValue) < 1) {
				break;
			}

			// System.out.println("Value is : " + value);

			oldValue = value;

		}

		return bl.getOutputData();
	}
	
	public boolean tryAssignToCheapestPossibleFacility(int customerId,boolean enforceEnable,int[] assigments)
	{
		int bestFacilityId = -1;
		double minCost = Double.MAX_VALUE;
		Customer customer = bl.getCustomer(customerId);
		bl.unassignCustomerFromFacility(customerId,true);
		
		
		for(int i=0;i<bl.getNumOfFacilities();i++)
		{
			Facility facility = bl.getFacility(i);
			if(!enforceEnable && !facility.isEnbled())
			{
				continue;
			}
			if(facility.getRemainingCapacity() < customer.getDemand())
			{
				continue;
			}
			
			double openingPrice = 0;
			if(!facility.isEnbled())
			{
				openingPrice = facility.getSetupCost()/assigments[facility.getId()];
			}
			
			double cost = openingPrice + Math.sqrt(customer.findSqrDistance(facility.getLocation()));
			if( cost <minCost)
			{
				bestFacilityId = i;
				minCost = cost;
			}
		}
				
		return bl.tryAssignCustomerToFacility(customer.getId(),bestFacilityId , enforceEnable);

	}

	public int getBestCostEffectiveFacility() {
		double bestCostPerCapacity = Double.MAX_VALUE;
		int bestFacility = -1;
		for (int i = 0; i < bl.getNumOfFacilities(); i++) {
			Facility f = bl.getFacility(i);
			double cost = f.getCapacity() / f.getSetupCost();
			if (bestCostPerCapacity > cost) {
				bestCostPerCapacity = cost;
				bestFacility = i;
			}
		}

		return bestFacility;
	}

}
