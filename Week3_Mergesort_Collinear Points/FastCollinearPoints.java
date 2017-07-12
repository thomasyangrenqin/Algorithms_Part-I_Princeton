import java.util.Arrays;
import java.util.ArrayList;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public final class FastCollinearPoints {
	private final ArrayList<LineSegment> ls;
	private int n;
	public FastCollinearPoints(Point[] points){
		if (points == null) 
			throw new java.lang.NullPointerException();
		int N = points.length;
		ls = new ArrayList<LineSegment>();
		Point[] copy = new Point[N];
		Point[] points2 = new Point[N];
		for (int o = 0; o < N; o++){
            copy[o] = points[o];
			points2[o] = points[o];}
		n = 0;
		for (int i = 0; i<N; i++){
			Point base = copy[i];
			if (copy[i] == null)
				throw new java.lang.NullPointerException();
			if (N >= 4) {
				Arrays.sort(points2,0,N,base.slopeOrder());
				int lo = 1;
	            int hi = 2;
	            boolean flag = base.compareTo(points2[lo]) < 0 ? true : false;
	            while (hi < N) {
	            	if (base.equals(points2[lo])||base.equals(points2[hi]))
	            		throw new java.lang.IllegalArgumentException();
	                if (points2[hi].slopeTo(base) == points2[lo].slopeTo(base)) {
	                    if (points2[hi].compareTo(base) < 0)
	                        flag = false;
	                }
	                
	                else {
	                    
	                    if (flag && hi - lo >= 3){
	                        
	                        Arrays.sort(points2, lo, hi);
	                        
	                        ls.add(new LineSegment(base,points2[hi-1]));
	                        n++;
	                     }
	                    lo = hi;
	                    flag = base.compareTo(points2[lo]) < 0 ? true : false;
	                }
	                hi++;
	            }
	            if (points2[N-1].slopeTo(base) == points2[lo].slopeTo(base)) {
	                if (flag && hi - lo >= 3){
	                	Arrays.sort(points2, lo, N);
	                    
	                    ls.add(new LineSegment(base,points2[N-1]));
	                    n++;
	                }
	            }
			}
			else{
				for (int q=i+1; q<N; q++){
					if (base.equals(points2[q]))
	            		throw new java.lang.IllegalArgumentException();
				}
			}
		}
	}
	
	public int numberOfSegments(){
		return n;
	}
	
	public LineSegment[] segments(){
		LineSegment[] ls_final = new LineSegment[n];
		for (int i = 0; i < n; i++){
			ls_final[i] = ls.get(i);
		}
		return ls_final;
	}
	
	public static void main(String[] args) {
		int number = Integer.parseInt(args[0]);
		Point[] points = new Point[number];
		StdDraw.enableDoubleBuffering();
	    StdDraw.setXscale(0, 32768);
	    StdDraw.setYscale(0, 32768);
		for (int l = 0; l<number;l++){
			int x = StdIn.readInt();
			int y = StdIn.readInt();
			points[l] = new Point(x,y);
		}
		FastCollinearPoints fast = new FastCollinearPoints(points);
		for (LineSegment segment : fast.segments()){
			StdOut.println(segment.toString());
			segment.draw();
		}
		StdDraw.show();
	}
}
