

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
/*
 * This class estimates the threshold value of a percolation
 * problem by computing monte carlo simulations.
 */



public class Percolation {
	
	private WeightedQuickUnionUF grid;
	private boolean[] openSites;
	private int numOpen;
	private int n;
	
	public Percolation(int n){
		if( n<= 0){
			throw new IllegalArgumentException();
		}
		this.n= n;
		numOpen=0;
		
		//creates grid with two sentinel nodes
		grid= new WeightedQuickUnionUF(n*n+2);
		//array to keep track of open sites
		//(notes, this might go over the n^2 time
		//threshold
		openSites= new boolean[n*n+2];
		//connect top row to top sentinel node
		//connect bottom row to bottom sentinel node
		for(int i=1; i <=n; i++){
			grid.union(0, i);
			//get rid of bottom sentinel to pevent backwash
			//grid.union(n*n+1, (n*n-n) +i);
		}
		
	}
	/*
	 * This method opens a gate and unions it
	 * with other adjacent open gates.
	 */
	public void open(int row, int col){
		int index= transformInput(row, col);
		if(openSites[index]){ //returns if already open
			return;
		}
		//set site as opened
		openSites[index]=true;
		numOpen++;
		
		//Note if statement will truncate if out of index of array
		if( index+1 <= row*n && openSites[index+1]){ //right boundary
			grid.union(index, index+1);
		}
		if( index-1 >= (row-1)*n +1 && openSites[index-1]){ //left boundary
			grid.union(index, index-1);
		}
		if( index+n <= n*n && openSites[index+n]){ //bottom boundary
			grid.union(index, index+n);
		}
		if( index-n >= 1 && openSites[index-n]){ //top boundary
			grid.union(index, index-n);
		}
	}
	/*
	 * this method returns whether a site is open
	 */
	public boolean isOpen(int row, int col){
		int index= transformInput(row,col);
		return openSites[index];
	}
	/*
	 * this method returns whether a site is full
	 * (i.e. if it's connected to the top)
	 */
	public boolean isFull(int row, int col){
		int index= transformInput(row,col);
		return (grid.connected(0, index) && openSites[index]);
	}
	/*
	 * getter for open sites
	 */
	public int numberOfOpenSites(){
		return numOpen;
	}
	/*
	 * This method returns whether the system
	 * percolates (i.e. top is connected to 
	 * bottom)
	 */
	public boolean percolates(){
	    //special case for n=1:
	    if(n==1 && numOpen==0){
	        return false;
	    }
	    //this seems very inefficient.. maybe fix
	    //way to prevent backwash
	    for(int i= (n*n -n)+1; i <= n*n; i++){
	        if(grid.connected(0, i)){
	            return true;
	        }
	    }
		return false;
	}
	
	/*
	 * This method transforms input of the form
	 * (row, col) --> (i) where i is an index in
	 * the WeightedQuickUnionUF structure
	 */
	private int transformInput(int row, int col){
		if( row <= 0 || row > n
				|| col <=0 || col > n){
			throw new IndexOutOfBoundsException();
		}
		
		return (row-1)*n + col;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		/**
		Percolation test= new Percolation(10);
		test.testTransformInput();

		
		for(int i=1; i <11; i++){
			for(int j=1; j < 11; j++){
				System.out.print(test.openSites[test.transformInput(i,j)] + "\t");
			}
			System.out.println("");
		}
		**/
	}
	
	private void testTransformInput(){
		for(int i=1; i <11; i++){
			for(int j=1; j < 11; j++){
				
				StdOut.print(transformInput(i,j) + "\t");
			}
			StdOut.println("");
		}	
		
	}	
	
}
