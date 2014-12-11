import java.util.ArrayList;


public class OutputData {
	private double value;
	private ArrayList<Integer>[] routes;
    
    public OutputData(double objectiveValue , ArrayList<Integer>[] routes)
    {
    	value = objectiveValue;
    	this.routes = routes;
    }

	public ArrayList<Integer> getAssignedRoute(int v) {
		return routes[v];
	}
	
	public ArrayList<Integer>[] getAllAssignedRoutes() {
		return routes;
	}
	
	public int getNumOfVehcles() {
		return routes.length;
	}
	
	public int getTotalNumOfStops() {
		int size =0;
		for(ArrayList<Integer> v : routes)
			size+= v.size();
		return size;
	}
	
	public int getNumOfStops(int v) {
		return routes[v].size();
	}

	public double getValue() {
		return value;
	}
	
	public OutputData clone()
	{
		return new OutputData(value, routes.clone());
	}

}
