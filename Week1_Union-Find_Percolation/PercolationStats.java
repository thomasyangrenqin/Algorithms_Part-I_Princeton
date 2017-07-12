import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	private int num_trials;
	private int num_per;
	private double[] results;
	
	public PercolationStats(int n, int trials){
		num_trials = trials;
		num_per = n;
		results = new double[trials];
		if(n<=0){
			throw new java.lang.IllegalArgumentException("n must larger than 0");
		}
		if(trials <= 0){
			throw new java.lang.IllegalArgumentException("trials must larger than 0");
		}
		for(int k=0; k<num_trials; k++){
			Percolation per = new Percolation(num_per);
			while (!per.percolates()){
			int i = StdRandom.uniform(1,n+1);
			int j = StdRandom.uniform(1,n+1);
			per.open(i, j);
			}
			int open_number = per.numberOfOpenSites();
			double p = (double)open_number/(num_per*num_per);
			results[k] = p;
		}
		
	}
	
	public double mean(){
		return StdStats.mean(results);
	}
	
	public double stddev(){
		return StdStats.stddev(results);
	}
	
	public double confidenceLo(){
		return mean()-1.96*stddev()/Math.sqrt(num_trials);
	}
	
	public double confidenceHi(){
		return mean()+1.96*stddev()/Math.sqrt(num_trials);
	}
	
	public static void main(String[] args){
		int m = Integer.parseInt(args[0]);
		int n = Integer.parseInt(args[1]);
		if(m <= 0 || n <= 0){
			throw new java.lang.IllegalArgumentException("n/trails must larger than 0");
		}
		PercolationStats ps = new PercolationStats(m,n);
		StdOut.println("mean                    = " + ps.mean());
		StdOut.println("stddev                  = " + ps.stddev());
		StdOut.println("95% confidence interval = [" + ps.confidenceLo() + "," + ps.confidenceHi() + "]");
	}
}
