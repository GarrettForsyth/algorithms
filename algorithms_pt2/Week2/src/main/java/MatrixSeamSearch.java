import edu.princeton.cs.algs4.MinPQ;

import java.util.Arrays;
import java.util.PriorityQueue;

// finds the shortest path from the top of matrix to the
// bottom, treating element values as weights.
public class MatrixSeamSearch {

    private final double[][] matrix;
    private int[] verticalSeam;
    private int numRows;
    private int numCols;
    private double[][] distTo;
    private MatrixPoint[][] edgeTo;
    private PriorityQueue<MatrixPoint> toSearch;

    private static final double[][] testMatrix =
            new double[][] { {1000.00, 1000.00, 1000.00, 1000.00},
                             {1000.00, 3.72, 2.83, 1000.00},
                             {1000.00, 4.12, 3.32, 1000.00},
                             {1000.00, 3.46, 3.61, 1000.00},
                             {1000.00, 3.32, 2.65, 1000.00},
                             {1000.00, 1000.00, 1000.00, 1000.00}};

    private static final double[][] testMatrix2 =
            new double[][] { {1000.00, 1000.00,1000.00, 1000.00, 1000.00},
                    {1000.00, 3.74, 5.66,2.24, 1000.00},
                    {1000.00, 3.32, 2.65, 4.12, 1000.00},
                    {1000.00, 4.58, 4.36, 4.24, 1000.00},
                    {1000.00, 1000.00,1000.00, 1000.00, 1000.00}};



    // todo track changes to avoid reintialization costs

    public MatrixSeamSearch(double[][] matrix){
        this.matrix = matrix;
        numRows = matrix.length;
        numCols = matrix[0].length;

        edgeTo = new MatrixPoint[numRows][numCols];
        distTo = new double[numRows][numCols];

        toSearch = new PriorityQueue<MatrixPoint>();
        verticalSeam = new int[numRows];

        initializeDistTo();
    }

    private void initializeDistTo(){
        // set values of rows to infinity (except for first)
        for (int i = 1; i < numRows; i++){
            for (int j = 0; j < numCols; j++){
                distTo[i][j] = (Double.POSITIVE_INFINITY);
            }
        }
    }

    public void searchMatrix(){
        addFirstRowToQueue();

        while(!toSearch.isEmpty()){
            MatrixPoint pt = toSearch.poll();

            // last row is full of 1000's, can exit here
            if (pt.row == numRows -1){
                System.out.println("Exiting at point " + pt.row + ", " + pt.col);
                setVerticalSeam(pt);
                return;
            }

            System.out.println("Check point : " + pt.row + ", " + pt.col + " = " + testMatrix2[pt.row][pt.col] +
             " ; distTo = " + distTo[pt.row][pt.col]);

            // check the three points under
            for (int col = pt.col-1; col <= pt.col+1; col++){
                if (isInsideMatrix(pt.row+1, col)){
                    relax(pt.row+1, col, pt);
                }
            }
        }
    }

    // Paths can start from anywhere in the first row
    private void addFirstRowToQueue(){
        for(int col = 0; col < numCols; col++){
            toSearch.add(new MatrixPoint(0, col));
        }
    }

    private boolean isInsideMatrix(int row, int col){
        if ((row >= 0 && row < numRows) &&
            (col >= 0 && col < numCols)){
            return true;
        }
        return false;
    }

    /* If a shorter path is found, entries are updated and
        the new point is added to the search queue.
     */
    private void relax(int row, int col, MatrixPoint fromPt){
        /*
        System.out.println("Relaxing.. from: " + fromPt.row + ", " + fromPt.col + " = " + matrix[fromPt.row][fromPt.col] +
                " to " + row + "," + col + " = " + matrix[row][col]);
        System.out.println("checking " + (distTo[row][col]) + " > " +(distTo[fromPt.row][fromPt.col] + matrix[row][col]));
        */

        if(distTo[row][col] > (distTo[fromPt.row][fromPt.col] + matrix[row][col])){
           distTo[row][col] = distTo[fromPt.row][fromPt.col] + matrix[row][col];

           edgeTo [row][col] = fromPt;
           toSearch.add(new MatrixPoint(row,col));
           System.out.println("Successful relax: adding " + matrix[row][col]);
        }

    }

    public int[] verticalSeam(){
        return verticalSeam;
    }

    private void setVerticalSeam(MatrixPoint pt){
        MatrixPoint next = pt;
        // set last row to be whatever col the second last row was
        // verticalSeam[numRows-1] = pt.col;
        while(next != null){
            verticalSeam[next.row] = next.col;
            next = edgeTo[next.row][next.col];
        }
    }

    /**
     * Class to use the priority queue more conveniently
     * Simply stores coordinates in the matrix the priority
     * of the point.
     */
    private class MatrixPoint implements Comparable<MatrixPoint>{
        public int row;
        public int col;
        Double priority;

        public MatrixPoint(int row, int col){
           this.row = row;
           this.col = col;
           this.priority = distTo[this.row][this.col];
        }

        public int compareTo(MatrixPoint other){
            return (int) (this.priority - other.priority);
        }
    }

    public static void main(String[] args){
        MatrixSeamSearch ms = new MatrixSeamSearch(testMatrix2);
        ms.searchMatrix();
        System.out.println(Arrays.toString(ms.verticalSeam()));
    }
}
