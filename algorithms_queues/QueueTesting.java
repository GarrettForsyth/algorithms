package queues;
import java.util.Iterator;

public class QueueTesting {
    
    private static void dequeTestAddFirstRemoveFirst(){
        Deque d= new Deque();
        for(int i=0; i < 10; i++){
            d.addFirst(i);
        }
        
        for(int i=0; i < 5; i++){
            System.out.println(d.removeFirst());
        }
        
        for(int i=0; i < 10; i++){
            d.addFirst(i);
        }
        
        while(!d.isEmpty()){
            System.out.println(d.removeFirst());
        }
        
        
    }
    
    public static void dequeTestAddLastRemoveFirst(){
        Deque d= new Deque();
        for(int i=0; i < 10; i++){
            d.addLast(i);
        }
        
        for(int i=0; i < 5; i++){
            System.out.println(d.removeFirst());
        }
        
        for(int i=0; i < 10; i++){
            d.addLast(i);
        }
        
        while(!d.isEmpty()){
            System.out.println(d.removeFirst());
        }
    }
    
    public static void dequeTestAddLastRemoveLast(){
        Deque d= new Deque();
        for(int i=0; i < 10; i++){
            d.addLast(i);
        }
        
        for(int i=0; i < 5; i++){
            System.out.println(d.removeLast());
        }
        
        for(int i=0; i < 10; i++){
            d.addLast(i);
        }
        
        while(!d.isEmpty()){
            System.out.println(d.removeLast());
        }
    }
    
    public static void dequeTestAddLastFirst(){
        Deque d= new Deque();
        for(int i=0; i < 10; i++){
            d.addLast(i);
        }
        
        for(int i=0; i < 5; i++){
            System.out.println(d.removeFirst());
        }
        
        for(int i=0; i < 10; i++){
            d.addLast(i);
        }
        
        while(!d.isEmpty()){
            System.out.println(d.removeFirst());
        }
    }
    
    public static void testEmptyDeque(){
        Deque d= new Deque();
        //d.addFirst(1);
        System.out.println(d.size());
        d.addLast(1);
        System.out.println(d.size());
      //  System.out.println(d.isEmpty());
      //  System.out.println(d.size());
      //  d.removeLast();
        d.removeFirst();
        System.out.println(d.size());
      //  System.out.println(d.isEmpty());
       // System.out.println(d.size());
     //   d.removeFirst();
        d.addFirst(45);
        System.out.println(d.size());
        d.addLast(54);
        System.out.println(d.size());
        d.removeLast();
    }
    
    public static void testDequeIterator(){
        Deque d= new Deque();
        d.addFirst(1);
        d.removeLast();
       // System.out.println((d.size()));
        Iterator itr= d.iterator();
        while(itr.hasNext()){
            int num= (int) itr.next();
            System.out.print(num);
        }
        
        Deque d2= new Deque();
        for(int i=0; i < 10; i++){
            d2.addLast(i);
        }
        
                
        Iterator itr2=d2.iterator();
        Iterator itr3=d2.iterator();            
        
        while(itr2.hasNext()){
            int num= (int) itr2.next();
            //System.out.println(num);
        }
        
        for(Object num : d2){
            System.out.println(num);
            for(Object num2 : d2){
                System.out.println(num2);
            }
        }
        
    }
    
    public static void RQTestEnqueue(){
        RandomizedQueue rq= new RandomizedQueue();
        System.out.println(rq.size());
        rq.enqueue(1);
        System.out.println(rq.size());
        rq.enqueue(2);
        System.out.println(rq.size());
    }
    
    public static void RQTestDequeue(){
        RandomizedQueue rq= new RandomizedQueue();
        for(int i=0; i < 10; i++){
            rq.enqueue(i);       
        } 
        for(int i=0; i < 10; i++){
            System.out.println(rq.sample());       
        } 

        for(int i=0; i < 10; i++){
           // System.out.println(rq.dequeue());       
        } 
    }
    
    public static void RQTestEmpty(){
        RandomizedQueue rq= new RandomizedQueue();
        System.out.println(rq.size());
        rq.enqueue(1);
        System.out.println(rq.size());
        rq.dequeue();
        System.out.println(rq.size());
        rq.enqueue(1);
        System.out.println(rq.size());
        rq.enqueue(2);
        System.out.println(rq.size());
       // rq.dequeue();
    }
    
    public static void RQTestIterator(){
        RandomizedQueue rq= new RandomizedQueue();
   
        for(Object item : rq){
            System.out.println("Failed..");
        }
        
        for(int i=0; i < 10; i++){
            rq.enqueue(i);
        }
        for(int i=0;i < 10; i++){
            rq.dequeue();
        }
        
        for(Object item : rq){
            System.out.println("Failed..");
        }
        
        
        
        for(Object item : rq){
          //  System.out.println(item);
            for(Object item2 : rq){
              //  System.out.println(item2);
            }
        }
    }
    public static void main(String[] args){
        //testDequeIterator();
       // dequeTestAddFirstRemoveFirst();
       // dequeTestAddLastRemoveFirst();
        //  dequeTestAddLastRemoveLast();
        //  dequeTestAddLastRemoveFirst();
       // testEmptyDeque();
       // RQTestDequeue();
       // RQTestEmpty();
      //  RQTestIterator();
    }

}
