import java.util.Arrays;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Queue;

public final class Board {
	private final int N;
	private final int[] block;
	public Board(int[][] blocks){
		N = blocks[0].length;
		block = new int[N*N];
		for (int i = 0; i < N; i++){
			for (int j = 0; j < N; j++){
				block[i*N+j] = blocks[i][j];
			}
		}
	}
	
	private Board(int[] blocks) {
        N = (int) Math.sqrt(blocks.length);
        block = new int[blocks.length];
        for (int i = 0; i < blocks.length; i++)
            block[i] = blocks[i];
    }
	
	public int dimension(){
		return N;
	}
	
	public int hamming(){
		int h_n = 0;
		for (int i = 0; i < block.length; i++){
			if (block[i] != 0 && block[i] != i+1){
				h_n++;
			}
		}
		return h_n;
	}
	
	public int manhattan(){
		int sum = 0;
		int row, col;
		for (int i = 0; i < block.length; i++){
			if (block[i] != 0 && block[i] != i+1){
				row = Math.abs(i/N-(block[i]-1)/N);
				col = Math.abs(i%N-(block[i]-1)%N);
				sum = sum+row+col;
			}
		}
		return sum;
	}
	
	public boolean isGoal(){
		for (int i = 0; i < block.length; i++){
			if (block[i] != 0 && block[i] != i+1){
				return false;
			}
		}
		return true;
	}
	
	public boolean equals(Object y){
		if (y == null) return false;
        if (y == this) return true;
        if (y.getClass() != this.getClass())
            return false;

        Board yBoard = (Board) y;
        return Arrays.equals(block, yBoard.block);
	}
	
	public Board twin(){
		if (N <= 1)    return null;
		
		int[] tblock = new int[block.length];
		for (int n = 0; n < block.length; n++){
			tblock[n] = block[n];
		}
		if (tblock[0] != 0 && tblock[1] != 0){
			exch(tblock,0,1);
		}
		else{
			exch(tblock,N,N+1);
		}
		Board tboard = new Board(tblock);
		return tboard;
	}
	
	public Iterable<Board> neighbors(){
		int loc;
		Board nboard;
		Queue<Board> pq = new Queue<Board>(); 
		
		for (loc = 0; loc < block.length; loc++){
			if (block[loc] == 0) break;
		}
		if (loc >= block.length) return null;
		
		if (loc >= N) {
			int[] up_block = new int[block.length];
			for (int n = 0; n < block.length; n++){
				up_block[n] = block[n];
			}
			exch(up_block,loc,loc-N);
			nboard = new Board(up_block);
			pq.enqueue(nboard);
		}
		if (loc < block.length - N){
			int[] down_block = new int[block.length];
			for (int n = 0; n < block.length; n++){
				down_block[n] = block[n];
			}
			exch(down_block,loc,loc+N);
			nboard = new Board(down_block);
			pq.enqueue(nboard);
		}
		if (loc % N != 0){
			int[] left_block = new int[block.length];
			for (int n = 0; n < block.length; n++){
				left_block[n] = block[n];
			}
			exch(left_block,loc,loc-1);
			nboard = new Board(left_block);
			pq.enqueue(nboard);
		}
		if ((loc+1) % N != 0){
			int[] right_block = new int[block.length];
			for (int n = 0; n < block.length; n++){
				right_block[n] = block[n];
			}
			exch(right_block,loc,loc+1);
			nboard = new Board(right_block);
			pq.enqueue(nboard);
		}
		return pq;
	}
	
	public String toString(){
		 StringBuilder s = new StringBuilder();
	     int digit = 0;
	     String format;

	     s.append(N);
	     s.append("\n");

	     for (int n = block.length; n != 0; n /= 10)
	    	 digit++;

	     format = "%" + digit + "d ";
	     for (int i = 0; i < block.length; i++) {
	         s.append(String.format(format, block[i]));
	         if ((i+1) % N == 0)
	             s.append("\n");
	     }
	     return s.toString();
	}
	
	private void exch(int[] a,int i, int j) {
        int swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }
	
	public static void main(String[] args) {
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        StdOut.print(initial.toString());
        StdOut.print(initial.twin().toString());
        StdOut.println(initial.hamming());
        StdOut.println(initial.manhattan());
        StdOut.println(initial.dimension());
        StdOut.println(initial.isGoal());
        
        for (Board b : initial.neighbors()) {
            StdOut.println(b.toString());
            for (Board d : b.neighbors()) {
                StdOut.println("===========");
                StdOut.println(d.toString());
                StdOut.println("===========");
            }
        }
    }
}
