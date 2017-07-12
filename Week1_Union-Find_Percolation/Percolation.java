import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private int[][] model;
	private int dim;
	private WeightedQuickUnionUF uf;
	private WeightedQuickUnionUF uf2;
	private int open_count;
	
	
	public Percolation (int n){
		if (n <= 0) throw new IllegalArgumentException();
		dim = n;
		open_count = 0;
		model = new int[n][n];
		for (int i=0;i<n;i++){
			for (int j=0;j<n;j++){
				model[i][j] = -1;
			}
		}
		uf = new WeightedQuickUnionUF(n*n+2);
		uf2 = new WeightedQuickUnionUF(n*n+1);
	}
	
	private void validate(int row, int col) {
        
        if (row > dim || col > dim || row < 1 || col < 1) {
            throw new IndexOutOfBoundsException("row/col should be between 1 and " + dim);  
        }
    }
	
	public void open(int row, int col){
		validate(row,col);
		if(!isOpen(row,col)){
			model[row-1][col-1] = (row-1)*dim+col;
			open_count++;
		}
		if(col<dim && model[row-1][col] != -1){
			uf.union((row-1)*dim+col,(row-1)*dim+col+1);
			uf2.union((row-1)*dim+col,(row-1)*dim+col+1);
		}
		if(col>1 && model[row-1][col-2] != -1){
			uf.union((row-1)*dim+col,(row-1)*dim+col-1);
			uf2.union((row-1)*dim+col,(row-1)*dim+col-1);
		}
		if(row<dim && model[row][col-1] != -1){
			uf.union((row-1)*dim+col,(row)*dim+col);
			uf2.union((row-1)*dim+col,(row)*dim+col);
		}
		if(row>1 && model[row-2][col-1] != -1){
			uf.union((row-1)*dim+col,(row-2)*dim+col);
			uf2.union((row-1)*dim+col,(row-2)*dim+col);
		}
		if(row == 1){
			uf.union((row-1)*dim+col,0);
			uf2.union((row-1)*dim+col,0);
		}
		if(row == dim){
			uf.union((row-1)*dim+col, dim*dim+1);
		}
		
	}
	
	
	
	public boolean isOpen(int row, int col){
		validate(row,col);
		if(model[row-1][col-1] == -1) return false;
		else return true;
	}
	
	public boolean isFull(int row, int col){
		validate(row,col);
		if (isOpen(row,col))
			return uf2.connected(0,(row-1)*dim+col);
		else return false;
	}
	
	public int numberOfOpenSites(){
		return open_count;
	}
	
	public boolean percolates(){
		return uf.connected(0, dim*dim+1);
	}
	
	public static void main(String[] args){
		int n = StdIn.readInt();
		if(n<=0){
			throw new java.lang.IllegalArgumentException("n must larger than 0");
		}
		Percolation per = new Percolation(n);
		while (!per.percolates()){
		int i = StdRandom.uniform(1,n+1);
		int j = StdRandom.uniform(1,n+1);
		per.open(i, j);
		}
		int open_number = per.numberOfOpenSites();
		double p = (double)open_number/(n*n);
		StdOut.println(p);
		StdOut.println(open_number);
	}
}
