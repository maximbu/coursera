public class OutputPrinter {

	private final static String optimizationFlag = "0";

	public static void print(OutputData output) {
		System.out.println(output.getValue() + " " + optimizationFlag);
		for (int i = 0; i < output.getNumOfAssignedFacilities(); i++) {
			System.out.print(output.getAssignedFacility(i) + " ");
		}
		System.out.println("");
	}

}
