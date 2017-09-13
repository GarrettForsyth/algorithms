package main;
/**
 * This class defines methods to solve the union-find
 * problem efficiently using weighted and compression
 * tree techniques.
 * @author Garrett
 *
 */

public class UnionFind {
	
	private int[] id;
	private int[] sz;
	private int[] lrg;
	
	//Constructor takes in the size
	UnionFind(int n){
		id= new int[n];
		sz= new int[n];
		lrg= new int[n];
		//initialize
		for(int i=0; i < sz.length; i++){
			lrg[i]=i;
			id[i]=i;
			sz[i]=1;
		}
	}
	
	private int root(int i){
		while(i != id[i]){ //root is when i matches its index
			id[i]= id[id[i]]; //sets root to 'grandparent'
			i=id[i]; //follows the pointers
		}
		return i;
	}
	
	public boolean connected(int p, int q){
		return root(p)==root(q);
	}
	
	public void union(int p, int q){
		int i= root(p);
		int j= root(q);
		if(i==j){return;}
		if(sz[i] < sz[j])	{ 
			id[i]= j; 
			sz[j] +=sz[i];
				if(q > lrg[j]){
					lrg[j]=q;
				}
				if(p > lrg[j]){
					lrg[j]=p;
				}
			}
		else 				{ 
			id[j]= i;
			sz[i] +=sz[j];
				if(p > lrg[i]){
					lrg[i]=p;
				}
				if(q > lrg[i]){
					lrg[i]=q;
				}
			}
	}
	
	//returns the largest element in the connected component
	//containing i (must take logarithmic time or better)
	//use binary search since nodes are ordered
	//note, right now this works worst case on order n
	public int find(int i){
		//components are connected if they share the same root
		return lrg[root(i)];	
	}
	
	public int[] getId(){
		return id;
	}
	
	public int[] getSz(){
		return sz;
	}
	
	public int[] getLargest(){
		return lrg;
	}

}
