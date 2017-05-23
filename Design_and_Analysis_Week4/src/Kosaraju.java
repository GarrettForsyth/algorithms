import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * Project    : Design_And_Analysis_Week4
 * File       : Kosaraju.java
 * Description: Performs Kosaraju's two-pass algorithm on a graph formatted as
 *              an adjacency list and returns an int array where each index
 *              corresponds to the vertex label of original graph and its element
 *              corresponds to which strongly connected component(SCC) it belongs
 *              to. Indices with equal elements are part of the same SCC and the
 *              SCC is named after the leader's vertex label (the vertex from 
 *              which starting a depth-first search on it, will lead to the 
 *              discovery of the entire SCC).  
 * Date       : Tue 23 May 2017 02:30:05 PM EDT
 * @author    : Garrett Forsyth 
 **/
 
public class Kosaraju{

	private static int[] SCCLeaders;       // tracks where each vertex was discovered from
	private static int[] finishTimes;      // tracks the order each vertex was found
	private static int currFinishTime;     // finishing order counter
	private static boolean[] visited;      // tracks which vertices have been visited
    private static int[] orderToExplore;   // holds the order in which to search 
    private static int currentLeader;      // tracks which vertex started the current DFS
    private static boolean FIRST_PASS;     // tracks if on the first pass or second pass	

	public static int[] findSCC(ArrayList<LinkedList<Integer>> graph){
		setup(graph);
		ArrayList<LinkedList<Integer>> revGraph = createReversedGraph(graph);
		DFS_Loop(revGraph);
		FIRST_PASS = false;
		visited = new boolean[graph.size()];
		DFS_Loop(graph);

		return SCCLeaders; 
	}

	private static void setup(ArrayList<LinkedList<Integer>> g){
		SCCLeaders = new int[g.size()];
		finishTimes = new int[g.size()];
		visited = new boolean[g.size()];
		orderToExplore = new int[g.size()];
		FIRST_PASS = true;
		currFinishTime = 0;
	}

	/**
	 * Returns a new graph with all the edges reversed.
	 * @param graph the graph to be reversed.
	 **/
	private static ArrayList<LinkedList<Integer>>
		createReversedGraph(ArrayList<LinkedList<Integer>> graph){
        ArrayList<LinkedList<Integer>> revGraph = new ArrayList<>(graph.size());

		// initialize the linked list in each entry.  
		for( int i = 0; i < graph.size(); i++){
			revGraph.add(new LinkedList<Integer>());
		}
		
		for(int vLabel = 1; vLabel < graph.size(); vLabel++){
		    LinkedList<Integer> adjListofVLabel = graph.get(vLabel);	

			for(int i = 0; i < adjListofVLabel.size(); i++){
			   revGraph.get(adjListofVLabel.get(i)).add(new Integer(vLabel));
			}
		}
		return revGraph;
	}
	
	private static void DFS_Loop(ArrayList<LinkedList<Integer>> g){
        if(FIRST_PASS){
			for(int i = 0; i < g.size(); i++){
				orderToExplore[i] = i; 
			}
		}
		else{
			orderToExplore = finishTimes;
		}

		for( int i = g.size()-1; i > 0; i--){
			if(!visited[orderToExplore[i]]){
				if(!FIRST_PASS){ currentLeader = orderToExplore[i];}
				DFS(g, orderToExplore[i]);
			}
		}
	}

	private static void DFS(ArrayList<LinkedList<Integer>> g, int startVertex){
		visited[startVertex] = true;
		if(!FIRST_PASS){ SCCLeaders[startVertex] = currentLeader;}
		for(int headVertex: g.get(startVertex)){
            if(!visited[headVertex]){
				DFS(g, headVertex);
			}
		}
		if(FIRST_PASS){
		    currFinishTime++;
		    finishTimes[currFinishTime] = startVertex;	
		}
	}
}
