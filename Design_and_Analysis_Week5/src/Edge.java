/**
 * Project    : Design_And_Analysis_Week5
 * File       : Edge.java
 * Description: A weighted edge from head to tail in a graph.
 * Date       : Sun 28 May 2017 06:16:03 PM EDT
 * @author    : Garrett Forsyth 
 **/
 
public class Edge {

	private Vertex head;
	private Vertex tail;
	private int weight;

	public Edge(Vertex tail, Vertex head, int weight) {
		this.head = head;
		this.tail = tail;
		this.weight = weight;
	}

	public void setHead(Vertex head) {
		this.head = head;
	}

	public Vertex getHead() {
		return head;
	}

	public void setTail(Vertex tail) {
		this.tail = tail;
	}

	public Vertex getTail() {
		return tail;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getWeight() {
		return weight;
	}

}
