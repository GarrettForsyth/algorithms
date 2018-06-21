package queues;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
public class Permutation {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        
        RandomizedQueue<String> rq= new RandomizedQueue<String>();
        int k= Integer.parseInt(args[0]);
                   
            while(!StdIn.isEmpty()){
               rq.enqueue(StdIn.readString());
            }
                            
        for(int i=0; i < k; i++){
            StdOut.println(rq.dequeue());
        }
        
    }

}
