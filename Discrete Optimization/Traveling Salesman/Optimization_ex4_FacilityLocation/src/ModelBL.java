
public class ModelBL {
   private Model model;
   
   private double objectiveVal;
   
   public ModelBL(Model m)
   {
	   model = m;
	   objectiveVal = 0;
   }
   
   public int getNumOfFacilities() {
		return model.getNumOfFacilities();
	}

	public int getNumOfCustomers() {
		return model.getNumOfCustomers();
	}

	public Facility[] getFacilities() {
		return model.getFacilities();
	}
	
	public void addFacility(Facility facility) {
		model.addFacility(facility);
	}
	
	public Facility getFacility(int id) {
		return model.getFacility(id);
	}
	
	public Customer[] getCustomers() {
		return model.getCustomers();
	}
	
	public void addCustomer(Customer customer) {
		model.addCustomer(customer);
	}
	
	public Customer getCustomer(int id) {
		return model.getCustomer(id);
	}
	
	
	public double getObjectiveVal()
	{
		return objectiveVal;
	}
	
	public OutputData getOutputData()
	{
		int[] assignedFacilities = new int[model.getNumOfCustomers()];
		for(Customer customer: model.getCustomers())
		{
			assignedFacilities[customer.getId()] = customer.getAssignedFacility();
		}
		return new OutputData(objectiveVal, assignedFacilities);
	}
	
	
	public void setFacilityEnabled(int facilityId, boolean enabled)
	{
		Facility facility = model.getFacility(facilityId);
		if(facility.isEnbled() == enabled)
			return;
		
		if(!enabled)
		{
			for(Customer customer: model.getCustomers())
			{
				if(customer.getAssignedFacility() == facilityId)
				{
					unassignCustomerFromFacility(customer.getId(),false);
				}
			}
		}
		
		facility.setEnbled(enabled);
		int sign = enabled ? 1 : -1;
		objectiveVal += facility.getSetupCost() * sign ;
	}
	
	public void unassignCustomerFromFacility(int customerId,boolean closeFacilityIfNeeded)
	{
		Customer customer = model.getCustomer(customerId);
		int facilityId = customer.getAssignedFacility();
		if(facilityId == -1)
		{
			return;
		}
		objectiveVal-= customer.getDistanceToFacility();
		customer.setAssignedFacility(-1, null);
		
		Facility facility = model.getFacility(facilityId);
		facility.freeCapacity(customer.getDemand());
		
		if(closeFacilityIfNeeded && !facility.isUsed())
		{
			setFacilityEnabled(facilityId,false);
		}
		
	}
	
	public void assignToFirstPossibleFacility(int customerId,boolean enforceEnable)
	{
		int facilityInd = 0;
		while(!tryAssignCustomerToFacility(customerId,facilityInd, enforceEnable))
		{
			facilityInd++;
		}
	}
	
	public boolean tryAssignToClosesetPossibleFacility(int customerId,boolean enforceEnable)
	{
		int bestFacilityId = getClosestFacility(customerId, enforceEnable);
				
		return tryAssignCustomerToFacility(customerId,bestFacilityId , enforceEnable);

	}

	public int getClosestFacility(int customerId, boolean allowClosed) {
		int bestFacilityId = -1;
		double minDist = Double.MAX_VALUE;
		Customer customer = model.getCustomer(customerId);
		
		for(int i=0;i<model.getNumOfFacilities();i++)
		{
			Facility facility = model.getFacility(i);
			if(!allowClosed && !facility.isEnbled())
			{
				continue;
			}
			if(facility.getRemainingCapacity() < customer.getDemand())
			{
				continue;
			}
			
			double dist = customer.findSqrDistance(facility.getLocation());
			if( dist <minDist)
			{
				bestFacilityId = i;
				minDist = dist;
			}
		}
		return bestFacilityId;
	}
	
	public boolean tryAssignToCheapestPossibleFacility(int customerId,boolean enforceEnable)
	{
		int bestFacilityId = -1;
		double minCost = Double.MAX_VALUE;
		Customer customer = model.getCustomer(customerId);
		unassignCustomerFromFacility(customerId,true);
		
		
		for(int i=0;i<model.getNumOfFacilities();i++)
		{
			Facility facility = model.getFacility(i);
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
				openingPrice = facility.getSetupCost();
			}
			
			double cost = openingPrice + Math.sqrt(customer.findSqrDistance(facility.getLocation()));
			if( cost <minCost)
			{
				bestFacilityId = i;
				minCost = cost;
			}
		}
				
		return tryAssignCustomerToFacility(customer.getId(),bestFacilityId , enforceEnable);

	}
	
	public boolean tryAssignCustomerToFacility(int customerId, int facilityId , boolean enforceEnabled)
	{
		if(facilityId<0)
			return false;
		
		unassignCustomerFromFacility(customerId,true);
		
		Facility facility = model.getFacility(facilityId);
		Customer customer = model.getCustomer(customerId);
		
		if(!facility.isEnbled() && !enforceEnabled)
			return false;
		
		if(facility.getRemainingCapacity() < customer.getDemand())
			return false;
		
		setFacilityEnabled(facilityId,true);
				
		customer.setAssignedFacility(facilityId, facility.getLocation());
		facility.useCapacity(customer.getDemand());
		objectiveVal+= customer.getDistanceToFacility();
		
		return true;
	}

	public void closeUnusedFacilities() {
		for(int i=0;i<model.getNumOfFacilities();i++)
		{
			Facility facility = model.getFacility(i);
			if(facility.isEnbled() && !facility.isUsed())
			{
				setFacilityEnabled(facility.getId(),false);
			}
		}
		
	}
}
