
public class Solver {


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			if (args.length == 0) {
				String[] testArgs = new String[1];
				testArgs[0] = "-file=D:\\Studies\\Optimization\\Facility Location\\Optimization_ex4_FacilityLocation\\bin\\data\\"+
				//"fl_25_2";    // 3269822 
				//"fl_50_6";		// 3732794
				// "fl_100_7";	// 2050
				 "fl_100_1";	// 26000000
				 //"fl_200_7";  // 5000000
				// "fl_500_7";	// 30000000
				 //"fl_1000_2"; // 10000000
				 // "fl_2000_2";// 10000000
				solve(testArgs);
			} else {
				solve(args);
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	
	private static void solve(String[] args) throws Exception {
		Model inputData = InputParser.readAndParse(args);
		SolverConfig solverConfig = new SolverConfig(inputData.getFileName());
		SolutionFinder solutionFinder = new SolutionFinder(new ModelBL(inputData), solverConfig);
		OutputData output = solutionFinder.findSolution();
		OutputPrinter.print(output);
	}


	
}
