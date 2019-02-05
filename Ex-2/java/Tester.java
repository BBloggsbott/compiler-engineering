public class Tester{
	public static void main(String[] args){
		NFA nfa = RegexToNFA.generateNFA("(abc)");
		System.out.print(nfa.alphabets);
		NFA dummy = nfa;
		System.out.print(dummy.alphabets);
	}
}