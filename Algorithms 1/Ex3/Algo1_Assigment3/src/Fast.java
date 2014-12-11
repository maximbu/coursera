import java.util.Arrays;

public class Fast {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		//String filename = "D:\\Studies\\Alg1\\Ex3\\Tests\\collinear-testing\\collinear\\input8.txt";
		//Point[] points = readPoints(filename);
		Point[] points = readPoints(args[0]);
		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);
		findSegments(points);

	}

	private static void findSegments(Point[] points) {
		int n = points.length;
		Point[] origArray = copyArray(points);
		Arrays.sort(origArray);
		for (int i = 0; i < n; i++) {
			Point p = origArray[i];
			Arrays.sort(points, p.SLOPE_ORDER);
			checkPoints(p, points);
		}
	}

	private static Point[] copyArray(Point[] orig) {
		// Construct array
		Point[] newArr = new Point[orig.length];

		// Copy element by element using for loop
		for (int i = 0; i < newArr.length; i++) {
			newArr[i] = orig[i];
		}

		return newArr;
	}

	private static Point[] copySubarrayAndAdd(Point[] orig, int srcIndex,
			int length, Point p) {
		Point[] newArr = new Point[length + 1];
		for (int i = 0; i < length; i++) {
			newArr[i] = orig[srcIndex + i];
		}
		newArr[length] = p;
		return newArr;
	}

	private static void checkPoints(Point p, Point[] points) {
		int startInd = -1;
		int endInd = -1;
		double slope;
		int n = points.length;
		for (int i = 0; i < n;) {
			Point q = points[i];
			slope = q.slopeTo(p);
			startInd = i;
			for (int j = i + 1; j < n; j++) {
				i = j;
				double newSlope = points[j].slopeTo(p);
				if (newSlope == slope) {
					endInd = j;
				} else {
					break;
				}
			}
			if (endInd >= startInd + 2) {
				printPoints(p, points, startInd, endInd);
				// return;
			}
			if (i + 1 >= n) {
				return;
			}
		}
	}

	private static void printPoints(Point p, Point[] points, int startInd,
			int endInd) {
		Point[] arr = copySubarrayAndAdd(points, startInd, endInd - startInd
				+ 1, p);
		Arrays.sort(arr);
		if (arr[0] != p) {
			return;
		}

		StringBuilder st = new StringBuilder(arr[0].toString());
		for (int i = 1; i < arr.length; i++) {
			st.append(" -> " + arr[i].toString());
		}
		StdOut.println(st);

		arr[0].drawTo(arr[arr.length - 1]);
	}

	private static Point[] readPoints(String fileName) {
		In in = new In(fileName);
		int N = in.readInt();
		Point[] points = new Point[N];
		for (int i = 0; i < N; i++) {
			int x = in.readInt();
			int y = in.readInt();
			Point p = new Point(x, y);
			points[i] = p;
			p.draw();
		}
		return points;
	}

}
