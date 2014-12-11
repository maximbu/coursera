
public class SolverConfig {

	private int currentProblem;
	private int optimalValue;
	private int tenPtsValue;
	private int sevenPtsValue;
	
	public SolverConfig(String fileName)
	{
		if(fileName.contains("fl_25_2"))
		{
			currentProblem = 1;
			sevenPtsValue = 4000000;
			tenPtsValue = 3269822;
			optimalValue = 3269821;
			return;
		}
		
		if(fileName.contains("fl_50_6"))
		{
			currentProblem = 2;
			sevenPtsValue = 4500000;
			tenPtsValue = 3732794;
			optimalValue = 3732793;
			return;
		}
		
		if(fileName.contains("fl_100_7"))
		{
			currentProblem = 3;
			sevenPtsValue = 2050;
			tenPtsValue = 1966;
			optimalValue = 1965;
			return;
		}
		
		if(fileName.contains("fl_100_1"))
		{
			currentProblem = 4;
			sevenPtsValue = 26000000;
			tenPtsValue = 26000000; // ?
			optimalValue = 22724065;
			return;
		}
		
		if(fileName.contains("fl_200_7"))
		{
			currentProblem = 5;
			sevenPtsValue = 5000000;
			tenPtsValue = 5000000;  // ?
			optimalValue = 4711294;
			return;
		}
		
		if(fileName.contains("fl_500_7"))
		{
			currentProblem = 6;
			sevenPtsValue = 30000000;
			tenPtsValue = 30000000;  // ?
			optimalValue = 26922939; // ?
			return;
		}
		
		if(fileName.contains("fl_1000_2"))
		{
			currentProblem = 7;
			sevenPtsValue = 10000000;
			tenPtsValue = 8879294;
			optimalValue = 8866343;
			return;
		}
		
		if(fileName.contains("fl_2000_2"))
		{
			currentProblem = 8;
			sevenPtsValue = 10000000;
			tenPtsValue = 7453531;
			optimalValue = 7229882;  // ?
			return;
		}
	}
	
	public double getOptimalPtsValue()
	{
		return optimalValue + 1;
	}
	
	public double getTenPtsValue()
	{
		return tenPtsValue + 1;
	}
	
	public double getSevenPtsValue()
	{
		return sevenPtsValue + 1;
	}
	
	public int getCurrentProblem()
	{
		return currentProblem;
	}
}
