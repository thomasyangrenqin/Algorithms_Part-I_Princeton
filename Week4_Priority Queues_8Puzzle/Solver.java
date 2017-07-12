import java.util.Comparator;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;

public final class Solver {
	private final s_node goal;
	
	private class s_node{
		private int move;
		private Board board;
		private s_node pre_node;
		
		public s_node(Board b){
			move =0;
			board = b;
			pre_node = null;
		}
	}
	
	public Solver(Board initial){
		Order order_1 = new Order();
		MinPQ<s_node> normal = new MinPQ<s_node>(order_1);
		s_node normal_node = new s_node(initial);
		normal.insert(normal_node);
		
		Order order_2 = new Order();
		MinPQ<s_node> tw = new MinPQ<s_node>(order_2);
		s_node tw_node = new s_node(initial.twin());
		tw.insert(tw_node);
		
		s_node minNode = normal.delMin();
		s_node tw_minNode = tw.delMin();
		while(!minNode.board.isGoal() && !tw_minNode.board.isGoal()){
			for(Board normal_b:minNode.board.neighbors()){
				if((minNode.pre_node == null)
						|| !normal_b.equals(minNode.pre_node.board)){
					s_node n_node = new s_node(normal_b);
					n_node.move = minNode.move + 1;
					n_node.pre_node = minNode;
					normal.insert(n_node);
				}
			}
			for(Board tw_b:tw_minNode.board.neighbors()){
				if((tw_minNode.pre_node == null)
						|| !tw_b.equals(tw_minNode.pre_node.board)){
					s_node t_node = new s_node(tw_b);
					t_node.move = tw_minNode.move + 1;
					t_node.pre_node = tw_minNode;
					tw.insert(t_node);
				}
			}
			
			minNode = normal.delMin();
			tw_minNode = tw.delMin();
		}
		
		if(minNode.board.isGoal()){
			goal = minNode;
		}
		else{
			goal= null;
		}
	}
	
	public boolean isSolvable(){
		return goal != null;
	}
	
	public int moves(){
		if(isSolvable()){
			return goal.move;
		}
		else{
			return -1;
		}
	}
	
	public Iterable<Board> solution(){
		if(isSolvable()){
			Stack<Board> result = new Stack<Board>();
			for(s_node next = goal; next != null; next = next.pre_node){
				result.push(next.board);
			}
			return result;
		}
		else{
			return null;
		}
	}
	
	private class Order implements Comparator<s_node>{
		public int compare (s_node s1, s_node s2){
			int sum1 = s1.board.manhattan() + s1.move;
			int sum2 = s2.board.manhattan() + s2.move;
			
			if (sum1 > sum2)  return 1;
			else if (sum1 < sum2) return -1;
			else   return 0;
		}
	}
	
	public static void main(String[] args) {
		if (args == null){
			throw new java.lang.NullPointerException();
		}
	    // create initial board from file
	    In in = new In(args[0]);
	    int n = in.readInt();
	    int[][] blocks = new int[n][n];
	    for (int i = 0; i < n; i++)
	        for (int j = 0; j < n; j++)
	            blocks[i][j] = in.readInt();
	    Board initial = new Board(blocks);

	    // solve the puzzle
	    Solver solver = new Solver(initial);

	    // print solution to standard output
	    if (!solver.isSolvable())
	        StdOut.println("No solution possible");
	    else {
	        StdOut.println("Minimum number of moves = " + solver.moves());
	        for (Board board : solver.solution())
	            StdOut.println(board.toString());
	    }
	}
}
