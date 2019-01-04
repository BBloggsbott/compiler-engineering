import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

public class NFA {
    ArrayList<Edge> edges;

    HashSet<Integer> nodes;

    public NFA() {
        edges = new ArrayList<Edge>();
        nodes = new HashSet<Integer>();
    }

    public void addEdge(int from, int to, String label) {
        this.edges.add(new Edge(from, to, label));
        Collections.sort(this.edges);
        this.nodes.add(from);
        this.nodes.add(to);
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

    public void extend(NFA nfa) {
        int lastNode = this.getLastNode();
        nfa.changeStartNode(lastNode);
        this.edges.addAll(nfa.edges);
        this.nodes.addAll(nfa.nodes);
        Collections.sort(this.edges);
    }

    public void absorb(NFA nfa){
        this.edges.addAll(nfa.edges);
        this.nodes.addAll(nfa.nodes);
        Collections.sort(this.edges);
    }

    public String toString(){
        String toReturn = "";
        for(Edge e : this.edges){
            toReturn = toReturn + e + "\n";
        }
        return toReturn;
    }
}
