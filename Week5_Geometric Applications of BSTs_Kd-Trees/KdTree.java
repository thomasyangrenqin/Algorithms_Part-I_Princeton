import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Queue;

public class KdTree {
	private Node root;
	private int size;
	
	private class Node{
		private Point2D point;
		private Node left, right;
		private RectHV rect;
		
		public Node(Point2D p, RectHV r){
			point = p;
			rect = r;
		}
	}
	
	public KdTree(){
		root = null;
		size = 0;
	}
	
	 public boolean isEmpty() {
	        return size() == 0;
	    }
	 
	 public int size() {
	        return size;
	    }
	 
	 
	 
	 public void insert(Point2D p) {
	        if (p == null) throw new IllegalArgumentException("first argument to insert is null");
	        if (isEmpty()) {
	        	RectHV initial_rect = new RectHV(0,0,1,1);
	        	root = insertV(root,p,initial_rect);
	        }
	        else root = insertV(root,p,root.rect);
	    }
	 
	 private Node insertV(Node x, Point2D p, RectHV r){
		 if (x == null) {
			 size++;
			 return new Node(p,r);
		 }
		 if (p.equals(x.point)) return x;
	     int cmp = Point2D.X_ORDER.compare(p, x.point);
	     RectHV new_r;
	     if (cmp <= 0){
	    	 if (x.left == null) new_r = new RectHV(r.xmin(),r.ymin(),x.point.x(),r.ymax());
	    	 else new_r = x.left.rect;
	    	 x.left = insertH(x.left,p,new_r);
	     }
	     else{
	    	 if (x.right == null) new_r = new RectHV(x.point.x(),r.ymin(),r.xmax(),r.ymax());
	    	 else new_r = x.right.rect;
	    	 x.right = insertH(x.right,p,new_r);
	     }
	    
	     return x;
	 }
	 
	 private Node insertH(Node x, Point2D p, RectHV r){
		 if (x == null) {
			 size++;
			 return new Node(p,r);
		 }
		 if (p.equals(x.point)) return x;
	     int cmp = Point2D.Y_ORDER.compare(p, x.point);
	     RectHV new_r;
	     if (cmp <= 0){
	    	 if (x.left == null) new_r = new RectHV(r.xmin(),r.ymin(),r.xmax(),x.point.y());
	    	 else new_r = x.left.rect;
	    	 x.left = insertV(x.left,p,new_r);
	     }
	     else{
	    	 if (x.right == null) new_r = new RectHV(r.xmin(),x.point.y(),r.xmax(),r.ymax());
	    	 else new_r = x.right.rect;
	    	 x.right = insertV(x.right,p,new_r);
	     }
	    
	     return x;
	 }
	 
	 public boolean contains(Point2D p){
		 return contains(root,p,true);
	 }
	 
	 private boolean contains(Node x, Point2D p, boolean flag){
		 if (x == null) return false;
		 if (p.equals(x.point)) return true; 
		 int cmp;
		 if (flag) cmp = Point2D.X_ORDER.compare(p, x.point);
		 else cmp = Point2D.Y_ORDER.compare(p, x.point);
		 if (cmp <= 0)  return contains(x.left,p,!flag);
		 else return contains(x.right,p,!flag);
	 }
	 
	 public void draw(){
		 StdDraw.setPenColor(StdDraw.BLACK);
		 StdDraw.rectangle(0.5, 0.5, 0.5, 0.5);
	     if (isEmpty()) return;
	     draw(root, true);
	 }
	 
	 private void draw(Node x,boolean flag){
		 if (x.left != null)     draw(x.left, !flag);
	     if (x.right != null)    draw(x.right, !flag);
		 
		 StdDraw.setPenColor(StdDraw.BLACK);
		 StdDraw.setPenRadius(.01);
		 x.point.draw();
		 
		 double xmin,xmax,ymin,ymax;
		 if (flag){
			 StdDraw.setPenColor(StdDraw.RED);
			 xmin = x.point.x();
			 xmax = x.point.x();
			 ymin = x.rect.ymin();
			 ymax = x.rect.ymax();
		 }
		 else {
			 StdDraw.setPenColor(StdDraw.BLUE);
			 ymin = x.point.y();
			 ymax = x.point.y();
			 xmax = x.rect.xmax();
			 xmin = x.rect.xmin();
		 }
		 StdDraw.setPenRadius();
		 StdDraw.line(xmin,ymin,xmax,ymax);
		 
	 }
	 
	 public Iterable<Point2D> range(RectHV rect){
		 Queue<Point2D> q = new Queue<Point2D>();
	     range(root, rect, q);
	     return q;
		 
	 }
	 
	 private void range(Node x, RectHV rect, Queue<Point2D> p){
		 if (x == null) return;
		 if (rect.contains(x.point)) p.enqueue(x.point);
		 if (x.left != null && rect.intersects(x.left.rect)) range(x.left,rect,p);
		 if (x.right != null && rect.intersects(x.right.rect)) range(x.right,rect,p);
		 return;
		 
	 }
	 
	 public Point2D nearest(Point2D p){
		 if (isEmpty()) return null;
		 return nearest(root,p,root.point,true);
	 }
	 
	 private Point2D nearest(Node x, Point2D p, Point2D mp, boolean flag){
		 Point2D min = mp;
		 
		 if (x == null) return min;
		 if (p.distanceTo(x.point) < p.distanceTo(min)) min = x.point;
		 int cmp;
		 if (flag) cmp = Point2D.X_ORDER.compare(p, x.point);
		 else cmp = Point2D.Y_ORDER.compare(p, x.point);
		 if (cmp <= 0 ){
			 min = nearest(x.left,p,min,!flag);
			 if (x.right != null && 
					 p.distanceTo(min) > x.right.rect.distanceTo(p)){
				 min = nearest(x.right,p,min,!flag);
			 }
		 }
		 else{
			 min = nearest(x.right,p,min,!flag);
			 if (x.left != null &&
					 p.distanceTo(min) > x.left.rect.distanceTo(p)){
				 min = nearest(x.left,p,min,!flag);
			 }
		 }
		 return min;
		 
	 }
	 
}
