import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Project    : Design_and_Analysis_Week3
 * File       : RandomContraction.java 
 * Description: Performs a randomized contraction algorithm to find
 *              the min cut of a graph. It is expected that the graph
 *              is completely connected and undirected.
 * Date       : Fri 19 May 2017
 * @author    : Garrett Forsyth 
 **/
 
public class RandomContraction{

	private VertexEntry[] id;          // uses union-find structure to track merges
	private ArrayList<Integer> keyPool;// tracks available vertices
	private Random rand;

	/**
	 * Randomly contracts edges until only two vertices remain.
	 * The number of edges between them represent the min cuts.
	 * The correctness of this is not guaranteed, and it is
	 * recommended at least n^2 trails are performed, taking the
	 * smallest result.
	 * @param graph : the graph to be contracted
	 **/
	public int contract(ArrayList<VertexEntry> graph){
	    setup(graph);	

		while (keyPool.size() > 2) {
            int[] randEdge = getRandomEdge();
			merge(randEdge[0], randEdge[1]);
		}
		return id[keyPool.get(0)].getAdjList().size();
	}

	private void setup(ArrayList<VertexEntry> g){
        id  = new VertexEntry[g.size()+1]; 
        id[0] = null; // dummy variable so index matches labels
        keyPool = new ArrayList<>();

	    for( int i = 0; i < g.size(); i ++){
            // create a copy as to not mutate the original
			id[i+1] = new VertexEntry(new Integer(g.get(i).getVertexLabel()),
                                      new ArrayList<>(g.get(i).getAdjList()));
			keyPool.add(new Integer(g.get(i).getVertexLabel()));
		}	

        rand = new Random();	
	}

	/**
	 * Returns a random edge of the graph between the vertices
	 * stored in edge[0] and edge[1]
	 **/
	private int[] getRandomEdge(){
		int[] edge = new int[2];
		edge[0] = keyPool.get(rand.nextInt(keyPool.size()));
	    ArrayList<Integer> adj = id[edge[0]].getAdjList();
		edge[1] = adj.get(rand.nextInt(adj.size())); 
		return edge;
	}

	/**
	 * Merges two vertices, u and v, together.
	 * This entails :
	 *  - removing any self-loops (edges pointed from the merged 
	 *    vertex to the merged vertex)
	 *  - combining the adjacency lists of each vertex
	 *  - removing one vertex from the keyPool (the other remains
	 *    representing the merged vertex)
	 *  - tracking the union in the union-find structure
	 *
	 *  The adjacency list of a merged vertex is always kept at
	 *  the index of its root element. 
	 *
	 *  @param u first vertex to be merged
	 *  @param v second vertex to be merged
	 **/ 
	private void merge(int u, int v){
		ArrayList<Integer> rvAdj = id[root(v)].getAdjList(); // root(v)'s adj list
		ArrayList<Integer> ruAdj = id[root(u)].getAdjList(); // root(u)'s adj list

		for( int i = 0; i < ruAdj.size(); i++){              // remove internal edges
			if(find(ruAdj.get(i), v)){
				ruAdj.remove(i);
				i--;
			}
		}
		for( int i = 0; i < rvAdj.size(); i++){              // combine adj lists
			if(!find(rvAdj.get(i), u)){                      // avoid self-loops
				ruAdj.add(rvAdj.get(i));
			}
		}
		keyPool.remove(keyPool.indexOf(root(v)));
		union(u, v); // important to union after to access v's adj list
	}

	/****************************************
	 * The following are helper functions for
	 * the union-find structure.
	 ****************************************/

	private int root(int i){
		while ( i != id[i].getVertexLabel())
			i = id[i].getVertexLabel();
		return i;
	}

	private void union(int u, int v){
		int i = root(u);
		int j = root(v);
		id[j].setVertexLabel(i);
	}

	private boolean find(int u, int v){
		return root(u) == root(v);
	}

}
