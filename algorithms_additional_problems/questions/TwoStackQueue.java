package interview.questions;

import java.util.LinkedList;

/**
 * Queue with two stacks. Implement a queue with two stacks 
 * so that each queue operations takes a constant amortized 
 * number of stack operations. 
 * 
 * Amortized analysis. Average running time per operation over
 * a worst-case sequence of operations.
 * 
 * 
 *  
 * @author Garrett
 *
 */

public class TwoStackQueue<T> {
        
    /*
     * Nested class that is stack to implement
     * the TwoStackQueue. Implemented with a 
     * LinkedList, each operation takes constant
     * time in the worst case.
     * 
     * Linked-list implementation.
     *・Every operation takes constant time in the worst case.
     *・Uses extra time and space to deal with the links.
     *Resizing-array implementation.
     * ・Every operation takes constant amortized time.
     * ・Less wasted space.
     * 
     * In this case we'll use an array implementation.
     */
    private miniStack<T> in;
    private miniStack<T> out;
    private int size;
    
    public TwoStackQueue(int capacity){
        in= new miniStack<T>(capacity);
        out= new miniStack<T>(capacity);
    }
    
    public void enqueue(T item){
        in.push(item);
    }
    
    public T dequeue(){
        if(out.isEmpty()){
            while(!in.isEmpty()){
                out.push(in.pop());
            }
        }

        return out.pop();
    }
    
    boolean isEmpty(){
        return out.isEmpty();
    }
    
     
    private class miniStack<T>{
        /*
         * Instance Variables:
         */
        private T[] s;
        //Variable used for indexing
        private int N=0;       
        /*
         * Constructor
         */
        public miniStack(int capacity){
           s= (T[]) new Object[capacity];
        }
        /*
         * Defining methods of a stacK:
         */
        public void push(T item){
            //Double size of array if full
            if(N== s.length){resize(2*s.length);}
           //set value, THEN increment
            s[N++]= item;
        }
        
        public T pop(){
            //decrement THEN return
           T item= s[--N];
           //This avoids 'loitering' since
           //the garbage collector will clean up
           s[N]= null;
           //Resize only if 1/4 full to avoid 'thrashing'
           //i.e. flipping between the expensive operations
           //of increasing size and decreasing size.
           if(N > 0 && N== s.length/4){resize(s.length/2);}
           return item;
            
        }
        
        public boolean isEmpty(){
            return N==0;
        }
        
        /*
         * Creates a new array of more appropriate size
         * and then copies information over to it.
         */
        private void resize(int capacity){
            T[] copy= (T[]) new Object[capacity];
            for(int i=0; i < N; i++){
                copy[i]=s[i];            
            }
            s=copy;
        }
    }
    public static void main(String[] args) {
         TwoStackQueue testQueue= new TwoStackQueue(10);
         for(int i=0; i < 10; i++){
             testQueue.enqueue(i);
         }
         for(int i=0; i < 5; i++){
            System.out.println(testQueue.dequeue());
         }
     
        /*
        TwoStackQueue testQueue= new TwoStackQueue(10);
        for(int i=100; i < 110; i++){
            testQueue.enqueue(i);
        }
        
        for(int i=0; i < 15; i++){
            System.out.println(testQueue.dequeue());
         }
         */
    }
        
}
