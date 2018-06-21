import edu.princeton.cs.algs4.In;

import java.util.HashMap;

/* TST with R^2 branching at the root where
    R is the set of capital alphabet characters.
 */
public class DictionaryTST<Value> {

    // Hard code the R^2 prefixes in:
    private Node root;
    private HashMap<String, Node> prefixCache = new HashMap<String, Node>();
    private static final int PREFIX_CACHE_LIMIT = 20000;

    public void put( String key, Value val ){
        root = put( root, key, val, 0 );
    }

    private Node put( Node x, String key, Value val, int d ) {
        char c = key.charAt(d); // get the current char that is being checked
        if (x == null) { x = new Node(); x.c = c; } // if at a null node, add a new node
        if      ( c < x.c)               x.left  = put( x.left, key, val, d );
        else if ( c > x.c)               x.right = put( x.right, key, val, d );
        else if ( d < key.length() - 1 ) x.mid   = put( x.mid, key, val, d+1 );
        else                             x.val   = val; // overwrite value
        return x;
    }

    public boolean contains(String key) {
        return get(key) != null;
    }

    public Value get(String key) {
        Node x = get(root, key, 0 );
        if (x == null) return null;
        return x.val;
    }

    private Node get(Node x, String key, int d){
        if (x == null) return null;
        char c = key.charAt(d);
        if      ( c < x.c )              return get(x.left, key, d);
        else if ( c > x.c )              return get(x.right, key, d);
        else if ( d < key.length() - 1 ) return get(x.mid, key, d+1);
        else                             return x;
    }

    public boolean containsPrefix(String prefix){
        if (prefixCache.containsKey(prefix)) return get(prefixCache.get(prefix), prefix, prefix.length() -1) != null;
        Node x = get(root, prefix, 0);
        cachePrefix(prefix,x);
        return  get(root, prefix, 0 ) != null;
    }

    private void cachePrefix(String prefix, Node x){
       if(prefixCache.size() == PREFIX_CACHE_LIMIT){
           prefixCache = new HashMap<String, Node>();
       }
       prefixCache.put(prefix, x);
    }

    /* A node the the TST tree */
    private class Node{
        private Value val;
        private char c;
        private Node left, mid, right;
    }
}
