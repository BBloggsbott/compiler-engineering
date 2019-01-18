import java.util.HashSet;
import java.util.ArrayList;

public class State implements Comparable<State>{
    public HashSet<Integer> nodes;
    public String name;
    public State(String name){
        this.name = name;
        nodes = new HashSet<Integer>();
    }

    public void addNode(int node){
        nodes.add(node);
    }

    public void addNodes(int[] nodeList){
        for(int i : nodeList){
            nodes.add(i);
        }
    }

    public void extend(State state){
        this.nodes.addAll((state.nodes));
    }

    public int compareTo(State state){
        int nThis = Integer.parseInt(this.name.substring(1));
        int nOther = Integer.parseInt(state.name.substring(1));
        return nThis - nOther;
    }

    public String toString(){
        return this.name;
    }

    public int length(){
        return this.nodes.size();
    }

    public boolean equals(State s){
        if(s.length() == this.length()){
            for (int node : s.nodes){
                if(!this.nodes.contains(node)){
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}