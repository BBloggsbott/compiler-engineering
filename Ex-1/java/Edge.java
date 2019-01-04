public class Edge implements Comparable<Edge>{
    int from, to;
    String label;
    public Edge(int from, int to, String label){
        this.from = from;
        this.to = to;
        this.label = label;
    }

    public String toString(){
        return this.from + " " + this.label + " " + this.to;
    }

    public void increaseNodesBy(int diff){
        this.from += diff;
        this.to += diff;
    }

    public int compareTo(Edge e){
        return this.from - e.from;
    }
}