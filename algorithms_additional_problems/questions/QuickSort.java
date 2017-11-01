package interview.questions;

import java.util.Arrays;
import java.util.Comparator;

import edu.princeton.cs.algs4.StdRandom;

public class QuickSort {
    
    
    public static Comparable select(Comparable[] a, int k){
        StdRandom.shuffle(a);
        int lo = 0;
        int hi = a.length - 1;
        
        while( hi > lo){
            int j = partition(a, lo, hi);
            if (j < k) lo = j + 1;
            if (j > k) hi = j - 1;
            else    return a[k];
        }
        return a[k];
    }
    /**
     * Performs quick on entire array of
     * Comparables
     * @param a array of Comparables
     * @param lo
     * @param hi
     */
    public static void sort(Comparable[] a){
        StdRandom.shuffle(a); // shuffle to guarantee performance
        sort(a, 0, a.length-1);
    }
    
    /**
     * Performs quick sort on a range of an array
     * of Comparables
     * @param a array of Comparables
     * @param lo
     * @param hi
     */
    private static void sort(Comparable[] a, int lo, int hi){
        if( hi <= lo) return;
        int j = partition(a , lo, hi);
        sort(a, lo, j-1);
        sort(a, j+1, hi);
    }
    
    //a tuned quick sort to enhance performance in the presence of 
    //duplicate keys
    private static void duplicateSort(Comparable[] a, int lo, int hi){
        if (hi <= lo) return;
        int lt = lo;
        int gt = hi;
        Comparable v = a[lo];
        int i = lo;
        while(i <= gt){
            int cmp = a[i].compareTo(v);
            if      (cmp > 0) exch(a, lt++, i++);
            else if (cmp < 0) exch(a, i, gt--);
            else              i++;
        }
        
        duplicateSort(a, lo, lt - 1);
        duplicateSort(a, gt +1, hi);
    }
    
    
    /**
     * For some j:
     * puts entry a[j] in its sorted place within the array
     * no larger entry to the left of j
     * no smaller entry to the right of j
     * @param a
     * @param lo
     * @param hi
     * @return index of partitioning item
     */
    private static int partition(Comparable[] a, int lo, int hi){
        int i = lo;
        int j = hi + 1;
        
        while(true){
            while (less(a[++i], a[lo])) //search for item on left to swap
               if(i == hi) break;
            while (less(a[lo], a[--j])) //search for item on the right to swap
                if(j  == lo) break;    
            
            if( i >= j) break; //check if pointers cross
            exch(a, i, j); //swap
        }            
        
        exch(a , lo, j); //swap with partitioning item
        return j; //return index of item now known to be in place
    }
    
    /**
     * Compares the values of two comparables using the
     * objects's compareTo() method
     * @param 1st comparable to be compared
     * @param second comparable to be compared
     * @return boolean state of whether first object
     * is less than the second object.
     */
    
    private static boolean less(Comparable v, Comparable w){
        return v.compareTo(w) < 0;
    }
    /**
     * Override of less method. Uses a Comparator to compare
     * two objects rather than the comparable's compareTo().
     * @param a comparator to compare the objects with
     * @param object one to be compared
     * @param object two to be compared
     * @return  boolean state of whether first object
     * is less than the second object.
     */
    private static boolean less(Comparator c, Object v, Object w){
        return c.compare(v,w) < 0;
    }
    
        /**
         * Exchanged two objects in an array of comparables
         * using the object's compareTo() method
         * @param an array of comparable objects
         * @param 1st object to be exchanged
         * @param 2nd object to be exchanged
         */
    private static void exch(Comparable[] a, int i, int j){
        Comparable swap = a[i]; //create copy
        a[i] = a[j];
        a[j]= swap;  
    }
    
    /**
     * Checks if an array of Comparables is sorted done
     * via the object's compareTo method.
     * @param an array of comparable objects
     * @return boolean state of whether array is sorted.
     */
    private static boolean isSorted(Comparable[] a){
        for(int i = 1; i < a.length; i++){
            if(less(a[i], a[i-1])) return false;
        }
        return true;
    }
    
    public static void main(String[] args) {
        
        int size = 100;
        Integer[] arr= new Integer[size];
        
        for(int i = 0; i < size; i++){
            arr[i]= StdRandom.uniform(size) +1;
        }
        System.out.println(Arrays.toString(arr));
        sort(arr);
        System.out.println(Arrays.toString(arr));
     }
    
    

    

}
