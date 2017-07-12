import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class RandomizedQueue<Item> implements Iterable<Item> {
	private Item[] q;
	private int n;
	private int first;
	private int last;
	
	public RandomizedQueue (){
		q = (Item[]) new Object[2];
        n = 0;
        first = 0;
        last = 0;
	}
	
	public boolean isEmpty() {
        return n == 0;
    }
	
	public int size() {
        return n;
    }
	
	private void resize(int capacity) {
        assert capacity >= n;
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            temp[i] = q[(first+i) % q.length];
        }
        q = temp;
        first = 0;
        last  = n;
    }
	
	public void enqueue(Item item) {
		if (item == null) throw new NullPointerException();
        if (n == q.length) resize(2*q.length);   
        q[last++] = item;                        
        n++;
    }
	
	public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        int number = StdRandom.uniform(n);
        Item item = q[number];
        if (number != n-1){
        	q[number] = q[n-1];
        	q[n-1] = null;
        	}
        else{
        	q[number] = null;
        }
        n--;
        last--;
        if (n > 0 && n == q.length/4) resize(q.length/2); 
        return item;
    }
	
	public Item sample(){
		if (isEmpty()) throw new NoSuchElementException("Queue underflow");
		int number = StdRandom.uniform(0,n);
		Item item = q[number];
		return item;
	}
	
	public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {
    	private int I = 0;
    	private Item[] new_q;
    	
    	public ArrayIterator(){
    		new_q = (Item[]) new Object[n];
    		for (int i = 0; i < n; i++){
    			new_q[i] = q[i];
    		}
    		StdRandom.shuffle(new_q);
    	}
        public boolean hasNext()  { return I < n;                               }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return new_q[I++];
        }
    }
    
    public static void main(String[] args) {
        RandomizedQueue<String> q = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (item.equals("-")) 
                StdOut.println("item removed: " + q.dequeue());
            else if (item.equals("s"))
                StdOut.println("sample item: " + q.sample());
            else 
                q.enqueue(item);
            StdOut.println("(" + q.size() + " left on queue)");
        }
    }
    
    
}
