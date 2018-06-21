
/**
 * Finds the shortest path from the top of a
 * double[][[] to the bottom using the element
 * values as weights.
 *
 */
public class SeamSearch {

    private final double matrix[][];
    private final int width;
    private  final int height;

    private double distTo[][];
    private int edgeTo[][];
    private int seam[];

    public SeamSearch(final double[][] matrix, int colLimit, int rowLimit){
        this.matrix = matrix;
        height = rowLimit;
        width = colLimit;

        distTo = new double[height][width];
        edgeTo = new int[height][width];
        seam = new int[height];


        // initialize distance to infinity
        // leave first row at 0
        // faster to transverse row by by row (!?)
        for (int row = 1; row < height; row++){
            for (int col = 0; col < width; col++){
                distTo[row][col] = Double.POSITIVE_INFINITY;
            }
        }


        search();
        setSeam();
        /*
        for( double[] arr : distTo){
            System.out.println(Arrays.toString(arr));
        }
        */
    }

    /**
     * Getter for seam.
     * @return int array of indices of shortest path from top to bottom
     */
    public int[] seam(){
        return seam;
    }

    /**
     * Note here: the rows are effectively in
     * topological order. E.g. you must go through
     * row 3 to get ot row 4 etc.
     */
    private void search(){

        for (int row = 0; row < height; row++) {
            for( int col = 0; col < width; col++) {
                // relax the three entries underneath:
                for(int col2 = col-1; col2 <= col+1; col2++){
                    int row2 = row+1;
                    if (isInsideMatrix(row2,col2)){
                        relax(col, row, col2, row2);
                    }
                }
           }
        }

    }

    private void relax(int col, int row, int col2, int row2){
        if (distTo[row2][col2] > (distTo[row][col] + matrix[row2][col2])){
           distTo[row2][col2] = distTo[row][col] + matrix[row2][col2];
           edgeTo[row2][col2] = col;
        }

    }

    private boolean isInsideMatrix(int row, int col){
        if ((row >= 0 && row < height) &&
            (col >= 0 && col < width)){
            return true;
        }
        return false;
    }

    private void setSeam(){
        int shortestPathIndex = scanLastRowForShortestPathIndex();

        for(int i = height-1; i >= 1; i--) {
           seam[i] = shortestPathIndex;
           shortestPathIndex = edgeTo[i][shortestPathIndex];
        }
        // special case for last iteration
        seam[0] = shortestPathIndex;


    }

    private int scanLastRowForShortestPathIndex(){
        int minIndex = 0;
        double shortestPath = Double.POSITIVE_INFINITY;

        for (int i = 0; i < width; i++){
            if (distTo[height-1][i] < shortestPath){
                minIndex = i;
                shortestPath = distTo[height-1][i];
            }
        }
        return minIndex;
    }

}
