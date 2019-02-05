import java.util.Scanner;

public class RegexToDFA {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter regex (Enclose entire regex in brackets): ");
        String regex = sc.next();
        NFA nfa = RegexToNFA.generateNFA(regex);
        System.out.print("--\nNFA\n--\n");
        System.out.println("Debug - RegexToDFA - Alphabet\n" + nfa.getAlphabet());
        DFA dfa = NFAToDFA.nfaToDFA(nfa);
        System.out.println("--\nDFA\n--");
        System.out.println(dfa);
    }
}