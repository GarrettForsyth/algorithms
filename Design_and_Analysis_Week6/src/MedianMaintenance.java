import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * Calculates the sum of medians from a stream of integers.
 * That is, the median is calculated each time a new integer is added,
 * and then once all integers are added, the sum of all previously
 * found medians is returned.
 * 
 * This class expects to read a text file formatted such that each line
 * contains a single integer. 
 **/
public class MedianMaintenance {

	private static PriorityQueue<Integer> lowHeap;
	private static PriorityQueue<Integer> highHeap;
	private static LinkedList<Integer> medians;

	public static int SumOfMedians(String dataFilename) {
		lowHeap = new PriorityQueue<>(Collections.reverseOrder());
		highHeap = new PriorityQueue<>();
		medians = new LinkedList<>();
    	
    	try{
    		findMediansFromStream(dataFilename);
    	}catch(IOException e){
    		e.printStackTrace();
    	}

	    return sumOfMedians();
    }
    
    private static void findMediansFromStream(String dataFileName) throws IOException{
    	BufferedReader br = null;
    	try{
    		br = createBufferedReader(dataFileName);
    	}catch(FileNotFoundException e){
    		e.printStackTrace();
    	}
		proccessData(br);
    }


	private static void proccessData(BufferedReader br) throws IOException{
        String currentLine;
		while((currentLine = br.readLine()) != null){
			int currentNumber = Integer.parseInt(currentLine);
			addToHeap(currentNumber);

			addMedian();

            /*
			Iterator highIt = highHeap.iterator();
			Iterator lowIt = lowHeap.iterator();
			System.out.print("high heap: ");
			
			while(highIt.hasNext()){
				System.out.print(highIt.next() + ", ");
			}
			System.out.print("\nlow heap: ");
			while(lowIt.hasNext()){
				System.out.print(lowIt.next() + ", ");
			}
			System.out.println("\n medians: " + medians);
			*/

		}
	}

	private static void addToHeap(int x){
        if (medians.isEmpty()){
			highHeap.add(x);
			return;
		}

		int smallestInHighHeap = highHeap.peek();
		if    (x <= smallestInHighHeap)     lowHeap.add(x);
	    else                                highHeap.add(x);
		rebalanceHeaps();
	}

	private static void rebalanceHeaps(){
		int maxAllowedSize = ((medians.size() + 1)/2) + 1;
		if      (lowHeap.size()  >= maxAllowedSize) highHeap.add(lowHeap.poll());
		else if (highHeap.size() >= maxAllowedSize) lowHeap.add(highHeap.poll());
	}

	private static void addMedian(){
		if(highHeap.size() > lowHeap.size())     medians.add(highHeap.peek());
		else                                     medians.add(lowHeap.peek());
	}

    private static BufferedReader createBufferedReader(String dataFileName) throws FileNotFoundException{
    	return new BufferedReader(new FileReader(dataFileName));
    }

    private static int sumOfMedians(){
         int sum = 0;
		 for(Integer i : medians){
			 sum += i;
		 }
		 return sum;
	}
}
