import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashSet;

public class TwoSumProblem {

	private HashSet<Long> allNumbers = new HashSet<>();
	private HashSet<Long> foundTargets = new HashSet<>();
	private int numberOfSolutions = 0;
	private static final long LOWER_BOUND = -10000;
	private static final long  UPPER_BOUND = 10000;

	public TwoSumProblem(String dataFilename) {
		
		try{
			findSumPairsInRange(dataFilename);
		}catch(IOException e){
			e.printStackTrace();
		}
		System.out.println("The number of solutions found: " + numberOfSolutions);
	}

	private void findSumPairsInRange(String dataFileName) throws IOException{
        String currentLine;
		BufferedReader br = null;
		try{
		    br = createBufferedReader(dataFileName);
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}

		while((currentLine = br.readLine()) != null){
		    long currentNumber = Long.parseLong(currentLine); 
			checkForSumPairs(currentNumber);
			allNumbers.add(currentNumber);
		}
	}
	
	private void checkForSumPairs(long num){
		for(long i = LOWER_BOUND; i <= UPPER_BOUND; i++){ 
            if(!foundTargets.contains(i) && allNumbers.contains(i - num)){
				numberOfSolutions++;
				foundTargets.add(i);
			}
		}
	}

	private BufferedReader createBufferedReader(String dataFileName) throws FileNotFoundException{
	    return new BufferedReader(new FileReader(dataFileName));
	}
}
