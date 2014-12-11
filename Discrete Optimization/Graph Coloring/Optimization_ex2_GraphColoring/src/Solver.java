import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jacop.constraints.Alldifferent;
import org.jacop.constraints.Constraint;
import org.jacop.constraints.Max;
import org.jacop.constraints.XeqC;
import org.jacop.constraints.XeqY;
import org.jacop.constraints.XltY;
import org.jacop.constraints.XlteqY;
import org.jacop.constraints.XneqY;
import org.jacop.core.IntVar;
import org.jacop.core.Store;
import org.jacop.search.DepthFirstSearch;
import org.jacop.search.IndomainMin;
import org.jacop.search.InputOrderSelect;
import org.jacop.search.Search;
import org.jacop.search.SelectChoicePoint;

public class Solver {

	private static int nodeCount;
	private static int edgeCount;
	private static Edge[] edges;
	private static int value;
	private static int[] colors;
	private static int colorToOptimize;
	private static int optimizationValue = 10;
	private static int newAllowdedCollors = 0;
	private static long startedTime;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			if (args.length == 0) {
				String[] testArgs = new String[1];
				testArgs[0] =
				// "-file=D:\\Studies\\Optimization\\Graph Coloring\\Optimization_ex2_GraphColoring\\bin\\data\\gc_70_7";
				// //2 17-
				// "-file=D:\\Studies\\Optimization\\Graph Coloring\\Optimization_ex2_GraphColoring\\bin\\data\\gc_100_5";
				// // 3 16-
				// "-file=D:\\Studies\\Optimization\\Graph Coloring\\Optimization_ex2_GraphColoring\\bin\\data\\gc_250_9";
				// //4 78-
				"-file=D:\\Studies\\Optimization\\Graph Coloring\\Optimization_ex2_GraphColoring\\bin\\data\\gc_500_1";
				// //5 16-
				// "-file=D:\\Studies\\Optimization\\Graph Coloring\\Optimization_ex2_GraphColoring\\bin\\data\\gc_1000_5";
				// //6 124-
				// "-file=D:\\Studies\\Optimization\\Graph Coloring\\Optimization_ex2_GraphColoring\\bin\\data\\gc_20_1";
				solve(testArgs);
			} else {
				solve(args);
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	private static void readAndParseInput(String[] args)
			throws FileNotFoundException, IOException {
		String fileName = null;

		// get the temp file name
		for (String arg : args) {
			if (arg.startsWith("-file=")) {
				fileName = arg.substring(6);
			}
		}
		if (fileName == null)
			return;

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
		nodeCount = Integer.parseInt(firstLine[0]);
		edgeCount = Integer.parseInt(firstLine[1]);

		edges = new Edge[edgeCount];

		for (int i = 1; i < edgeCount + 1; i++) {
			String line = lines.get(i);
			String[] parts = line.split("\\s+");

			int from = Integer.parseInt(parts[0]);
			int to = Integer.parseInt(parts[1]);
			edges[i - 1] = new Edge(from, to);
		}
	}

	private static void solve(String[] args) throws IOException {
		readAndParseInput(args);
		findColoring();
		printOutput();
		if (!isValidSolution()) {
			System.out.println("Bad solution !!!");
		}
	}

	private static void printOutput() {
		System.out.println(value + " 0");
		for (int i = 0; i < nodeCount; i++) {
			System.out.print(colors[i] + " ");
		}
		System.out.println("");
	}

	private static void findColoring() throws IOException {
		value = 0;
		colors = new int[nodeCount];
		// useNaiveApproach();
		// useCPExternalTool();
		// greedy();
		// localSearchTry();
		feasibleSolutionSearchTry();
	}

	private static void greedy() {
		ArrayList<Integer>[] arrayOfConnectedNodes = createConnectedNodesArray();
		for (int i = 0; i < 2; i++) {
			runIteration(arrayOfConnectedNodes);
		}
	}

	private static void runBackwardIteration(
			ArrayList<Integer>[] arrayOfConnectedNodes) {
		value = 0;
		for (int i = nodeCount - 1; i >= 0; i--) {
			colors[i] = findMinimalColorFor(arrayOfConnectedNodes[i], value);
			if (colors[i] > value) {
				value = colors[i];
			}
		}
		value++;
	}

	private static void runIteration(ArrayList<Integer>[] arrayOfConnectedNodes) {
		value = 0;
		for (int i = 1; i < nodeCount; i++) {
			colors[i] = findMinimalColorFor(arrayOfConnectedNodes[i], value);
			if (colors[i] > value) {
				value = colors[i];
			}
		}
		value++;
	}

	private static void updateVertix(int vertixToUpdate, int newColor) {
		colors[vertixToUpdate] = newColor;
	}

	private static boolean[] updateColor(int colorToUpdate, int newValue) {
		boolean[] updated = new boolean[nodeCount];
		for (int i = 0; i < nodeCount; i++) {
			if (colors[i] == colorToUpdate) {
				colors[i] = newValue;
				updated[i] = true;
			}
		}
		return updated;
	}

	private static int getColorValue() {
		int colorVal = 0;
		int valueOfColorToOptimize = Integer.MIN_VALUE;
		// updateValue();
		for (int color = 0; color < value + 1; color++) {
			int colorValue = getValueContributionOf(color);
			colorVal += colorValue;
			if (colorValue > valueOfColorToOptimize && colorValue != 0) {
				valueOfColorToOptimize = colorValue;
				colorToOptimize = color;
			}
		}
		return colorVal;
	}

	private static int getValueContributionOf(int color) {
		int Ci = getColorVerticesCount(color);
		int Bi = getBadEdgesCount(color);
		return Ci * (2 * Bi - Ci);
	}

	private static void updateValue() {
		value = 0;
		final int max_len = 200;
		boolean[] seen = new boolean[max_len];
		for (int j = 0; j < nodeCount; j++) {
			seen[colors[j]] = true;
		}

		for (int j = 0; j < max_len; j++) {
			if (seen[j]) {
				value++;
			}
		}

	}

	private static boolean isValidSolution() {
		for (int j = 0; j < nodeCount; j++) {
			if (getBadEdgesCount(colors[j]) > 0) {
				return false;
			}
		}
		return true;
	}

	private static int getBadEdgesCount(int color) {
		int cnt = 0;
		for (int j = 0; j < edgeCount; j++) {
			if (colors[edges[j].fromNode] == color
					&& colors[edges[j].toNode] == color) {
				cnt++;
			}
		}
		return cnt;
	}

	private static int getColorVerticesCount(int color) {
		int cnt = 0;
		for (int j = 0; j < nodeCount; j++) {
			if (colors[j] == color) {
				cnt++;
			}
		}
		return cnt;
	}

	private static int findMinimalColorFor(ArrayList<Integer> neighbors,
			int maxVal) {
		for (int color = 0; color <= maxVal; color++) {
			boolean found = true;
			for (Integer neighbor : neighbors) {
				if (colors[neighbor] == color) {
					found = false;
					break;
				}
			}
			if (found) {
				return color;
			}
		}
		return maxVal + 1;
	}

	private static void feasibleSolutionSearchTry() {
		int iteration = 0;
		greedy();
		optimizationValue = value / 3;
		for (int j = 0; j < nodeCount; j++) {
			colors[j] = 0;
		}
		value = optimizationValue;
		startedTime = System.currentTimeMillis();
		ArrayList<Integer>[] connectedVertixes = createConnectedNodesArray();
		int totalViolations = getTotalViolations(connectedVertixes);

		while (true) {
			printIntermidiateSolution(iteration, totalViolations);
			if (isValidSolution()) {
				return;
			}
			iteration++;
			newAllowdedCollors = iteration / 10000;
			value = optimizationValue + newAllowdedCollors;
			VertixViolation v = selectVertixToUpdate(connectedVertixes);
			while (v.violations == 0) {
				v = selectVertixToUpdate(connectedVertixes);
			}
			totalViolations -= v.violations;
			int violationsLeftForVertix = updateVertixToMinimumViolations(
					v.vertix, connectedVertixes[v.vertix]);
			totalViolations += violationsLeftForVertix;
		}
	}

	private static int updateVertixToMinimumViolations(int vertix,
			ArrayList<Integer> connectedVertixes) {
		int totalCollorsToSelectFrom = optimizationValue + newAllowdedCollors;
		int minNumOfViolations = nodeCount;
		if (getRandomIndex(10) == 9) {
			int randomColor = getRandomIndex(totalCollorsToSelectFrom);
			minNumOfViolations = getNumberOfViolations(randomColor,
					connectedVertixes);
			updateVertix(vertix, randomColor);
		} else {
			int numOfViolations;
			int oldColor = colors[vertix];
			ArrayList<Integer> optional = new ArrayList<>();
			for (int i = 0; i < totalCollorsToSelectFrom; i++) {
				updateVertix(vertix, i);
				numOfViolations = getNumberOfViolations(i, connectedVertixes);
				if (numOfViolations < minNumOfViolations) {
					optional.clear();
					minNumOfViolations = numOfViolations;
				}
				if (numOfViolations == minNumOfViolations) {
					optional.add(i);
				}
				updateVertix(vertix, oldColor);
			}
			int randomIndex = getRandomIndex(optional.size());
			updateVertix(vertix, optional.get(randomIndex));
		}
		return minNumOfViolations;
	}

	private static VertixViolation selectVertixToUpdate(
			ArrayList<Integer>[] connectedVertixes) {
		int randomVertix = getRandomIndex(nodeCount);
		VertixViolation maxNumOfViolations = new VertixViolation(randomVertix,
				getNumberOfViolations(colors[randomVertix],
						connectedVertixes[randomVertix]));
		if (getRandomIndex(3) < 2) {
			int numOfViolations;
			ArrayList<Integer> optional = new ArrayList<>();
			for (int i = 0; i < nodeCount; i++) {
				numOfViolations = getNumberOfViolations(colors[i],
						connectedVertixes[i]);
				if (numOfViolations > maxNumOfViolations.violations) {
					optional.clear();
					maxNumOfViolations = new VertixViolation(i, numOfViolations);
				}
				if (numOfViolations == maxNumOfViolations.violations) {
					optional.add(i);
				}
			}
			int randomIndex = getRandomIndex(optional.size());
			maxNumOfViolations = new VertixViolation(optional.get(randomIndex),
					maxNumOfViolations.violations);
		}
		return maxNumOfViolations;
	}

	private static int getRandomIndex(int n) {
		return (int) (Math.random() * (n));
	}

	private static void printIntermidiateSolution(int iteration,
			int totalViolations) {
		System.out.println("Itreration :" + iteration);
		printOutput();
		System.out.println("Colors :" + value);
		System.out.println("Total violations are : " + totalViolations);
		long tEnd = System.currentTimeMillis();
		long tDelta = tEnd - startedTime;
		double elapsedSeconds = tDelta / 1000.0;
		System.out.println("Time passed : " + elapsedSeconds);
	}

	private static int getTotalViolations(ArrayList<Integer>[] connectedVertixes) {

		int violations = 0;
		for (int i = 0; i < nodeCount; i++) {
			violations += getNumberOfViolations(colors[i], connectedVertixes[i]);
		}
		return violations / 2;
	}

	private static int getNumberOfViolations(int color,
			ArrayList<Integer> neighbors) {
		int violations = 0;
		for (int neighbor : neighbors) {
			if (colors[neighbor] == color) {
				violations++;
			}
		}
		return violations;
	}

	private static void localSearchTry() {
		int prevValue = Integer.MAX_VALUE;
		greedy();
		startedTime = System.currentTimeMillis();
		// useNaiveApproach();
		// System.out.println("Greedy :");
		// printOutput();

		final int iterations = 10000;
		int iteration = 0;
		while (true) {
			boolean isValidSolution = isValidSolution();
			int colorValue = getColorValue();
			printIntermidiateSolution(iteration, isValidSolution, colorValue);
			// if ((colorValue == prevValue || iteration >= iterations)
			updateValue();
			if (value <= optimizationValue && isValidSolution) {
				return;
			}
			if (iteration % 10 == 1) {
				int randomColortoReplace = getRandomIndex(value);
				int newRandomColor = getRandomIndex(value);
				updateColor(randomColortoReplace, newRandomColor);
				//
				// int randomVertix = (int) (Math.random() * (nodeCount - 1));
				// updateVertix(randomVertix, randomColor);
			}
			iteration++;
			prevValue = colorValue;
			// int color = colorToOptimize;
			// ValueColor val = selectBestValueFor(color);
			// updateColor(color,val.color);

			// selectBestPossibleValue();
			selectValueForEachVertix();
		}
	}

	private static void printIntermidiateSolution(int iteration,
			boolean isValidSolution, int colorValue) {
		System.out.println("Itreration :" + iteration);
		printOutput();

		if (!isValidSolution) {
			System.out.println("Not Valid !!!");
		}
		System.out.println("Coloring value is : " + colorValue);
		long tEnd = System.currentTimeMillis();
		long tDelta = tEnd - startedTime;
		double elapsedSeconds = tDelta / 1000.0;
		System.out.println("Time passed : " + elapsedSeconds);
	}

	private static void selectValueForEachVertix() {
		for (int vertix = 0; vertix < nodeCount; vertix++) {
			updateValue();
			ValueColor val = selectBestValueFor(vertix);
			updateVertix(vertix, val.color);
		}

	}

	private static void selectBestPossibleValue() {
		updateValue();
		int bestOptimizePotentialVertix = 0;
		ValueColor bestValue = new ValueColor(Integer.MAX_VALUE, value);
		for (int vertix = 0; vertix < nodeCount; vertix++) {
			ValueColor val = selectBestValueFor(vertix);
			if (val.value < bestValue.value) {
				bestOptimizePotentialVertix = vertix;
				bestValue = new ValueColor(val.value, val.color);
			}

		}
		updateVertix(bestOptimizePotentialVertix, bestValue.color);
		// updateColor(bestOptimizePotentialColor, bestValue.color);
	}

	private static ValueColor selectBestValueFor(int vertixForOptimization) {
		// int currentBest = vertixForOptimization;
		int bestValue = Integer.MAX_VALUE;
		int origColor = colors[vertixForOptimization];
		int origContribution = getValueContributionOf(origColor);
		// updateValue();
		ArrayList<Integer> optional = new ArrayList<>();
		for (int color = 0; color <= value; color++) {
			// boolean[] updated = updateColor(colorForOptimization, color);
			int potentialColorValue = getValueContributionOf(color);
			updateVertix(vertixForOptimization, color);
			// int colorValue = getColorValue();
			int newColorValue = getValueContributionOf(color);
			int oldColorValue = getValueContributionOf(origColor);
			int colorValue = newColorValue + oldColorValue - origContribution
					- potentialColorValue;
			if (colorValue < bestValue) {
				optional.clear();
				// currentBest = color;
				bestValue = colorValue;
			}
			if (colorValue == bestValue) {
				optional.add(color);
			}
			updateVertix(vertixForOptimization, origColor);
			// updateBack(colorForOptimization, updated);
		}
		int randomIndex = getRandomIndex(optional.size());
		return new ValueColor(bestValue, optional.get(randomIndex));
	}

	private static void updateBack(int colorToOptimize, boolean[] updated) {
		for (int i = 0; i < nodeCount; i++) {
			if (updated[i]) {
				colors[i] = colorToOptimize;
			}
		}
	}

	private static void useCPExternalTool() throws IOException {
		PrintStream ps = new PrintStream("\\file.txt");
		PrintStream orig = System.out;
		System.setOut(ps);

		Store store = new Store(); // define FD store
		// define finite domain variables
		IntVar[] v = new IntVar[nodeCount];
		for (int i = 0; i < nodeCount; i++)
			v[i] = new IntVar(store, "v" + i, 1, 16);

		// TODO : arrayOfNodesFrom !!!!!
		// ArrayList<Integer>[] arrayOfNodesFrom = createNodesFromArray();
		// IntVar[] from = new IntVar[nodeCount];
		// for (int i = 0; i < nodeCount; i++)
		// {
		// from[i] = new IntVar(store, "e" + i, 1, nodeCount);
		// store.impose(new XeqY(v[from[i]], v[edges[i].toNode]));
		// }
		// Constraint alldiff = new Alldifferent(from);
		// store.impose(alldiff);

		// define constraints
		for (int i = 0; i < edgeCount; i++) {
			store.impose(new XneqY(v[edges[i].fromNode], v[edges[i].toNode]));
		}

		// for (int i = 0; i < nodeCount; i++) {
		// for (int j = i+1; j < nodeCount; j++) {
		// store.impose(new XlteqY(v[i], v[j]));
		// }
		//
		// }

		store.impose(new XeqC(v[0], 1));

		// IntVar maxColorNumber = new IntVar(store, "c", 16, 16);
		// Constraint ctr = new Max(v, maxColorNumber);
		// store.impose(ctr);

		// search for a solution and print results
		Search<IntVar> search = new DepthFirstSearch<IntVar>();
		SelectChoicePoint<IntVar> select = new InputOrderSelect<IntVar>(store,
				v, new IndomainMin<IntVar>());
		// search.setOptimize(true);
		// search.getSolutionListener().searchAll(true);
		search.setTimeOut(9000);
		if (search.labeling(store, select)) {
			for (int i = 0; i < nodeCount; i++) {
				colors[i] = v[i].value() - 1;
				if (value < colors[i]) {
					value = colors[i];
				}
			}
			value++;
		} else {
			value = -1;
		}

		System.setOut(orig);
		ps.close();

	}

	private static ArrayList<Integer>[] createNodesFromArray() {
		ArrayList<Integer>[] arrayOfNodesFrom = (ArrayList<Integer>[]) new ArrayList[nodeCount];
		for (int i = 0; i < nodeCount; i++) {
			arrayOfNodesFrom[i] = new ArrayList<>();
			for (int j = 0; j < edgeCount; j++) {

				if (edges[j].fromNode == i) {
					arrayOfNodesFrom[i].add(edges[j].toNode);
				}
			}
		}

		return arrayOfNodesFrom;
	}

	private static ArrayList<Integer>[] createConnectedNodesArray() {
		ArrayList<Integer>[] arrayOfConnectedNodes = (ArrayList<Integer>[]) new ArrayList[nodeCount];
		for (int i = 0; i < nodeCount; i++) {
			arrayOfConnectedNodes[i] = new ArrayList<>();
			for (int j = 0; j < edgeCount; j++) {

				if (edges[j].fromNode == i) {
					if (!arrayOfConnectedNodes[i].contains(edges[j].toNode)) {
						arrayOfConnectedNodes[i].add(edges[j].toNode);
					}
				}

				if (edges[j].toNode == i) {
					if (!arrayOfConnectedNodes[i].contains(edges[j].fromNode)) {
						arrayOfConnectedNodes[i].add(edges[j].fromNode);
					}
				}
			}
		}

		return arrayOfConnectedNodes;
	}

	private static void useNaiveApproach() {
		value = nodeCount;
		for (int i = 0; i < nodeCount; i++) {
			colors[i] = i;
		}
	}

}
