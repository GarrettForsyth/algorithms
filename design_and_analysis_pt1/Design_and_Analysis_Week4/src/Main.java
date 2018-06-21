import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Project    : Design_And_Analysis_Week4 
 * File       : Main.java 
 * Description: Reads in a graph form a local directory and computes
 *              the size of its five biggest strongly connected components
 *              using Kosaraju's Two-Pass algorithm. 
 * Date       : Tue 23 May 2017 10:20:34 AM EDT
 * @author    : Garrett Forsyth 
 **/
public class Main{

	private static final int NUMBER_OF_VERTEX = 875714;

	public static void main(String[] args){
        ArrayList<LinkedList<Integer>> adjListGraph = readGraphFile("./SCC.txt");

		int[] SCCs = Kosaraju.findSCC(adjListGraph);
		printSCCSizes(SCCs);
	}

   	/**
  	 * Given a local file name, this method reads the file and
   	 * stores the data as a graph.
   	 * The format of the local file is expected to be : 
   	 * -- First number tail vertex 
   	 * -- Following number head vertex 
	 * -- Tail vertices are given in ascending order
   	 * @param filename the name of the local file to be read
   	 **/
   	public static ArrayList<LinkedList<Integer>> readGraphFile (String filename){
        ArrayList<LinkedList<Integer>> adjListGraph = new ArrayList<>(NUMBER_OF_VERTEX); 
		adjListGraph.add(new LinkedList<>()); // add dummy index so index match labels
   		BufferedReader br = null;
   
   	    try {
   			br = new BufferedReader(new FileReader(filename));
   			String currLine;
   			String[] splitLine;
   
   			while ((currLine = br.readLine()) != null) {
   			    splitLine = currLine.split("\\W");
   				int vLabel = Integer.parseInt(splitLine[0]);
				while (vLabel >= adjListGraph.size()) {
				    adjListGraph.add(new LinkedList<>());	
				}
				adjListGraph.get(vLabel).add(Integer.parseInt(splitLine[1]));
   			}
   	    } catch(Exception e){
   	    	e.printStackTrace();
   	    }	
		return adjListGraph;
   	}

	/**
	 * Prints the sizes of the five biggest SCC's. The input expected
	 * is an array where each index (vertex label) that has equal elements
	 * is part of the same SCC. This array can be found by running the 
	 * Kosaraju.findSCC method on a graph.
	 * @param SCCs array containing information on which SCC each vertex
	 *             belongs to.
	 **/
	private static void printSCCSizes(int[] SCCs){
		// iterate over array and track how many entries belong to each SCC
		HashMap<Integer,Integer> SCCSizeMap = new HashMap<>();
		for(int i = 1; i < SCCs.length; i++){
			if(!SCCSizeMap.containsKey(SCCs[i])){
                SCCSizeMap.put(SCCs[i], 1);
			}
			else{
				SCCSizeMap.put(SCCs[i], SCCSizeMap.get(SCCs[i])+1);
			}
		}
        // find the 5 largest SCCs. If there are less than five, 0's will be returned
        ArrayList<Integer> fiveLargestSCCs = new ArrayList<>(Arrays.asList(0,0,0,0,0));
        for(Map.Entry<Integer, Integer> entry : SCCSizeMap.entrySet()){
			if(entry.getValue() > fiveLargestSCCs.get(getMinIndex(fiveLargestSCCs))){
				// the length of fiveLargestSCCs is always five, so constant work is done inside this loop 
			    fiveLargestSCCs.remove(getMinIndex(fiveLargestSCCs));	
				fiveLargestSCCs.add(entry.getValue());
			}
		}

		System.out.println("The five largest SCCs are " + fiveLargestSCCs);
	}

	/**
	 * Returns the index of the smallest element in an ArrayList<Intger>
	 * @param arr array to be examined.
	 **/
	private static int getMinIndex(ArrayList<Integer> arr){
        if( arr == null || arr.isEmpty()) return -1; 		
		int minIndex = 0;
		int minValue = arr.get(0);
		for( int i = 1; i < arr.size(); i++){
			if (arr.get(i) < minValue){
                 minValue = arr.get(i);
				 minIndex = i;
			}
		}
		return minIndex;
	}
}
