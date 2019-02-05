import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

public class NFA {
    ArrayList<Edge> edges;
    String epsilon = "epsilon";
    HashSet<Integer> nodes;
    public HashSet<String> alphabets;

    public NFA() {
        edges = new ArrayList<Edge>();
        nodes = new HashSet<Integer>();
        alphabets = new HashSet<String>();
    }

    public NFA(NFA nfa){
        this.edges = nfa.edges;
        this.nodes = nfa.nodes;
        this.alphabets = nfa.alphabets;
    }

    public void addEdge(int from, int to, String label) {
        this.edges.add(new Edge(from, to, label));
        Collections.sort(this.edges);
        this.nodes.add(from);
        this.nodes.add(to);
        System.out.println("Debug - addEdge - NFA\n"+this.alphabets);
        if(epsilon.compareTo(label) != 0){
            this.alphabets.add(label);
        }
        System.out.println("Debug - addEdge - NFA\n"+alphabets);
    }

    public int getLastNode() {
        if(this.nodes.size() == 0){
            return 0;
        }
        return Collections.max(this.nodes);
    }

    public void changeStartNode(int val){
        if(this.nodes.size() == 0){
            return;
        }
        int diff;
        diff = val - Collections.min(nodes);
        for (Edge e : this.edges){
            e.increaseNodesBy(diff);
        }
        HashSet<Integer> temp = new HashSet<Integer>();
        for(Integer i : nodes){
            temp.add(i+diff);
        }
        nodes = new HashSet<Integer>(temp);
        Collections.sort(this.edges);
    }

    public void extend(NFA fa) {
        int lastNode = this.getLastNode();
        fa.changeStartNode(lastNode);
        this.edges.addAll(fa.edges);
        this.nodes.addAll(fa.nodes);
        this.alphabets.addAll(fa.alphabets);
        Collections.sort(this.edges);
    }

    public void absorb(NFA fa){
        this.edges.addAll(fa.edges);
        this.nodes.addAll(fa.nodes);
        Collections.sort(this.edges);
    }

    public HashSet<Edge> findEdgesFrom(int node){
        HashSet<Edge> edgesFromNode = new HashSet<Edge>();
        for (Edge edge : this.edges){
            if(edge.from == node){
                edgesFromNode.add(edge);
            }
        } 
        return edgesFromNode;
    }

    public HashSet<String> getAlphabet(){
        return this.alphabets;
    }

    public String toString(){
        String toReturn = "";
        for(Edge e : this.edges){
            toReturn = toReturn + e + "\n";
        }
        return toReturn;
    }
}
