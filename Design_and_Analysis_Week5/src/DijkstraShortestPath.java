import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * Project    : Design_And_Analysis_Week5
 * File       : DijkstraShortestPath.java 
 * Description: Computes the shortest path between a vertex and all other
 *              vertices in a graph using Dijkstra's algorithm.
 *              A heap structure is used to hold vertices to be searched.
 *              Any vertex not connected to the start vertex is defaulted
 *              to be a distance of 1000000 away.
 *
 * Date       : Mon 29 May 2017 11:59:51 AM EDT
 * @author    : Garrett Forsyth 
 **/
 
public class DijkstraShortestPath{

	private static ArrayList<LinkedList<Edge>> graph;
	private static Set<Integer> processedVerticesIds;
	private static int[] shortestDistanceTo; // expect integer weights for edges
	private static MinHeap<Vertex> vertexProcessingQueue;
	private static boolean[] isVertexProcessed; // extra space to save O(n) search in heap

	public static int[] computeShortestPath(ArrayList<LinkedList<Edge>> graph,
			int startVertexId){
		setup(graph, startVertexId);
		processingLoop();
		return shortestDistanceTo;
	}

	/**
	 * Main loop of the algorithm.
	 * Note: in general, it would take O(n) to find (and delete) a vertex
	 * in the heap. One way to overcome this would be to maintain a map
	 * between vertices and their position in the heap. Here, instead of
	 * deleting vertices, they are only processed if they haven't been
	 * seen before (this is done by tracking in a boolean array which takes
	 * less space than a map between vertices and heap indices). As a
	 * consequence, this implementation will be more expensive  in cases where
	 * there are many edges pointing to the same vertex. 
	 **/
	private static void processingLoop(){
		Vertex vertexBeingProcessed;
        while(processedVerticesIds.size() != graph.size()-1 &&
			   	!vertexProcessingQueue.isEmpty()){// -1 from dummy 0 index in graph	      

            vertexBeingProcessed = vertexProcessingQueue.getMin();
        	if(isVertexProcessed[vertexBeingProcessed.getId()] == false){
                shortestDistanceTo[vertexBeingProcessed.getId()] = vertexBeingProcessed.getPriority();
				addVerticesToProcessingQueue(vertexBeingProcessed);
			}
			processedVerticesIds.add(vertexBeingProcessed.getId());
			isVertexProcessed[vertexBeingProcessed.getId()] = true;
        }
	}

	private static void addVerticesToProcessingQueue(Vertex vertexBeingProcessed){
		for(Edge edge: graph.get(vertexBeingProcessed.getId())){
			int headVertexId = edge.getHead().getId();
			int priority = shortestDistanceTo[vertexBeingProcessed.getId()] + edge.getWeight();
            vertexProcessingQueue.add(new Vertex(headVertexId, priority));
		}
	}

	private static void setup(ArrayList<LinkedList<Edge>> g, int startVertexId){
		graph = g;
        shortestDistanceTo = new int[g.size()];
		isVertexProcessed = new boolean[g.size()];
		vertexProcessingQueue = new MinHeap();
		processedVerticesIds = new HashSet<>();
		
		vertexProcessingQueue.add(new Vertex(startVertexId, 0));

		for(int i = 1; i < shortestDistanceTo.length; i++){
			shortestDistanceTo[i] = 1000000;
		}
		shortestDistanceTo[startVertexId] = 0;
	}
    
}  
