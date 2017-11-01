public class Outcast {

    private final WordNet mWordNet;
    private int[][] similarityMatrix;

    /**
     * The WordNet to be used to find the
     * relateness of words.
     * @param wordNet wordNet containing words
     *                that should be compared.
     */
    public Outcast(WordNet wordNet){
        mWordNet = wordNet;
    }

    // Assumed that arguments are valid WordNet Nouns
    public String outcast(String[] nouns){
        createSimilarityMatrix(nouns);
        for(int i = 0; i < nouns.length; i++){
            for(int j = 0; j < nouns.length; j++){
                if(similarityMatrix[i][j] == 0){
                    int dist =  mWordNet.distance(nouns[i], nouns[j]);
                    similarityMatrix[i][j] += dist;
                    similarityMatrix[j][i] += dist;
                }
            }
        }

        return nouns[getMaxDistIndex()];

    }

    private void createSimilarityMatrix(String[] nouns){
        int len = nouns.length;
        similarityMatrix = new int[len][len];
        for(int i = 0; i < len; i++){
            for(int j = 0; j < len; j++){
                similarityMatrix[i][j] = 0;
            }
        }
    }

    private int getMaxDistIndex(){
        int len = similarityMatrix.length;
        int[] dists = new int[len];
        int max = 0;
        int maxIndex = 0;

        for(int i = 0; i < len; i++) {
            for(int j = 0; j < len; j++){
                dists[i] += similarityMatrix[i][j];
            }
            if (dists[i] > max){
                max = dists[i];
                maxIndex = i;
            }
        }
        return  maxIndex;
    }


    public static void main(String[] args){
        WordNet wn = new WordNet("src/main/wordnet/synsets.txt",
                "src/main/wordnet/hypernyms.txt");

        Outcast o = new Outcast(wn);
        System.out.println(o.outcast(new String[]{"horse", "zebra", "cat", "bear", "table"}));
        System.out.println(o.outcast(new String[]{"coffee", "water", "bed", "orange_juice", "milk","apple_juice", "tea" }));
        System.out.println(o.outcast(new String[]{"potato", "apple", "pear", "peach", "banana", "lime","lemon",
                "blueberry", "strawberry", "mango", "watermelon"}));
    }


}
