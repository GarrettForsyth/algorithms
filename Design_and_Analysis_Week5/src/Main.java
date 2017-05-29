import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

public class Main{

    private static int NUMBER_OF_VERTEX = 200;
	
	public static void main(String[] args){

        ArrayList<LinkedList<Edge>> adjListGraph = readGraphFile("./dijkstraData.txt");
		int[] shortestPaths = DijkstraShortestPath.computeShortestPath(adjListGraph, 1);
	    for( int i = 1; i < shortestPaths.length; i++){
	    	System.out.println(i + "\t" + shortestPaths[i]);
	    }	
	}

   	/**
  	 * Given a local file name, this method reads the file and
   	 * stores the data as a graph.
   	 * The format of the local file is expected to be : 
	 *   -- each line corresponds to a tail vertex and contains a list in the
	 *   format of ( head-vertex, edge-weight ) to all the vertices the tail
	 *   vertex points to.
   	 * @param filename the name of the local file to be read
   	 **/
   	public static ArrayList<LinkedList<Edge>> readGraphFile (String filename){
        ArrayList<LinkedList<Edge>> adjListGraph = new ArrayList<>(NUMBER_OF_VERTEX); 
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

				Vertex tail = new Vertex(vLabel, 0);
				for(int i = 1; i < splitLine.length; i= i+2){
					    int vertexHeadLabel = Integer.parseInt(splitLine[i]);
						int edgeWeight = Integer.parseInt(splitLine[i+1]);
                        Vertex head = new Vertex(vertexHeadLabel, 0);
						Edge newEdge = new Edge(tail, head, edgeWeight);
			        	adjListGraph.get(vLabel).add(newEdge);
				}

   			}
   	    } catch(Exception e){
   	    	e.printStackTrace();
   	    }	
		return adjListGraph;
   	}
}
