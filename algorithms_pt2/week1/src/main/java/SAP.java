import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This is a helper class for finding the shortest path
 * and common ancestors between two nouns in a Wordnet.
 */
public class SAP {

    /* The graph corresponding the the hypernyms in the WordNet */
    private static Digraph mGraph;

    /* Instance of wordnet BFS to reuse and avoid re-initialization costs*/
    private WordNetBFS mBfs;

    /**
     * Creates an immutable instance of SAP from a digraph.
     * @param hypernyms digraph representing hypernyms in a WordNet.
     */
    public SAP(Digraph hypernyms){
        mGraph = new Digraph(hypernyms);
    }

    /**
     * Length of the shortest common path between two
     * id's in the hypernyms digraph.
     * @param v first noun's id.
     * @param w second noun's id.
     * @return shortest connecting path of the nouns, or
     *      -1 if no such path exists.
     */
    public int length(int v, int w){
        mBfs = mBfs == null ? new WordNetBFS(mGraph) : mBfs;
        mBfs.createBfs(new ArrayList<Integer>(Arrays.asList(v, w)));
        int ca = mBfs.getCommonAncestor();
        return mBfs.getSap();
    }

    /**
     * Gets the common ancestor between two ids in the
     * hypernym digraph.
     * @param v first noun id.
     * @param w second noun id.
     * @return the id of the common ancestor or -1 if none exists.
     */
    public int ancestor(int v, int w){
        mBfs = mBfs == null ? new WordNetBFS(mGraph) : mBfs;
        mBfs.createBfs(new ArrayList<Integer>(Arrays.asList(v, w)));
        int ca = mBfs.getCommonAncestor();
        return ca;
    }

    /**
     * Finds the shortest connecting path between two sets of ids.
     * @param v first set of ids.
     * @param w secocond set of ids.
     * @return length of shortest connecting path or -1 if none exists.
     */
    public int length(Iterable<Integer> v, Iterable<Integer> w){
        mBfs = mBfs == null ? new WordNetBFS(mGraph) : mBfs;
        mBfs.createBfs(v, w);
        return mBfs.getSap();
    }

    /**
     * Finds the common ancestor of the shortest connectin path between
     * two sets of ids.
     * @param v first set of ids.
     * @param w second set of ids
     * @return id of common ancestor or -1 if none exists.
     */
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w){
        mBfs = mBfs == null ? new WordNetBFS(mGraph) : mBfs;
        mBfs.createBfs(v, w);
        return mBfs.getCommonAncestor();
    }

    public static void main(String[] args){
        In in = new In("src/main/wordnet/digraph5.txt");
        Digraph g = new Digraph(in);
        SAP test = new SAP(g);

        System.out.println(test.length(15,5));
        System.out.println(test.ancestor(15,5));

        in = new In("src/main/wordnet/digraph3.txt");
        g = new Digraph(in);
        test = new SAP(g);

        System.out.println(test.length(10,7));
        System.out.println(test.ancestor(10,7));

        in = new In("src/main/wordnet/digraph6.txt");
        g = new Digraph(in);
        test = new SAP(g);
        System.out.println(test.length(5,1));
        System.out.println(test.ancestor(5,1));

        in = new In("src/main/wordnet/digraph9.txt");
        g = new Digraph(in);
        test = new SAP(g);
        System.out.println(test.length(0,4));
        System.out.println(test.ancestor(0,4));

        System.out.println("");

        in = new In("src/main/wordnet/digraph2.txt");
        g = new Digraph(in);
        test = new SAP(g);
        System.out.println(test.length(3,1));
        System.out.println(test.ancestor(3,1));

        in = new In("src/main/wordnet/digraph3.txt");
        g = new Digraph(in);
        test = new SAP(g);
        System.out.println(test.length(2,6));
        System.out.println(test.ancestor(2,6));

        in = new In("src/main/wordnet/digraph4.txt");
        g = new Digraph(in);
        test = new SAP(g);
        System.out.println(test.length(0,1));
        System.out.println(test.ancestor(0,1));

        in = new In("src/main/wordnet/digraph6.txt");
        g = new Digraph(in);
        test = new SAP(g);
        System.out.println(test.length(5,0));
        System.out.println(test.ancestor(5,0));

        in = new In("src/main/wordnet/digraph5.txt");
        g = new Digraph(in);
        test = new SAP(g);
        System.out.println(test.length(14,20));
        System.out.println(test.ancestor(14,20));

    }

}
