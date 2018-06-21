
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public final class Solver {
    
    private MinPQ<SearchNode> pq;
    private MinPQ<SearchNode> twinpq;
    private int minMoves;
    private Iterable<Board> solution;
    private boolean isPossible;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial){
        if(initial == null){
            throw new java.lang.NullPointerException("Null parameter in  Solver constructor.");
        }
       
        
        SearchNode first = new SearchNode(initial, 0);
        first.prev = null;
        
        SearchNode firstTwin = new SearchNode(initial.twin(), 0);
        firstTwin.prev = null;
                     
        pq = new MinPQ<SearchNode>(new puzzleComparator());
        pq.insert(first);
        
        twinpq = new MinPQ<SearchNode>(new puzzleComparator());
        twinpq.insert(firstTwin);
               
       
        SearchNode goalNode = ASearch();      
        
        this.solution = createSolutionPath(goalNode,initial);  
        
        if(!isPossible){
            minMoves = -1;
        }
        
    }
    
    private Iterable<Board> createSolutionPath(SearchNode end, Board initial){
        Stack<Board> solution = new Stack<>();
        
        SearchNode curr = end;
        
        
        while(curr.prev != null){
            solution.push(curr.item);
            curr = curr.prev;
        }
        solution.push(initial);
        minMoves = solution.size()-1;
        return solution;
    }
    
    /*
    private SearchNode aStarSearch(){
        LinkedList<Board> visited = new LinkedList<>();
        
        //initialize:
        SearchNode curr;
        //while PQ is not empty:
        while(!pq.isEmpty()){
            //dequeue curr from front of queue
            curr= pq.delMin();
            StdOut.println("Checking : " + curr.item);
            //if curr not visited:
            if(!visited.contains(curr.item)){
                //add curr to visted set
                
                visited.add(curr.item);
                if(curr.item.isGoal()){
                    isPossible = true;
                    return curr;
                }
                //for each of curr's neighbors, n, not visited:
                for(Board b : (curr.item).neighbors()){
                    if(!visited.contains(b)){
                        //if path through curr to n is shorter:
                        SearchNode newNode = new SearchNode(b, curr.movesToReach +1);
                        newNode.prev = curr;
                        //only add if less or equal priority to curr
                       // if(curr.priority <= newNode.priority){
                           pq.insert(newNode);                          
                       // }
                    }
                }                               
            }
        }
        return null;
    }
    */
     
    
    //Returns the goal node:
    private SearchNode ASearch(){
        while(!pq.isEmpty() || !twinpq.isEmpty()){  
            if(!pq.isEmpty()){
                SearchNode currNode = pq.delMin();
                if(currNode.item.isGoal()){
                    isPossible = true;
                    return currNode;
                }
                
                for(Board b: (currNode.item).neighbors()){
                    if( !b.equals(currNode.item)){
                        if((currNode.prev) != null && !b.equals((currNode.prev).item)){
                            SearchNode newNode = new SearchNode(b , currNode.movesToReach + 1);
                            newNode.prev = currNode;
                            pq.insert(newNode);
                        }
                        else if(currNode.prev == null){
                            SearchNode newNode = new SearchNode(b, currNode.movesToReach + 1);
                            newNode.prev = currNode;
                                pq.insert(newNode);
                                
                        }
                    }
                }
            }
            
            if(!twinpq.isEmpty()){
                SearchNode currNodeTwin = twinpq.delMin();  
                if(currNodeTwin.item.isGoal()){
                    isPossible = false;
                    return currNodeTwin;
                }
                for(Board b: (currNodeTwin.item).neighbors()){
                    if( !b.equals(currNodeTwin.item)){
                        if((currNodeTwin.prev) != null && !b.equals((currNodeTwin.prev).item)){
                            SearchNode newNodeTwin = new SearchNode( b, currNodeTwin.movesToReach + 1);
                            newNodeTwin.prev = currNodeTwin;
                                twinpq.insert(newNodeTwin);
                                
                        }
                        else if(currNodeTwin.prev == null){
                            SearchNode newNodeTwin = new SearchNode(b, currNodeTwin.movesToReach + 1);
                            newNodeTwin.prev = currNodeTwin;
                                twinpq.insert(newNodeTwin);
                                
                        }
                    }
                }
            }
        }
        return null;
    }
    
    
    // is the initial board solvable?
    public boolean isSolvable()  {
        return isPossible;
    }
    
    // min number of moves to solve initial board; -1 if unsolvable
    public int moves(){         
        return minMoves;
    }
    
    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution(){
        if(!isPossible) return null;
        return solution;
    }
    
    // a 'wrapper class' to board
    private class SearchNode{
        private Board item;
        private SearchNode prev;
        private int priority;
        int movesToReach;    

        public SearchNode(Board b, int moves){
            item = b;
            movesToReach = moves;
            priority = item.manhattan() + movesToReach;
        }
    }

    private class puzzleComparator implements Comparator<SearchNode>{

        @Override
        public int compare(SearchNode sn1, SearchNode sn2) {

                    if( sn1.priority < sn2.priority ) return -1;
                    if( sn1.priority > sn2.priority ) return 1;
                    return 0;                 
        }                
    }
    
    // solve a slider puzzle (given below)
    public static void main(String[] args) {
     // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
    
}