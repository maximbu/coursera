
public class Solver {


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			if (args.length == 0) {
				String[] testArgs = new String[1];
				testArgs[0] = "-file=D:\\Studies\\Optimization\\Vehicle Routing\\Optimization_ex5_VehicleRouting\\bin\\data\\"+
				//"vrp_5_4_1";    // 3269822 
				 "vrp_16_3_1";    // 3269822 
				//"vrp_26_8_1";		// 3732794
				// "vrp_51_5_1";	// 2050
				// "vrp_101_10_1";	// 26000000
				 //"vrp_200_16_1";  // 5000000
				 //vrp_421_41_1";	// 30000000
				solve(testArgs,true);
			} else {
				solve(args,false);
			}
		} catch (Exception e) {
			 e.printStackTrace();
		}
	}

	
	private static void solve(String[] args,boolean debug) throws Exception {
		Model inputData = InputParser.readAndParse(args);
		SolverConfig solverConfig = new SolverConfig(inputData.getFileName(),debug);
		SolutionFinder solutionFinder = new SolutionFinder(new ModelBL(inputData), solverConfig);
		OutputData output = solutionFinder.findSolution();
		OutputPrinter.print(output);
	}


	
}
