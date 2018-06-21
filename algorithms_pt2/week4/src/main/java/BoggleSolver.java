import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;

/**
 * TODO:
 *  A better solution would be to make a trie with R = 26.
 *  Then optimize by:
 *      - calculating all neighbours for each square in preprocessing
 *      - create a cache of the last (10 000 !?) or so prefix looked up
 *          and call upon the node referenced in th cache to avoid a recursive call
 *      -
 */
public class BoggleSolver
{

    private TrieST26<Integer> mDictionary;

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary){
        mDictionary = new TrieST26<Integer>();
        createDictionaryFromArray(dictionary);

    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board){
        Trie26DFS search = new Trie26DFS(board, mDictionary);
        return search.getWords();
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word){
        Integer score = mDictionary.get(word);
        return score == null ? 0 : score;
    }

    private void createDictionaryFromArray(String[] dictionary){
        for ( String word : dictionary ){
            int l = word.length();
            if      ( l < 3 ) mDictionary.put(word, 0);
            else if ( l < 5 ) mDictionary.put(word, 1);
            else if ( l < 6 ) mDictionary.put(word, 2);
            else if ( l < 7 ) mDictionary.put(word, 3);
            else if ( l < 8 ) mDictionary.put(word, 5);
            else              mDictionary.put(word, 11);
        }
    }

    public static void main(String[] args) {
        String test = "hello";
        System.out.println( test.substring(0, test.length()-1));

        In in = new In("boggle/dictionary-yawl.txt");
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard("boggle/board-qwerty.txt");
        System.out.println(solver.getAllValidWords(board).toString());

        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);

        in = new In("boggle/dictionary-algs4.txt");
        dictionary = in.readAllStrings();
        solver = new BoggleSolver(dictionary);
        board = new BoggleBoard("boggle/board-q.txt");
        System.out.println(solver.getAllValidWords(board).toString());

        score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }

        in = new In("boggle/dictionary-algs4.txt");
        dictionary = in.readAllStrings();
        solver = new BoggleSolver(dictionary);

        StdOut.println("Score = " + score);

        int numBoards1 = 100000;

        System.out.println("TESTING 4X4 BOARDS:");

        long start = System.currentTimeMillis();

        for (int i = 0; i < numBoards1; i++){
            BoggleBoard b = new BoggleBoard(4,4);
            solver.getAllValidWords(b);
        }
        long end = System.currentTimeMillis();
        System.out.println("Solved " + (numBoards1*1000)/(end-start) + " boards per second." );
        System.out.println("");
        System.out.println("TESTING 10X10 BOARDS");
        int numBoards10 = 1000;


        start = System.currentTimeMillis();
        for (int i = 0; i < numBoards10; i++){
            BoggleBoard b10 = new BoggleBoard(10,10);
            solver.getAllValidWords(b10);
        }
        end = System.currentTimeMillis();

        System.out.println("Solved " + (numBoards10*1000)/(end-start) + " boards per second." );




    }

}
