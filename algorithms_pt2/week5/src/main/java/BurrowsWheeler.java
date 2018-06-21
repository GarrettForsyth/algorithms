import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import java.util.Arrays;

public class BurrowsWheeler {

    private static final int R = 256; // extended ASCII

    private int first;
    private String input;
    private char[] t;

    // apply Burrows-Wheeler encoding, reading from standard input and writing to standard output
    public static void encode(){
        String input = BinaryStdIn.readString();

        CircularSuffixArray csa = new CircularSuffixArray(input);

        int first = -1;
        char[] t = new char[csa.length()];
        for (int i = 0; i < csa.length(); i++){
           int index = csa.index(i) - 1;
           if (index < 0) { first = i; index = csa.length()-1; }
           t[i] = input.charAt(index);
        }

        BinaryStdOut.write(first);
        for (char c : t) {
            BinaryStdOut.write(c);
        }

        BinaryStdOut.close();

    }

    // apply Burrows-Wheeler decoding, reading from standard input and writing to standard output
    public static void decode() {


        int first = BinaryStdIn.readInt();

        String input = BinaryStdIn.readString();
        int N  = input.length();
        char[] sortedInput = new char[N];

        int[] count = new int[R+1];

        for (int i = 0; i < N; i++) {
            count[input.charAt(i)+1]++;
        }

        for (int r = 0; r < R; r++) {
            count[r+1] += count[r];
        }

        for (int i = 0; i < N; i++){
            sortedInput[count[input.charAt(i)]++] = input.charAt(i);
        }

        int[] next = new int[N];

        int zeroCount = 0; // tracks special case of where to put the first character of the alphabet
        for ( int i = 0; i < N; i++ ) {
            char ch = input.charAt(i);
            // special case for first character
            if (ch == (char)0) next[zeroCount++] = i;
            else               next[count[(ch-1)]++] = i;
        }

        int z = first ;
        BinaryStdOut.write(sortedInput[z]);
        for (int i = 0; i < N-1; i++ ) {
            BinaryStdOut.write(sortedInput[next[z]]);
            z = next[z];
        }

        BinaryStdOut.close();


    }

    // if args[0] is '-', apply Burrows-Wheeler encoding
    // if args[0] is '+', apply Burrows-Wheeler decoding
    public static void main(String[] args) {
        if ( args[0].equals("-") ) encode();
        if ( args[0].equals("+") ) decode();

    }
}
