import java.util.Scanner;

class RegexToNFA{
    public static int addStar(NFA nfa, String prevChar, int node){
        nfa.addEdge(node, node+1, "epsilon");
        node+=1;
        nfa.addEdge(node, node+1, prevChar);
        node+=1;
        nfa.addEdge(node, node-1, "epsilon");
        nfa.addEdge(node, node+1, "epsilon");
        node+=1;
        nfa.addEdge(node-3, node, "epsilon");
        return node+1;
    }

    public static NFA addStarToNFA(NFA nfa){
        nfa.changeStartNode(1);
        int lastNode = nfa.getLastNode();
        nfa.addEdge(0, 1, "epsilon");
        nfa.addEdge(lastNode, lastNode+1, "epsilon");
        nfa.addEdge(lastNode, 1, "epsilon");
        nfa.addEdge(0, lastNode+1, "epsilon");
        return nfa;
    }

    public static NFA buildNFA(String exp){
        NFA nfa = new NFA();
        int node = 0;
        boolean or_flag = false;
        char cur_char;
        
        for(int i = 0; i < exp.length(); i++){
            cur_char = exp.charAt(i);
            if(i != exp.length()-1){
                if(exp.charAt(i+1) == '*'){
                    node = addStar(nfa, cur_char+"", node);
                    continue;
                }
            }
            if(cur_char == '*'){
                continue;
            }
            if(cur_char != '|' && !or_flag){
                nfa.addEdge(node, node+1, cur_char+"");
                node+=1;
            }
            else if(cur_char != '|' && or_flag){
                nfa.addEdge(node-1, node, cur_char+"");
                or_flag = false;
            }
            else if(cur_char == '|'){
                or_flag = true;
            }
        }
        return nfa;
    }

    public static NFA generateNFA(String exp){
        String subExp = "";
        NFA curNFA = new NFA();
        NFA tempNFA;
        for(int i = 0; i < exp.length(); i++){
            char cur_char = exp.charAt(i);
            if(cur_char=='(' || cur_char==')'){
                tempNFA = buildNFA(subExp);
                if(i!=exp.length()-1 && cur_char==')'){
                    if(exp.charAt(i+1)=='*'){
                        tempNFA = addStarToNFA(tempNFA);
                    }
                }
                if(!subExp.equalsIgnoreCase("")){
                    curNFA.extend(tempNFA);
                    subExp = "";
                }
                continue;
            }
            subExp = subExp+cur_char;
        }
        System.out.print(curNFA.alphabets);
        return curNFA;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String exp;
        System.out.print("Enter regex (Enclose entire regex in brackets): ");
        exp = sc.next();
        System.out.print(generateNFA(exp));
    }
}