import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class SolutionFinder {

	private static Logger logger;
	private ModelBL bl;
	private SolverConfig solverConfig;

	public SolutionFinder(ModelBL inputData, SolverConfig solverConfig) {
		this.bl = inputData;
		this.solverConfig = solverConfig;
		createLogger(solverConfig.getCurrentProblem());
		logger.info(solverConfig.getFileName());
	}

	public OutputData findSolution() {

		//return facility2FlipsSearch();
		return facilityOpenToCloseSearch();
	}
	
	private OutputData facilityOpenToCloseSearch() {
		
		for (int i = 0; i < bl.getNumOfFacilities(); i++) {
			bl.flipFacilityEnabled(i);
		}
		
		findOptimalAssigmentOfCustomers(null,false);
		OutputData bestOut = bl.getOutputData();
		int iteration = 0;

		boolean continueLoop = true;
		
		while (continueLoop || !bl.allCustomersAssigned()) {

			iteration++;
			printOutput("Iteration is : " + iteration);
			printOutput("Best value is : " + bestOut.getValue());
			printOutput("Old value is : " + bl.getObjectiveVal());

			double oldValue = bestOut.getValue();
			findOptimalAssigmentOfCustomers(null,false);
			bestOut = updateBestIfNeeded(bestOut);
			
			int currentProblem = solverConfig.getCurrentProblem();
			if(currentProblem == 1 || currentProblem == 2 || currentProblem == 3)
				break;
			double origValue = bestOut.getValue();
			for (int i = 0; i < bl.getNumOfFacilities(); i++) {
				if(bl.getFacility(i).isEnbled() == false)
					continue;
				bl.flipFacilityEnabled(i);
				findOptimalAssigmentOfCustomers(null,false);
				assaignNotAssignedCustomers();
				bestOut = updateBestIfNeeded(bestOut);
				bl.flipFacilityEnabled(i);
			}
			
			if(Math.abs(bestOut.getValue()-oldValue)>1 || iteration%10==0)
			{
				bl.assignPerRequested(bestOut.getAssignedFacilities());
			}
			
			printOutput("After optimization value is : " + bl.getObjectiveVal());

			if (Math.abs(bestOut.getValue()-oldValue)<1) {
				if(iteration%3==0)
				{
					randomizeFacilitiesEnable(1);
				}
				randomizeCustomerAssigments(bl.getNumOfCustomers());
				printOutput("After random value is : " + bl.getObjectiveVal());
			}			
			
			bestOut = updateBestIfNeeded(bestOut);
			
			continueLoop = /*iteration<solverConfig.getNumOfIteraitions() &&*/ solverConfig.getTenPtsValue() < bestOut
					.getValue() && !solverConfig.shouldStopOption() ;
		}
		return bestOut;
	}

	private OutputData updateBestIfNeeded(OutputData bestOut) {
		if (bl.getObjectiveVal() < bestOut.getValue() && bl.allCustomersAssigned()) {
			return bl.getOutputData();
		}
		return bestOut;
	}


	private int[] shuffle(int N)
	{
		
		int[] in = new int[N];
		for(int i=0 ; i<N;i++)
		{
			in[i]=i;
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
	
	private OutputData facility2FlipsSearch() {
		int[] assigments = getAssignedFacilitiesCount(getClosestFacilities());
		findOptimalAssigmentOfCustomers(assigments,true);
		OutputData bestOut = bl.getOutputData();
		int iteration = 0;

		boolean continueLoop = true;
		
		while (continueLoop || !bl.allCustomersAssigned()) {

			iteration++;
			printOutput("Iteration is : " + iteration);
			printOutput("Best value is : " + bestOut.getValue());
			printOutput("After random value is : " + bl.getObjectiveVal());

			double oldValue = bestOut.getValue();
			bestOut = flipEachFacility(bestOut);
			
			int currentProblem = solverConfig.getCurrentProblem();
			if(currentProblem == 1 || currentProblem == 2 || currentProblem == 3)
				break;
			
			for (int i = 0; i < bl.getNumOfFacilities(); i++) {
				bl.flipFacilityEnabled(i);
				bestOut = flipEachFacility(bestOut);
				bl.flipFacilityEnabled(i);
			}
			bl.assignPerRequested(bestOut.getAssignedFacilities());

			printOutput("After optimization value is : " + bl.getObjectiveVal());

			if (Math.abs(bestOut.getValue()-oldValue)<1) {
				randomizeFacilitiesEnable(3);
				assaignNotAssignedCustomers();
				randomizeCustomerAssigments(5);
			}
			
			bl.closeUnusedFacilities();
			
			if (bl.getObjectiveVal() < bestOut.getValue()) {
				bestOut = bl.getOutputData();
			}
			
			continueLoop = /*iteration<solverConfig.getNumOfIteraitions() &&*/ solverConfig.getTenPtsValue() < bestOut
					.getValue() && !solverConfig.shouldStopOption() ;
		}
		return bestOut;
	}

	private OutputData flipEachFacility(OutputData bestOut) {
		for (int i = 0; i < bl.getNumOfFacilities(); i++) {
			bl.flipFacilityEnabled(i);
			findOptimalAssigmentOfCustomers(getAssignedFacilitiesCount(bestOut
					.getAssignedFacilities()),true);
			bestOut = updateBestIfNeeded(bestOut);
			bl.flipFacilityEnabled(i);
		}
		return bestOut;
	}

	private void assaignNotAssignedCustomers() {
		int[] arr = shuffle(bl.getNumOfCustomers());
		for (int i = 0; i < arr.length; i++) {
			int customerId = arr[i];
			if(bl.getCustomer(customerId).getAssignedFacility() == -1)
			{
				if(!bl.tryAssignToCheapestPossibleFacility(customerId, true))
				{
					bl.assignToFirstPossibleFacility(customerId, true);
				}
			}
		}
	}

	private void randomizeFacilitiesEnable(int num) {
		for (int i = 0; i < num; i++) {
			int facilityId = getRandomIndex(bl.getNumOfFacilities());
			bl.flipFacilityEnabled(facilityId);
		}
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
					"D:\\Studies\\Optimization\\Facility Location\\Optimization_ex4_FacilityLocation\\bin\\Logs\\MyLogFile"
							+ i + ".log");
			logger.addHandler(fh);
			logger.setUseParentHandlers(false);
			SimpleFormatter formatter = new SimpleFormatter();
			fh.setFormatter(formatter);
		} catch (Exception e) {

		}
	}

	private void randomizeCustomerAssigments(int num) {
		for (int i = 0; i < num; i++) {
			int customerId = getRandomIndex(bl.getNumOfCustomers());
			int facilityId = getRandomIndex(bl.getNumOfFacilities());
			if (!bl.tryAssignCustomerToFacility(customerId, facilityId, false)) {
				bl.tryAssignToClosesetPossibleFacility(customerId, true);
			}
		}
	}

	private static int getRandomIndex(int n) {
		return (int) (Math.random() * (n));
	}

	private int[] getClosestFacilities() {
		int[] facilities = new int[bl.getNumOfCustomers()];
		for (int i = 0; i < bl.getNumOfCustomers(); i++) {
			facilities[i] = bl.getClosestFacility(i, true);
		}

		return facilities;
	}

	private int[] getAssignedFacilitiesCount(int[] assigments) {
		int[] facilities = new int[bl.getNumOfFacilities()];
		for (int i = 0; i < bl.getNumOfCustomers(); i++) {
				facilities[assigments[i]]++;
		}

		return facilities;
	}

	private void findOptimalAssigmentOfCustomers(int[] assigments, boolean allowOpen) {
		double oldValue = 0;

		while (true) {
			int[] arr = shuffle(bl.getNumOfCustomers());
			for (int i = 0; i < arr.length; i++) {
				tryAssignToCheapestPossibleFacility(arr[i], allowOpen, assigments);
			}

			double value = bl.getObjectiveVal();
			if (Math.abs(value - oldValue) < 1) {
				break;
			}

			oldValue = value;

		}

		//return bl.getOutputData();
	}

	public boolean tryAssignToCheapestPossibleFacility(int customerId,
			boolean enforceEnable, int[] assigments) {
		int bestFacilityId = -1;
		double minCost = Double.MAX_VALUE;
		Customer customer = bl.getCustomer(customerId);
		bl.unassignCustomerFromFacility(customerId, true);

		for (int i = 0; i < bl.getNumOfFacilities(); i++) {
			Facility facility = bl.getFacility(i);
			if (!enforceEnable && !facility.isEnbled()) {
				continue;
			}
			if (facility.getRemainingCapacity() < customer.getDemand()) {
				continue;
			}

			double openingPrice = 0;
			if (!facility.isEnbled()) {
				openingPrice = calculateFacilityOpenCost(assigments, facility);
			}

			double cost = openingPrice
					+ Math.sqrt(customer.findSqrDistance(facility.getLocation()));
			if (cost < minCost) {
				bestFacilityId = i;
				minCost = cost;
			}
		}

		return bl.tryAssignCustomerToFacility(customer.getId(), bestFacilityId,
				enforceEnable);

	}

	private float calculateFacilityOpenCost(int[] assigments, Facility facility) {
		int divCost = 1;
		if(assigments!=null)
		{
			divCost = assigments[facility.getId()];
		}
		
		return facility.getSetupCost()/divCost;
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
