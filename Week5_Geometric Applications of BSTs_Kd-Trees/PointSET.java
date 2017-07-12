import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class PointSET {
	private SET<Point2D> set;
	public PointSET(){
		set = new SET<Point2D>();
	}
	
	public boolean isEmpty(){
		return set.isEmpty();
	}
	
	public int size(){
		return set.size();
	}
	
	public void insert(Point2D p){
		set.add(p);
	}
	
	public boolean contains(Point2D p){
		return set.contains(p);
	}
	
	public void draw(){
		for(Point2D p : set){
			p.draw();
		}
	}
	
	public Iterable<Point2D> range(RectHV rect){
		SET<Point2D> range_set = new SET<Point2D>();
		for(Point2D p : set){
			if (p.x() <= rect.xmax() && p.x() >= rect.xmin()
					&& p.y() <= rect.ymax() && p.y() >= rect.ymin()){
				range_set.add(p);
			}
		}
		return range_set;
	}
	
	public Point2D nearest(Point2D p){
		if (set.isEmpty()) return null;
		Point2D p1 = null;
		double dis,mindis = Double.MAX_VALUE;
		for (Point2D p2 :set){
			dis = p2.distanceTo(p);
			if (dis<mindis){
				mindis = dis;
				p1 = p2;
			}
		}
		return p1;
	}

}
