import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

/**
 * My implementation of quick-sort that counts
 * the total number of comparisons. This is
 * dependent on the pivot element, so three
 * pivoting rules are explored:
 *
 * 1) always use the first element of the array
 * as the pivoting element 
 *
 * 2) always use the final element of the array 
 * as the pivoting element
 *
 * 3) 'median of three' pivot rule: use the median
 * of the first, middle and final elements as the
 * pivoting element
 *
 * Author: Garrett Forsyth
 * Date:   May 7th 2017
 *
 * Design and Analysis of Algorithms
 * Week II
 *
 */

public class MyQuickSort {

	private static int[] data;

	/**
	 * Reads in a file formatted as a list of
	 * integers, one integer per line. Size is also
	 * read in to initialize the array.
	 *
	 * Sorts the file, then outputs the number of
	 * comparisons using the pivoting rules mentioned
	 * above.
	 */
	public MyQuickSort(String filename, int size) {
		data = new int[size];
		readFile(filename);
		int compares = sort(data, 0, data.length-1);
		System.out.println("Compares: " + compares);
	}

	private static void readFile(String filename){
		BufferedReader br = null;
		FileReader fr = null;
		String currLine;
		int i=0;
		try{
		br = new BufferedReader(new FileReader(filename));

			while((currLine = br.readLine()) != null){
				data[i] = Integer.parseInt(currLine);
				i++;
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	/**
	 * Reads in an array and a range to sort on
	 * using one of the pivoting rules from
	 * above.
	 */
	private static int sort(int[] m, int lo, int hi){
		// note here, lo > hi if for example, the second last recursion acts
		// on [2,1] , where the partition function returns i-1 = 1
		// and the second sort calls on arguments (m, 2, 1) 
		if (lo >= hi) return 0;
		int p= choosePivot(m, lo, hi);
	    // pivot rule 1: int p = lo;
	    // pivot rule 2: p= hi;swap(m, hi, lo);	
	    swap(m, p, lo);	
 		p = partition(m,lo, hi);
	    int a = sort(m, lo, p-1);
		int b = sort(m, p+1, hi);
		return a+b+(hi-lo);
	}

	/**
	 * 'Median of Three Rule'
	 *  Gets median form first, last and middle elements
	 *  Side note: the Arrays' library sort function is used here
	 *  rather than the one in this class to avoid counting the comparisons
	 *  in calculating the median.
	 */
	private static int choosePivot(int[] arr, int lo, int hi){
		if( (hi-lo) %2 == 0){ // zero indexing ==> range is odd length
			int[] theThree = {arr[lo], arr[lo+(hi-lo)/2], arr[hi]}; 
		    Arrays.sort(theThree);
			 System.out.println("Median array "+ Arrays.toString(theThree));
			if (theThree[1] == arr[lo]) return lo;
			if (theThree[1] == arr[hi]) return hi ;	
			if (theThree[1] == arr[lo+(hi-lo)/2]) return (lo+(hi-lo)/2);
		}
		else{
	       int[] theThree = {arr[lo], arr[lo+(hi-lo)/2], arr[hi]}; 
		   Arrays.sort(theThree);
		   System.out.println("Median array "+ Arrays.toString(theThree));
		   if (theThree[1] == arr[lo]) return lo;
		   if (theThree[1] == arr[hi]) return hi ;	
		   if (theThree[1] == arr[lo+(hi-lo)/2]) return (lo+(hi-lo)/2);
		}
		return -1;
	}

	/**
	 * Partitions an array arr, over range [lo,hi]
	 * This is the crucial part of quick-sort.
	 */
	private static int partition(int[] arr, int lo, int hi){
		int i = lo+1;

		// last iteration should be when j EQUAL hi
		for (int j=lo+1; j <= hi; j++){
			if(arr[j] <= arr[lo]){
				swap(arr, j, i);
				i++;
			}	
		}
		swap(arr, lo , i-1);
		return (i-1);
	}

	/**
	 * Helper function:
	 * Swaps element at index a, with index b of array arr
	 */
	private static void swap(int[] arr, int a, int b){
		int temp = arr[a];
		arr[a]=arr[b];
		arr[b]=temp;
	}

	/**
	 * Testing:
	 */
	public static void main(String[] args){
        MyQuickSort qsrt = new MyQuickSort("../QuickSort.txt", 10000);		
		for(int i=0; i < 10; i++){
			System.out.println(data[i]);
		}
	}
}

