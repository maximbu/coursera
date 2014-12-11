public class KdTree {

	private static final boolean HORIZONTAL = true;

	private static class Node {
		private boolean orientation;
		private Point2D p; // the point
		private RectHV rect; // the axis-aligned rectangle corresponding to this
								// node
		private Node lb; // the left/bottom subtree
		private Node rt; // the right/top subtree

		public Node(Point2D p, RectHV rect, boolean orientation) {
			this.p = p;
			this.rect = rect;
			this.orientation = orientation;
		}
	}

	private Node root;

	private int size = 0;

	private Point2D nearest;

	public KdTree() {

	}

	public boolean isEmpty() {
		return size == 0;
	}

	public int size() {
		return size;
	}

	public boolean contains(Point2D p) {
		if (isEmpty()) {
			return false;
		}
		if (p.equals(root.p)) {
			return true;
		}
		return find(root, p, HORIZONTAL) != null;
	}

	private Node find(final Node node, final Point2D p, boolean orientation) {
		if (node == null) {
			return null;
		}
		if (p.equals(node.p)) {
			return node;
		}

		Node searchNode = null;

		if (isLess(p, node.p, orientation)) {
			searchNode = find(node.lb, p, flipOrientation(orientation));
		} else {
			searchNode = find(node.rt, p, flipOrientation(orientation));
		}
		return searchNode;
	}

	public void insert(Point2D p) {
		root = insert(root, null, p, HORIZONTAL);
	}

	private Node insert(Node node, Node parentNode, Point2D p,
			boolean orientation) {
		if (node == null) {
			size++;
			return new Node(p, createRect2(parentNode, p, orientation),
					orientation);
		}
		if (p.equals(node.p)) {
			return node;
		}
		if (isLess(p, node.p, orientation)) {
			node.lb = insert(node.lb, node, p, flipOrientation(orientation));
		} else {
			node.rt = insert(node.rt, node, p, flipOrientation(orientation));
		}
		return node;
	}


	private RectHV createRect2(Node parentNode, Point2D p, boolean orientation) {
		double rectXmin = 0.0;
	      double rectXmax = 1.0;
	      double rectYmin = 0.0;
	      double rectYmax = 1.0;
	      if (parentNode != null) {
	        if (HORIZONTAL == orientation) {
	          rectXmin = parentNode.rect.xmin();
	          rectXmax = parentNode.rect.xmax();
	          if (p.y() < parentNode.p.y()) {
	            rectYmin = parentNode.rect.ymin();
	            rectYmax = parentNode.p.y();
	          } else if (p.y() > parentNode.p.y()) {
	            rectYmin = parentNode.p.y();
	            rectYmax = parentNode.rect.ymax();
	          }
	        } else {
	          rectYmax = parentNode.rect.ymax();
	          rectYmin = parentNode.rect.ymin();
	          if (p.x() < parentNode.p.x()) {
	            rectXmin = parentNode.rect.xmin();
	            rectXmax = parentNode.p.x();
	          } else if (p.x() > parentNode.p.x()) {
	            rectXmin = parentNode.p.x();
	            rectXmax = parentNode.rect.xmax();
	          }
	        }
	      }
	        return new RectHV(rectXmin, rectYmin, rectXmax,
	                rectYmax);
	}

	private boolean isLess(Point2D p1, Point2D p2, boolean orientation) {
		return getKey(p1, orientation) < getKey(p2, orientation);
	}


	private double getKey(Point2D p, boolean orientation) {
		return HORIZONTAL == orientation ? p.x() : p.y();
	}

	private boolean flipOrientation(boolean orientation) {
		return !orientation;
	}

	private RectHV createRect(Node parentNode, Point2D p, boolean orientation) {
		return new RectHV(rectXMin(parentNode, p, orientation), rectYMin(
				parentNode, p, orientation), rectXMax(parentNode, p,
				orientation), rectYMax(parentNode, p, orientation));
	}

	private double rectXMin(Node parentNode, Point2D p, boolean orientation) {
		if (parentNode == null)
			return 0.0;
		if (HORIZONTAL == orientation || p.x() < parentNode.p.x()) {
			return parentNode.rect.xmin();
		}
		return parentNode.p.x();
	}

	private double rectXMax(Node parentNode, Point2D p, boolean orientation) {
		if (parentNode == null)
			return 1.0;
		if (HORIZONTAL == orientation || p.x() > parentNode.p.x()) {
			return parentNode.rect.xmax();
		}
		return parentNode.p.x();
	}

	private double rectYMin(Node parentNode, Point2D p, boolean orientation) {
		if (parentNode == null)
			return 0.0;
		if (HORIZONTAL != orientation || p.y() < parentNode.p.y()) {
			return parentNode.rect.ymin();
		}
		return parentNode.p.y();
	}

	private double rectYMax(Node parentNode, Point2D p, boolean orientation) {
		if (parentNode == null)
			return 1.0;
		if (HORIZONTAL != orientation || p.y() > parentNode.p.y()) {
			return parentNode.rect.ymax();
		}
		return parentNode.p.y();
	}

	public void draw() {
		//draw(root);
		drawPoint(root, null, HORIZONTAL);
	}
	
	
	 private void drawPoint(Node node, Node parentNode, boolean orient) {
		    if (node == null) {
		      return;
		    }
		    StdDraw.setPenColor(StdDraw.BLACK);
		    StdDraw.setPenRadius(.01);
		    node.p.draw();

		    StdDraw.setPenRadius();
		    StdDraw.setPenColor(HORIZONTAL == orient ? StdDraw.RED : StdDraw.BLUE);
		    if (HORIZONTAL == orient) {
		      if (parentNode != null) {
		        if (node.p.y() < parentNode.p.y()) {
		          new Point2D(node.p.x(), 0.0).drawTo(new Point2D(
		              node.p.x(), parentNode.p.y()));
		        } else if (node.p.y() > parentNode.p.y()) {
		          new Point2D(node.p.x(), parentNode.p.y())
		              .drawTo(new Point2D(node.p.x(), 1.0));
		        }
		      } else {
		        new Point2D(node.p.x(), 0.0).drawTo(new Point2D(node.p
		            .x(), 1.0));
		      }
		    } else {
		      if (parentNode != null) {
		        if (node.p.x() < parentNode.p.x()) {
		          new Point2D(0.0, node.p.y()).drawTo(new Point2D(
		              parentNode.p.x(), node.p.y()));
		        } else if (node.p.x() > parentNode.p.x()) {
		          new Point2D(parentNode.p.x(), node.p.y())
		              .drawTo(new Point2D(1.0, node.p.y()));
		        }
		      } else {
		        new Point2D(0.0, node.p.y()).drawTo(new Point2D(1.0,
		            node.p.y()));
		      }
		    }
		    drawPoint(node.lb, node, !orient);
		    drawPoint(node.rt, node, !orient);
		  }

	private void draw(final Node n) {
		if (n == null)
			return;
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setPenRadius(.01);
		StdDraw.point(n.p.x(), n.p.y());
		StdDraw.setPenRadius();

		if (n.orientation == HORIZONTAL)
			StdDraw.setPenColor(StdDraw.BLUE);
		else
			StdDraw.setPenColor(StdDraw.RED);
		final RectHV r = n.rect;
		StdDraw.line(r.xmin(), r.ymin(), r.xmax(), r.ymax());
		draw(n.lb);
		draw(n.rt);
	}

	public Point2D nearest(Point2D p) {
		if(root == null)
		{
			return null;
		}
		nearest = root.p;
	    nearestSearch(root, p, HORIZONTAL);
	    return nearest;
	}
	
	private void nearestSearch(Node node, Point2D queryPoint, boolean orient) {
	    if (node == null) {
	      return;
	    }
	    double distanceToFoundSoFar = nearest.distanceSquaredTo(queryPoint);
	    double distanceToCurNodeRect = node.rect.distanceSquaredTo(queryPoint);
	    if (distanceToFoundSoFar < distanceToCurNodeRect) {
	      return;
	    } else {
	      if (nearest.distanceSquaredTo(queryPoint) > node.p
	          .distanceSquaredTo(queryPoint)) {
	        nearest = node.p;
	      }
	      Node firstNode = null;
	      Node secondNode = null;
	      if (HORIZONTAL == orient) {
	        if (queryPoint.x() < node.p.x()) {
	          firstNode = node.lb;
	          secondNode = node.rt;
	        } else {
	          firstNode = node.rt;
	          secondNode = node.lb;
	        }
	      } else {
	        if (queryPoint.y() < node.p.y()) {
	          firstNode = node.lb;
	          secondNode = node.rt;
	        } else {
	          firstNode = node.rt;
	          secondNode = node.lb;
	        }
	      }
	      nearestSearch(firstNode, queryPoint, !orient);
	      nearestSearch(secondNode, queryPoint, !orient);
	    }
	}

	private Point2D nearest(final Node n, final Point2D c, final Point2D p) {
		Point2D nrl = c, nrr = c;
		if ((n.lb != null) && (greater(c, n.lb, p))) {
			nrl = nearest(n.lb, n.lb.p, p);
		}
		if ((n.rt != null) && (greater(c, n.rt, p))) {
			nrr = nearest(n.rt, n.rt.p, p);
		}
		return (nrl.distanceTo(p) > nrr.distanceTo(p)) ? nrr : nrl;
	}

	private boolean greater(final Point2D c, final Node n, final Point2D p) {
		return c.distanceTo(p) > n.p.distanceTo(p);
	}

	public Iterable<Point2D> range(RectHV rect) {
		Queue<Point2D> result = new Queue<Point2D>();
		rangeSearch(root, rect, result);
		return result;
	}

	private void rangeSearch(Node node, RectHV rect, Queue<Point2D> result) {
		if (node == null || !rect.intersects(node.rect)) {
			return;
		}
		if (rectContainsPoint(rect, node.p)) {
			result.enqueue(node.p);
		}
		rangeSearch(node.lb, rect, result);
		rangeSearch(node.rt, rect, result);
	}

	private static boolean rectContainsPoint(RectHV rect, Point2D p) {
		return (p.x() >= rect.xmin() && p.x() <= rect.xmax()
				&& p.y() >= rect.ymin() && p.y() <= rect.ymax());
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
