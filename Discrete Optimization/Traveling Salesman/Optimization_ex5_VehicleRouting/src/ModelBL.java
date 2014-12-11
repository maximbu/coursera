import java.util.ArrayList;
import java.util.HashMap;


public class ModelBL {
   private Model model;
   
   private double objectiveVal;
   private HashMap<Integer,ArrayList<Integer>> routes = new HashMap<Integer,ArrayList<Integer>>();
   
   
   public ModelBL(Model m)
   {
	   model = m;
	   objectiveVal = 0;
	   for(Vehicle v: m.getVehicles())
	   {
		   routes.put(v.getId(), new ArrayList<Integer>());
	   }
	   
   }
   
   public int getNumOfVehicles() {
		return model.getNumOfVehicles();
	}

	public int getNumOfCustomers() {
		return model.getNumOfCustomers();
	}

	public Vehicle[] getFacilities() {
		return model.getVehicles();
	}
	
	
	public Vehicle getVehicle(int id) {
		return model.getVehicle(id);
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
		int num = model.getNumOfVehicles();
		ArrayList<Integer>[] tmp = new ArrayList[num];
		for(int i=0;i<model.getNumOfVehicles();i++)
		{
			tmp[i] =  (ArrayList<Integer>)routes.get(i).clone();
		}
		return new OutputData(objectiveVal, tmp);
	}
	
	
	public void assignPerRequested(ArrayList<Integer>[] assignedRoutes) {
		for(int v=0;v<assignedRoutes.length;v++)
		{
			ArrayList<Integer> route = assignedRoutes[v];
			for(int c = 0 ; c<route.size();c++)
			{
				tryAssign(route.get(c),v,c);
			}
		}
	}
	
	public boolean tryAssign(int customerId , int vechileId , int location)
	{
		if(!simpleAssign(customerId, vechileId))
			return false;
		addCustomerToRoute(customerId, vechileId, location);
		return true;
	}

	private boolean simpleAssign(int customerId, int vechileId) {
		Customer c = model.getCustomer(customerId);
		Vehicle v = model.getVehicle(vechileId);
		if(v.getRemainingCapacity() < c.getDemand())
			return false;
		v.useCapacity(c.getDemand());
		c.setAssignedVehicle(vechileId);
		return true;
	}
	
	
	
	public boolean tryBestVehicle(int customerId)
	{
		double bestObj = Double.MAX_VALUE;
		int bestV =0;
		for(int i=0 ; i<model.getNumOfVehicles();i++)
		{
			Pair best = findBestAssign(customerId, i);
			if(best != null)
			{
				if(bestObj > best.Value)
				{
					bestObj = best.Value;
					bestV = i;
				}
				
				removeCustomerFromVehicle(customerId, i);
			}
		}
		return tryBestAssign(customerId,bestV);
	}
	
	
	public boolean tryBestAssign(int customerId , int vechileId)
	{
		Pair best = findBestAssign(customerId, vechileId);
		if(best == null)
			return false;
		addCustomerToRoute(customerId, vechileId, best.Location);
		return true;
	}

	private Pair findBestAssign(int customerId, int vechileId) {
		if(!simpleAssign(customerId, vechileId))
			return null;
		Vehicle v = model.getVehicle(vechileId);
		int use = v.customersUse()-1;
		double bestObj = Double.MAX_VALUE;
		int bestLoc =0;
		for(int i=0;i<=use;i++)
		{
			addCustomerToRoute(customerId, vechileId, i);
			if(bestObj > objectiveVal)
			{
				bestObj = objectiveVal;
				bestLoc = i;
			}
			removeCustomerFromRoute(customerId, vechileId,i);
		}
		return new Pair(bestLoc,bestObj);
	}
	
	
	public boolean removeCustomerAssigment(int customerId)
	{
		Customer c = model.getCustomer(customerId);
		int vId = c.getAssignedVehicle();
		if(vId == -1)
			return false;
		if(!removeCustomerFromVehicle(customerId,vId))
			return false;
		int location = findCustomerLocationInRoute(customerId,vId);
		return removeCustomerFromRoute(customerId,vId,location);
	}
	
	
	private int findCustomerLocationInRoute(int customerId, int vId) {
		ArrayList<Integer> route = routes.get(vId);
		for(int i=0 ; i< route.size();i++)
		{
			if(route.get(i) == customerId)
				return i;
		}
		return -1;
	}

	private boolean removeCustomerFromVehicle(int customerId, int vechileId) {
		Customer c = model.getCustomer(customerId);
		Vehicle v = model.getVehicle(vechileId);
		if(c.getAssignedVehicle()!= v.getId())
			return false;
		v.freeCapacity(c.getDemand());
		c.setAssignedVehicle(-1);
		return true;
	}
	
	private boolean removeCustomerFromRoute(int customerId, int vechileId,int location) {

		ArrayList<Integer> route = routes.get(vechileId);
		double oldRouteObj = getObjFromRoute(route);
		route.remove(location);
		double newRouteObj = getObjFromRoute(route);
		objectiveVal = objectiveVal - oldRouteObj + newRouteObj;
		return true;
	}

	public boolean tryAppend(int customerId , int vechileId)
	{
		return tryAssign(customerId,vechileId,model.getVehicle(vechileId).customersUse());
	}

	private void addCustomerToRoute(int customerId, int vechileId, int location) {
		ArrayList<Integer> route = routes.get(vechileId);
		int addLocation = location;
		if(addLocation < 0)
		{
			addLocation = 0;
		}
		if(addLocation > route.size())
		{
			addLocation = route.size();
		}
		double oldRouteObj = getObjFromRoute(route);
		route.add(addLocation, customerId);
		double newRouteObj = getObjFromRoute(route);
		objectiveVal = objectiveVal - oldRouteObj + newRouteObj;
	}

	private double getObjFromRoute(ArrayList<Integer> route) {
		double val = 0;
		Point base = model.getBase().getLocation();
		Point p = base;
		for(int i=0;i<route.size();i++)
		{
			Customer c = model.getCustomer(route.get(i));
			val+= Math.sqrt(c.findSqrDistance(p));
			p = c.getLocation();
			if(i == route.size()-1)
			{
				val+= Math.sqrt(c.findSqrDistance(base));
			}
		}
		return val;
		
	}

	public boolean allCustomersAssigned() {
		for(int i=1;i<model.getNumOfCustomers();i++)
		{
			if(model.getCustomer(i).getAssignedVehicle() == -1)
				return false;
		}
		return true;
	}
}
