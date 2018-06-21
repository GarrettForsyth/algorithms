import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class BaseballElimination {

    private HashMap<String, Integer> teamName;
    private int[]    winsFor;
    private int[]    lossesFor;
    private int[]    remainingGamesFor;
    private int[][]  remainingGamesAgainst;
    private BaseballFlowNetwork baseballFlowNetwork;
    private FordFulkerson ff;

    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename){
        parse(filename);

    }

    // number of teams
    public int numberOfTeams(){
        return teamName.size();
    }

    // all teams
    public Iterable<String> teams(){
        return teamName.keySet();
    }

    // number of wins for given team
    public int wins(String team){
        if (!teamName.containsKey(team)) throw new IllegalArgumentException();
        return winsFor[teamName.get(team)];
    }

    // number of losses for given team
    public int losses(String team){
        if (!teamName.containsKey(team)) throw new IllegalArgumentException();
        return lossesFor[teamName.get(team)];
    }

    // number of remaining games for given team
    public int remaining(String team){
        if (!teamName.containsKey(team)) throw new IllegalArgumentException();
        return remainingGamesFor[teamName.get(team)];
    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2){
        if (!teamName.containsKey(team1)) throw new IllegalArgumentException();
        if (!teamName.containsKey(team2)) throw new IllegalArgumentException();

        return remainingGamesAgainst[teamName.get(team1)][teamName.get(team2)];
    }

    // is given team eliminated?
    public boolean isEliminated(String team){
        if (!teamName.containsKey(team)) throw new IllegalArgumentException();
        if (teamName.size() == 1) return false;
        String se = checkSimpleElimination(team);
        if (se != null) return true;

        baseballFlowNetwork = new BaseballFlowNetwork(team,
               teamName,
               winsFor,
               lossesFor,
               remainingGamesFor,
               remainingGamesAgainst);

       ff = new FordFulkerson(baseballFlowNetwork.flowNetwork(),
               0,
               baseballFlowNetwork.flowNetwork().V()-1);

        int sourceFlow = 0;
        int sourceCapacity = 0;
        for (FlowEdge e : baseballFlowNetwork.flowNetwork().adj(0)){
            sourceFlow += e.flow();
            sourceCapacity += e.capacity();
        }

        return sourceCapacity != sourceFlow;
    }

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team){
        if (!teamName.containsKey(team)) throw new IllegalArgumentException();
        if (teamName.size() == 1) return null;

        String se = checkSimpleElimination(team);
        if (se != null){
            ArrayList<String> ret = new ArrayList<String>();
            ret.add(se);
            return ret;
        }
        baseballFlowNetwork = new BaseballFlowNetwork(team,
                teamName,
                winsFor,
                lossesFor,
                remainingGamesFor,
                remainingGamesAgainst);

        ff = new FordFulkerson(baseballFlowNetwork.flowNetwork(),
                0,
                baseballFlowNetwork.flowNetwork().V()-1);
        return baseballFlowNetwork.certificateOfElimination(team, ff);
    }

    private String checkSimpleElimination(String team){
        int teamID = teamName.get(team);
        for (int i = 0; i < winsFor.length; i++){
            if (winsFor[teamID] + remainingGamesFor[teamID] < winsFor[i]){
                for (Map.Entry<String, Integer> e : teamName.entrySet()){
                    if (e.getValue() == i) return e.getKey();
                }
                return null;
            }
        }
        return null;
    }

    private void parse(String fileName){
        In in = null;

        try {
            in = new In(fileName);

            int N = Integer.parseInt(in.readLine());
            teamName =  new HashMap<String, Integer>();
            winsFor = new int[N];
            lossesFor = new int[N];
            remainingGamesFor = new int[N];
            remainingGamesAgainst = new int[N][N];

            String line;
            for (int i = 0; i < N; i++){
                teamName.put(in.readString(), i);
                winsFor[i] = in.readInt();
                lossesFor[i] = in.readInt();
                remainingGamesFor[i] = in.readInt();

                for ( int j = 0; j < N ; j++ ) {
                    remainingGamesAgainst[i][j] = in.readInt();
                }

            }
        }catch (IllegalArgumentException e){
            System.out.println("Illegal argument during parsing: "+ e.getMessage());
        }finally{
            if(in != null) in.close();
        }
    }

    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination("src/baseball/teams4.txt");

        /*
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            }
            else {
                StdOut.println(team + " is not eliminated");
            }
        }
        */
        System.out.println(division.certificateOfElimination("Philadelphia"));
        System.out.println(division.isEliminated("Philadelphia"));
        System.out.println(division.certificateOfElimination("Philadelphia"));
        System.out.println(division.isEliminated("Philadelphia"));
    }
}
