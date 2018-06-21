import java.util.Arrays;
import java.util.Random;

public class CircularSuffixArray {

    private int[] index;
    private String sequence;

    // circular suffix array of s
    public CircularSuffixArray(String s){
        if (s == null) throw new IllegalArgumentException();

        sequence = s;
        Pair[] sortedSuffixes = new Pair[s.length()]; // pair each value with it's index
        index = new int[s.length()];

        for (int i = 0; i < s.length(); i++) {
           sortedSuffixes[i] = new Pair(i, s.charAt(i));
        }

        if (!isAllSameCharacter(s)) Arrays.sort(sortedSuffixes);

        for (int i = 0; i < s.length(); i++) {
            index[i] = sortedSuffixes[i].index;
        }

    }


    // length of s
    public int length(){
        return sequence.length();
    }

    // returns index of ith sorted suffix
    public int index(int i){
        if ( i < 0 || i >= index.length) throw new IllegalArgumentException();
        return index[i];
    }

    // check corner case
    private boolean isAllSameCharacter(String s){
        if (s.length() == 0) return true;
        char c = s.charAt(0);
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) != c) return false;
        }
        return true;
    }

    // Helper class to bind index with value
    private class Pair implements Comparable<Pair> {
        private final int index;
        private final char value;

        public Pair(int index, char value) {
            this.index = index;
            this.value = value;
        }

        // if there is a tie, try looking ahead in the string to break
        public int compareTo(Pair other) {

            int thisValue = this.value;
            int thisIndex = this.index;
            int otherValue = other.value;
            int otherIndex = other.index;
            boolean flag = false;

            while (thisValue == otherValue) {
               if (thisIndex  == sequence.length()-1) thisIndex  = -1;
               if (otherIndex == sequence.length()-1){
                   if (flag) break;
                   otherIndex = -1;
                   flag = true;
               }

               thisValue =  sequence.charAt(thisIndex+1);
               otherValue = sequence.charAt(otherIndex+1);
               thisIndex++;
               otherIndex++;
            }

            return thisValue - otherValue;
        }
    }


    // unit testing of the methods (optional)
    public static void main(String[] args){
        int numRuns = 1;
        for (int i = 0; i < numRuns; i++) {
            String randString = randomBinaryString(10);
            System.out.println("Testing: " + randString);
            CircularSuffixArray csa = new CircularSuffixArray("couscous");
            System.out.println(csa.length());
            System.out.println(Arrays.toString(csa.index));
            System.out.println("");

        }
    }


    private static String randomBinaryString(int len){
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();
        for (int i = 0; i < len; i ++){
            sb.append(rand.nextInt(2));
        }
        return sb.toString();
    }

}
