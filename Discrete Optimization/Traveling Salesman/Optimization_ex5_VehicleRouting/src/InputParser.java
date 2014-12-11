import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


public class InputParser {

	public static Model readAndParse(String[] args) throws Exception
	{
		String fileName = null;

		// get the temp file name
		for (String arg : args) {
			if (arg.startsWith("-file=")) {
				fileName = arg.substring(6);
			}
		}
		if (fileName == null)
			return null;

		// read the lines out of the file
		List<String> lines = new ArrayList<String>();

		BufferedReader input = new BufferedReader(new FileReader(fileName));
		try {
			String line = null;
			while ((line = input.readLine()) != null) {
				lines.add(line);
			}
		} finally {
			input.close();
		}

		// parse the data in the file
		String[] firstLine = lines.get(0).split("\\s+");
		int n = Integer.parseInt(firstLine[0]);
		int v = Integer.parseInt(firstLine[1]);
		int c = Integer.parseInt(firstLine[2]);
		
		Model data = new Model(n, v,c,fileName);
		
		for (int i = 1; i < n+1 ; i++) {
			String line = lines.get(i);
			String[] parts = line.split("\\s+");
			
			float demand = Float.parseFloat(parts[0]);

			double x = Double.parseDouble(parts[1]);

			double y = Double.parseDouble(parts[2]);

			data.addCustomer(new Customer(i-1, demand, x, y));
		}
		
		return data;
	}
}
