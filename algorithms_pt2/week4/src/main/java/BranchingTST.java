/* All legal boggle words must be at least 3 characters long */
public class BranchingTST<Value> {

    private final char[] R = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    private DictionaryTST[] roots;
    private static final int NUM_ROOTS = 676+26; // 26^2

    public BranchingTST(){
        roots = new  DictionaryTST[NUM_ROOTS];
        for (int i  = 0; i < NUM_ROOTS; i++ ) {
            roots[i] = new DictionaryTST<Value>();
        }
    }

    public void put( String key, Value val){
        roots[getIndex(key)].put(key, val);
    }

    public boolean contains(String key){
        return roots[getIndex(key)].contains(key);
    }

    public Value get(String key){
        return (Value) roots[getIndex(key)].get(key);
    }

    public boolean containsPrefix(String prefix){
        return roots[getIndex(prefix)].containsPrefix(prefix);
    }

    private static int getIndex(String word){
        if (word.length() == 1) return (word.charAt(0)-65 + 676);
        String root = word.substring(0,2);
        int index = (root.charAt(0)-65)*26 + root.charAt(1)-65;
        return index;

    }

    public static void main(String[] args){
        System.out.println("hello".substring(0,2));
        System.out.println(( 'A'+0));
        System.out.println(getIndex("AA"));
        System.out.println(getIndex("ZZ"));

    }

}
