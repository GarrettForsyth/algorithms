import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Project    : Design and Analysis of Algorithms Week 3
 * File       : Main.java
 * Description: Main file of execution. Reads in graph text file and calls
 *              a randomized contraction algorithm on it.
 * Date       : Wed 17 May 2017
 * @author    : Garrett Forsyth 
 **/
public class Main{

	public static void main (String[] args) {
        ArrayList<VertexEntry>  g = readGraphFile("./kargerMinCut.txt");
		System.out.println("Min cut = " + getMinCut(g));
	}

	/**
	 * Given a local file name, this method reads the file and
	 * stores the data as a graph.
	 * The format of the local file is expected to be : 
	 * -- First number in a row is the vertex label
	 * -- Following numbers in the row are adjacent vertices.
	 * -- Vertex labels are in order, i.e. line 5 is vertex 5
	 * @param filename the name of the local file to be read
	 **/
	public static ArrayList<VertexEntry> readGraphFile (String filename){
        ArrayList<VertexEntry> graph = new ArrayList<>(); 
		BufferedReader br = null;

	    try {
			br = new BufferedReader(new FileReader(filename));
			String currLine;
			String[] splitLine;

			while ((currLine = br.readLine()) != null) {
			    splitLine = currLine.split("\\W");
				int vLabel = Integer.parseInt(splitLine[0]);
				graph.add(new VertexEntry(vLabel, new ArrayList<Integer>()));

				for(int i = 1; i < splitLine.length; i++){
                     graph.get(vLabel - 1).getAdjList().add(Integer.parseInt(
								 splitLine[i]));
				}
			}
	    } catch(Exception e){
	    	e.printStackTrace();
	    }	
		return graph;
	}

	/**
	 * Performs a randomized contraction algorithm on a graph
	 * n^2 times ( n being the number of vertices in the graph)
	 * and returns the smallest min cut found.
	 * @param g : graph to be searched for min cut
	 **/
	private static int getMinCut(ArrayList<VertexEntry> g){
		RandomContraction r = new RandomContraction();
	    int N = g.size()*g.size();	
		int min = r.contract(g);
		int x;
		r = new RandomContraction();
		for( int i = 0; i < N; i ++){
            x = r.contract(g);
           if( x < min ) min = x; 
		   r = new RandomContraction();
		}
		return min;
	}
}
