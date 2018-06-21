import java.util.Map.Entry;

/**
 * Project    : 
 * File       : 
 * Description: 
 * Date       : Mon 05 Jun 2017 12:08:14 PM EDT
 * @author    : Garrett Forsyth 
 **/
public class MyLLHashMap<Key, Value> {

	private int INITIAL_ARRAY_SIZE = 199;
    private Entry<Key, Value>[] bucketArray;
	private int numberOfEntries = 0;

	/* Entry objects have no meaning outside this class so it nested.
	 * It should not ave access to the outside class' member functions,
	 * so it is declared static.
	 **/
   private static  class Entry<Key, Value> {
    	private Key key;
	    private Value val;
	    private Entry<Key,Value> next;
   
		public Entry(Key key, Value val) {
			this.key = key;
			this.val = val;
			this.next = null;	
		}

		public Entry(Key key, Value val, Entry<Key,Value> next) {
			this.key = key;
			this.val = val;
			this.next = next;	
		}
   }	

	public MyLLHashMap(){
		bucketArray = new Entry[INITIAL_ARRAY_SIZE];
	}
	
	public MyLLHashMap(int initialSize){
		bucketArray = new Entry[initialSize];
	}
    
	private void resizeBucketArray(Entry<Key,Value> arr, int newSize){
		Entry<Key,Value>[] copy = new Entry[newSize];
		/* must copy over whole array since elements are inserted randomly
		 * throughout */
		for(int i = 0; i < bucketArray.length; i++){
			copy[i] = bucketArray[i];
		}
		bucketArray = copy;
	}
	
}

