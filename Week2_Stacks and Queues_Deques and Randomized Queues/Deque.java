import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;


public class Deque<Item> implements Iterable<Item> {
	private Node<Item> first,last;
	private int n;
	
	private static class Node<Item> {
        private Item item;
        private Node<Item> next;
        private Node<Item> pre;
    }
	
	public Deque(){
		first = null;
		last = null;
		n = 0;
	}
	
	public boolean isEmpty() {
		return n == 0;
	}
	
	public int size(){
		return n;
	}
	
	public void addFirst(Item item)
    {
        if (item == null)
            throw new java.lang.NullPointerException();
        Node<Item> oldFirst = first;
        first = new Node<Item>();
        first.item = item;
        if (isEmpty())
            first.next = null;
        else
        {
            first.next = oldFirst;
            oldFirst.pre = first;
        }
        first.pre = null;
        n++;
        if (n == 1)
            last = first;
        
    }
    
    public void addLast(Item item)
    {
        if (item == null)
            throw new java.lang.NullPointerException();
        Node<Item> oldLast = last;
        last = new Node<Item>();
        last.item = item;
        if (isEmpty())
            last.pre = null;
        else
        {
            last.pre = oldLast;
            oldLast.next = last;
        }
        last.next = null;
        ++n;
        if (n == 1)
            first = last;
    }

	
	public Item removeFirst()
    {
        if(isEmpty()) 
            throw new NoSuchElementException("Queue underflow");
        Item item = first.item;
        if (n > 1)
        {
            first = first.next;
            first.pre = null;
        }
        else
        {
            first = null;
            last = null;
        }
        --n;
        return item;
    }

    
    public Item removeLast()  
    {
        if (isEmpty()) 
            throw new NoSuchElementException("Queue underflow");
        Item item = last.item;
        if (n > 1)
        {
            last = last.pre;
            last.next = null;
        }
        else
        {
            last = null;
            first = null;
        }
        --n;
        return item;
    }
	
	public Iterator<Item> iterator(){
		return new ListIterator<Item>(first);
    }

    private class ListIterator<Item> implements Iterator<Item> {
        private Node<Item> current;

        public ListIterator(Node<Item>first) {
            current = first;
        }

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next; 
            return item;
        }
	}
    
    
    public static void main(String[] args) {
        Deque<String> q = new Deque<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (item.equals("l")) 
                q.addLast(item);
            else if (item.equals("--"))
                StdOut.println(q.removeFirst()+ " ");
            else if (item.equals("f"))
                q.addFirst(item);
            else
                StdOut.println(q.removeLast()+ " ");
             StdOut.println("(" + q.size() + " left on Deque)");
        }
       
    }
}
