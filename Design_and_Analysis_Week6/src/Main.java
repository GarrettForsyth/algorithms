public class Main {

	public static void main(String[] args) {
		int sum = MedianMaintenance.SumOfMedians("./Median.txt");
		System.out.println("Sum of medians is " + sum%10000);
	}
}
