import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Stack;

public class BoggleDFS{

    private static int numRows;
    private static int numCols;
    private static int total;

    private static BoggleBoard sBoard;
    private static BranchingTST<Integer> sDictionary;
    private static HashSet<String> foundWords;

    private static boolean[] onPath;
    private static Stack<Integer> path;

    private static StringBuilder currentSequence;
    private static final int SIZE = 10;

    // optimizations
    private static LinkedList<Integer>[] neighbours; // cache local neighbours
    private static Stack<Integer> changes; // track which index change each iteration

    public static Iterable<String> search(BoggleBoard board, BranchingTST<Integer> dictionary){
        numCols = board.cols();
        numRows = board.rows();
        total = numCols*numRows;

        sBoard = board;
        sDictionary = dictionary;
        foundWords = new HashSet<String>();

        neighbours = new LinkedList[total];
        changes = new Stack<Integer>();


        // resize if needed
        if ( (onPath != null && onPath.length < total) || onPath == null ){
            onPath = new boolean[total];
        }

        for (int c = 0; c < numCols; c++) {
            for (int r = 0; r < numRows; r++) {
                dfsAllPaths(getNodeId(r, c));
            }
        }
        return foundWords;
    }

    public Iterable<String> getFoundSequences(){
        return foundWords;
    }

    /* Compute dfs between one node and all other nodes */
    private static void dfsAllPaths(int startNode) {
        for ( int c = 0; c < numCols; c++) {
            for (int r = 0; r < numRows; r++) {
                path = new Stack<Integer>();
                currentSequence = new StringBuilder();
                dfs(startNode, getNodeId(r, c));
            }
        }
    }

    /* DFS from start to target */
    private static void dfs(int v, int t){
        path.push(v);
        onPath[v] = true;

        // prune branch if no words contain the sequence as a prefix
        currentSequence.append(getLetter(v));
        if (getLetter(v) == 'Q') currentSequence.append('U');
        if (currentSequence.length() > 2 && !sDictionary.containsPrefix(currentSequence.toString())){
            path.pop();
            if (currentSequence.charAt(currentSequence.length()-2) == 'Q'){
                currentSequence.deleteCharAt(currentSequence.length()-1);
            }
            currentSequence.deleteCharAt(currentSequence.length()-1);
            onPath[v] = false;
            return;
        }
        // only search for words longer than length 2 since all others score 0
        if (currentSequence.length() > 2 && sDictionary.contains(currentSequence.toString()) && !foundWords.contains(currentSequence.toString())){
            foundWords.add(currentSequence.toString());
        }


        LinkedList<Integer> currentNeighbours = neighbours[v] == null ? getNeighbours(v) : neighbours[v];

        for(int w : currentNeighbours){
          if (!onPath[w]){
                  dfs(w, t);
            }
        }

        path.pop();
        if (currentSequence.length() > 1 && currentSequence.charAt(currentSequence.length()-2) == 'Q'){
            currentSequence.deleteCharAt(currentSequence.length()-1);
        }
        currentSequence.deleteCharAt(currentSequence.length()-1);
        onPath[v] = false;
    }

    /* Gets all adjacent nodes to node */
    private static LinkedList<Integer> getNeighbours( int node){
        LinkedList<Integer> neighbours = new LinkedList<Integer>();
        if (!onTopEdge(node))              neighbours.add(node-numCols); //above
        if (!onBottomEdge(node))           neighbours.add(node+numCols); //below
        if (!onLeftEdge(node))             neighbours.add(node-1); //left
        if (!onRightEdge(node))            neighbours.add(node+1); //right
        if (hasTopLeftNeighbour(node))     neighbours.add(node-numCols-1);
        if (hasTopRightNeighbour(node))    neighbours.add(node-numCols+1);
        if (hasBottomLeftNeighbour(node))  neighbours.add(node+numCols-1);
        if (hasBottomRightNeighbour(node)) neighbours.add(node+numCols+1);
        return neighbours;
    }

    private static boolean onTopEdge(int nodeId) {
        return (nodeId < numCols);
    }

    private static boolean  onBottomEdge(int nodeId) {
        return (nodeId >= ((numCols*numRows) - numCols));
    }

    private static boolean onRightEdge(int nodeId) {
        return ((nodeId+numCols+1)%numCols == 0);
    }

    private static boolean onLeftEdge(int nodeId) {
        return nodeId%numCols == 0;
    }

    private static boolean hasTopLeftNeighbour(int nodeId) {
        return !(onTopEdge(nodeId) || onLeftEdge(nodeId));

    }

    private static boolean hasTopRightNeighbour(int nodeId) {
        return !(onTopEdge(nodeId) || onRightEdge(nodeId));
    }

    private static boolean hasBottomLeftNeighbour(int nodeId) {
        return  !(onBottomEdge(nodeId) || onLeftEdge(nodeId));
    }

    private  static boolean hasBottomRightNeighbour(int nodeId) {
        return !(onBottomEdge(nodeId) || onRightEdge(nodeId));
    }

    private static int getNodeId(int row, int col) {
        return (row * numCols + col);
    }

    // returns coordinates (row, col)
    private static int[] getNode2DCoord(int nodeId){
        int[] coords = new int[2];
        coords[1] = nodeId%numCols;
        coords[0] = nodeId/numCols;
        return coords;
    }

    private static char getLetter(int nodeId){
        int[] coords = getNode2DCoord(nodeId);
        return sBoard.getLetter(coords[0], coords[1]);
    }

    private boolean isOnBoard(int col, int row, BoggleBoard board) {
        return ((col >= 0 && col < board.cols()) && (row >= 0 && row < board.rows()));
    }

    private boolean isOnBoard(int nodeID){
        return ( nodeID >= 0 && nodeID < (numCols*numRows));
    }


    public static void main(String[] args) {
        BoggleBoard b = new BoggleBoard("boggle/board4x4.txt");

    }

}


