
package interview.questions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Three sum problem is finding three numbers in a set that
 * sum to zero. 
 * 
 * 
 **/

public class ThreeSum {
  //merge sort this function works on nlgn time.
    public static void mergeSort(int[] arr, int start, int end){
        if(start < end){
            //System.out.println("Start: " + start);
            //System.out.println("End: "+ end);
            int mid= (end+start)/2;
            //System.out.println(mid);
            mergeSort(arr, start, mid);
            mergeSort(arr, mid+1, end);
            merge(arr, start, mid, end);
        }
    }
    
    //merge helper method
    private static void merge(int[] arr, int start, int mid, int end){
        //separate into two arrays with a sentinel at the end of each
        int n1= mid-start+1;
        int n2= end-mid;
        int[] L= new int[n1+1]; 
        int[] R= new int[n2+1];
        for(int i=0; i < n1; i++){ //fills in L array
            L[i]= arr[start+i];
        }
        for(int i=0; i < n2; i++){//fills in right array
            R[i]= arr[mid+i+1];
        }
        //add sentinel nodes
        L[n1]= Integer.MAX_VALUE;
        R[n2]= Integer.MAX_VALUE;
        
        //compare the 'top' element of each array
        //and add the smallest one to the original array
        int i=0;
        int j=0;
        for(int k= start; k <= end; k++){
            if(L[i] <= R[j]){
                arr[k]= L[i];
                i++;
            }
            else{
                arr[k]= R[j];
                j++;
            }
        }
    }
    
    /*
     * Compute binary search. Works in lgn time.
     */
    private static int ascendingBinarySearch(int[] a, int lo, int hi, int key){

        
        while(lo <= hi){
            int mid= lo+(hi-lo)/2;
            if(key < a[mid]) hi= mid-1;
            else if (key >a[mid]) lo= mid+1;
            else return mid;
        }
        return -1;
    }
    private static int descendingBinarySearch(int[] a, int lo, int hi, int key){

        
        while(lo <= hi){
            int mid= lo+(hi-lo)/2;
            if(key > a[mid]) hi= mid-1;
            else if (key < a[mid]) lo= mid+1;
            else return mid;
        }
        return -1;
    }
    /*
      * Computes 3SUM assuming input is sorted and
      * proportional to n^2 time.
      * The idea here is for each pair of entries (i,j)
      * search for the entry -(input[i] + input[j]) using
      * a binary search
      * 
      * Currently doesn't take into account double counting
      * or multiple occurrences of the same entry.
      */
    public static ArrayList<int[]> computeThreeSum(int[] input){
        ArrayList<int[]> output= new ArrayList<int[]>();
        for(int i=0; i < input.length; i++){
            for(int j=0; j < input.length; j++){
                int seek= -(input[i]+ input[j]);
                int result= ascendingBinarySearch(input, 0, input.length-1, seek);
                if(result != -1 ){                   
                    int[] ans={input[i], input[j], seek};
                    output.add(ans);
                }
            }
        }
        return output;
    }
    /*
     * Testing method
     */
    public static void testingThreeSum(int n){
        Random rand= new Random();
        int length=n;
        
        int[] randArray= new int[length];
        for (int i=0; i < length; i++){
            randArray[i]= rand.nextInt(40) -19; // rand number between -20, 20;
        }
        mergeSort(randArray,0, randArray.length-1);
       
        System.out.print(n + "\t");
        long start= System.nanoTime();
        computeThreeSum(randArray);
        long end= System.nanoTime();
        long diff= end-start;
        System.out.println("\t" + diff);
        
    }
    /*
     * An array is bitonic if it is comprised of an increasing sequence 
     * of integers followed immediately by a decreasing sequence of integers. 
     * Write a program that, given a bitonic array of n distinct integer
     *  values, determines whether a given integer is in the array.
     */
    public static void searchBitonic(int[] input, int start, int end, int key){
        //Take middle of array
        if(start==end){
            return; //only 1 entry
        }
        int mid= (start+end)/2;
        if(input[mid] == key){
            System.out.println("Found value: " + mid);
            return; //value found
        }
        //Compare middle element with one neighbor to see if max on right or left
        if(start+1==end){
            System.out.println("Cannot split array.");
            return; //Cannot split
        }
        

        if(input[mid] > input[mid-1]){ // if array is ascending 
            if(key > input[mid]){ //if the key is greater than the middle point
                //==> we  know the key can't be on the left
                //repeat on right side:
                searchBitonic(input, mid+1, end, key);
            }
            else{//key is less than middle point
                //we can compute two binary searches
                //one will be set will have part of it's sequence
                //out of order but since all the out of sequenced elements
                // are less than the key, this won't affect the search.
                ascendingBinarySearch(input, start, mid, key);           
                descendingBinarySearch(input, mid+1, end, key); //out of order won't affect results                           
            }
        }
        else{
            if(key > input[mid]){
                searchBitonic(input, start, mid, key);
            }
            else{
                ascendingBinarySearch(input, start, mid, key);           
                descendingBinarySearch(input, mid+1, end, key); //out of order won't affect results                           
            }
        }                    
    }
    
    public static void main(String[] args) {
       // for(int i=0; i < 20; i++){
       //     testingThreeSum((int) Math.pow(2, i));
       // }
        int[] bitonic= {1, 3, 4, 6, 9, 14, 11, 7, 2, -4, -9};
        searchBitonic(bitonic, 0, bitonic.length, -4);
    }
}

