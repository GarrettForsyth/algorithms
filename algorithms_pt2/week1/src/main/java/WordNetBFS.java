import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Queue;

import java.util.*;

public class WordNetBFS {

    private static final int INFINITY = Integer.MAX_VALUE;

    /* tracks if vertex was discovered by to vertex (=1) or
        a from vertex (=-1)
     */
    private HashMap<Integer, LinkedList<Integer>> multiMark;
    private HashMap<Integer, LinkedList<Integer>> distTo;
    private HashMap<Integer, Integer> edgeTo;
    private int commonAncestor;
    private int sap;
    private Digraph g;
    private Stack<Integer> changedIds;




    public WordNetBFS(Digraph g){
        this.g = g;
        changedIds = new Stack<Integer>();
        multiMark = new HashMap<Integer, LinkedList<Integer>>();
        distTo = new HashMap<Integer, LinkedList<Integer>>();
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
            multiMark.put(s,s);
            distTo.put(s, 0);
            changedIds.push(s);
            q.enqueue(s);
        }
        while(!q.isEmpty()){
           int v = q.dequeue();
           for (int w : g.adj(v)){
               if(sap != -1 && distTo.get(v) > sap) return;
               if( !multiMark.containsKey(w)){
                   edgeTo.put(w, v);
                   distTo.put(w, ((distTo.get(v)) +1));
                   multiMark.put(w,multiMark.get(v));
//                   System.out.println("distTo["+w+"] is " + distTo.get(w));
                   changedIds.push(w);
                   q.enqueue(w);
               }
               else{
                   if(multiMark.get(w) != multiMark.get(v)){
                       if(sap == -1 || (distTo(v) + distTo(w) +1) < sap) {
                           commonAncestor = w;
                           sap = distTo.get(v) + distTo.get(w) + 1; }
                           multiMark.put(w, distTo.get(v) + 1);
                           multiMark.put(w, multiMark.get(v));
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
            for (int w : g.adj(v)){
                if( !multiMark.containsKey(w)){
                    edgeTo.put(w, v);
                    distTo.put(w, distTo.get(v) + 1);
                    multiMark.put(w, multiMark.get(v) == 1 ? 1 : -1);
                    changedIds.push(w);
                    q.enqueue(w);
                }
                else{
                    // if connecting a to path and a from path
                    if((multiMark.get(w) == -1 && multiMark.get(v) == 1) ||
                            multiMark.get(v) == -1 && multiMark.get(w) == 1) {
                        commonAncestor = w;
                        sap = distTo.get(v) + distTo.get(w) + 1;
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
                multiMark.put(i, 1);
                distTo.put(i, 0);
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
                multiMark.put(i, -1);
                distTo.put(i, 0);
                changedIds.push(i);
            }
        }
    }

    private void bfs(Digraph g, int s){
        Queue<Integer> q = new Queue<Integer>();
        multiMark.put(s, 1);
        distTo.put(s, 0);
        q.enqueue(s);
        while(!q.isEmpty()){
            int v = q.dequeue();
            for (int w :g.adj(v)){
                if( !multiMark.containsKey(w)){
                    edgeTo.put(w, v);
                    distTo.put(w, distTo.get(v) + 1);
                    multiMark.put(w, 1);
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

    public int distTo(int v) {
        validateVertex(v);
        return distTo.get(v);
    }

    public int getCommonAncestor() {
        return commonAncestor;
    }

    public int getSap() {
        return sap;
    }
}
