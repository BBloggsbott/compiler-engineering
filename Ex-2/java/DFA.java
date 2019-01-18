import java.util.ArrayList;

public class DFA{
    ArrayList<DFAEdge> edges;

    public DFA(){
        edges = new ArrayList<DFAEdge>();
    }

    public void addEdge(State from, State to, String label){
        this.edges.add(new DFAEdge(from, to, label));
    }

    public String toString(){
        String toReturn = "";
        for(DFAEdge e : this.edges){
            toReturn = toReturn + e + "\n";
        }
        return toReturn;
    }
}