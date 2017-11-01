package main;

import java.util.Arrays;
import java.util.Random;

public class Sorting {
	
	public Sorting(){
		
	}
	
	//insertion sort for an integer array from largest to smallest
	public void insertionSort(int[] arr){
		for(int i=1; i < arr.length; i++){
			int key= arr[i];
			//insert arr[i} into the sorted sequence arr[0..(j-1)]
			int j= i-1; // compare to elements to left..
			while( j >= 0 && arr[j] < key){
				arr[j+1]= arr[j];
				j=j-1;
			}
			arr[j+1]=key;
		}
	}
	
	//selection sort
	public void selectionSort(int[] arr){
		for(int i=0; i < arr.length; i++){
			int key= arr[i];
			for(int j=0; j < arr.length; j++){
				if(arr[j] >key){
					arr[i]=arr[j];
					arr[j]=key;
					key= arr[i];
				}
			}
		}
	}
	
	//merge sort
	public void mergeSort(int[] arr, int start, int end){
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
	private void merge(int[] arr, int start, int mid, int end){
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
	
	//generates random numbers form 0 to 100 and puts in an array
	public int[] genRandIntArr(int size){
		Random rand= new Random();
		int[] arr= new int[size];
		for(int i=0; i < size; i++){
			arr[i]= (int)rand.nextInt(101);
		}
		return arr;
	}
	
	//prints unsorted array and sorted array
	public  void printSort(){
		int size=10;
		int[] arr= genRandIntArr(size);
		System.out.println("Printing unsorted array..");
		System.out.println(Arrays.toString(arr));
		mergeSort(arr,0,9);
		System.out.println("Printing sorted array..");
		System.out.println(Arrays.toString(arr));
	}


}
