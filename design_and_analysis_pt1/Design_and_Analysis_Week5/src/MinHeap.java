import java.util.ArrayList;
import java.util.Collections;
/**
 * Project    : Design_and_Analysis_Week5 
 * File       : MinHeap.java 
 * Description: Very basic min-heap structure supporting
 *              -add
 *              -getMin 
 *              -isEmpty
 *              operations.
 * Date       : Mon 29 May 2017 12:18:33 PM EDT
 * @author    : Garrett Forsyth 
 **/
 
public class MinHeap<Key extends Comparable<Key>>{

	private static final int INITIAL_CAPACITY = 10;
	private int numberOfElements;
	public ArrayList<Key> data;

	public MinHeap() {
		data = new ArrayList<>(); 
		data.add(null); // dummy index at 0
		numberOfElements = 0;
	}

	/**
	 * Maintains heap invariant by swapping a child
	 * at index i with its parent if the child is 
	 * smaller. 
	 *
	 * Since i >= 0. Casting (int) i/2 behaves the 
	 * same as Math.floor(i/2).
	 *
	 **/
	private void bubbleUp(int i){
		if(i == 1) return;
		if(data.get(i/2).compareTo(data.get(i)) > 0){
            Collections.swap(data, i/2, i);
			bubbleUp(i/2);
		}   
	}

	/**
	 * Swaps parent with smallest child until heap 
	 * invariant is satisfied.
	 */
	private void bubbleDown(int i){
		if(2*i > numberOfElements) return;            // no children
		int s = getIndexOfSmallestChild(i);           // two children
		if(data.get(i).compareTo(data.get(s)) > 0){
		    Collections.swap(data, i, s);
            bubbleDown(s);
		}
	}
	

	private int getIndexOfSmallestChild(int i){
		if(data.size() <=  2*i+1)                             return 2*i;
		else if(data.get(2*i).compareTo(data.get(2*i+1)) < 0) return 2*i;
		else                                                  return 2*i+1;
	}

	public void add(Key key){
		data.add(key);
		numberOfElements++;
		bubbleUp(numberOfElements);
	}

	public Key getMin(){
		if(isEmpty()) throw new IndexOutOfBoundsException(); 
		Collections.swap(data, 1, numberOfElements);
		Key min = data.remove(numberOfElements);
		numberOfElements--;
		bubbleDown(1);
		return min;
	}

	public boolean isEmpty(){
		return numberOfElements == 0;
	}
}
