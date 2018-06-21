import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.KosarajuSharirSCC;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * WordNet is a semantic lexicon for the English language that is used extensively
 * by computational linguists and cognitive scientists; for example, it was a key
 * component in IBM's Watson. WordNet groups words into sets of synonyms called synsets
 * and describes semantic relationships between them. One such relationship is the is-a
 * relationship, which connects a hyponym (more specific synset) to a hypernym
 * (more general synset). For example, animal is a hypernym of both bird and fish;
 * bird is a hypernym of eagle, pigeon, and seagull.
 */
public class WordNet {

    /* Maps synset ids to synset nouns */
    private final HashMap<Integer, String> mSynsetsIdsToNouns;

    /* Maps synset nouns to a list of possible synset ids */
    private final HashMap<String, LinkedList<Integer>> mSynsetsNounsToIds;

    /* a diagraph reprsenting synset relations */
    private final Digraph mHypernyms;

    /* an instance of a helper class for computing shortest paths
        and common ancestors between synsets
     */
    private SAP sap;

    /**
     * Creates an instance of WordNet from local text files.
     * Files are expected to be in CSV format.
     * @param synsetsFileName synset_id, synsetGroup, gloss
     * @param hypernymsFileName synset_id, {hypernym_ids}
     * @throws IllegalArgumentException null argument is passed or if
     *      the input does not correspond to a rooted DAG;
     */
    public WordNet(String synsetsFileName, String hypernymsFileName){

        throwExceptionIfNull(synsetsFileName);
        throwExceptionIfNull(hypernymsFileName);

        HashMap<Integer,String> mutableSynsetsIdsToNouns= new HashMap<Integer,String>();
        HashMap<String,LinkedList<Integer>> mutableSynsetsNounstoIds=
                new HashMap<String,LinkedList<Integer>>();

        WordNetParser.parseSynsets(synsetsFileName,
                                   mutableSynsetsIdsToNouns,
                                   mutableSynsetsNounstoIds);

        mSynsetsIdsToNouns = new HashMap<Integer, String>(mutableSynsetsIdsToNouns);
        mSynsetsNounsToIds = new HashMap<String, LinkedList<Integer>>(mutableSynsetsNounstoIds);

        Digraph mutableHypernyms = new Digraph(mSynsetsIdsToNouns.size());
        WordNetParser.parseHypernyms(hypernymsFileName, mutableHypernyms);
        mHypernyms = new Digraph(mutableHypernyms);

        throwExceptionIfDoesNotCorrespondToRootedDAG(mHypernyms);
    }

    /**
     * Returns all WordNet nouns.
     * @return a set of all word net nouns
     */
    public Iterable<String> nouns() {return mSynsetsNounsToIds.keySet();}

    /**
     * Checks if word is in the word net.
     * @param word the word to be checked.
     * @return true if the word is contained in the wordnet.
     * @throws IllegalArgumentException if argument is null.
     */
    public boolean isNoun(String word){
        throwExceptionIfNull(word);
        return mSynsetsNounsToIds.get(word) != null;
    }

    /**
     * Returns theshortest distance between two words in the word net.
     * @param nounA first word.
     * @param nounB second word.
     * @return the shortest integer distance between the two words or
     *      -1 if there is no path
     * @throws IllegalArgumentException if arguments are null or if
     *      not contained in the word net.
     */
    public int distance(String nounA, String nounB){

        throwExceptionIfNull(nounA);
        throwExceptionIfNull(nounB);
        throwExceptionIfNotWordNetNoun(nounA);
        throwExceptionIfNotWordNetNoun(nounB);

        sap = sap == null? new SAP(mHypernyms) : sap;

        // nouns can have multiple ids; need to check each
        // and return the shortest
        int min = Integer.MAX_VALUE;
        int dist;
        for (int id1 : mSynsetsNounsToIds.get(nounA)){
            for(int id2 : mSynsetsNounsToIds.get(nounB)){
                dist = sap.length(id1, id2);
                if (dist < min) min = dist;

            }
        }

        return min;
    }

    /**
     * Returns the common synset of two words.
     * @param nounA first word.
     * @param nounB second word.
     * @return the common ancestor's id or -1 if none is found.
     * @throws IllegalArgumentException
     */
    public String sap(String nounA, String nounB){

        throwExceptionIfNull(nounA);
        throwExceptionIfNull(nounB);
        throwExceptionIfNotWordNetNoun(nounA);
        throwExceptionIfNotWordNetNoun(nounB);

        sap = sap == null? new SAP(mHypernyms) : sap;

        int caId = sap.length(mSynsetsNounsToIds.get(nounB), mSynsetsNounsToIds.get(nounA));
        if (caId != -1) return  mSynsetsIdsToNouns.get(caId);
        else            return null;
    }

    private void throwExceptionIfDoesNotCorrespondToRootedDAG(Digraph g){
        int rootId = -1;
        for (int id : mSynsetsIdsToNouns.keySet()){
            if(g.outdegree(id) == 0 && g.indegree(id) != 0){
                if(rootId != -1){
                    throw new IllegalArgumentException("More than one root found.");
                }
                else{
                    rootId = id;
                }
            }
        }
        // check for cycles and connectivity:
        KosarajuSharirSCC ss = new KosarajuSharirSCC(mHypernyms);
        if (ss.count() != mHypernyms.V()) throw new IllegalArgumentException("Cycle detected.");
    }

    private void throwExceptionIfNull(Object o){
        if (o == null) throw new IllegalArgumentException();
    }

    private void throwExceptionIfNotWordNetNoun(String noun){
        if(!isNoun(noun)) throw new IllegalArgumentException("Argument is not WordNet noun.");
    }

   // do unit testing of this class
    public static void main(String[] args){
        WordNet test = new WordNet("src/main/wordnet/synsets.txt",
                "src/main/wordnet/hypernyms.txt");
        System.out.println(test.mSynsetsIdsToNouns.size());
        System.out.println(test.mSynsetsNounsToIds.size());

        System.out.println(test.mHypernyms.V());
        System.out.println(test.mHypernyms.E());

    }

}
