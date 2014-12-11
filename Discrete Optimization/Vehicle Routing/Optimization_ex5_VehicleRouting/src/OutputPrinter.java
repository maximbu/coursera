import java.util.ArrayList;

public class OutputPrinter {

	private final static String optimizationFlag = "0";

	public static void print(OutputData output) {
		System.out.println(output.getValue() + " " + optimizationFlag);
		for (int i = 0; i < output.getNumOfVehcles(); i++) {
			ArrayList<Integer> route = output.getAssignedRoute(i);
			System.out.print("0" + " ");
			for (int j = 0; j < route.size(); j++) {
				String customerRep = String.valueOf(route.get(j));  
				System.out.print(customerRep + " ");
			}
			System.out.print("0" + " ");
			System.out.println("");
		}
		System.out.println("");
	}
}
