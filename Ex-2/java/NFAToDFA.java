import java.util.ArrayList;
import java.util.HashSet;

public class NFAToDFA{
    final static String epsilon = "epsilon";
    public static ArrayList<State> allStates = new ArrayList<State>();
    public static HashSet<Integer> allNames = new HashSet<Integer>();
    public static State epsilonClosure(NFA nfa, State state){
        HashSet<Edge> edgesFromNode;
        if(state.length() == 0){
            return state;
        }
        int name = Integer.parseInt(state.name.substring(1))+1;
        System.out.println("Debug - epsilonClosure\n"+name);
        while(allNames.contains(name)){
            name += 1;
        }
        allNames.add(name);
        State newState = new State("s"+name);
        for (int node: state.nodes){
            edgesFromNode = nfa.findEdgesFrom(node);
            for(Edge e : edgesFromNode){
                if(e.label.equalsIgnoreCase(epsilon)){
                    newState.addNode(e.to);
                }
            }
        }
        newState.extend(epsilonClosure(nfa, newState));
        newState.extend(state);
        return newState;
    }

    public static State closure(NFA nfa, State state, String label){
        HashSet<Edge> edgesFromNode;
        int name = Integer.parseInt(state.name.substring(1))+1;
        while(allNames.contains(name)){
            name+=1;
        }
        allNames.add(name);
        State newState = new State("s"+name);
        for (int i : state.nodes){
            edgesFromNode = nfa.findEdgesFrom(i);
            for( Edge j : edgesFromNode){
                if(j.label.equalsIgnoreCase(label)){
                    newState.addNode(j.to);
                }
            }
        }
        return newState;
    }

    public static DFA nfaToDFA(NFA nfa){
        System.out.println("Debug - nfaToDFA\n"+nfa);
        DFA dfa = new DFA();
        ArrayList<State> processed = new ArrayList<State>();
        State s0 = new State("s0");
        allStates.removeAll(allStates);
        allNames.removeAll(allNames);
        allStates.add(s0);
        allNames.add(0);
        s0.addNode(0);
        int k = 0;
        while(true){
            for(State i : allStates){
                System.out.println("Debug - nfaToDFA\n"+i);
                if(processed.contains(i)){
                    continue;
                }
                State temp1 = epsilonClosure(nfa, i);
                System.out.println("Debug - nfaToDFA\n"+temp1.nodes);
                System.out.println("Debug - nfaToDFA\n"+nfa.alphabets);
                for(String j : nfa.alphabets){
                    System.out.println("Debug - nfaToDFA\n"+j);
                    State temp2 = closure(nfa, temp1, j);
                    temp2 = epsilonClosure(nfa, temp2);
                    if(!allStates.contains(temp2)){
                        allStates.add(temp2);
                        dfa.addEdge(i, temp2, j);
                        System.out.println("Debug - nfaToDFA\n"+dfa);
                    }
                    else{
                        int ind = allStates.indexOf(temp2);
                        dfa.addEdge(i, allStates.get(ind), j);
                        System.out.println("Debug - nfaToDFA\n"+dfa);
                    }
                }
                processed.add(i);
            }
            if(allStates.size() == processed.size()){
                break;
            }
        }
        return dfa;
    }
}