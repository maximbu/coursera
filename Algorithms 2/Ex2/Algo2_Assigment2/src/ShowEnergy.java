/*************************************************************************
 *  Compilation:  javac ShowEnergy.java
 *  Execution:    java ShowEnergy input.png
 *  Dependencies: SeamCarver.java SCUtility.java Picture.java StdDraw.java
 *                
 *
 *  Read image from file specified as command line argument. Show original
 *  image (only useful if image is large enough).
 *
 *************************************************************************/

public class ShowEnergy {

    public static void main(String[] args)
    {
    	String arg = "D:\\Studies\\Algo2\\Ex2\\seamCarving-testing\\seamCarving\\HJocean.png";
        Picture inputImg = new Picture(arg);
        System.out.printf("image is %d columns by %d rows\n", inputImg.width(), inputImg.height());
        inputImg.show();        
        SeamCarver sc = new SeamCarver(inputImg);
        
        System.out.printf("Displaying energy calculated for each pixel.\n");
        SCUtility.showEnergy(sc);

    }

}