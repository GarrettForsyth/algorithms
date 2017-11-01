package queues;



import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

/*
 * A randomized queue is similar to a stack or queue, 
 * except that the item removed is chosen uniformly at
 * random from items in the data structure. Create a 
 * generic data type RandomizedQueue that implements 
 * the following API:
 * 
 * Performance requirements. Your randomized queue implementation
 *  must support each randomized queue operation (besides creating 
 *  an iterator) in constant amortized time. That is, any sequence 
 *  of m randomized queue operations (starting from an empty queue) 
 *  should take at most cm steps in the worst case, for some constant 
 *  c. A randomized queue containing n items must use at most
 *  48n + 192 bytes of memory. Additionally, your iterator 
 *  implementation must support operations next() and hasNext() in 
 *  constant worst-case time; and construction in linear time; you 
 *  may (and will need to) use a linear amount of extra memory per 
 *  iterator.
 *  
 *  Resizing-array implementation.
 * ・Every operation takes constant amortized time.
 * ・Less wasted space.
 */
public class RandomizedQueue<Item> implements Iterable<Item>{
    /*
     * Instance variables:
     */
    private Item[] q;
    private int N;
    /*
     * construct an empty randomized queue
     */
     @SuppressWarnings("unchecked")
    public RandomizedQueue(){
         //start with initial capacity of 10
         q= (Item[]) new Object[10];
         //first element will go into 5th index
         N=0;
     }
    /*
     * is the queue empty?
     */
     public boolean isEmpty(){return N==0;}
    /*
     * return the number of items on the queue
     */
    public int size(){return N; }
    /*
     * add the item
     */
    public void enqueue(Item item){
        if(item==null){
            throw new NullPointerException("Cannot add null item.");
        }
        if(N== q.length){resize(2*q.length);} 
        q[N++]= item;
     }
     /*
     * remove and return a random item
     */
     public Item dequeue(){
         if(this.isEmpty()){
             throw new NoSuchElementException("Cannot remove from empty deque.");
         }
         if(N < q.length/4){resize(q.length/2);}
         int randIndex= StdRandom.uniform(N); // [0,N-1]
         Item ret= q[randIndex];
         //move entry from tail to fill in 
         if(N!=1){
             q[randIndex]=q[N-1];
             q[N-1]=null;
         }
         N--;
         return ret;
     }
    /*
     * return (but do not remove) a random item
     */
     public Item sample(){
         if(this.isEmpty()){
             throw new NoSuchElementException("Cannot remove from empty deque.");
         }
         int randIndex= StdRandom.uniform(N); // [0,N-1]
         return  q[randIndex];
         
     }
    /*
     * return an independent iterator over items in random order
     */
    public Iterator<Item> iterator(){       
        return new RandomIterator(copyOfRange(q, 0, N));        
    }
    /*
     * private inner class defining an iterator:
     */
    private class RandomIterator implements Iterator<Item>{
        Item[] copy;
        int i=0;
        public RandomIterator(Item[] in){
            StdRandom.shuffle(in);
            this.copy=in;
        }
        public boolean hasNext(){ return i<N;}
        public Item next(){
            if(!hasNext()){
                throw new NoSuchElementException("No more elements to iterate over.");
            }
            return  copy[i++];
        }
        public void remove(){
            throw new UnsupportedOperationException("remove() is not supported.");
        }
    }
    /*
     * private helper that makes the array larger
     * and shifts all the values to the front.
     */
    private void resize(int capcity){
        @SuppressWarnings("unchecked")
        Item[] copy= (Item[]) new Object[capcity];
        //refill q starting from the end
        int size= N;
        for(int i=0; i < size; i++){         
            copy[i]= q[i];
        }
        q=copy;
    }
    /*
     * helper method:
      */
    private Item[] copyOfRange(Item[] arr, int start, int end){
        @SuppressWarnings("unchecked")
        Item[] copy= (Item[]) new Object[end-start];
        for(int i=0; i < end-start; i++){
            copy[i]= arr[start+i];
        }
        return copy;
    }
    /*
    * unit testing (optional)
     */
    public static void main(String[] args){
           // RandomizedQueue rq= new RandomizedQueue();
           // for(int i=0; i < 15; i++){
          //      rq.enqueue(i);
          //  }
            
          //  Iterator itr1= rq.iterator();
           // Iterator itr2= rq.iterator();
            
           // while(itr1.hasNext()){
           //     int num= (int) itr1.next();
           //     System.out.println(num);
          //  }
            
          //  System.out.println("****");
            
          //  while(itr2.hasNext()){
           //     int num= (int) itr2.next();
            //    System.out.println(num);
           // }
            
    }
}
