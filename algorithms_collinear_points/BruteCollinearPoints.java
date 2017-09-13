package collinearAssignment;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
/**
 * Write a program BruteCollinearPoints.java that examines 4 
 * points at a time and checks whether they all lie on the same 
 * line segment, returning all such line segments. To check 
 * whether the 4 points p, q, r, and s are collinear, check
 * whether the three slopes between p and q, between p and r, 
 * and between p and s are all equal.
 * 
 * @author Garrett
 *
 */
public class BruteCollinearPoints {
    
    private int numSegments;
    private LineSegment[] segments;


    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points){
        if(points==null){
            throw new NullPointerException("Null points passed to " 
                    + "BruteCollinearPoints constructor.");
        }
        
        this.numSegments=0;
        LineSegment newSegment=null;
        
        //It can grow quadratically as a function of N. Consider the n 
        //points of the form: (x, y) for x = 0, 1, 2, and 3 and 
        //y = 0, 1, 2, ..., n / 4. This means that if you store all of 
        //the (maximal) collinear sets of points, you will need quadratic
        //space in the worst case.
        segments= new LineSegment[points.length];
        Point[] temp= new Point[4];
        
        
        for(int i=0; i < points.length-3; i++){
            for(int j=i+1; j < points.length; j++){
                if(points[j].compareTo(points[i]) ==0){
                    throw new IllegalArgumentException("Duplicate points.");
                }
                for(int k= j+1; k < points.length; k++){
                    if(points[k].compareTo(points[j]) ==0){
                        throw new IllegalArgumentException("Duplicate points.");
                    }
                    for(int l= k+1; l < points.length; l++){
                        if(points[l].compareTo(points[k]) ==0){
                            throw new IllegalArgumentException("Duplicate points.");
                        }
                        if(isCollinear(points[i], points[j],
                                points[k], points[l])){
                            
                            temp[0]=points[i];
                            temp[1]=points[j];
                            temp[2]=points[k];
                            temp[3]=points[l];
                            newSegment= createLineSegment(temp);
                            segments[numSegments]= newSegment;
                            numSegments++;                
                        }  
                        
                        
                    }
                }
            }                             
        }
        LineSegment[] ret= new LineSegment[numSegments];
        for(int i=0; i < numSegments; i++){
            ret[i]= segments[i];
        }
        segments=ret;
    }
    
    // the number of line segments
    public int numberOfSegments(){return numSegments;}
    // the line segments
    public LineSegment[] segments(){return segments;}
    
    //*Returns true if four points are collinear */
    private boolean isCollinear(Point p0, Point p1, Point p2, Point p3){
        return (p0.slopeTo(p1)== p0.slopeTo(p2) && 
                    p0.slopeTo(p1)== p0.slopeTo(p3));       
    }
    
    private LineSegment createLineSegment(Point [] pts){
        for(int k=0; k < pts.length; k++){
        }
       for(int i=0; i < pts.length-1; i++){
           for(int j=i+1; j < pts.length; j++){
               
               if(pts[j].compareTo(pts[i]) ==0){
                   throw new IllegalArgumentException("Duplicate points.");
               }
               if(pts[j].compareTo(pts[i]) < 0){
                   swap(pts, j, i);
               }
           }                      
       }
       
       return new LineSegment(pts[0], pts[pts.length-1]); 
    }
    
    /*
     * helper method:
     */
    private Point[] copyOfRange(Point[] arr, int start, int end){

        Point[] copy= new Point[end-start+1];
        for(int i=0; i < end-start+1; i++){
            copy[i]= arr[start+i];
        }
        return copy;
    }
    
    private void swap(Point[] arr, int a, int b){
        Point copy= arr[a];
        arr[a]=arr[b];
        arr[b]=copy;
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }


}
