import java.util.ArrayList;

/**
 * Project    : Design_and_Analysis_Week3
 * File       : VertexEntry.java 
 * Description: Holds an Integer representing a vertex label
 *              and an ArrayList representing the vertex's
 *              adjacent vertices. 
 * Date       : Fri 19 May 2017 01:05:37 PM EDT
 * @author    : Garrett Forsyth 
 **/
public class VertexEntry {

	private Integer vertexLabel;
	private ArrayList<Integer> adjList;

	public VertexEntry(Integer vertexLabel, ArrayList<Integer> adjList) {
		this.vertexLabel = vertexLabel;
		this.adjList = adjList;
	}

	public void setVertexLabel(Integer vertexLabel) {
		this.vertexLabel = vertexLabel;
	}

	public Integer getVertexLabel() {
		return vertexLabel;
	}

	public void setAdjList(ArrayList<Integer> adjList) {
		this.adjList = adjList;
	}

	public ArrayList<Integer> getAdjList() {
		return adjList;
	}

	@Override
	public int hashCode(){ return vertexLabel.hashCode() ^ adjList.hashCode();}

	@Override
	public boolean equals(Object o){
		if(!(o instanceof VertexEntry)) return false;
		VertexEntry ve = (VertexEntry) o;
		return this.vertexLabel.equals(ve.getVertexLabel()) &&
			   this.adjList.equals(ve.getAdjList());
	}

}
