import java.util.Arrays;

public class test {

    public static void main (String[] args){
        int moveIndex = 4;
        int[] test = new int[] {1,2,3,4,5};
        int val = test[moveIndex];
        System.arraycopy(test, 0,test, 1, moveIndex);
        test[0] = val;
        System.out.println(Arrays.toString(test));
    }

}
