import java.util.Iterator;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET {
    
    private TreeSet<Point2D> pointTree;
   // private double minDistance = 0;
    //private Point2D minPoint = null

    
     // construct an empty set of points 
    public PointSET()
    {
        pointTree = new TreeSet<>();
    }
    
    // is the set empty? 
    public boolean isEmpty()  
    {
        return pointTree.isEmpty();
    }
    
    // number of points in the set 
    public int size()  
    {
        return pointTree.size();
    }
    
    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) 
    {
        if( p == null ) throw new NullPointerException();
        pointTree.add(p);
    }
    
    
    // does the set contain point p? 
    public boolean contains(Point2D p)  
    {
        if( p == null ) throw new NullPointerException();
        return pointTree.contains(p);
    }
    
    // draw all points to standard draw 
    public void draw()      
    {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        Iterator<Point2D> itr = pointTree.iterator();
        while( itr.hasNext() )
        {
            itr.next().draw();
        }
        return;
    }
    
    // all points that are inside the rectangle 
    public Iterable<Point2D> range(RectHV rect)  
    {
        if( rect == null ) throw new NullPointerException();      
        List<Point2D> rectPts = new LinkedList<>();
        if ( pointTree.isEmpty() ) return rectPts;
        for ( Point2D pt: pointTree){
            if ( isPtInRect(pt, rect) ) {
                rectPts.add(pt); 
            }
        }
        
        return rectPts;
    }
   
    /**
     * Checks if a point pt lies within a rectangle, rect.
     * @param pt
     * @param rect
     * @return
     */
    private boolean isPtInRect(Point2D pt, RectHV rect)
    {
        if ( pt.x() <= rect.xmax() && pt.x() >= rect.xmin() &&
             pt.y() <= rect.ymax() && pt.y() >= rect.ymin()  ) {
            return true;
        }
        return false;          
    }
    
    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p)
    {
        if( p == null ) throw new NullPointerException();
        double minDistance = Double.POSITIVE_INFINITY;
        Point2D minPoint = null;
        double currDistance = 0;
        for ( Point2D pt : pointTree ){
           
            currDistance = p.distanceSquaredTo(pt);
            if ( currDistance < minDistance ) {
                minPoint = pt;
                minDistance = currDistance;
            }
        }
        return minPoint;
    }
    
       
    // unit testing of the methods (optional) 
    public static void main(String[] args)    
    {
           //Testing tree structure : 
            PointSET testSet = new PointSET();
            for(double  i = 0; i < 0.5; i+= 0.1){
                testSet.insert(new Point2D(i+0.5, 1-i - 0.25));
               System.out.println(testSet.size());
            }
            
            for(double  i = 0; i < 0.5; i+= 0.1){
                testSet.insert(new Point2D(0.5-i, 1-i - 0.25));
               System.out.println(testSet.size());
            }
            
            for(double i = 0; i < 0.5; i+=0.1){
                System.out.println(testSet.contains(new Point2D(i, 1-i)));
             }

            testSet.draw();
            
            RectHV rect = new RectHV(0.35, 0.25, 0.75, 0.7);
            rect.draw();
            
            for( Point2D pt: testSet.range(rect)){
                System.out.println(pt);
            }
           
          // RectHV rect = new RectHV(0, 0.2, 0.5, 0.95);
          // rect.draw();
          // for(Point2D pt : kdTest.range(rect)){
          //     System.out.println(pt);
          //}
           
            /*
          Point2D query = new Point2D(0.2,0.75);
          Point2D nn = testSet.nearest(query);
          System.out.println(nn);
         // double temp = minDist;
          StdDraw.line(query.x(), query.y(), nn.x(), nn.y());
          */
         // System.out .println(minDist );
    }

}
