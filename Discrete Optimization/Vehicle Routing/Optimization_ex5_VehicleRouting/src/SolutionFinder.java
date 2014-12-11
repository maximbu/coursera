import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class SolutionFinder {

	private static Logger logger;
	private ModelBL bl;
	private SolverConfig solverConfig;
	private Customer[] customersByDemand;
	private ExternalSolverResults externalResults;

	public SolutionFinder(ModelBL inputData, SolverConfig solverConfig) {
		this.bl = inputData;
		this.solverConfig = solverConfig;
		createLogger(solverConfig.getCurrentProblem());
		logger.info(solverConfig.getFileName());
		externalResults = new ExternalSolverResults(bl);
	}

	private void printOutput(String st) {
		if (!solverConfig.allowOutput()) {
			logger.setUseParentHandlers(false);
			logger.info(st);
		} else {
			System.out.println(st);
		}
	}

	private static void createLogger(int i) {
		try {
			logger = Logger.getLogger("MyLog");
			FileHandler fh = new FileHandler(
					"D:\\Studies\\Optimization\\Vehicle Routing\\Optimization_ex5_VehicleRouting\\bin\\Logs\\MyLogFile"
							+ i + ".log");
			logger.addHandler(fh);
			logger.setUseParentHandlers(false);
			SimpleFormatter formatter = new SimpleFormatter();
			fh.setFormatter(formatter);
		} catch (Exception e) {

		}
	}
	
	public OutputData findSolution() {
		return externalResults.solution5();
	}

	
	public OutputData findSolution_old() {
		
		customersByDemand = bl.getCustomers().clone();
		Arrays.sort(customersByDemand);
		
		tryBestPossible();
		OutputData bestOut = bl.getOutputData();
		int iteration = 0;

		boolean continueLoop = true;
		int cusomersToRandomize = 0;
		
		while (continueLoop || !bl.allCustomersAssigned()) {
			iteration++;
			printOutput("Iteration is : " + iteration);
			printOutput("Best value is : " + bestOut.getValue());
			printOutput("Old value is : " + bl.getObjectiveVal());
			printOutput("CusomersToRandomize is : " + cusomersToRandomize);
			if(!bl.allCustomersAssigned())
			{
				printOutput("Not all customers are assigned!");
			}

			//int currentProblem = solverConfig.getCurrentProblem();
			
			if(cusomersToRandomize < bl.getNumOfCustomers())
			{
				unassignRandomCustomers(cusomersToRandomize);
			}
			else
			{
				unassignAllCustomers();
				cusomersToRandomize = 1;
			}
			reassignInRandomOrder();
			//tryBestPossible();
			double oldValue = bestOut.getValue();
			bestOut = updateBestIfNeeded(bestOut);
			
			if(Math.abs(oldValue-bestOut.getValue())>1)
			{
				cusomersToRandomize = 1;
			}
			else
			{
				if(cusomersToRandomize < bl.getNumOfCustomers())
					cusomersToRandomize++;
					
			}
			continueLoop = /*iteration<solverConfig.getNumOfIteraitions() &&*/ solverConfig.getTenPtsValue() < bestOut
					.getValue() && !solverConfig.shouldStopOption() ;
		}
		
		return bestOut; 
	}
	
	private void unassignAllCustomers() {
		for (int i = 1; i < bl.getNumOfCustomers(); i++) {
			bl.removeCustomerAssigment(i);
		}
	}

	private void reassignInRandomOrder() {
		int[] arr = shuffle(bl.getNumOfCustomers()-1);
		for (int i = 0; i < arr.length; i++) {
			assignBestPossible(arr[i]);
		}
		
	}

	private void unassignRandomCustomers(int num) {
		for (int i = 0; i < num; i++) {
			int customerId = 1+getRandomIndex(bl.getNumOfCustomers()-1);
			bl.removeCustomerAssigment(customerId);
		}
	}

	private int[] shuffle(int N)
	{
		
		int[] in = new int[N];
		for(int i=0 ; i<N;i++)
		{
			in[i]=i+1;
		}
		int tmp;
		for(int i=0 ; i<N;i++)
		{
			int r= getRandomIndex(i);
			tmp = in[i];
			in[i] = in [r];
			in[r] = tmp;
		}
		return in;
	}
	
	private OutputData updateBestIfNeeded(OutputData bestOut) {
		if (bl.getObjectiveVal() < bestOut.getValue() && bl.allCustomersAssigned()) {
			return bl.getOutputData();
		}
		return bestOut;
	}
	
	private static int getRandomIndex(int n) {
		return (int) (Math.random() * (n));
	}

	private void tryBestPossible() {
		for(int i=0;i<bl.getNumOfCustomers()-1;i++)
		{
			assignBestPossible(customersByDemand[i].getId());
		}
	}

	private void tryFirstPossible() {
		for(int i=1;i<bl.getNumOfCustomers();i++)
			assignFirstPossible(i);
	}

	private boolean assignFirstPossible(int customer) {
		int v = 0;
		while(!bl.tryBestAssign(customer, v))
		{
			v++;
			if(v >= bl.getNumOfVehicles() )
				return false;
		}
		return true;
	}
	
	private void assignBestPossible(int customer) {
		int i=bl.getNumOfCustomers()-2;
		while(!bl.tryBestVehicle(customer) && i> 0)
		{
			bl.removeCustomerAssigment(customersByDemand[i].getId());
			i--;
		}
//		i++;
//		for(;i<=bl.getNumOfCustomers()-2;i++)
//		{
//			int customerId = customersByDemand[i].getId();
//			if(bl.getCustomer(customerId).getAssignedVehicle()!= -1)
//				continue;
//			assignBestPossible(customerId);
//		}
	}

	
}
