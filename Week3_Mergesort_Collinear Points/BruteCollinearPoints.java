import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;
import java.util.ArrayList;

public final class BruteCollinearPoints {
	private final ArrayList<LineSegment> ls;
	private int n;
	public BruteCollinearPoints(Point[] points){
		if (points == null) 
			throw new java.lang.NullPointerException();
		int N = points.length;
		ls = new ArrayList<LineSegment>();
		Point[] points2 = new Point[N];
		for (int o = 0; o < N; o++){
			points2[o] = points[o];
		}
		n = 0;
		Arrays.sort(points2);
		if (N >= 4){
			for(int i = 0; i < N-3; i++){
				if (points2[i] == null)
					throw new java.lang.NullPointerException();
				for(int j = i+1; j< N-2; j++){
					for(int k = j+1; k < N-1; k++){
						for(int l = k+1; l < N; l++){
							if (points2[i].equals(points2[j])
									|| points2[i].equals(points2[k])
									|| points2[i].equals(points2[l])
									|| points2[j].equals(points2[k])
									|| points2[j].equals(points2[l])
									|| points2[k].equals(points2[l]))
								throw new java.lang.IllegalArgumentException();
							if (points2[i].slopeTo(points2[j]) == points2[i].slopeTo(points2[k])
									&& points2[i].slopeTo(points2[j]) == points2[i].slopeTo(points2[l]))
							{	
								Point[] temp = new Point[]{
										points2[i],points2[j],points2[k],points2[l]};
			                    Arrays.sort(temp);
								ls.add(new LineSegment(temp[0],temp[3]));
								n++;
							}
						}
					}
				}
			}
		}
		else{
			for (int p=0; p<N; p++){
				for (int q = p+1; q < N;q++){
					if (points2[p].equals(points2[q]))
	            		throw new java.lang.IllegalArgumentException();}
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
		BruteCollinearPoints Brute = new BruteCollinearPoints(points);
		for (LineSegment segment : Brute.segments()){
			StdOut.println(segment.toString());
			segment.draw();
		}
		StdDraw.show();
	}
}
