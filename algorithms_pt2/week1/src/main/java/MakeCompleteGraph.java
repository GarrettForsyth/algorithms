import java.util.Random;

public class MakeCompleteGraph {

    public static void main (String[] args){
        System.out.println("10");
        Random rand = new Random(4);

        System.out.println("40");
        for(int i=0; i < 40; i++){
            System.out.println(rand.nextInt(10) + " " + rand.nextInt(10));
        }
    }
}
