import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class BaseballFlowNetwork {

    private FlowNetwork flowNetwork;
    private int numMatches;
    private int numOpponents;
    private HashMap<String, Integer> teamName;
    private int eliminationCandidateId;

    public BaseballFlowNetwork(String team,
                               HashMap<String,Integer> teamName,
                               int[] winsFor,
                               int[] lossesFor,
                               int[] remainingGamesFor,
                               int[][] remainingGamesAgainst){
        this.teamName = teamName;
       eliminationCandidateId = teamName.get(team);
       /*
          One vertex for each match up
          One vertex for each team
          One vertex for source
          One vertex For target
          Do not include vertices involving the elimination candidate
        */
       numOpponents = teamName.size()-1;
       numMatches = choose(numOpponents, 2);
       int  V = numMatches + numOpponents + 2;

       flowNetwork = new FlowNetwork(V);

       /*
          Call the first vertex the source
          The next numMatches vertices are game vertices
          The Next numOpponents vertices are team vertices
          The final vertex is the target/sink vertex
        */

       /* First set up outgoing edges from source to match vertices */
       int vertexCounter = 1;

       for (int i = 0; i < remainingGamesAgainst.length; i++){
           for (int j = i; j < remainingGamesAgainst[0].length; j++){
               if ( i != j &&
                    i != eliminationCandidateId &&
                    j != eliminationCandidateId){
                   FlowEdge fe = new FlowEdge(0, vertexCounter, remainingGamesAgainst[i][j]);
                   flowNetwork.addEdge(fe);
                   vertexCounter++;
               }
           }
       }

       /*
            Set up edges from match vertices to team vertices
            Note here, vertexCounter = 1 + numMatches
        */
       int matchCounter = 1;
       int iOffset = 0;
       for (int i = 0; i <= numOpponents; i++){
           if ( i == eliminationCandidateId ) iOffset = 1;
           int jOffset = 0; // offset to account for skipping over elimination id
           for (int j = i; j <= numOpponents; j++){
               if(j == eliminationCandidateId) jOffset = 1;
               if (    i != j &&
                       i != eliminationCandidateId &&
                       j != eliminationCandidateId){

                       int capacity = Integer.MAX_VALUE;
                       FlowEdge fe1 = new FlowEdge(matchCounter, vertexCounter + i - iOffset, capacity);
                       FlowEdge fe2 = new FlowEdge(matchCounter, vertexCounter + j - jOffset -iOffset, capacity);
                       flowNetwork.addEdge(fe1);
                       flowNetwork.addEdge(fe2);
                       matchCounter++;
               }
           }
       }

       /*
            Lastly set up edges from team vertices to target vertex.
            vertexCounter is still at 1 + numMatches
        */

       int ecWins = winsFor[eliminationCandidateId];
       int ecRemaining = remainingGamesFor[eliminationCandidateId];
       int offset = 0; // offset to account for skipping over elimination id

       for (int i = 0; i <= numOpponents; i++){
           if ( i != eliminationCandidateId ){
               int capacity = ecWins + ecRemaining - winsFor[i];
               FlowEdge fe = new FlowEdge(vertexCounter+i-offset, V-1, Math.max(0, capacity));
               flowNetwork.addEdge(fe);
           }
           else offset = 1;
       }
    }

    public FlowNetwork flowNetwork() {
        return flowNetwork;
    }

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team, FordFulkerson ff){

        LinkedList<String> certOfE =  new LinkedList<String>();
        int offset = 0;
        for ( int i = (1 + numMatches);
              i <= (1 + numMatches + numOpponents);
              i++){

            if (i-numMatches-1 == eliminationCandidateId) offset = 1;

            if (ff.inCut(i)) {
                certOfE.add(getTeamName((i-numMatches -1 + offset)));
            }

        }

        if (certOfE.isEmpty()) return null;
        return certOfE;
    }

    private String getTeamName(int id){
        for (Map.Entry<String, Integer> e : teamName.entrySet()){
            if (e.getValue() == id){
                return e.getKey();
            }
        }
        return null;

    }

    /*
        Generates Pascals Triangle.
        n choose k is the nth row and kth column
     */
    static private int choose(int N, int K){
        for (int n = 0; n <= N; n++){
            int nCk = 1;
            for (int k = 0; k <= n; k++){
                nCk = nCk * (n-k) / (k+1);
                if (n == N && k == K-1) return nCk;
            }
        }
        return -1;
    }

    public static void main (String[] args){
        System.out.println(choose(10,2));
    }
}
