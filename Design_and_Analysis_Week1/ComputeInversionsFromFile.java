package Week1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 *  Given a filename containing an array with each entry on its own line
 *  and the number of entries in the file, this class will compute the
 *  number of inversions using a divide and conquer technique. 
 *  
 *  It piggy backs merge sort and is expected to run at O(nlgn)
 *  This implementation of merge sort uses a copy of itself, so it takes
 *  up extra space.
 *  
 *  Also, the the data type used to track the number of inversions 
 *  was BigInteger instead of int, to handle larger data sets. BigInteger
 *  seems slower than using int, so this will add to the run time.
 *  
 *  A possible improvement to this would be to only use BigInteger if
 *  numbers reach above a certain threshold ( noting that the biggest
 *  possible int value is  2,147,483,647 (inclusive).
 * 
 * @author Garrett
 *
 */

public class ComputeInversionsFromFile {
	
	private static int[] data;
	
	/**
	 * Constructor...
	 * @param filename     name of file containing array; expected format is
	 * 					   1 entry per line.
	 * @param numEntries   i.e. the number of lines in the file
	 */
	public ComputeInversionsFromFile(String filename, int numEntries){
		data = new int[numEntries];
		readFile(filename);
	}
	
	/**
	 * Reads the file and stores it's data in an int array
	 * @param filename
	 */
	private static void readFile(String filename){
		
		try{
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			int entry = 0;
			while ((line = br.readLine()) != null){
				data[entry] = Integer.parseInt(line);
				entry++;
			}
			
			if (entry != data.length){
				System.out.println("Note : entries do not match expected length: ");
				System.out.println("Expected : " + data.length + " read : " + entry);
			}
			
		}catch (Exception e){
			e.printStackTrace();
		}
				
		
	}
	
	/**
	 * This function 'piggy-backs' a merge sort and counts the number of
	 * inversions as it is sorting.
	 * 
	 * @param arr	The array of data to have inversions counted.
	 * @return		The number of inversions in the array.
	 */
	public static BigInteger SortAndCount(int[] arr){
		// create copy of range to be sorted since original will get mixed up as it is sorted
		int[] copy = new int[arr.length];
		return SortAndCount(arr, copy, 0, arr.length-1);
	}
	
	/**
	 * 
	 * @param arr		The array of data to have inversions counted.
	 * @param copy		A copy of the array
	 * @param lo		the lowest index
	 * @param hi		the highest index in the range to be sorted and counted
	 * @return			the number of inversions in the range (also sorts)
	 */
	private static BigInteger SortAndCount(int[] arr, int[] copy, int lo, int hi){
		if ( hi<= lo) return new BigInteger("0");;
		int mid = lo + (hi - lo)/2; // note integer arithmetic
		BigInteger l = SortAndCount(arr, copy, lo, mid);
		BigInteger r = SortAndCount(arr, copy, mid+1, hi);
		BigInteger s = MergeAndCountSplitInv(arr, copy, lo, mid, hi);		
			
		return (l.add(r)).add(s);
	}
	
	/**
	 * Merges two pre-sorted sub arrays.
	 * arr[lo.. mid] is sorted.
	 * arr[mid+1, .. hi[ is sorted.
	 * 
	 * @param arr		The array of data to have inversions counted.
	 * @param copy		A copy of the array
	 * @param lo		the lowest index
	 * @param hi		the highest index in the range to be sorted and counted
	 * @return			the number of inversions in the range (also sorts)
	 *
	 */
	private static BigInteger MergeAndCountSplitInv(int[] arr, int[] copy, int lo, int mid, int hi){
		// create copy of range to be sorted since original will get mixed up as it is sorted:
		for (int i = lo; i <= hi; i ++){
			copy[i]= arr[i];
		}
		
	    BigInteger invCount = new BigInteger("0");   // tracks the number of inversions
	    BigInteger ONE= new BigInteger("1");
		int j = lo;			// tracks the lowest not merged element on left
		int k = mid +1;		// tracks the lowest not merged element on right
		
		// iterate over the range lo to hi
		for ( int l = lo; l <= hi; l++){
			// if the lowest element on the left is beyond mid,
			// then add all remaining elements from the right.
			if			(j > mid)			arr[l] = copy[k++];
			// similarly, if the lowest element on the right is
			// beyond hi, add all the remaining elements on the
			// left.
			else if		(k > hi)			arr[l] = copy[j++];
			// otherwise, compare the two and add the smaller element.
			else if     (copy[k] < copy[j]){			
				arr[l] = copy[k++];
				// if the right side element is inserted 
				// all elements on left side represent an inversion:
				for ( int m = j; m < mid+1; m++){
					invCount= invCount.add(ONE);
				}
			}
			else							arr[l] = copy[j++];
			
		}

		return invCount;
	}
	

	public static void main(String args[]){
		//ComputeInversionsFromFile inv = new ComputeInversionsFromFile(args[0], Integer.parseInt(args[1]));
		//BigInteger numInv = SortAndCount(data);
		//System.out.println("There were " + numInv + " found.");
		//addQuotes.addQuotesToList();
		
		addQuotes.createSQLCreateTableFromString();
		System.out.println("done");

	}

}
