package interview.questions;

import java.util.Iterator;
/*
 * This class would be better implemented using two stacks:
 * one to store all the items and another storing the
 * maximums.
 */

public class StackWithMax<T>{
        /*
         * Instance Variables:
         */
        private T[] s;
        //Variable used for indexing
        private int N=0;       
        /*
         * Constructor
         */
        public StackWithMax(int capacity){
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
        
        /*
         * Creates an iterator and looks for the
         * max value in the stack.
         */
        public T getMax(){
            T max=null;
            Iterator<T> stackIterator= this.iterator();
            if(stackIterator.hasNext()){max= stackIterator.next();}
            while(stackIterator.hasNext()){
                T item= (T) stackIterator.next();
                //assuming compareTo is defined for type T
                //i.e. T implements comparable;
               // if(item.compareTo(max) < 0){
                //    max= item;
               // }
            }
            return max;
        }
        /*
         * Iterator
         */
        public Iterator<T> iterator(){
            return new StackIterator();
        }
        
        /*
         * Stack should iterate following FIFO
         */ 
        private class StackIterator implements Iterator<T>{
            private int i= N;
            
            public boolean hasNext(){return i>0;}
            public T next(){ return s[--i];}
        }
    
}
