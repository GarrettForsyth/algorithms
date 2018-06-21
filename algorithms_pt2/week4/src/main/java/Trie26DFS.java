import java.util.HashSet;
import java.util.LinkedList;
import java.util.Stack;

public class Trie26DFS {

    private final BoggleBoard mBoard;
    private final TrieST26<Integer> mDictionary;

    private final int numCols;
    private final int numRows;
    private final int total;

    private LinkedList<Integer>[] adj;

    private Stack<Integer> path;
    private boolean onPath[];
    private StringBuilder currentSequence;

    private HashSet<String> foundWords;


    public Trie26DFS(BoggleBoard board, TrieST26<Integer> dictionary){
        mBoard = board;
        numRows = board.rows();
        numCols = board.cols();
        total = numCols * numRows;

        mDictionary = dictionary;
        createAdjj();

        onPath = new boolean[total];

        foundWords = new HashSet<String>();

        searchAllPaths();
    }

    public Iterable<String> getWords(){
        return foundWords;
    }

    private void searchAllPaths(){
        for (int i = 0; i < total; i++ ){
            path = new Stack<Integer>();
            currentSequence = new StringBuilder();
            dfs(i);
        }
    }

    private void dfs(int s){
        path.push(s);
        onPath[s] = true;

        currentSequence.append(getLetter(s));


        if (currentSequence.charAt(currentSequence.length()-1) == 'Q') currentSequence.append('U');
        String currentSequenceAsString = currentSequence.toString(); // avoid copying the array multiple times in .toString()
        // stop exploring this branch if its current sequence does not correspond to any words in the dictionary
        if (currentSequence.length() > 2 && !mDictionary.containsPrefix(currentSequenceAsString)) {
            onPath[s] = false;
            if (currentSequence.length() > 1 && currentSequence.charAt(currentSequence.length()-2) == 'Q'){
                currentSequence.deleteCharAt(currentSequence.length()-1);
            }
            currentSequence.deleteCharAt(currentSequence.length()-1);
            return;
        }

        // check if a new word is found
        if (currentSequence.length() > 2 && mDictionary.contains(currentSequenceAsString)){
            foundWords.add(currentSequenceAsString);
        }

        // explore neighbours
        for (int w : adj[s]){
            if (!onPath[w]){
                dfs(w);
            }
        }

        // unwind
        path.pop();
        onPath[s] = false;
        if (currentSequence.length() > 1 && currentSequence.charAt(currentSequence.length()-2) == 'Q'){
            currentSequence.deleteCharAt(currentSequence.length()-1);
        }
        currentSequence.deleteCharAt(currentSequence.length()-1);
    }

    // cache neighbour entries as
    private void createAdjj(){
        adj = new LinkedList[total];
        for(int i = 0; i < total; i++){
            adj[i] =  getNeighbours(i);
        }
    }

    /* Gets all adjacent nodes to node */
    private LinkedList<Integer> getNeighbours(int node){
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

    private boolean onTopEdge(int nodeId) {
        return (nodeId < numCols);
    }

    private boolean  onBottomEdge(int nodeId) {
        return (nodeId >= ((numCols*numRows) - numCols));
    }

    private boolean onRightEdge(int nodeId) {
        return ((nodeId+numCols+1)%numCols == 0);
    }

    private boolean onLeftEdge(int nodeId) {
        return nodeId%numCols == 0;
    }

    private boolean hasTopLeftNeighbour(int nodeId) {
        return !(onTopEdge(nodeId) || onLeftEdge(nodeId));

    }

    private boolean hasTopRightNeighbour(int nodeId) {
        return !(onTopEdge(nodeId) || onRightEdge(nodeId));
    }

    private boolean hasBottomLeftNeighbour(int nodeId) {
        return  !(onBottomEdge(nodeId) || onLeftEdge(nodeId));
    }

    private  boolean hasBottomRightNeighbour(int nodeId) {
        return !(onBottomEdge(nodeId) || onRightEdge(nodeId));
    }

    private int getNodeId(int row, int col) {
        return (row * numCols + col);
    }

    // returns coordinates (row, col)
    private int[] getNode2DCoord(int nodeId){
        int[] coords = new int[2];
        coords[1] = nodeId%numCols;
        coords[0] = nodeId/numCols;
        return coords;
    }

    private char getLetter(int nodeId){
        int[] coords = getNode2DCoord(nodeId);
        return mBoard.getLetter(coords[0], coords[1]);
    }

}
