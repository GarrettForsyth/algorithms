import edu.princeton.cs.algs4.Picture;

import java.awt.Color;
import java.util.Arrays;
import java.util.Random;

public class SeamCarver {

    private Picture mCurrentPicture;
    private int[][] mColorInformation;
    private boolean isTransposed;
    private double[][] mCurrentEnergyMatrix;
    private int mCurrentWidth;
    private int mCurrentHeight;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture){
        if (picture == null) throw new IllegalArgumentException();
        mCurrentPicture = new Picture(picture);
        mCurrentHeight = mCurrentPicture.height();
        mCurrentWidth = mCurrentPicture.width();

        setUpColorInformation(mCurrentPicture);
        mCurrentEnergyMatrix = createEnergyMatrix(mCurrentPicture);
        isTransposed = false;

    }

    private void setUpColorInformation(Picture picture){
       mColorInformation = new int[picture.height()][picture.width()];
       for (int row = 0; row < picture.height(); row++){
           for (int col = 0; col < picture.width(); col++){
               mColorInformation[row][col] = picture.getRGB(col, row);
           }
       }
    }

    // current picture
    public Picture picture(){
        return createPictureFromColorInformation();
    }

    private Picture createPictureFromColorInformation(){
        if (isTransposed) transpose();
        Picture picture = new Picture(mCurrentWidth, mCurrentHeight);
        for (int row = 0; row < mCurrentHeight; row++){
            for (int col = 0; col < mCurrentWidth; col++){
                picture.setRGB(col,row, mColorInformation[row][col]);
            }
        }
        return picture;
    }

    // width of current picture
    public int width(){
        return mCurrentWidth;
    }

    // height of current picture
    public int height(){
        return mCurrentHeight;
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y){
        if (isOutsideRange(x, 0, mCurrentWidth)) throw new IllegalArgumentException();
        if (isOutsideRange(y, 0, mCurrentHeight))  throw new IllegalArgumentException();
        // return 1000 for border pixels
        if (mCurrentWidth  == 1 || x % (mCurrentWidth-1)  == 0) return 1000;
        if (mCurrentHeight == 1 || y % (mCurrentHeight-1) == 0) return 1000;

        Color left = new Color(mColorInformation[y][x-1]);
        Color right = new Color(mColorInformation[y][x+1]);
        double xGradientSquared = gradientSquared(left, right);

        Color above = new Color(mColorInformation[y-1][x]);
        Color below = new Color(mColorInformation[y+1][x]);
        double yGradientSquared = gradientSquared(above, below);

        return Math.sqrt(xGradientSquared + yGradientSquared);
    }

    private double gradientSquared(Color x1, Color x2){
        return (squareOfDifference(x1.getRed(),   x2.getRed())   +
                squareOfDifference(x1.getGreen(), x2.getGreen()) +
                squareOfDifference(x1.getBlue(),  x2.getBlue()));

    }

    private double squareOfDifference(double d1, double d2){
        return Math.pow((d2-d1), 2);
    }


    // sequence of indices for horizontal seam
    public   int[] findHorizontalSeam(){
        if (!isTransposed) transpose();
        SeamSearch ss = new SeamSearch(mCurrentEnergyMatrix,mCurrentWidth, mCurrentHeight);
        if (isTransposed) transpose();
        return ss.seam();
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam(){
        if (isTransposed) transpose();
        SeamSearch ss = new SeamSearch(mCurrentEnergyMatrix, mCurrentWidth,mCurrentHeight);
        return ss.seam();
    }

    private double[][] createEnergyMatrix(Picture picture){
        double[][] energyMatrix = new double[picture.height()][picture.width()];
        for (int row = 0; row < picture.height(); row++){
            for (int col = 0; col < picture.width(); col++){
                energyMatrix[row][col] = energy(col,row);
            }
        }
        return energyMatrix;
    }

    // remove horizontal seam from current picture
    // make sure to decrement height before stitching
    // energy array!!
    public void removeHorizontalSeam(int[] seam){
        if (!isTransposed) transpose();
        if (seam == null) throw new IllegalArgumentException();
        if (mCurrentWidth < 1) throw new IllegalArgumentException();
        if (isNotSeam(seam)) throw new IllegalArgumentException();
        removeSeamFromArrays(seam);
        if (isTransposed) transpose();
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam){
        if (isTransposed) transpose();
        if (seam == null) throw new IllegalArgumentException("Null arguement.");
        if (mCurrentWidth < 1) throw new IllegalArgumentException("Width under one.");
        if (isNotSeam(seam)) throw new IllegalArgumentException("Not a seam.");

        removeSeamFromArrays(seam);
    }

    private void removeSeamFromArrays(int[] seam){
        for (int i = 0; i < seam.length; i++){
            removeAndSlideIndexDoubleArray(mCurrentEnergyMatrix[i], seam[i]);
            removeAndSlideIndexIntegerArray(mColorInformation[i], seam[i]);
        }

        mCurrentWidth--;
        stitchEnergySeam(seam);
    }

    private void removeAndSlideIndexDoubleArray(double[] arr, int i){
        int start = i + 1;
        int length = arr.length - i - 1;
        int to = i;
        System.arraycopy(arr,start,arr, to, length);
    }

    private void removeAndSlideIndexIntegerArray(int[] arr, int i){
        int start = i + 1;
        int length = arr.length - i - 1;
        int to = i;
        System.arraycopy(arr,start,arr, to, length);
    }

    // Reevaluates the energies that had dependencies
    // on the removed entries
    private void stitchEnergySeam(int[] seam){
        for (int i = 0; i < seam.length; i++){

           int col = seam[i];
           int row = i;
           // top and bottom row can have edge entries
           // which would mess things up
            if(col != mCurrentWidth) mCurrentEnergyMatrix[i][col] = energy(col, row);
            if(col != 0) mCurrentEnergyMatrix[i][(col-1)] = energy((col-1), row);

        }
    }

    // Checks if the seem is the proper length and that
    // it forms a line that does not jump
    private boolean isNotSeam(int[] seam){
        if (seam.length != mCurrentHeight) return true;

        for (int i = 1; i < seam.length; i++){
            if (Math.abs(seam[i]-seam[i-1]) > 1 ||
                    isOutsideRange(seam[i], 0, mCurrentWidth)){
                return true;
            }
        }
        // check if first element is in range, otherwise return false
        return isOutsideRange(seam[0], 0, mCurrentWidth);
    }

    private boolean isOutsideRange(int num, int start, int end){
        if (num < start) return true;
        if (num > end -1  ) return true;
        return  false;
    }

    private void transpose(){
        mCurrentEnergyMatrix = transposeDouble(mCurrentEnergyMatrix);
        mColorInformation = transposeInt(mColorInformation);
        int temp = mCurrentWidth;
        mCurrentWidth = mCurrentHeight;
        mCurrentHeight = temp;
        isTransposed = !isTransposed;
    }

    private double[][] transposeDouble(double[][] matrix){
        int width = matrix.length;
        int height = matrix[0].length;
        double[][] transpose = new double[height][width];
        for (int row = 0; row < height; row++){
            for (int col = 0; col < width; col++){
                transpose[row][col] =  matrix[col][row];
            }
        }
        return transpose;
    }

    private int[][] transposeInt(int[][] matrix){
        int width = matrix.length;
        int height = matrix[0].length;
        int[][] transpose = new int[height][width];
        for (int row = 0; row < height; row++){
            for (int col = 0; col < width; col++){
                transpose[row][col] =  matrix[col][row];
            }
        }
        return transpose;
    }

    public static void main (String[] args){
        String filename = "src/seam/6x5.png";
        Picture picture = new Picture(filename);

        SeamCarver sc = new SeamCarver(picture);
        int[] seam = sc.findVerticalSeam();
        System.out.println(Arrays.toString(seam));
        sc.removeVerticalSeam(seam);


        SeamCarver sc2 = new SeamCarver(picture);
        int[] hseam = sc2.findHorizontalSeam();
        System.out.println(Arrays.toString(hseam));
        sc2.removeHorizontalSeam(hseam);

        int[] hseam2 = sc2.findHorizontalSeam();
        System.out.println(Arrays.toString(hseam2));
        sc2.removeHorizontalSeam(hseam2);

        int num = 100;
        for (int i = 0; i < num; i++){
            SeamCarver mySeamCarver = new SeamCarver(picture);
            int[] loopSeam = makeRandomSeam(5, 6);
            System.out.println("Removing: " + Arrays.toString(loopSeam));
            mySeamCarver.removeVerticalSeam(loopSeam);
        }

        SeamCarver sizeCarver = new SeamCarver(picture);
        int[] loopSeam = makeRandomSeam(5, 6);
        System.out.println("height is " + sizeCarver.height() + " and width is  = " +  sizeCarver.width());
        sizeCarver.removeVerticalSeam(loopSeam);
        System.out.println("height is " + sizeCarver.height() + " and width is  = " +  sizeCarver.width());
        System.out.println(Arrays.toString(sizeCarver.findHorizontalSeam()));


        /*
        int[] loopSeam2 = makeRandomSeam(5, 5);
        System.out.println("height is " + sizeCarver.height() + " and width is  = " +  sizeCarver.width());
        sizeCarver.removeVerticalSeam(loopSeam);

        System.out.println("height is " + sizeCarver.height() + " and width is  = " +  sizeCarver.width());
        sizeCarver.removeHorizontalSeam(loopSeam2);

        System.out.println("height is " + sizeCarver.height() + " and width is  = " +  sizeCarver.width());

        int[] loopSeam3 = makeRandomSeam(4, 5);
        sizeCarver.removeVerticalSeam(loopSeam3);

        System.out.println("height is " + sizeCarver.height() + " and width is  = " +  sizeCarver.width());

        int[] loopSeam4 = makeRandomSeam(4, 4);
        sizeCarver.removeHorizontalSeam(loopSeam4);
        System.out.println("height is " + sizeCarver.height() + " and width is  = " +  sizeCarver.width());
        */





        sc.picture();
    }

    private static int[] makeRandomSeam(int length, int range){
        Random rand = new Random();
        int[] seam = new int[length];
        seam[0] = rand.nextInt(range);
        for (int i = 1; i < length; i++){
            int j;
            while (true){
               j = rand.nextInt(2)-1;
               if ( ( j + seam[i-1] ) >= 0 && (j + seam[i-1]) < range ){
                  break;
               }
            }
            seam[i] = seam[i-1] + j;
        }
        return seam;

    }
}