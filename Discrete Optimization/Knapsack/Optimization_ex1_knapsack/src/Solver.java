import java.io.*;
import java.util.Arrays;
import java.util.BitSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The class <code>Solver</code> is an implementation of a greedy algorithm to
 * solve the knapsack problem.
 * 
 */
public class Solver {

	private static int[] taken;
	private static int value;
	private static int weight;
	private static WeightedData[] optimized;
	private static double bestPossible;
	private static int capacity;
	private static int items;
	private static int[] values;
	private static int[] weights;

	/**
	 * The main class
	 */
	public static void main(String[] args) {
		try {
			if (args.length == 0) {
				String[] testArgs = new String[1];
				testArgs[0] = "-file=D:\\Studies\\Optimization\\Knapsack\\Optimization_ex1_knapsack\\bin\\data\\ks_400_0";
				// testArgs[0] =
				// "-file=D:\\Studies\\Optimization\\Knapsack\\Optimization_ex1_knapsack\\bin\\data\\max";
				solve(testArgs);
				System.out.println("best possible :" + bestPossible);
				System.out.println("capacity is :" + capacity);
				System.out.println("weight is :" + weight);
			} else {
				solve(args);
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	/**
	 * Read the instance, solve it, and print the solution in the standard
	 * output
	 */
	public static void solve(String[] args) throws IOException {
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
		items = Integer.parseInt(firstLine[0]);
		capacity = Integer.parseInt(firstLine[1]);

		values = new int[items];
		weights = new int[items];

		for (int i = 1; i < items + 1; i++) {
			String line = lines.get(i);
			String[] parts = line.split("\\s+");

			values[i - 1] = Integer.parseInt(parts[0]);
			weights[i - 1] = Integer.parseInt(parts[1]);
		}

		runSolver();
	}

	private static void runSolver() {
		value = 0;
		weight = 0;
		taken = new int[items];
		bestPossible = 0;

		// branchWithRelaxationDFS();
		// dynamicProgramming();
		// branchWithRelaxationPQ();
		// branchWithRelaxationPQBetterNodeVer();
		// branchWithRelaxationDFSBetterNodeVer();
		dynamicProgramming3();
		// greedyByValuePerWeight(items, capacity, values, weights);
		// solveExt();

		// prepare the solution in the specified output format
		System.out.println(value + " 1");
		for (int i = 0; i < items; i++) {
			System.out.print(taken[i] + " ");
		}
		System.out.println("");
	}

	private static void solveExt() {
		KnapsackBandB ext = new KnapsackBandB(items, weights, values, capacity);
		ext.solve();
		value = ext.getMaxValue();

	}

	private static void greedyByValuePerWeight() {

		createWeightedInput(items, values, weights);

		for (int i = items - 1; i >= 0; i--) {
			if (weight + optimized[i].getWeight() <= capacity) {
				optimized[i].setTaken(1);
				value += optimized[i].getValue();
				weight += optimized[i].getWeight();
			} else {
				optimized[i].setTaken(0);
			}
			taken[optimized[i].getIndex()] = (int) optimized[i].getTaken();
		}

		bestPossible = findBestPossible(capacity);
	}

	private static void createWeightedInput(int items, int[] values,
			int[] weights) {
		optimized = new WeightedData[items];
		for (int i = 0; i < items; i++) {
			optimized[i] = new WeightedData(i, weights[i], values[i]);
		}
		Arrays.sort(optimized);
	}

	private static int findBestPossible(int capacity) {
		int leftWeight = capacity;
		int bestPossible = 0;
		for (int i = optimized.length - 1; i >= 0; i--) {
			if (leftWeight > optimized[i].getWeight()) {
				bestPossible += optimized[i].getValue();
				leftWeight -= optimized[i].getWeight();
			} else {
				bestPossible += leftWeight * optimized[i].getCalculatedValue();
			}
		}
		return bestPossible;
	}

	private static void dynamicProgramming() {
		DPCell[][] dpTable = new DPCell[capacity + 1][items + 1];

		for (int i = 0; i < capacity + 1; i++) {
			dpTable[i][0] = new DPCell(0, 0);
		}

		for (int j = 1; j < items + 1; j++) {
			int itemWeight = weights[j - 1];
			int itemVal = values[j - 1];
			for (int i = 0; i < capacity + 1; i++) {
				DPCell without = dpTable[i][j - 1];
				DPCell with = without;
				if (i >= itemWeight) {
					DPCell cellToCompare = dpTable[i - itemWeight][j - 1];
					if (cellToCompare.weightSoFar + itemWeight <= i) {
						int withVal = cellToCompare.cellValue + itemVal;
						int withWeightSoFar = cellToCompare.weightSoFar
								+ itemWeight;
						with = new DPCell(withVal, withWeightSoFar);
					}
				}
				if (with.cellValue > without.cellValue)
					dpTable[i][j] = with;
				else
					dpTable[i][j] = without;
			}
		}

		DPCell bestCell = dpTable[capacity][items];
		int leftCapacity = bestCell.weightSoFar;
		for (int j = items; j > 0; j--) {
			if (dpTable[leftCapacity][j].cellValue != dpTable[leftCapacity][j - 1].cellValue) {
				taken[j - 1] = 1;
				leftCapacity -= weights[j - 1];
				value += values[j - 1];
				weight += weights[j - 1];
			}
		}
	}

	private static void dynamicProgramming3() {

		int[] dpRow = new int[capacity + 1];
		int[] weightSoFar = new int[capacity + 1];
		BitSet[] selected = new BitSet[capacity + 1];

		for (int i = 0; i < capacity + 1; i++) {
			dpRow[i] = 0;
			weightSoFar[i] = 0;
			selected[i] = new BitSet(items);
		}

		for (int j = 1; j < items + 1; j++) {
			int itemInd = j - 1;
			int itemWeight = weights[itemInd];
			int itemVal = values[itemInd];

			for (int i = capacity; i >= 0; i--) {

				// without
				boolean toSelect = false;

				if (i >= itemWeight) {
					// with
					int tempVal = dpRow[i - itemWeight];
					if (weightSoFar[i - itemWeight] + itemWeight <= i) {
						if (dpRow[i] < tempVal + itemVal) {
							weightSoFar[i] = itemWeight
									+ weightSoFar[i - itemWeight];
							selected[i] = (BitSet) selected[i - itemWeight]
									.clone();
							dpRow[i] = tempVal + itemVal;
							toSelect = true;
						}
					}
				}

				selected[i].set(itemInd, toSelect);

			}

		}

		value = dpRow[capacity];

		for (int i = 0; i < items; i++) {
			if (selected[capacity].get(i) == true) {
				taken[i] = 1;
			} else {
				taken[i] = 0;
			}
		}
	}

	private static void tryMinimizeInput() {
		// TODO Auto-generated method stub
		createWeightedInput(items, values, weights);
		LinkedList<WeightedData> bestInputData = new LinkedList<WeightedData>();
		for (int i = 0; i < items; i++) {
			if (!shouldFilterOut(optimized[i])) {
				bestInputData.add(optimized[i]);
			}
		}

		double bestValues = 0;
		double bestWeight = 0;

		for (WeightedData node : bestInputData) {
			bestValues += node.getValue();
			bestWeight += node.getWeight();

		}

		items = bestInputData.size();
		weights = new int[items];
		values = new int[items];

		int i = 0;
		for (WeightedData node : bestInputData) {
			weights[i] = node.getWeight();
			values[i] = node.getValue();
			i++;
		}
	}

	private static boolean shouldSelect(WeightedData item) {
		for (int j = 0; j < items; j++) {
			if (item.getWeight() > optimized[j].getWeight()
					&& item.getValue() < optimized[j].getValue()) {
				return false;
			}
		}
		return true;
	}

	private static boolean shouldFilterOut(WeightedData item) {
		for (int j = 0; j < items; j++) {
			if (item.getWeight() > optimized[j].getWeight()
					&& item.getValue() < optimized[j].getValue()) {
				return true;
			}
		}
		return false;
	}

	public static String encode(String source) {
		StringBuffer dest = new StringBuffer();
		for (int i = 0; i < source.length(); i++) {
			int runLength = 1;
			while (i + 1 < source.length()
					&& source.charAt(i) == source.charAt(i + 1)) {
				runLength++;
				i++;
			}
			dest.append(runLength);
			dest.append(source.charAt(i));
		}
		return dest.toString();
	}

	public static String decode(String source) {
		StringBuffer dest = new StringBuffer();
		Pattern pattern = Pattern.compile("[0-9]+|[a-zA-Z]");
		Matcher matcher = pattern.matcher(source);
		while (matcher.find()) {
			int number = Integer.parseInt(matcher.group());
			matcher.find();
			while (number-- != 0) {
				dest.append(matcher.group());
			}
		}
		return dest.toString();
	}

	private static void dynamicProgramming2() {
		NewDPCell[] dpRow = new NewDPCell[capacity + 1];
		NewDPCell[] dpNewRow = new NewDPCell[capacity + 1];

		for (int i = 0; i < capacity + 1; i++) {
			dpRow[i] = new NewDPCell(0, null, false, -1);
			dpNewRow[i] = new NewDPCell(0, null, false, -1);
		}

		for (int j = 1; j < items + 1; j++) {
			int itemInd = j - 1;
			int itemWeight = weights[itemInd];
			int itemVal = values[itemInd];

			for (int i = 0; i < capacity + 1; i++) {
				dpRow[i].update(dpNewRow[i]);
			}

			for (int i = 0; i < capacity + 1; i++) {

				// without
				int newVal = dpRow[i].cellValue;
				NewDPCell cell = dpRow[i];
				boolean toSelect = false;

				if (i >= itemWeight) {
					// with
					NewDPCell tempCell = dpRow[i - itemWeight];
					if (weightSoFar(tempCell) + itemWeight <= i) {
						if (newVal < tempCell.cellValue + itemVal) {
							cell = tempCell;
							newVal = tempCell.cellValue + itemVal;
							toSelect = true;
						}
					}
				}

				dpNewRow[i].update(newVal, cell, toSelect, itemInd);

			}

		}

		value = dpNewRow[capacity].cellValue;

		for (int i = 0; i < items; i++) {
			if (dpNewRow[capacity].taken.get(i) == true) {
				taken[i] = 1;
			} else {
				taken[i] = 0;
			}
		}
	}

	private static int weightSoFar(NewDPCell cell) {
		int soFar = 0;
		if (cell.taken == null)
			return 0;
		for (int i = 0; i < items; i++) {
			if (cell.taken.get(i) == true) {
				soFar += weights[i];
			}
		}
		return soFar;
	}

	// private static void branchWithRelaxationPQBetterNodeVer() {
	// NodeComparator nc = new NodeComparator();
	// PriorityQueue<BetterTreeNode> pq = new PriorityQueue<BetterTreeNode>(
	// items, nc);
	// //
	// createWeightedInput(items, values, weights);
	// BetterTreeNode root = new BetterTreeNode(-1, 0, capacity,
	// findBestPossible(capacity), null, false);
	//
	// pq.add(root);
	//
	// BetterTreeNode current = root;
	// BetterTreeNode bestFoundNode = root;
	//
	// while (!pq.isEmpty()) {
	// current = pq.poll();
	// if (shouldProceed(current, bestFoundNode)) {
	// // process current Node
	// if (bestFoundNode.value < current.value) {
	// bestFoundNode = new BetterTreeNode(current);
	// }
	//
	// int index = current.index + 1;
	// if (index < items) {
	//
	// // with
	// int newRoom = current.roomLeft - weights[index];
	// double newOptimalRelaxation = current.opt;
	// if (newRoom > 0
	// && newOptimalRelaxation > bestFoundNode.value) {
	// current.withNext = new BetterTreeNode(index,
	// current.value + values[index], newRoom,
	// newOptimalRelaxation, current, true);
	// pq.add(current.withNext);
	// }
	// // without
	// newRoom = current.roomLeft;
	// newOptimalRelaxation = calculateOptimalRelaxationNew(current);
	// if (newRoom > 0
	// && newOptimalRelaxation > bestFoundNode.value) {
	// current.withoutNext = new BetterTreeNode(index,
	// current.value, newRoom, newOptimalRelaxation,
	// current, false);
	// pq.add(current.withoutNext);
	// }
	// }
	// }
	// current = null;
	// }
	// createSolution(bestFoundNode);
	// }

	private static void branchWithRelaxationDFSBetterNodeVer() {

		java.util.Stack<BetterTreeNode> stack = new java.util.Stack<BetterTreeNode>();
		createWeightedInput(items, values, weights);
		BetterTreeNode root = new BetterTreeNode(-1, 0, capacity,
				findBestPossible(capacity), null, false);

		stack.push(root);

		BetterTreeNode current = root;
		BetterTreeNode bestFoundNode = root;

		while (!stack.isEmpty()) {
			while (current != null && shouldProceed(current, bestFoundNode)) {
				// process current Node
				if (bestFoundNode.value < current.value) {
					bestFoundNode = new BetterTreeNode(current);
				}

				int index = current.index + 1;
				BetterTreeNode newCurrent = null;
				if (index < items) {

					// with
					int newRoom = current.roomLeft - weights[index];
					double newOptimalRelaxation = calculateOptimalRelaxationNew(
							current, true);

					if (newRoom > 0
							&& newOptimalRelaxation > bestFoundNode.value) {
						newCurrent = new BetterTreeNode(index, current.value
								+ values[index], newRoom, newOptimalRelaxation,
								current, true);
					}

					// without
					newRoom = current.roomLeft;

					newOptimalRelaxation = calculateOptimalRelaxationNew(
							current, false);
					if (newRoom > 0
							&& newOptimalRelaxation > bestFoundNode.value) {

						stack.push(new BetterTreeNode(index, current.value,
								newRoom, newOptimalRelaxation, current, false));
					}

				}

				current = newCurrent;
			}

			current = stack.pop();
		}

		createSolution(bestFoundNode);
	}

	private static void createSolution(BetterTreeNode bestFoundNode) {
		for (int i = 0; i < bestFoundNode.index + 1; i++) {
			if (bestFoundNode.taken.get(i) == true) {
				taken[i] = 1;
				value += values[i];
			} else {
				taken[i] = 0;
			}
		}
	}

	private static double calculateOptimalRelaxationNew(BetterTreeNode current,
			boolean takeNew) {
		int newValues[] = values.clone();
		int newWeights[] = weights.clone();
		for (int i = 0; i < current.index + 1; i++) {
			if (current.taken.get(i) == false) {
				newValues[i] = 0;
				newWeights[i] = 0;
			}
		}

		if (takeNew == false) {
			newValues[current.index + 1] = 0;
			newWeights[current.index + 1] = 0;
		}

		return calculateNewRelaxaition(capacity, newValues, newWeights);
	}

	private static void branchWithRelaxationPQ() {
		TreeNode root = createRoot();
		NodeComparator nc = new NodeComparator();
		PriorityQueue<TreeNode> pq = new PriorityQueue<TreeNode>(items, nc);
		//
		pq.add(root);

		TreeNode current = root;
		TreeNode bestFoundNode = root;

		while (!pq.isEmpty()) {
			current = pq.poll();
			if (shouldProceed(current, bestFoundNode)) {
				// process current Node
				if (bestFoundNode.value < current.value) {
					bestFoundNode = current;
				}

				int index = current.index + 1;
				if (index < items) {

					// with
					int newRoom = current.roomLeft - weights[index];
					double newOptimalRelaxation = current.optimalRelaxaition;
					if (newRoom > 0
							&& newOptimalRelaxation > bestFoundNode.value) {
						current.withNext = new TreeNode(index, current.value
								+ values[index], newRoom, newOptimalRelaxation,
								current);
						pq.add(current.withNext);
					}
					// without
					newRoom = current.roomLeft;
					newOptimalRelaxation = calculateOptimalRelaxation2(current,
							index);
					if (newRoom > 0
							&& newOptimalRelaxation > bestFoundNode.value) {
						current.withoutNext = new TreeNode(index,
								current.value, newRoom, newOptimalRelaxation,
								current);
						pq.add(current.withoutNext);
					}
				}
			}
			current = null;
		}
		updateSolution(root, bestFoundNode);
	}

	private static void branchWithRelaxationDFS() {
		TreeNode root = createRoot();
		java.util.Stack<TreeNode> st = new java.util.Stack<TreeNode>();
		// st.push(root);

		TreeNode current = root;
		TreeNode bestFoundNode = root;

		while (true) {
			while (current != null && shouldProceed(current, bestFoundNode)) {
				// process current Node
				if (bestFoundNode.value < current.value) {
					bestFoundNode = current;
				}

				int index = current.index + 1;
				if (index < items) {

					current.withNext = new TreeNode(index, current.value
							+ values[index], current.roomLeft - weights[index],
							current.optimalRelaxaition, current);

					if (!shouldProceed(current.withNext, bestFoundNode)) {
						current.withNext = null;
					}

					current.withoutNext = new TreeNode(index, current.value,
							current.roomLeft, 0, current);
					calculateOptimalRelaxation(current.withoutNext);

					if (!shouldProceed(current.withoutNext, bestFoundNode)) {
						current.withoutNext = null;
					}

				}

				TreeNode better;
				TreeNode worse;
				if (current.withoutNext != null
						&& current.withNext != null
						&& current.withoutNext.optimalRelaxaition > current.withNext.optimalRelaxaition) {
					better = current.withoutNext;
					worse = current.withNext;
				} else {
					better = current.withNext;
					worse = current.withoutNext;
				}

				st.push(worse);
				current = better;

			}
			if (st.isEmpty()) {
				updateSolution(root, bestFoundNode);
				return;
			}
			current = st.pop();
			// current=current.withoutNext;
		}

	}

	private static void updateSolution(TreeNode root, TreeNode bestFoundNode) {
		while (bestFoundNode != root) {
			if (bestFoundNode.parent.withNext == bestFoundNode) {
				int ind = bestFoundNode.index;
				taken[ind] = 1;
				value += values[ind];
				weight += weights[ind];
			}
			bestFoundNode = bestFoundNode.parent;
		}
	}

	private static void branchWithRelaxationBFS() {
		TreeNode root = createRoot();
		Stack<TreeNode> st = new Stack<TreeNode>();
		st.push(root);

		TreeNode current = null;
		TreeNode bestFoundNode = root;

		while (!st.isEmpty()) {
			current = st.pop();

			if (!shouldProceed(current, bestFoundNode))
				continue;

			if (bestFoundNode.value < current.value) {
				bestFoundNode = current;
			}

			int index = current.index + 1;
			if (index >= items) {
				continue;
			}

			int newRoom = current.roomLeft - weights[index];
			double newOptimalRelaxation = current.optimalRelaxaition;

			if (newRoom > 0 && newOptimalRelaxation > bestFoundNode.value) {
				st.push(new TreeNode(index, current.value + values[index],
						newRoom, newOptimalRelaxation, current));
			}

			newRoom = current.roomLeft;
			newOptimalRelaxation = calculateOptimalRelaxation2(current, index);
			if (newRoom > 0 && newOptimalRelaxation > bestFoundNode.value) {
				st.push(new TreeNode(index, current.value, newRoom,
						newOptimalRelaxation, current));
			}

		}

		updateSolution(root, bestFoundNode);

	}

	private static void calculateOptimalRelaxation(TreeNode origNode) {

		int newValues[] = values.clone();
		int newWeights[] = weights.clone();
		TreeNode node = origNode;
		while (node.index != -1) {
			if (node.parent.withoutNext == node) {
				int ind = node.index;
				newValues[ind] = 0;
				newWeights[ind] = 0;
			}
			node = node.parent;
		}
		origNode.optimalRelaxaition = calculateNewRelaxaition(capacity,
				newValues, newWeights);
	}

	private static double calculateOptimalRelaxation2(TreeNode origNode,
			int newIndex) {

		int newValues[] = values.clone();
		int newWeights[] = weights.clone();
		newValues[newIndex] = 0;
		newWeights[newIndex] = 0;
		TreeNode node = origNode;
		while (node.index != -1) {
			if (node.parent.withoutNext == node) {
				int ind = node.index;
				newValues[ind] = 0;
				newWeights[ind] = 0;
			}
			node = node.parent;
		}
		return calculateNewRelaxaition(capacity, newValues, newWeights);
	}

	private static double calculateNewRelaxaition(int capacity,
			int[] newValues, int[] newWeights) {

		createWeightedInput(newValues.length, newValues, newWeights);
		return findBestPossible(capacity);
	}

	private static boolean shouldProceed(TreeNode current,
			TreeNode bestFoundNode) {
		return !(current.roomLeft < 0 || bestFoundNode.value > current.optimalRelaxaition);
	}

	private static boolean shouldProceed(BetterTreeNode current,
			BetterTreeNode bestFoundNode) {
		return !(current.roomLeft < 0 || bestFoundNode.value > current.opt);
	}

	private static TreeNode createRoot() {
		createWeightedInput(items, values, weights);
		return new TreeNode(-1, 0, capacity, findBestPossible(capacity), null);
	}

	private static void verySimpleButNotEfficient() {
		List<Integer> locTaken = new ArrayList<Integer>();
		value = badOneRec(items - 1, capacity, locTaken);
		for (int i = 0; i < items; i++) {
			taken[i] = locTaken.contains(i) ? 1 : 0;
		}
	}

	private static int badOneRec(int curItem, int capacity,
			List<Integer> locTaken) {
		if (curItem < 0) {
			return 0;
		} else if (weights[curItem] <= capacity) {
			int without = badOneRec(curItem - 1, capacity, locTaken);
			int with = values[curItem]
					+ badOneRec(curItem - 1, capacity - weights[curItem],
							locTaken);
			if (with > without) {
				locTaken.add(curItem);
				return with;
			} else {
				if (locTaken.contains(curItem)) {
					locTaken.remove((Object) curItem);
				}
				return without;
			}
		}

		if (locTaken.contains(curItem)) {
			locTaken.remove((Object) curItem);
		}
		return badOneRec(curItem - 1, capacity, locTaken);
	}

	private static void greedyDefault() {
		// a trivial greedy algorithm for filling the knapsack
		// it takes items in-order until the knapsack is full

		for (int i = 0; i < items; i++) {
			if (weight + weights[i] <= capacity) {
				taken[i] = 1;
				value += values[i];
				weight += weights[i];
			} else {
				taken[i] = 0;
			}
		}
	}
}