package collinearAssignment;
import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;


public class FastCollinearPoints {
    
    private int numSegments;
    private LineSegment[] segments;
   // private Point[] newLinePoints;

    
    //finds all the line segments containing 4 or more points
    public FastCollinearPoints(Point[] points){
        if(points==null){
            throw new NullPointerException("Null points passed to " 
                    + "FastCollinearPoints constructor.");
        }
        
        segments= new LineSegment[points.length*points.length];
        numSegments=0;
        findCollinear(points, points.length);
       
        /*
        //note: do not need to consider the last THREE points
        //consider the sub array with only four points left:
        // either they from a line and it's found, or they don't
        //and none of the points contribute to any lines since
        //other lines would have been found in previous searches
        //sorting the sub array in between iterations will not change this
        for(int i =0; i < points.length-3; i++){
            //sort a sub array from i+1 to the end of points w.r.t. their slopes with point[i] 
            Arrays.sort(points, i+1, points.length, points[i].slopeOrder());
            
            
            //only need to check points that haven't been checked yet--> start at i
            //also, the don't need to check the last TWO points, since at least four
            //points are required to constitute a line (need to include point[i])
            for(int curr=i+1; curr < points.length-2; curr++){
                //line length starts with one point (points[i]):
                int lineStart=curr;
                //while loop feels more elegant than an if statement:
                //so, while two points in  a row have equal slope w.r.t. point[i]:
                while(points[i].slopeTo(points[curr]) == points[i].slopeTo(points[curr+1])){
                    //counting how many in a row have equal slope:
                    curr++;
                    //reached end of list when (to avoid out of bound exception):
                    if(curr == points.length-1) break;
               }
                
               //if there are at lest THREE equals slopes found, make a new line segment:
               //(this difference would be 2 four three equal entries)
                if(curr-lineStart >=2){

                        createNewLinePointArray(points, lineStart, curr, i);
                        //Point[i] must be smaller than every point on the line
                        //to avoid permutations:
                        boolean flag= false;
                        for(int z=1; z < newLinePoints.length; z++){
                            if(newLinePoints[z].compareTo(points[i]) > 0){
                                flag=true;
                            }
                        }
                        if(flag==false){
                            segments[numSegments]= new LineSegment(newLinePoints[0], 
                                                    newLinePoints[curr-lineStart+1]);
                            numSegments++;     
                        }
                }
                //otherwise , the segment isn't large enough so carry on the search
                //starting at curr (since elements up to curr have already been checked)              
            }         
        }       
        */
        LineSegment[] ret= new LineSegment[numSegments];
        for(int i=0; i < numSegments; i++){
            ret[i]= segments[i];
        }
        segments=ret;
    }
    
    /*
     * Fins all lines of four or more points from a given array of Points
     * and an integer N.
     * Avoid finding subsegments of lines.
     * for each point in the array, sort the array by slope and then
     * check to find consecutive equal elements (which means they are 
     * points on the same line.
     */
    private void findCollinear(Point[] points, int N){
        //create a copy //create a copy of the points:
        Point[] copy= new Point[points.length];
        for(int i=0; i < N; i++){
            copy[i]= points[i];
        }
        
        //for each point in the array, sort the rest of the array
        // then check for all lines involving the curr point
        for(int i = 0; i < N; i++){
            Point curr=copy[i];
            //sort by slopes w.r.t. curr
            Arrays.sort(points,0,N, curr.slopeOrder());
            int lo=1;
            int hi=2;
            //to avoid permutations, ensure curr is 'smallest' point on the line
            //based on the compare to defined in Point
            boolean flag= curr.compareTo(points[lo]) < 0 ? true : false;
            
            while(hi < N){
                //if equal slopes are found, make sure they're higher than curr point
                if(points[hi].slopeTo(curr) == points[lo].slopeTo(curr)){
                    if(points[hi].compareTo(curr) < 0){
                        flag=false;
                    }
                }
                // otherwise the slopes are unequal:
                else{
                    //if 3 collinear points are found
                    if(flag && hi - lo >= 3){
                        handleCollinear(points,lo,hi);
                    }
                    lo = hi;
                    flag = curr.compareTo(points[lo]) < 0 ? true : false; 
                }
                hi++;
            }
            //edge case of last point
           // if(points[N-1].slopeTo(curr) == points[lo].slopeTo(curr)){
           //     if(flag && hi - lo >= 3){
           //         handleCollinear(points,lo,hi);
           //     }
          //  }          
        }        
    }
    
    /*
     * Creates a  line segment from found collinear Points.
     */
    private void handleCollinear(Point[] points, int lo, int hi){
            Arrays.sort(points, lo, hi);
            segments[numSegments]= new LineSegment(points[lo], points[hi-1]);
            numSegments++;             
    }
    
    /*
    //resizes the linePoint array as appropriate:
    private void createNewLinePointArray(Point[] arr, int start, int end, int i){
        newLinePoints= new Point[end-start+2];        
        //refill

        newLinePoints[0]= arr[i];
        for(int j=1; j < end-start+2; j++){
            newLinePoints[j]= arr[start+j-1];
        }

        //sorts new array (should be quick)
        Arrays.sort(newLinePoints);
    }
    */
         
    // the number of line segments
    public int numberOfSegments(){
        return numSegments;
    }
    
    // the line segments
    public LineSegment[] segments(){
        return segments;
    }
    
    public static void main(String[] args) {
        
        // read the n points from a file

        In in = new In(args[0]);
        
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}
