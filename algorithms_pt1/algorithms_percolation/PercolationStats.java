
package percolation;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats{
    
        private double [ ]results;
		
		
		public PercolationStats(int n, int trials){
			if( n<= 0 || trials <=0){
				throw new IllegalArgumentException();
			}
			results= new double[trials];
			
			for(int i=0; i < trials; i++){
				results[i]= simulate(n);
			}
			
			//StdOut.println("mean\t\t\t\t " + mean());
			//StdOut.println("stddev\t\t\t\t " + stddev());
			//StdOut.println("95% confidence interval\t\t" + 
			//		"["+confidenceLo()+" , " + confidenceHi()
			//		+ "]");
		}
		
		public double mean(){
			return StdStats.mean(results);
		}
		
		public double stddev(){
			return StdStats.stddev(results);
		}
		
		public double confidenceLo(){
			return mean() - 1.96*stddev()/(Math.sqrt(results.length));
			
		}
		
		public double confidenceHi(){
			return mean() + 1.96*stddev()/(Math.sqrt(results.length));
		}
		/*
		 * Private method to compute square root
		 * since can't use library method.
		 */
		
		private static double sqrt(int number) {
			double t;
		 
			double squareRoot = number / 2;
		 
			do {
				t = squareRoot;
				squareRoot = (t + (number / t)) / 2;
			} while ((t - squareRoot) != 0);
		 
			return squareRoot;
		}
		
		private double simulate(int n){
			Percolation simulation= new Percolation(n);
			int currRow;
			int currCol;
			while(!simulation.percolates()){
				currRow= StdRandom.uniform(n) + 1; //rand gets [0, arg) 
				currCol= StdRandom.uniform(n) + 1; 
				if(!simulation.isOpen(currRow, currCol)){
					simulation.open(currRow, currCol);
				}
			}
			return (double)simulation.numberOfOpenSites()/(n*n);
			
		}
		
		public static void main(String[] args) {
			// TODO Auto-generated method stub
			int n= StdIn.readInt();
			int trials= StdIn.readInt();
			PercolationStats sims= new PercolationStats(n,trials);
			
		}
}