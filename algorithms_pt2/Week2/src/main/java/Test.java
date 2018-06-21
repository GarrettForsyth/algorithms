import java.util.Arrays;

public class Test {

    public static void main(String[] args){

        int[] arr1 = {1,2,3,4,5};
        int indexToRemove = 4;
        int start = indexToRemove + 1;
        int length = arr1.length - indexToRemove - 1;
        int to = indexToRemove;
        System.arraycopy(arr1,start,arr1,to,length);
        System.out.println(Arrays.toString(arr1));
    }
}
