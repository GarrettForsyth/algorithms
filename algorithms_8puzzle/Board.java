
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public final class Board {
    
    private final int dim;
    private final int[] blocks;
    private int zero;
    
    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks){
        this.dim = blocks.length;
        //map 2d array blocks into 1d array
        this.blocks = new int[dim*dim+1];
        for(int i = 0; i < dim; i++){
            for(int j = 0; j < dim; j ++){
                this.blocks[transformInput(i+1,j+1,dim)] = blocks[i][j];
                if(blocks[i][j] == 0 ){
                    zero = transformInput(i+1,j+1,dim);
                }
            }
        }
    }
    
    //maps a 2d array to a 1d array
    private int transformInput(int row, int col, int n){
        if( row <= 0 || row > n
                || col <=0 || col > n){
            throw new IndexOutOfBoundsException();
        }  
        return (row-1)*n + col;
    }
       
    // board dimension n
    public int dimension(){
        return dim;
    }
    
    // number of blocks out of place
    public int hamming(){
        int count=0;
        for(int i = 1; i < blocks.length; i++){
            if( i != blocks[i] && blocks[i] != 0) count++;
        }
        return count;
    }
    
    // sum of Manhattan distances between blocks and goal
    public int manhattan(){
        int totalDistance=0;
        for(int i = 1; i < blocks.length; i++){
            if( i != blocks[i] && blocks[i] != 0){
                //distance = rows off plus distance off
                  totalDistance += Math.abs((i-1)/dim - (blocks[i]-1)/dim) +
                                  Math.abs((blocks[i] - ((blocks[i]-1)/dim)*dim) - (i - ((i-1)/dim)*dim));
            }
        }
        return totalDistance;
    }
    
    // is this board the goal board?
    public boolean isGoal(){     
        for(int i = 1; i < blocks.length-1; i++){
            if( i != blocks[i]) return false;
        }
        return true;
    }
    
    // a board that is obtained by exchanging any pair of blocks
    public Board twin(){
        Board twin = new Board(new int[dim][dim]);
        //assume at least 2x2
        //if statements to avoid exchanging with blank space
        if(this.blocks[2] != 0 && this.blocks[1] != 0){
            twin.blocks[1] = this.blocks[2];
            twin.blocks[2] = this.blocks[1];
            twin.blocks[3] = this.blocks[3];
            if(twin.blocks[3] == 0){
                twin.zero = 3;
            }
        }
        else if(this.blocks[2] != 0 && this.blocks[3] != 0){
            twin.blocks[1] = this.blocks[1];
            if(twin.blocks[1] == 0){
                twin.zero = 1;
            }
            twin.blocks[2] = this.blocks[3];
            twin.blocks[3] = this.blocks[2];
        }
        else if(this.blocks[1] != 0 && this.blocks[3] != 0){
            twin.blocks[1] = this.blocks[3];
            twin.blocks[2] = this.blocks[2];
            if(twin.blocks[2] == 0){
                twin.zero = 2;
            }
            twin.blocks[3] = this.blocks[1];
        }     
        for(int i = 4 ; i < blocks.length; i++){
            twin.blocks[i] = this.blocks[i];
            if(twin.blocks[i] == 0){
                twin.zero = i;
            }
        }
        return twin;       
    }
    
    // does this board equal y?
    public boolean equals(Object y){
        if (y == this) return true;
        if( y == null) {return false;}
        if ( !(y instanceof Board)) return false;  
        Board b = (Board) y;
        
        if ( b.dim != this.dim ) return false;
        for( int i = 1; i < blocks.length; i++){
            if(this.blocks[i] !=  b.blocks[i])
                return false;        
        }
        return true;
    }
    
    // all neighboring boards
    public Iterable<Board> neighbors(){
        Stack<Board> neighbors = new Stack<>(); 

        //check for top neighbour:
        if(zero - dim > 0){
            neighbors.push(createNeighbor(zero, zero-dim));
        }
        
        //check for bottom neighbour:
        if(zero + dim <= dim*dim){
            neighbors.push(createNeighbor(zero, zero+dim));
        }
       //check for left neighbour:d
        if((zero-1)/dim == (zero-2)/dim){
            //special case for index =1
            if(zero != 1){
                neighbors.push(createNeighbor(zero, zero-1));
            }
        }
        //check for right neighbour:
        if((zero)/dim == (zero-1)/dim){
            neighbors.push(createNeighbor(zero, zero+1));
        }

        return neighbors;
    }
    
    //creates a neighbor by exchanging indices a and b
    private Board createNeighbor(int n, int m){
        Board neigh = new Board(new int[dim][dim]);
        for(int i = 1; i < blocks.length; i++){
            if( i != n && i != m){
                neigh.blocks[i] = this.blocks[i];
            }
            else if (i == n){
                neigh.blocks[i] = this.blocks[m];
            }
            else{;
                neigh.blocks[i] = this.blocks[n];
            }
            if(neigh.blocks[i] == 0){
                neigh.zero = i;
            }
        }   
        return neigh;
    }
    
    // string representation of this board (in the output format specified below)
    public String toString(){
        StringBuilder str = new StringBuilder();
        str.append(dim + "\n");
        for(int j = 0; j < dim; j++){
            for(int i = 1; i < dim+1; i++){
                str.append(String.format("%2d ", blocks[i+j*dim]));
            }
            str.append ("\n");
        }
        return str.toString();
    }
    
    // unit tests (not graded)
    public static void main(String[] args) {
        int[][] test = { { 1, 2},
                      {  0,3 }};

        

        
        Board testBoard = new Board(test);
       //Board testBoard2 = new Board(test2);
        StdOut.print(testBoard);
        //StdOut.println(testBoard.equals(testBoard2));
        StdOut.println(testBoard.isGoal());
        //StdOut.println(testBoard2.isGoal());
        //StdOut.println(testBoard.equals(testBoard2));
        //LinkedList<Board> testList= (LinkedList<Board>) testBoard.neighbors();
        for(Board b : testBoard.neighbors()){
             StdOut.println(b);
         }
        
        StdOut.println(testBoard.twin());
        
        for(Board b : (testBoard.twin()).neighbors()){
            StdOut.println(b);
        }
    }

}
