import java.io.File;


public class SolverConfig {

	private int currentProblem;
	private int optimalValue;
	private int tenPtsValue;
	private int sevenPtsValue;
	private boolean allowOutput;
	private int numOfIterations;
	private String file;
	
	public SolverConfig(String fileName,boolean fromDebug)
	{
		allowOutput = fromDebug;
		if(fileName.contains("vrp_16_3_1"))
		{
			currentProblem = 1;
			sevenPtsValue = 387;
			tenPtsValue = 280;
			optimalValue = 278; //?
			numOfIterations = 10;
			return;
		}
		
		if(fileName.contains("vrp_26_8_1"))
		{
			currentProblem = 2;
			sevenPtsValue = 1019;
			tenPtsValue = 630;
			optimalValue = 607; //?
			numOfIterations = 20000;
			return;
		}
		
		if(fileName.contains("vrp_51_5_1"))
		{
			currentProblem = 3;
			sevenPtsValue = 713;
			tenPtsValue = 713; //?
			optimalValue = 524; //?
			numOfIterations = 1000;
			return;
		}
		
		if(fileName.contains("vrp_101_10_1"))
		{
			currentProblem = 4;
			sevenPtsValue = 1193;
			tenPtsValue = 1193;  //?
			optimalValue = 819; //?
			numOfIterations = 1000;
			return;
		}
		
		if(fileName.contains("vrp_200_16_1"))
		{
			currentProblem = 5;
			sevenPtsValue = 3719;
			tenPtsValue = 1400;  
			optimalValue = 1308;
			numOfIterations = 500;
			return;
		}
		
		if(fileName.contains("vrp_421_41_1"))
		{
			currentProblem = 6;
			sevenPtsValue = 2392;
			tenPtsValue = 2392; 
			optimalValue = 1842; // ?
			numOfIterations = 200;
			return;
		}
		
		file = fileName;
	}
	
	public int getNumOfIteraitions()
	{
		return numOfIterations;
	}
	
	public String getFileName()
	{
		return file;
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

	public boolean allowOutput() {
		return allowOutput;
	}
	
	public boolean shouldStopOption()
	{
		File f = new File("D:\\Studies\\Optimization\\Vehicle Routing\\Optimization_ex5_VehicleRouting\\bin\\stop.txt");
		return f.exists();
	}
}
