package main;

import java.util.Arrays;
import java.util.HashSet;



public class Main {
    
    /*
     *  Input :  a string
     *  Return : true if string has all unique characters
     */

    public static boolean isUniqueCharacters( String str ) throws Exception {
        boolean[] char_set = new boolean[256];
        for (int i =0; i < str.length(); i ++){
            int val = str.charAt(i);
            if (char_set[val]) return false;
            char_set[val] = true;
        }
        return true;
    }
    
    /*
     *  “abcd ” 
     *
     */

    public static String reverseCString( String input) {
        char[] inputChars = input.toCharArray();

        for (int i = 0 ; i < inputChars.length/2 ; i ++) {
            swap(inputChars, i, inputChars.length - i -2);
    }
        
        return new String(inputChars);
    }

    //helper function that swaps to elements in a character array
    private static void swap ( char[] input ,int i , int j ) {
        char temp = input[i];
        input[i] = input[j];
        input[j] = temp;
    }

	public static void main(String[] args) throws Exception {
		

	    System.out.println( reverseCString("abcdX"));
	    
		
	}
}