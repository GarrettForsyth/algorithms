
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;



public class KdTree {   

   private Node root;
   private int n; 
   private double minDist;
   private Point2D nn;
   private static final boolean HORIZONTAL = true;
   private static final boolean VERTICAL = false;
    
    // construct an empty set of points 
   public KdTree()
   {
       this.root = null;
       this.n = 0;
   }
   
   // is the set empty? 
   public boolean isEmpty()  
   {
       return size() == 0;
   }
   
   // number of points in the set 
   public int size()  
   {
       return n;
   }

   
   // add the point to the set (if it is not already in the set)
   public void insert(Point2D p) 
   {
       if( p == null ) throw new NullPointerException();
       root = insert(root, p, HORIZONTAL, 0, 0, 1 , 1);
   }
   private Node insert(Node node, Point2D p, boolean orientation,
                       double xmin, double ymin, double xmax, double ymax)
   {
       if ( node == null )
       {
           this.n++;
           return  new Node (p, new RectHV(xmin, ymin, xmax, ymax));
       }
       
       if ( node.p.equals(p) ) return node;
       

       if (orientation == HORIZONTAL){  
           double cmp = p.x() - node.p.x();
           if       ( cmp < 0 ) node.lb = insert(node.lb, p, !orientation,
                                                 node.rect.xmin(),  node.rect.ymin(),
                                                 node.p.x(), node.rect.ymax());
           else if  ( cmp >= 0 ) node.rt = insert(node.rt, p, !orientation,
                                                 node.p.x(),  node.rect.ymin(),
                                                 node.rect.xmax(), node.rect.ymax());
       }
       
       else{
           double  cmp = p.y() - node.p.y();
           if       ( cmp < 0 ) node.lb = insert(node.lb, p, !orientation,
                                                 node.rect.xmin(),  node.rect.ymin(),
                                                 node.rect.xmax(), node.p.y());
           else if  ( cmp >= 0 ) node.rt = insert(node.rt, p, !orientation,
                                                 node.rect.xmin(),  node.p.y(),
                                                 node.rect.xmax(), node.rect.ymax());
       }
           
       return node;
   }
   
   
   // does the set contain point p? 
   public boolean contains(Point2D p)  
   {
       if( p == null ) throw new NullPointerException();
       return get(p);
   }
   
   private boolean get(Point2D p){
       return get(root, p, VERTICAL);
   }
   
   private boolean get(Node node, Point2D p, boolean orientation){
       
      if ( node == null) return false;
      if ( node.p.equals(p)) return true;
       
      double cmp;
      if ( orientation == VERTICAL) cmp = p.x() - node.p.x();
      else                            cmp = p.y() - node.p.y();

      if (cmp < 0)        return get(node.lb, p, !orientation);
      else                return get(node.rt, p, !orientation);
   }
   
   // draw all points to standard draw 
   public void draw()      
   {   
       draw(root, VERTICAL);
   }
   
   private void draw(Node node, boolean orientation){
      
       if (orientation == VERTICAL){
           StdDraw.setPenColor(StdDraw.RED);
           StdDraw.line(node.p.x(), node.rect.ymin(),
                        node.p.x(), node.rect.ymax());
       }
       else{
           StdDraw.setPenColor(StdDraw.BLUE);
           StdDraw.line(node.rect.xmin(), node.p.y(),
                        node.rect.xmax(), node.p.y());
       }
       if( node.lb != null) draw(node.lb, !orientation);
       if( node.rt != null) draw(node.rt, !orientation);
       
       StdDraw.setPenColor(StdDraw.BLACK);
       StdDraw.setPenRadius(0.01);
       StdDraw.line(node.p.x(), node.p.y(),
                    node.p.x(), node.p.y());
       
   }
   
   // all points that are inside the rectangle 
   public Iterable<Point2D> range(RectHV rect)  
   {
       if( rect == null ) throw new NullPointerException();
       List<Point2D> ptsInRange= new LinkedList<>();
       // if the query rectangle does not intersect the rectangle corresponding 
       //to a node, there is no need to explore that node (or its subtrees). A 
       //subtree is searched only if it might contain a point contained in the query rectangle.
       range(root, rect, ptsInRange);
       return ptsInRange;
   }
  
   private void range(Node node, RectHV rect, List<Point2D> list){
       if(doesRectIntersect(node.rect, rect)){
           if (rect.contains(node.p)) list.add(node.p);
           if (node.lb != null ) range(node.lb , rect, list);
           if (node.rt != null ) range(node.rt , rect, list);
       }
   }
   
   private boolean doesRectIntersect(RectHV rect1, RectHV rect2){
       
       if  (rect1.xmax() < rect2.xmin()
       ||   rect2.xmax() < rect1.xmin()
       ||   rect1.ymax() < rect2.ymin()
       ||   rect2.ymax() < rect1.ymin()) return false;
       return true;
   }

   public Point2D nearest(Point2D p)
   {
       if( p == null ) throw new NullPointerException();
       if (root == null) return null;
       return nearest(root, p, Double.POSITIVE_INFINITY);
   
   }   
   
   private Point2D nearest(Node node, Point2D p, double minDistance){
       if ( node == null ) return null;

       // if the closest point discovered so far is closer than the 
       // distance between the query point and the rectangle corresponding to a
       // node, there is no need to explore that node (or its subtrees).
       //System.out.println("Checking node : " + node.p);
       
       if ( minDistance <= node.rect.distanceSquaredTo(p)) return null;
       
       Point2D nn = null;
       double minDist = minDistance;
       double d = p.distanceSquaredTo(node.p);
       
       //if this node is closer, update the champion:
       if ( d < minDistance ){
           nn = node.p;
           minDist = d;
       }
       
       //arbitrary order for now
       Node checkFirst = node.lb;
       Node checkSecond = node.rt;
       
       //switch if right/top branch is closer
       if ( checkFirst != null && checkSecond != null ){
           if (checkFirst.rect.distanceSquaredTo(p) > checkSecond.rect.distanceSquaredTo(p)){
               checkFirst = node.rt;
               checkSecond = node.lb;
           }
       }
       
       //check first branch
       Point2D firstNN = nearest(checkFirst, p, minDist);
       if ( firstNN != null){
           //if closer node is found update
           d = p.distanceSquaredTo(firstNN);
           if ( d < minDist){
               nn = firstNN;
               minDist = d;
           }
       }
       
       //check first branch
       Point2D secondNN = nearest(checkSecond, p, minDist);
       if ( secondNN != null){
           //if closer node is found update
           d = p.distanceSquaredTo(secondNN);
           if ( d < minDist){
               nn = secondNN;
               minDist = d;
           }
       }
       
       return nn;  
       
   }
    
    private static class Node
    {        
        private Point2D p; // the point
        private RectHV rect; // the axis-aligned rectangle corresponding to this node
        private Node lb; // the left / bottom subtree
        private Node rt; // the right / top subtree
        
        public Node(Point2D p, RectHV rect){
            this.p = p;
            this.rect = rect;
        }
    }
        
    public static void main(String[] args)    
    {
        
       //Testing tree structure : 
        KdTree kdTest = new KdTree();
        for(double  i = 0; i < 0.5; i+= 0.1){
           kdTest.insert(new Point2D(i+0.5, 1-i - 0.25));
           System.out.println(kdTest.size());
        }
        
        for(double  i = 0; i < 0.5; i+= 0.1){
           kdTest.insert(new Point2D(0.5-i, 1-i - 0.25));
           System.out.println(kdTest.size());
        }
        
        for(double i = 0; i < 0.5; i+=0.1){
            System.out.println(kdTest.contains(new Point2D(i, 1-i)));
         }
            
       System.out.println(kdTest.contains( new Point2D(34,10)));
       System.out.println(kdTest.contains( new Point2D(-19,10)));
       System.out.println(kdTest.contains( new Point2D(0,0)));
       System.out.println(kdTest.contains( new Point2D(10,10)));
       
       kdTest.draw();
       
      // RectHV rect = new RectHV(0, 0.2, 0.5, 0.95);
      // rect.draw();
      // for(Point2D pt : kdTest.range(rect)){
      //     System.out.println(pt);
      //}
       
      Point2D query = new Point2D(0.2,0.75);
      Point2D nn = kdTest.nearest(query);
      System.out.println(nn);
     // double temp = minDist;
      StdDraw.line(query.x(), query.y(), nn.x(), nn.y());
     // System.out .println(minDist );
        
    }

}
