import java.util.Arrays;

public class Brute {
	public static void main(String[] args) {
		// String filename =
		// "D:\\Studies\\Alg1\\Ex3\\Tests\\collinear-testing\\collinear\\input8.txt";
		// Point[] points = readPoints(filename);
		Point[] points = readPoints(args[0]);
		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);
		findSegments(points);
	}

	private static void findSegments(Point[] points) {
		int n = points.length;
		Arrays.sort(points);
		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				for (int k = j + 1; k < n; k++) {
					for (int l = k + 1; l < n; l++) {
						double slopeIJ = points[i].slopeTo(points[j]);
						double slopeKL = points[k].slopeTo(points[l]);
						if (slopeIJ == slopeKL) {
							double slopeJK = points[j].slopeTo(points[k]);
							if (slopeIJ == slopeJK) {
								printPoints(points, i, j, k, l);
							}
						}

					}
				}
			}
		}

	}

	private static void printPoints(Point[] points, int i, int j, int k, int l) {
		String st = String.format("%s -> %s -> %s -> %s", points[i].toString(),
				points[j].toString(), points[k].toString(),
				points[l].toString());
		StdOut.println(st);
		Point[] drawPoints = new Point[4];
		drawPoints[0] = points[i];
		drawPoints[1] = points[j];
		drawPoints[2] = points[k];
		drawPoints[3] = points[l];
		Arrays.sort(points);
		if(drawPoints[0] == points[i])
		{
			drawPoints[0].drawTo(drawPoints[3]);
		}
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