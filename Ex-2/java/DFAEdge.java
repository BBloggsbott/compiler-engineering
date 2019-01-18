import java.util.HashSet;

public class DFAEdge  implements Comparable<DFAEdge>{
    State from, to;
    String label;
    public DFAEdge(State from, State to, String label){
        this.from = from;
        this.to = to;
        this.label = label;
    }

    public String toString(){
        return from + " " + label + " " + to;
    }

    public int compareTo(DFAEdge other){
        return this.from.length() - other.from.length();
    }

    public boolean equals(DFAEdge e){
        return e.from.equals(this.from) && e.to.equals(this.to) && e.label.equalsIgnoreCase(this.label);
    }
}