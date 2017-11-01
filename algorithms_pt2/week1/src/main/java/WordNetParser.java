import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class WordNetParser {

    public static void parseSynsets(String fileName,
                                    HashMap<Integer, String> synsetsIdsToNouns,
                                    HashMap<String, LinkedList<Integer>> synsetsNounsToIds){
        In in = null;

        try {
            in = new In(fileName);

            String line;
            String[] synset;

            while (in.hasNextLine()) {
                line = in.readLine();
                synset = line.split(",");
                int id = Integer.parseInt(synset[0]);
                String[] nouns = synset[1].split(" ");
                for(String noun : nouns) {
                    synsetsIdsToNouns.put(id, noun);
                    if (synsetsNounsToIds.get(noun) == null) synsetsNounsToIds.put(noun, new LinkedList<Integer>());
                    synsetsNounsToIds.get(noun).add(id);
                }
            }
        }catch (IllegalArgumentException e){
            System.out.println("Illegal argument during parsing: "+ e.getMessage());
        }finally{
            if(in != null) in.close();
        }
    }

    public static void parseHypernyms(String fileName, Digraph hyernyms){
        In in = null;

        try {
            in = new In(fileName);
            int fromVertex;
            int toVertex;

            String line;
            String[] hypernym;

            while (in.hasNextLine()) {
                line = in.readLine();
                hypernym = line.split(",");
                fromVertex = Integer.parseInt(hypernym[0]);
                for(int i = 1; i < hypernym.length; i++){
                    toVertex = Integer.parseInt(hypernym[i]);
                    hyernyms.addEdge(fromVertex, toVertex);
                }
            }
        }catch (IllegalArgumentException e){
            System.out.println("Illegal argument during parsing: "+ e.getMessage());
        }finally{
            if(in != null) in.close();
        }
    }


}
