package queues;


import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item>{
    /*
     * Instance variables:
     */
    private Node<Item> first=null;
    private Node<Item> last=null;
    private int size;
    /*
     * construct an empty deque
     */
    public Deque(){size=0;}
    /*
     * Private inner class representing a 
     * node in the LinkedList.
     * Contains data T, and a pointer to the
     * next node.
     */
    @SuppressWarnings("hiding")
    private class Node<Item>{
        private Item item;
        private Node<Item> next;
        private Node<Item> prev;
    }
    
    /*
     *  is the deque empty?
     */
    public boolean isEmpty(){return size==0; }
    /*
     * return the number of items on the deque
     */
    public int size(){return size; }
    /*
     * add the item to the front
     */
    public void addFirst(Item item){
        if(item==null){
            throw new NullPointerException("Cannot add null item.");
        }
        //Set new node to point to oldFirst
        Node<Item> oldFirst= first;
        first= new Node<Item>();
        first.item= item;
        first.next=oldFirst;
        first.prev=null;
        //set oldFirst to point to new Node
        if(oldFirst!=null){ oldFirst.prev= first;}
        //special case if first element:
        if(size==0){
            last= first;
        }
        size++;
    }
    /*
     * add the item to the end
     */
    public void addLast(Item item){
        if(item==null){
            throw new NullPointerException("Cannot add null item.");
        }
        //set new node to point to oldLast
        Node<Item> oldLast= last;
        last= new Node<Item>();
        last.item=item;
        last.next=null;
        last.prev= oldLast;
        //set oldLast to point to new Node
        if(oldLast!=null){ oldLast.next= last;}
      //special case if first element:
        if(size==0){
            first= last;
        }
        size++;
    }
    /*
     * remove and return the item from the front
     */
    public Item removeFirst(){       
        if(this.isEmpty()){
            throw new NoSuchElementException("Cannot remove from empty deque.");
        }
        Node<Item> temp= first;
        first=null;
        
        Item item= temp.item;     
        first=temp.next;
        if(first!=null){
            first.prev=null; //stop pointing at removed item
        }
        
        temp=null;
        
        size--;
        if(size==0){
            first= null;
            last= null;
        }
        return item; 
    }
    /*
     *  remove and return the item from the end
     */
    public Item removeLast(){   
        if(this.isEmpty()){
            throw new NoSuchElementException("Cannot remove from empty deque.");
        }
        Node<Item> temp=last;
        last=null; //avoid loitering
        
        Item item= temp.item;
        last=temp.prev;
        if(last!=null){
            last.next=null;
        }
        temp=null;
        
        size--;
        if(size==0){
            first= null;
            last= null;
        }
        return item;
    }
    /*
     *  return an iterator over items in order from front to end
     */
    public Iterator<Item> iterator(){
        return new DequeIterator();
    }
    /*
     * private iterator class from front to back
     */
    private class DequeIterator implements Iterator<Item>{
        Node<Item> curr= first;
        Node<Item> temp;
        public boolean hasNext(){ return curr!=null;}
        public Item next(){
            if(!hasNext()){
                throw new NoSuchElementException("No more elements to iterate over.");
            }
            temp=curr;
            curr= curr.next;
            return temp.item;
        }
        public void remove(){
            throw new UnsupportedOperationException("remove() is not supported.");
        }
    }
    /*
     * unit testing (optional)
     */
    public static void main(String[]args){
      //  Deque deque= new Deque();
        
        
       // deque.addFirst(1);
        //deque.addFirst(2);
       // deque.addFirst(3);
        
       // System.out.println(deque.removeFirst());
       // System.out.println(deque.removeFirst());
       /// System.out.println(deque.removeFirst());
        
       // deque.addFirst(1);
        //deque.addFirst(2);
        //deque.addFirst(3);
        
       // Iterator itr= deque.iterator();
       // while(itr.hasNext()){
         //   int num= (int) itr.next();
        //    System.out.println(num);
        //}
        
        
       // System.out.println(deque.removeLast());
       // System.out.println(deque.removeLast());
       // System.out.println(deque.removeLast());
        
    }

}
