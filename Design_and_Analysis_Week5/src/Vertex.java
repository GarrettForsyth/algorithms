/**
 * Project    : Design_and_Analysis_Week5
 * File       : Vertex.java
 * Description: This object represents a vertex in a graph. It has two fields
 *				1) an id field for reference
 *				2) a priority field for sorting purposes
 * Date       : Sun 28 May 2017 06:05:54 PM EDT
 * @author    : Garrett Forsyth 
 **/
 
public class Vertex implements Comparable<Vertex> {

	private int id;
	private int priority;

	public Vertex(int id, int priority) {
		this.id = id;
		this.priority = priority;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getPriority() {
		return priority;
	}

	public int compareTo(Vertex that){
		return (this.getPriority() - that.getPriority());
	}

}
