import java.util.Set;
import java.util.TreeSet;

public class PointSET {

	private Set<Point2D> tree;

	public PointSET() {
		tree = new TreeSet<Point2D>();
	}

	public boolean isEmpty() {
		return size() == 0;
	}

	public int size() {
		return tree.size();
	}

	public boolean contains(Point2D p) {
		return tree.contains(p);
	}

	public void insert(Point2D p) {
		if (!contains(p)) {
			tree.add(p);
		}
	}

	public void draw() {
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setPenRadius(.01);
		for (Point2D p : tree) {
			p.draw();
		}
	}

	public Point2D nearest(Point2D p) {
		double minDistance = Double.MAX_VALUE;
		Point2D closestPoint = null;
		for (Point2D point : tree) {
			double distance = point.distanceSquaredTo(p);
			if (distance < minDistance) {
				minDistance = distance;
				closestPoint = point;
			}
		}
		return closestPoint;
	}

	public Iterable<Point2D> range(RectHV rect) {
		Queue<Point2D> foundPoints = new Queue<Point2D>();
		for (Point2D p : tree) {
			if (rectContainsPoint(rect, p)) {
				foundPoints.enqueue(p);
			}
		}
		return foundPoints;
	}

	private boolean rectContainsPoint(RectHV rect, Point2D p) {
		return (p.x() >= rect.xmin() && p.x() <= rect.xmax()
				&& p.y() >= rect.ymin() && p.y() <= rect.ymax());
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
