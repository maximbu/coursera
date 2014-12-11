import java.awt.Color;

public class SeamCarver {

	private int[][] rep;
	private int [][] energy = null;
	private int width;
	private int height;
	private static final int BorderPixelEnergy = 195075;

	public SeamCarver(Picture picture) {
		width = picture.width();
		height = picture.height();
		rep = new int[width][height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				rep[x][y] = colorToInt(picture.get(x, y));
			}
		}
		calculateEnergy();
	}

	private void calculateEnergy() {
		if(energy!=null)
			return;
		energy = new int[width][height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				energy[x][y] = calculateEnergy(x, y);
			}
		}
	}

	private int calculateEnergy(int x, int y) {
		if (x == 0 || y == 0 || y == height - 1 || x == width - 1)
			return BorderPixelEnergy;
		int dx = getDValue(x + 1, y, x - 1, y);
		int dy = getDValue(x, y + 1, x, y - 1);
		return dx + dy;
	}

	private int colorToInt(Color c)
	{
		return c.getRGB();
	}
	
	private Color intToColor(int x)
	{
		return new Color(x);
	}
	
	
	private int getDValue(int x1, int y1, int x2, int y2) {
		Color c1 = intToColor(rep[x1][y1]);
		Color c2 = intToColor(rep[x2][y2]);
		int dR = c1.getRed() - c2.getRed();
		int dG = c1.getGreen() - c2.getGreen();
		int dB = c1.getBlue() - c2.getBlue();
		return dR * dR + dG * dG + dB * dB;
	}

	public Picture picture() // current picture
	{
		Picture image = new Picture(width, height);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.set(x, y, intToColor(rep[x][y]));
			}
		}
		return image;
	}

	public int width() // width of current picture
	{
		return width;
	}

	public int height() // height of current picture
	{
		return height;
	}


	private void swapWidthAndHeight() {
		int a = width;
		width = height;
		height = a;
	}


	private void transposeEnergy() {
		int[][] tmp = new int[height][width];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				tmp[y][x] = energy[x][y];
			}
		}
		energy = tmp;
		swapWidthAndHeight();
	}

	private void transposeColors() {
		int[][] tmpColor = new int[height][width];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				tmpColor[y][x] = rep[x][y];
			}
		}
		rep = tmpColor;
		swapWidthAndHeight();
	}

	public double energy(int x, int y) // energy of pixel at column x and row y
										// in current picture
	{
		if (x < 0 || y < 0 || y > height - 1 || x > width - 1)
			throw new java.lang.IndexOutOfBoundsException();

		calculateEnergy();
		return energy[x][y];
	}

	public int[] findHorizontalSeam() // sequence of indices for horizontal seam
										// in current picture
	{
		calculateEnergy();
		transposeEnergy();
		int[] ans = findVerticalSeam();
		transposeEnergy();
		return ans;
	}

	public int[] findVerticalSeam() // sequence of indices for vertical seam in
									// current picture
	{
		calculateEnergy();
		if (height <= 1 || width <= 1)
			throw new java.lang.IllegalArgumentException();

		double[][] path = new double[width][height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				path[x][y] = energy[x][y];
			}
		}

		for (int y = 1; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int xInd = getMinimalXIndex(path, x, y - 1);
				path[x][y] += path[xInd][y - 1];
			}
		}

		int[] minPath = new int[height];

		int minInd = 0;
		for (int x = 1; x < width; x++) {
			if (path[x][height - 1] < path[minInd][height - 1]) {
				minInd = x;
			}
		}

		minPath[height - 1] = minInd;

		for (int y = height - 2; y >= 0; y--) {
			minPath[y] = getMinimalXIndex(path, minPath[y + 1], y);
		}

		return minPath;
	}

	private int minIndex(double x1, double x2, double x3, int ind1, int ind2,
			int ind3) {
		double minVal = Math.min(Math.min(x1, x2), x3);
		if (minVal == x1)
			return ind1;
		if (minVal == x2)
			return ind2;
		return ind3;
	}

//	private void printArray(double[][] arr) {
//
//		System.out.println("Printing array");
//		for (int x = 0; x < height; x++) {
//			for (int y = 0; y < width; y++) {
//				System.out.printf("%6.0f ", arr[y][x]);
//			}
//			System.out.println();
//		}
//		System.out.println();
//	}

	private int getMinimalXIndex(double[][] path, int x, int refY) {
		double xleft = Double.MAX_VALUE;
		if (x > 0) {
			xleft = path[x - 1][refY];
		}
		double xright = Double.MAX_VALUE;
		if (x < width - 1) {
			xright = path[x + 1][refY];
		}
		double xRef = path[x][refY];

		return minIndex(xleft, xright, xRef, x - 1, x + 1, x);
	}


	private int[] removeElt(int[] arr, int remIndex) {
		int numElts = arr.length - (remIndex + 1);
		int[] tmpArr = new int[arr.length-1];
		System.arraycopy(arr, 0, tmpArr, 0, remIndex);
		System.arraycopy(arr, remIndex + 1, tmpArr, remIndex, numElts);
		return tmpArr;
	}

	public void removeHorizontalSeam(int[] a) // remove horizontal seam from
	// current picture
	{
		energy = null;
		
		if (a.length != width) {
			throw new java.lang.IllegalArgumentException();
		}
		for (int y = 0; y < width; y++) {
			rep[y] = removeElt(rep[y], a[y]);
		}

		height--;		
	}

	public void removeVerticalSeam(int[] a) // remove vertical seam from current
											// picture
	{
		energy = null;
		transposeColors();
		removeHorizontalSeam(a);
		transposeColors();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
