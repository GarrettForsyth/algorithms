import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Queue;

import java.util.*;

public class WordNetBFS {

    private static final int INFINITY = Integer.MAX_VALUE;

    /* tracks if vertex was discovered by to vertex (=1) or
        a from vertex (=-1)
     */
    private HashMap<Integer, ArrayList<Integer>> multiMark;
    private HashMap<Integer, ArrayList<Integer>> distTo;
    private HashMap<Integer, Integer> edgeTo;
    private int commonAncestor;
    private int sap;
    private Digraph g;
    private Stack<Integer> changedIds;




    public WordNetBFS(Digraph g){
        this.g = g;
        changedIds = new Stack<Integer>();
        multiMark = new HashMap<Integer, ArrayList<Integer>>();
        distTo = new HashMap<Integer, ArrayList<Integer>>();
        edgeTo = new HashMap<Integer, Integer>();
    }

    public void createBfs(int s){
        resetStructure();
        validateVertex(s);
        bfs(g, s);
    }

    public void createBfs( Iterable<Integer> sources){
        resetStructure();
        commonAncestor = -1;
        sap = -1;
        validateVertices(sources);
        bfs(g, sources);
    }

    public void createBfs( Iterable<Integer> fromVertices, Iterable<Integer> toVertices){
        resetStructure();
        commonAncestor = -1;
        sap = -1;
        validateVertices(fromVertices);
        validateVertices(toVertices);
        bfs(g, fromVertices, toVertices);
    }

    /* Mark each vertex with the source that found it to ensure
        self loops don't trigger an exit
     */
    private void bfs(Digraph g, Iterable<Integer> sources){
        Queue<Integer> q = new Queue<Integer>();
        for(int s : sources){
            if(multiMark.containsKey(s)){
                sap = 0;
                commonAncestor = s;
                return;
            }
            multiMark.put(s, new ArrayList<Integer>());
            distTo.put(s, new ArrayList<Integer>());
            multiMark.get(s).add(s);
            distTo.get(s).add(0);
            changedIds.push(s);
            q.enqueue(s);
        }
        while(!q.isEmpty()){
           int v = q.dequeue();
           if(sap != -1 && Collections.min(distTo.get(v)) >= sap) return;
           for (int w : g.adj(v)){
               if( !multiMark.containsKey(w)){
                   edgeTo.put(w, v);
                   ArrayList<Integer> newDistance = addOneToDist(distTo.get(v));
                   distTo.put(w, newDistance);
                   multiMark.put(w, new ArrayList<Integer>(multiMark.get(v)));
//                   System.out.println("distTo["+w+"] is " + distTo.get(w));
                   changedIds.push(w);
                   q.enqueue(w);
               }
               else{
                   // only check if markers don't match
                   if(!(multiMark.get(v).containsAll(multiMark.get(w))) ||
                      !(multiMark.get(w).containsAll(multiMark.get(v)))) {
                       // get initial sizes
                       final int vSize = multiMark.get(v).size();
                       final int wSize = multiMark.get(w).size();


                       // iterate over v's mark list
                       for (int i = 0; i < vSize; i++) {
                           // for each element in v, iterate over w's list
                           for (int j = 0; j < wSize; j++) {
                               // if path is not a self cycle
                               if (multiMark.get(v).get(i) != multiMark.get(w).get(j)) {
                                   // update with new entries
                                   multiMark.get(w).add(multiMark.get(v).get(i));
                                   distTo.get(w).add(distTo.get(v).get(i) + 1);
                                   // check if new path is shortest
                                   int newDist = distTo.get(v).get(i) + distTo.get(w).get(j) + 1;
                                   if (sap == -1 || newDist < sap) {
                                       sap = newDist;
                                       commonAncestor = w;
                                   }
                               }
                           }
                       }
                   }
               }
           }
        }
    }

    private void bfs(Digraph g, Iterable<Integer> fromVertices,
                     Iterable<Integer> toVertices){

        Queue<Integer> q = new Queue<Integer>();
        addVertexToQueueAlternating(q, fromVertices, toVertices);
        if(sap != -1) return; // if from and to vertices share a value

        while(!q.isEmpty()){
            int v = q.dequeue();
            if(sap != -1 && Collections.min(distTo.get(v)) > sap) return;
            for (int w : g.adj(v)){
                if( !multiMark.containsKey(w)){
                    edgeTo.put(w, v);

                    ArrayList<Integer> newDist = addOneToDist(distTo.get(v));
                    distTo.put(w, newDist);

                    ArrayList<Integer> newMark = new ArrayList<Integer>();
                    if(multiMark.get(v).contains(1)){
                        newMark.add(1);
                    }
                    if(multiMark.get(v).contains(-1)){
                        newMark.add(-1);
                    }
                    multiMark.put(w, newMark);
                    changedIds.push(w);
                    q.enqueue(w);
                }
                else{
                    // if connecting a to path and a from path
                    if(((multiMark).get(w).contains(-1) && multiMark.get(v).contains(1)) ||
                            multiMark.get(v).contains(-1) && multiMark.get(w).contains(1)) {
                        commonAncestor = w;
                        sap = distTo.get(v).get(0) + distTo.get(w).get(0) + 1;
                        return;
                    }
                }
            }
        }
    }

    private void addVertexToQueueAlternating(Queue<Integer> q, Iterable<Integer> fromVertices,
                                             Iterable<Integer> toVertices) {
        // add in alternating order
        Iterator<Integer> it1 = fromVertices.iterator();
        Iterator<Integer> it2 = toVertices.iterator();
        int i;
        while (it1.hasNext() || it2.hasNext()) {
            if (it1.hasNext()) {
                i = it1.next();
                if (multiMark.containsKey(i)){
                    commonAncestor = i;
                    sap = 0;
                    return;
                }
                q.enqueue(i);
                ArrayList<Integer> newMark = new ArrayList<Integer>();
                newMark.add(1);
                ArrayList<Integer> newDist = new ArrayList<Integer>();
                newDist.add(0);
                multiMark.put(i, newMark);
                distTo.put(i, newDist);
                changedIds.push(i);
            }
            if (it2.hasNext()) {
                i = it2.next();
                if (multiMark.containsKey(i)){
                    commonAncestor = i;
                    sap = 0;
                    return;
                }
                q.enqueue(i);
                ArrayList<Integer> newMarker = new ArrayList<Integer>();
                ArrayList<Integer> newDist = new ArrayList<Integer>();
                newMarker.add(-1);
                newDist.add(0);
                multiMark.put(i, newMarker);
                distTo.put(i, newDist);
                changedIds.push(i);
            }
        }
    }

    private void bfs(Digraph g, int s){
        Queue<Integer> q = new Queue<Integer>();
        ArrayList<Integer> newMarker = new ArrayList<Integer>();
        ArrayList<Integer> newDist = new ArrayList<Integer>();
        newMarker.add(1);
        newDist.add(0);
        multiMark.put(s, newMarker);
        distTo.put(s, newDist);
        q.enqueue(s);
        while(!q.isEmpty()){
            int v = q.dequeue();
            for (int w :g.adj(v)){
                if( !multiMark.containsKey(w)){
                    edgeTo.put(w, v);
                    ArrayList<Integer> newDistance = addOneToDist(distTo.get(v));
                    distTo.put(w, newDistance);
                    ArrayList<Integer> newM = new ArrayList<Integer>();
                    newM.add(1);
                    multiMark.put(w, newM);
                    changedIds.push(w);
                    q.enqueue(w);
                }
            }
        }
    }

    private void validateVertex(int v){
        int V = g.V();
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex "+ v +
            " is not between 0 and " + (V-1));
    }

    private void validateVertices(Iterable<Integer> vertices){
        if (vertices == null) {
            throw new IllegalArgumentException("argument is null");
        }
        int V = g.V();
        for (int v : vertices) {
            if (v < 0 || v >= V) {
                throw new IllegalArgumentException("vertex " +
                        v + " is not between 0 and " + (V-1));
            }
        }
    }

    private void resetStructure() {
        int id;
        while (!changedIds.isEmpty()) {
            id = changedIds.pop();
            multiMark.remove(id);
            edgeTo.remove(id);
            distTo.remove(id);
        }
        commonAncestor = -1;
        sap = -1;
    }

    private ArrayList<Integer> addOneToDist(ArrayList<Integer> distTo){
        ArrayList<Integer> copy =  new ArrayList<Integer>(distTo);
        for( int i = 0; i < distTo.size(); i++){
            int oldVal = distTo.get(i);
            int newVal = oldVal +1;
            copy.set(i, newVal);
        }
        return copy;
    }

    public int distTo(int v) {
        validateVertex(v);
        int minIndex = getMinIndex(distTo.get(v));
        return distTo.get(v).get(minIndex);
    }

    private int getMinIndex(ArrayList<Integer> arr){
        int min = Integer.MAX_VALUE;
        int minIndex = -1;
        for(int i = 0; i < arr.size(); i ++){
            if ( arr.get(i) < min){
                min = arr.get(i);
                minIndex = i;
            }
        }
        return minIndex;
    }

    public int getCommonAncestor() {
        return commonAncestor;
    }

    public int getSap() {
        return sap;
    }
}
