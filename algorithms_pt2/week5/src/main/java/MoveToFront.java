import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import java.util.Arrays;

public class MoveToFront {

    private static final int R = 256;

    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode(){
        char[] orderedAlphabet = initializeAlphatbet();

        while (!BinaryStdIn.isEmpty()){
            char ch = BinaryStdIn.readChar(8);
            int i = linearScan(ch, orderedAlphabet);
            BinaryStdOut.write(i,8);
            System.arraycopy(orderedAlphabet, 0, orderedAlphabet, 1, i);
            orderedAlphabet[0] = ch;
        }


        BinaryStdOut.close();

    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode(){
        char[] orderedAlphabet = initializeAlphatbet();

        while (!BinaryStdIn.isEmpty()){
            int idx = BinaryStdIn.readInt(8);
            char ch = orderedAlphabet[idx];
            BinaryStdOut.write(ch);
            int i = linearScan(ch, orderedAlphabet);
            System.arraycopy(orderedAlphabet, 0, orderedAlphabet, 1, i);
            orderedAlphabet[0] = ch;
        }

        BinaryStdOut.close();

    }

    private static int linearScan(char ch, char[] arr){
        for (int i = 0; i < arr.length; i++ ) {
            if ( arr[i] == ch ) return i;
        }
        return -1;
    }

    private static char[] initializeAlphatbet(){
        char[] alphabet = new char[R];
        for (int i = 0; i < R; i++){
            alphabet[i] = (char) i;
        }
        return alphabet;
    }


    // if args[0] is '-', apply move-to-front encoding
    // if args[0] is '+', apply move-to-front decoding
    public static void main(String[] args){
        if ( args[0].equals("-") ) encode();
        if ( args[0].equals("+") ) decode();
    }
}
